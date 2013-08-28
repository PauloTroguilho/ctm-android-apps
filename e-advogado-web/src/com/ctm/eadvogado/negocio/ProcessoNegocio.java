/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.xml.ws.WebServiceException;

import br.jus.cnj.pje.v1.TipoMovimentoProcessual;
import br.jus.cnj.pje.v1.TipoProcessoJudicial;

import com.ctm.eadvogado.comparators.TipoMovimentoProcessualComparator;
import com.ctm.eadvogado.dao.DeviceDao;
import com.ctm.eadvogado.dao.LancamentoDao;
import com.ctm.eadvogado.dao.ProcessoDao;
import com.ctm.eadvogado.dao.TribunalDao;
import com.ctm.eadvogado.dao.UsuarioDao;
import com.ctm.eadvogado.dao.UsuarioProcessoDao;
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
import com.ctm.eadvogado.model.UsuarioProcesso;
import com.ctm.eadvogado.util.CacheUtils;
import com.ctm.eadvogado.util.PJeServiceUtil;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.ServiceUnavailableException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

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
	private DeviceDao deviceDao;
	@Inject
	private TribunalDao tribunalDao;
	@Inject
	private UsuarioProcessoDao usuarioProcessoDao;
	
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
	 * Busca os processos do Usu�rio.
	 * @param usuario
	 * @return
	 * @throws PersistenceException
	 */
	public List<Processo> findByUsuario(Usuario usuario) throws PersistenceException{
		return getDao().findByUsuario(usuario);
	}
	
	/**
	 * Retorna os processos associados a usu�rios.
	 * @return
	 * @throws PersistenceException
	 */
	public List<Processo> findAllAssociados() throws PersistenceException {
		return getDao().findAllAssociados();
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
			Processo processo = findByID(idProcesso);
			Long idUsuario = usuario.getKey().getId();
			UsuarioProcesso usuarioProcesso = usuarioProcessoDao.selectPorUsuarioProcesso(idUsuario, idProcesso);
			if (usuarioProcesso == null) {
				Lancamento lancamento = new Lancamento();
				lancamento.setData(new Date());
				lancamento.setQuantidade(1);
				lancamento.setTipo(TipoLancamento.DEBITO);
				lancamento.setUsuario(usuario.getKey());
				lancamentoDao.insert(lancamento);
				
				usuarioProcesso = new UsuarioProcesso();
				usuarioProcesso.setUsuario(usuario.getKey());
				usuarioProcesso.setProcesso(processo.getKey());
				usuarioProcessoDao.insert(usuarioProcesso);
			}
			
		} else {
			throw new NegocioException("erro.negocio.saldoInsuficiente");
		}
	}
	
	public static Comparator<TipoMovimentoProcessual> movimentoReverseComparator = Collections.reverseOrder(new TipoMovimentoProcessualComparator());
	
	/**
	 * Consulta um processo
	 * 
	 * @param npu
	 * @param idTribunal
	 * @param tipoJuizo
	 * @param ignorarCache
	 * 	true - ignora a cache de processos e busca diretamente no servi�o. Caso o servi�o esteja indispon�vel, busca na cache e bd.
	 * @param incluirDocumentos
	 *  true - inclui os documentos na busca do processo no servi�o. 
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
					npu, idTribunal, tipoJuizo, incluirDocumentos);
			if (processoJudicial != null) {
				Collections.sort(processoJudicial.getMovimento(), movimentoReverseComparator);
				if (processoJudicial.getDadosBasicos().getNivelSigilo() == 0) {
					if (processo != null) {
						verificaAtualizacaoENotifica(processo, processoJudicial);
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
					logger.log(Level.WARNING, String.format("N�o � permitido acessar o processo %s, idTribunal %s, tipoJuizo %s, ", npu, idTribunal, tipoJuizo));
					throw new UnauthorizedException("Desculpe! O processo informado n�o pode ser acessado!");
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
				logger.log(Level.WARNING, String.format("Servi�o indispon�vel para o processo %s, idTribunal %s, tipoJuizo %s, ", npu, idTribunal, tipoJuizo));
				throw new ServiceUnavailableException("Desculpe! Servi�o de consulta processual tempor�riamente indispon�vel neste tribunal.");
			} else {
				logger.log(Level.WARNING, String.format("N�o foi poss�vel localizar o processo %s, idTribunal %s, tipoJuizo %s, ", npu, idTribunal, tipoJuizo));
				throw new NotFoundException("Desculpe! O Processo informado n�o foi localizado.");
			}
		}
		return processo;
	}
	
	/**
	 * @param processo
	 * @param processoJudicial
	 */
	public void verificaAtualizacaoENotifica(Processo processo, TipoProcessoJudicial processoJudicial) {
		Collections.sort(processo.getProcessoJudicial().getMovimento(), movimentoReverseComparator);
		Date dataUltimoMovAnterior = null;
		try {
			dataUltimoMovAnterior = TipoMovimentoProcessualComparator.movimentoDateFormat.parse(
					processo.getProcessoJudicial().getMovimento().get(0).getDataHora());
			logger.info(String.format("NPU: %s, Data ultimo movimento anterior: %s", processo.getNpu(), dataUltimoMovAnterior));
		} catch (Exception e1) {
			logger.log(Level.SEVERE, "Falha ao converter data da movimentacao", e1);
		}
		Date dataUltimoMovAtual = null;
		try {
			dataUltimoMovAtual = TipoMovimentoProcessualComparator.movimentoDateFormat.parse(
					processoJudicial.getMovimento().get(0).getDataHora());
			logger.info(String.format("NPU: %s, Data ultimo movimento atual: %s", processo.getNpu(), dataUltimoMovAtual));
		} catch (Exception e1) {
			logger.log(Level.SEVERE, "Falha ao converter data da movimentacao", e1);
		}
		if (dataUltimoMovAtual != null && dataUltimoMovAnterior != null &&
				dataUltimoMovAtual.after(dataUltimoMovAnterior)) {
			Queue queue = QueueFactory.getDefaultQueue();
			queue.add(TaskOptions.Builder.withUrl("/notificarMovimentacao")
					.method(Method.POST)
					.param("npu",processo.getNpu())
					.param("idTribunal", processo.getTribunal().getId() + "")
					.param("tipoJuizo", processo.getTipoJuizo().name()));
			logger.info(String.format(
					"Queue de notificacao criada para o processo %s, %s, %s",
					processo.getNpu(), processo.getTribunal().getId(),
					processo.getTipoJuizo()));
		}
	}
	
	
	/**
	 * Consulta um {@link TipoProcessoJudicial} no servi�o do tribunal.
	 * 
	 * @param npu
	 * @param idTribunal
	 * @param tipoJuizo
	 * @param incluirDocumentos
	 * @return
	 * @throws NotFoundException
	 * @throws UnauthorizedException
	 * @throws ServiceUnavailableException
	 */
	public TipoProcessoJudicial consultarProcessoJudicial(String npu, Long idTribunal,
			TipoJuizo tipoJuizo, boolean incluirDocumentos)
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
			} catch(WebServiceException e) {
				logger.log(Level.SEVERE, String.format("Servi�o temporariamente indispon�vel no tribunal %s, %s", idTribunal, tipoJuizo.name()), e);
				throw new ServiceUnavailableException("O servi�o de consulta neste tribunal est� temporariamente indispon�vel!");
			} catch(Exception e) {
				logger.log(Level.SEVERE, String.format("Falha ao consultar processo %s, %s, %s no servico", npu, idTribunal, tipoJuizo.name()), e);
			}
		} else {
			throw new NotFoundException("Servi�o n�o disponivel para o Tipo de Ju�zo informado.");
		}
		return processoJudicial;
	}
	
	@Transacional
	public void removerProcessoDoUsuario(Long idProcesso, String email) throws NegocioException, PersistenceException{
		Usuario usuario = usuarioDao.findByEmail(email);
		if (usuario.getProcessos() != null && !usuario.getProcessos().isEmpty()) {
			Key keyToRemove = null;
			for (Key processoKey : usuario.getProcessos()) {
				if (processoKey.getId() == idProcesso.longValue()) {
					keyToRemove = processoKey;
					break;
				}
			}
			if (keyToRemove != null) {
				usuario.getProcessos().remove(keyToRemove);
			}
			usuarioDao.update(usuario);
		}
		UsuarioProcesso usuarioProcesso = usuarioProcessoDao.selectPorUsuarioProcesso(usuario.getKey().getId(), idProcesso);
		if (usuarioProcesso != null) {
			usuarioProcessoDao.remove(usuarioProcesso);
		}
	}
	
	
	
	/**
	 * @param npu
	 * @param idTribunal
	 * @param tipoJuizo
	 * @throws NotFoundException
	 */
	public void notificarMovimentacaoProcessual(String npu, Long idTribunal, TipoJuizo tipoJuizo)
			throws NotFoundException {
		Processo processo = findByNpuTribunalTipoJuizo(npu, idTribunal, tipoJuizo);
		if (processo != null) {
			Tribunal tribunal = tribunalDao.findByID(idTribunal);
			List<Usuario> usuarios = usuarioDao.findByProcesso(processo);
			for (Usuario usuario : usuarios) {
				Queue queue = QueueFactory.getDefaultQueue();
				queue.add(TaskOptions.Builder.withUrl("/enviarNotificacao")
						.method(Method.POST)
						.param("email", usuario.getEmail())
						.param("titulo", "Processo atualizado!")
						.param("mensagem", String.format("%s: %s", tribunal.getSigla(), npu))
						.param("npu", npu)
						.param("idTribunal", idTribunal.toString())
						.param("tipoJuizo", tipoJuizo.name()));
				logger.info(String.format(
						"Queue /enviarNotificacao criada para o email %s", usuario.getEmail()));
			}
		} else {
			throw new NotFoundException("Processo n�o encontrado!");
		}
	}
	
}
