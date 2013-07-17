/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import br.jus.cnj.pje.v1.TipoProcessoJudicial;

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
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.ServiceUnavailableException;
import com.google.api.server.spi.response.UnauthorizedException;
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
	 * @param usuario
	 * @return
	 * @throws PersistenceException
	 */
	public List<Processo> findByUsuario(Usuario usuario) throws PersistenceException{
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
	public void associarProcessoAoUsuario(Long idProcesso, String email) throws NegocioException, PersistenceException{
		Usuario usuario = usuarioDao.findByEmail(email);
		Long saldoLancamentos = lancamentoDao.getSaldoLancamentos(usuario);
		if (saldoLancamentos.longValue() > 0 || 
					usuario.getTipoConta().equals(TipoConta.PREMIUM)) {
			if (usuario.getTipoConta().equals(TipoConta.PREMIUM)) {
				if (usuario.getDataExpiracao() != null && usuario.getDataExpiracao().before(new Date())) {
					//TODO validar com o google
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
	 * 	true - ignora a cache de processos e busca diretamente no serviço. Caso o serviço esteja indisponível, busca na cache e bd.
	 * @param incluirDocumentos
	 *  true - inclui os documentos na busca do processo no serviço. 
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
			processo = CacheUtils.getInstance().getProcessoFromCache(npu, tipoJuizo, idTribunal);
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
					throw new UnauthorizedException("Desculpe! O processo informado não pode ser acessado!");
				}
			} else {
				if (ignorarCache) {
					processo = CacheUtils.getInstance().getProcessoFromCache(npu, tipoJuizo, idTribunal);
					if (processo == null) {
						processo = findByNpuTribunalTipoJuizo(npu, idTribunal, tipoJuizo);
					}
				}
			}
		}
		if (processo != null) {
			try {
				CacheUtils.getInstance().putProcessoToCache(processo);
			} catch(Exception e) {
				logger.log(Level.SEVERE, String.format("Falha ao colocar processo %s, %s, %s na cache", npu, idTribunal, tipoJuizo.name()), e);
			}
		} else {
			if (falhaNoServico) {
				throw new ServiceUnavailableException("Desculpe! Serviço de consulta processual temporáriamente indisponível neste tribunal.");
			} else {
				throw new NotFoundException("Desculpe! O Processo informado não foi localizado.");
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
	public TipoProcessoJudicial consultarProcessoJudicial(String npu, Long idTribunal,
			TipoJuizo tipoJuizo, boolean ignorarCache, boolean incluirDocumentos)
			throws NotFoundException, UnauthorizedException,
			ServiceUnavailableException {
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
				processoJudicial = PJeServiceUtil.consultarProcessoJudicial(endpoint,
						npu, null, Boolean.TRUE, incluirDocumentos, null);
			} catch(Exception e) {
				logger.log(Level.SEVERE, String.format("Falha ao consultar processo %s, %s, %s no servico", npu, idTribunal, tipoJuizo.name()), e);
			}
		} else {
			throw new NotFoundException("Serviço não disponivel para o Tipo de Juízo informado.");
		}
		return processoJudicial;
	}
	
}
