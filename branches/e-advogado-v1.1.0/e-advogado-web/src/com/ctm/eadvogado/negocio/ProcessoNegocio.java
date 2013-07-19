/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import br.jus.cnj.pje.v1.TipoProcessoJudicial;

import com.ctm.eadvogado.androidpublisher.Utils;
import com.ctm.eadvogado.dao.LancamentoDao;
import com.ctm.eadvogado.dao.ProcessoDao;
import com.ctm.eadvogado.dao.TribunalDao;
import com.ctm.eadvogado.dao.UsuarioDao;
import com.ctm.eadvogado.exception.DAOException;
import com.ctm.eadvogado.exception.NegocioException;
import com.ctm.eadvogado.interceptors.Transacional;
import com.ctm.eadvogado.model.Lancamento;
import com.ctm.eadvogado.model.Processo;
import com.ctm.eadvogado.model.TipoConta;
import com.ctm.eadvogado.model.TipoJuizo;
import com.ctm.eadvogado.model.TipoLancamento;
import com.ctm.eadvogado.model.Tribunal;
import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.util.CacheUtils;
import com.ctm.eadvogado.util.PJeServiceUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.ServiceUnavailableException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.api.services.androidpublisher.AndroidPublisher;
import com.google.api.services.androidpublisher.AndroidPublisher.Purchases.Get;
import com.google.api.services.androidpublisher.model.SubscriptionPurchase;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * @author Cleber
 * 
 */
@Named
public class ProcessoNegocio extends BaseNegocio<Processo, ProcessoDao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private LancamentoDao lancamentoDao;
	@Inject
	private UsuarioDao usuarioDao;
	@Inject
	private TribunalDao tribunalDao;

	@Override
	@Inject
	public void setDao(ProcessoDao dao) {
		super.setDao(dao);
	}

	public Processo findByNpuTribunalTipoJuizo(String npu, Long idTribunal,
			TipoJuizo tipoJuizo) throws PersistenceException {
		return getDao().findByNpuTribunalTipoJuizo(npu, idTribunal, tipoJuizo);
	}

	/**
	 * Busca os processos do Usuário.
	 * 
	 * @param usuario
	 * @return
	 * @throws PersistenceException
	 */
	public List<Processo> findByUsuario(Usuario usuario)
			throws PersistenceException {
		return getDao().findByUsuario(usuario);
	}

	@Override
	@Transacional
	public Processo insert(Processo entity) throws PersistenceException {
		Processo p = findByNpuTribunalTipoJuizo(entity.getNpu(), entity
				.getTribunal().getId(), entity.getTipoJuizo());
		if (p != null) {
			throw new DAOException("processo.erro.npu.jaExiste");
		}
		return super.insert(entity);
	}

	/**
	 * @param idProcesso
	 * @param email
	 * @throws NegocioException
	 * @throws PersistenceException
	 */
	@Transacional
	public void associarProcessoAoUsuario(Long idProcesso, String email)
			throws NegocioException, PersistenceException {
		Usuario usuario = usuarioDao.findByEmail(email);
		Long saldoLancamentos = lancamentoDao.getSaldoLancamentos(usuario);
		if (saldoLancamentos.longValue() > 0
				|| usuario.getTipoConta().equals(TipoConta.PREMIUM)) {
			if (usuario.getTipoConta().equals(TipoConta.PREMIUM)) {
				if (usuario.getDataExpiracao() != null
						&& usuario.getDataExpiracao().before(new Date())) {
					HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
					JsonFactory JSON_FACTORY = new JacksonFactory();
					String CLIENT_ID = "935757146253.apps.googleusercontent.com";
					String CLIENT_SECRET = "_IU7C5cPHKTlEAc6RgDjXa0O";
					
					GoogleCredential credential = new GoogleCredential.Builder()
							.setTransport(HTTP_TRANSPORT)
							.setJsonFactory(JSON_FACTORY)
							.setServiceAccountId("935757146253@developer.gserviceaccount.com")
							.setServiceAccountScopes(
									Arrays.asList("https://www.googleapis.com/auth/androidpublisher"))
							.setClientSecrets(CLIENT_ID, CLIENT_SECRET).build();

					AndroidPublisher publisher = new AndroidPublisher.Builder(
							HTTP_TRANSPORT, JSON_FACTORY, credential)
							.setApplicationName("e-advogado-web").build();

					AndroidPublisher.Purchases purchases = publisher
							.purchases();
					Get get = null;
					try {
						get = purchases.get("com.ctm.eadvogado", "conta_premium", usuario.getTokenContaPremium());
					} catch (IOException e) {
						logger.log(Level.SEVERE, "Erro", e);
					}
					try {
						SubscriptionPurchase subscripcion = get.execute();
						System.out.println(subscripcion);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			Lancamento lancamento = new Lancamento();
			lancamento.setData(new Date());
			lancamento.setQuantidade(1);
			lancamento.setTipo(TipoLancamento.DEBITO);
			lancamento.setUsuario(usuario.getKey());
			lancamentoDao.insert(lancamento);
			Processo processo = findByID(idProcesso);
			usuario.getProcessos().add(processo.getKey());
			usuarioDao.update(usuario);
		} else {
			throw new NegocioException("erro.negocio.saldoInsuficiente");
		}
	}

	/**
	 * Consulta um processo
	 * 
	 * @param npu
	 * @param idTribunal
	 * @param tipoJuizo
	 * @param ignorarCache
	 *            true - ignora a cache de processos e busca diretamente no
	 *            serviço. Caso o serviço esteja indisponível, busca na cache e
	 *            bd.
	 * @param incluirDocumentos
	 *            true - inclui os documentos na busca do processo no serviço.
	 * @return
	 * @throws NotFoundException
	 * @throws UnauthorizedException
	 * @throws ServiceUnavailableException
	 */
	@Transacional
	public Processo consultarProcesso(String npu, Long idTribunal,
			TipoJuizo tipoJuizo, boolean ignorarCache, boolean incluirDocumentos)
			throws NotFoundException, UnauthorizedException,
			ServiceUnavailableException {
		boolean falhaNoServico = false;
		Processo processo = null;
		if (!ignorarCache) {
			processo = CacheUtils.getInstance().getProcessoFromCache(npu,
					tipoJuizo, idTribunal);
		}
		if (processo == null) {
			processo = findByNpuTribunalTipoJuizo(npu, idTribunal, tipoJuizo);
		}
		if (processo == null || (processo != null && ignorarCache)) {
			TipoProcessoJudicial processoJudicial = consultarProcessoJudicial(
					npu, idTribunal, tipoJuizo, ignorarCache, incluirDocumentos);
			if (processoJudicial != null) {
				if (processoJudicial.getDadosBasicos().getNivelSigilo() == 0) {
					if (processo != null) {
						processo.setProcessoJudicial(processoJudicial);
						getDao().update(processo);
					} else {
						processo = new Processo();
						processo.setNpu(npu);
						processo.setTipoJuizo(tipoJuizo);
						processo.setTribunal(KeyFactory.createKey(
								Tribunal.class.getSimpleName(), idTribunal));
						processo.setProcessoJudicial(processoJudicial);
						getDao().insert(processo);
					}
				} else {
					throw new UnauthorizedException(
							"Desculpe! O processo informado não pode ser acessado!");
				}
			} else {
				if (ignorarCache) {
					processo = CacheUtils.getInstance().getProcessoFromCache(
							npu, tipoJuizo, idTribunal);
					if (processo == null) {
						processo = findByNpuTribunalTipoJuizo(npu, idTribunal,
								tipoJuizo);
					}
				}
			}
		}
		if (processo != null) {
			try {
				CacheUtils.getInstance().putProcessoToCache(processo);
			} catch (Exception e) {
				logger.log(Level.SEVERE, String.format(
						"Falha ao colocar processo %s, %s, %s na cache", npu,
						idTribunal, tipoJuizo.name()), e);
			}
		} else {
			if (falhaNoServico) {
				throw new ServiceUnavailableException(
						"Desculpe! Serviço de consulta processual temporáriamente indisponível neste tribunal.");
			} else {
				throw new NotFoundException(
						"Desculpe! O Processo informado não foi localizado.");
			}
		}
		return processo;
	}

	/**
	 * Consulta um {@link TipoProcessoJudicial} no serviço do tribunal.
	 * 
	 * @param npu
	 * @param idTribunal
	 * @param tipoJuizo
	 * @param ignorarCache
	 * @param incluirDocumentos
	 * @return
	 * @throws NotFoundException
	 * @throws UnauthorizedException
	 * @throws ServiceUnavailableException
	 */
	public TipoProcessoJudicial consultarProcessoJudicial(String npu,
			Long idTribunal, TipoJuizo tipoJuizo, boolean ignorarCache,
			boolean incluirDocumentos) throws NotFoundException,
			UnauthorizedException, ServiceUnavailableException {
		TipoProcessoJudicial processoJudicial = null;
		Tribunal tribunal = tribunalDao.findByID(idTribunal);
		String endpoint = null;
		switch (tipoJuizo) {
		case PRIMEIRO_GRAU:
			endpoint = tribunal.getPje1gEndpoint();
			break;
		case SEGUNDO_GRAU:
			endpoint = tribunal.getPje2gEndpoint();
			break;
		}
		if (endpoint != null) {
			try {
				processoJudicial = PJeServiceUtil.consultarProcessoJudicial(
						endpoint, npu, null, Boolean.TRUE, incluirDocumentos,
						null);
			} catch (Exception e) {
				logger.log(Level.SEVERE, String.format(
						"Falha ao consultar processo %s, %s, %s no servico",
						npu, idTribunal, tipoJuizo.name()), e);
			}
		} else {
			throw new NotFoundException(
					"Serviço não disponivel para o Tipo de Juízo informado.");
		}
		return processoJudicial;
	}

	
	public static void main(String[] args) {
		try {
			AndroidPublisher androidPublisher = Utils.loadAndroidPublisherClient();
			SubscriptionPurchase execute = androidPublisher.purchases().get("com.ctm.eadvogado", "conta_premium", null).execute();
			System.out.println(execute);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
