/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.xml.ws.WebServiceException;

import org.apache.commons.codec.binary.Hex;

import br.jus.cnj.pje.v1.TipoDocumento;
import br.jus.cnj.pje.v1.TipoProcessoJudicial;

import com.ctm.eadvogado.comparators.TipoMovimentoProcessualComparator;
import com.ctm.eadvogado.dao.DeviceDao;
import com.ctm.eadvogado.dao.LancamentoDao;
import com.ctm.eadvogado.dao.ProcessoDao;
import com.ctm.eadvogado.dao.TipoDocumentoDao;
import com.ctm.eadvogado.dao.TribunalDao;
import com.ctm.eadvogado.dao.UsuarioDao;
import com.ctm.eadvogado.dao.UsuarioProcessoDao;
import com.ctm.eadvogado.exception.DAOException;
import com.ctm.eadvogado.exception.NegocioException;
import com.ctm.eadvogado.interceptors.Transacional;
import com.ctm.eadvogado.model.Documento;
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
	@Inject
	private TipoDocumentoDao tipoDocumentoDao;
	
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
	
	/**
	 * Retorna os processos associados a usuários.
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
		setBinariosToNullBeforePersist(entity);
		return super.insert(entity);
	}
	
	@Override
	@Transacional
	public Processo update(Processo entity) throws PersistenceException {
		setBinariosToNullBeforePersist(entity);
		return super.update(entity);
	}
	
	private static void setBinariosToNullBeforePersist(Processo processo) {
		if (processo != null) {
			setBinariosToNullBeforePersist(processo.getProcessoJudicial());
		}
	}
	
	/**
	 * @param processoJudicial
	 */
	private static void setBinariosToNullBeforePersist(TipoProcessoJudicial processoJudicial) {
		if (processoJudicial != null && processoJudicial.getDocumento() != null
					&& !processoJudicial.getDocumento().isEmpty()) {
			for (TipoDocumento doc : processoJudicial.getDocumento()) {
				if (doc != null) {
					if (doc.getConteudo() != null) {
						doc.setConteudo((byte[]) null);
					}
					if (doc.getAssinatura() != null) {
						doc.getAssinatura().clear();
					}
				}
			}
		}
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
	
	//public static Comparator<TipoMovimentoProcessual> movimentoReverseComparator = Collections.reverseOrder(new TipoMovimentoProcessualComparator());
	
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
		logger.fine(String.format("Consultando Processo: NPU: %s, Trib: %s, TpJ: %s, IgnC: %s, IncD: %s", npu, idTribunal, tipoJuizo, ignorarCache, incluirDocumentos));
		boolean falhaNoServico = false;
		Processo processo = null;
		if (!ignorarCache) {
			processo = CacheUtils.getInstance().getProcessoFromCache(npu, tipoJuizo, idTribunal);
		}
		if (processo == null) {
			processo = findByNpuTribunalTipoJuizo(npu, idTribunal, tipoJuizo);
		}
		if (processo == null || (processo != null && ignorarCache)) {
			TipoProcessoJudicial processoJudicial = null;
			try {
				processoJudicial = consultarProcessoJudicial(
						npu, idTribunal, tipoJuizo, incluirDocumentos);
			} catch(Exception e) {
				logger.log(Level.SEVERE, String.format("Erro ao consultar atualizacao do processo/tribunal/tipoJuizo: %s/%s/%s. Message: %s", npu,idTribunal,tipoJuizo, e.getMessage()), e);
			}
			if (processoJudicial == null) {
				processoJudicial = consultarProcessoJudicial(npu, idTribunal, tipoJuizo, false);
			}
			if (processoJudicial != null) {
				Collections.sort(processoJudicial.getMovimento(), 
						Collections.reverseOrder(new TipoMovimentoProcessualComparator()));
				if (processoJudicial.getDadosBasicos().getNivelSigilo() == 0) {
					if (processo != null) {
						verificaAtualizacaoENotifica(processo, processoJudicial);
						processo.setProcessoJudicial(processoJudicial);
						try {
							update(processo);
						} catch(Exception e) {
							logger.log(Level.SEVERE, String.format("Erro ao atualizar o processo/tribunal/tipoJuizo: %s/%s/%s. Message: %s", npu,idTribunal,tipoJuizo, e.getMessage()), e);
						}
					} else {
						processo = new Processo();
						processo.setNpu(npu);
						processo.setTipoJuizo(tipoJuizo);
						processo.setTribunal(KeyFactory.createKey(
								Tribunal.class.getSimpleName(), idTribunal));
						processo.setProcessoJudicial(processoJudicial);
						try {
							insert(processo);
						} catch(Exception e) {
							logger.log(Level.SEVERE, String.format("Erro ao inserir o processo/tribunal/tipoJuizo: %s/%s/%s. Message: %s", npu,idTribunal,tipoJuizo, e.getMessage()), e);
						}
					}
				} else {
					logger.log(Level.WARNING, String.format("Não é permitido acessar o processo %s, idTribunal %s, tipoJuizo %s, ", npu, idTribunal, tipoJuizo));
					throw new UnauthorizedException("O nivel de sigilo deste processo não permite consultá-lo!");
				}
			} else {
				if (ignorarCache) {
					processo = CacheUtils.getInstance().getProcessoFromCache(npu, tipoJuizo, idTribunal);
					if (processo == null) {
						processo = findByNpuTribunalTipoJuizo(npu, idTribunal, tipoJuizo);
						fixDocumentosNulos(processo.getProcessoJudicial());
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
				logger.log(Level.WARNING, String.format("Serviço indisponível para o processo %s, idTribunal %s, tipoJuizo %s, ", npu, idTribunal, tipoJuizo));
				throw new ServiceUnavailableException("O sistema PJ-e deste tribunal está temporáriamente indisponível!");
			} else {
				logger.log(Level.WARNING, String.format("Não foi possível localizar o processo %s, idTribunal %s, tipoJuizo %s, ", npu, idTribunal, tipoJuizo));
				throw new NotFoundException("O processo informado não foi localizado no PJ-e!");
			}
		}
		return processo;
	}
	
	@Transacional
	public Processo consultarProcessoComDocumentos(String npu, Long idTribunal,
			TipoJuizo tipoJuizo, boolean ignorarCache, boolean incluirDocumentos)
			throws NotFoundException, UnauthorizedException,
			ServiceUnavailableException {
		logger.fine(String.format("Consultando Processo com Documentos: NPU: %s, Trib: %s, TpJ: %s, IgnC: %s, IncD: %s", npu, idTribunal, tipoJuizo, ignorarCache, incluirDocumentos));
		Processo processo = consultarProcesso(npu, idTribunal, tipoJuizo, ignorarCache, incluirDocumentos);
		if (processo != null) {
			Tribunal tribunal = tribunalDao.findByID(idTribunal);
			List<TipoDocumento> documentosPJ = processo.getProcessoJudicial().getDocumento();
			List<Documento> documentos = new ArrayList<Documento>();
			for (TipoDocumento docPJe : documentosPJ) {
				Documento documento = new Documento();
				documento.setIdDocumento(docPJe.getIdDocumento());
				documento.setMimeType(docPJe.getMimetype());
				com.ctm.eadvogado.model.TipoDocumento tipoDoc = 
						tipoDocumentoDao.findPorTribunalETipoJuizoECodigo(
								tribunal, tipoJuizo, docPJe.getTipoDocumento());
				if (tipoDoc == null) {
					tipoDoc = new com.ctm.eadvogado.model.TipoDocumento();
					tipoDoc.setCodigo(docPJe.getTipoDocumento());
					tipoDoc.setDescricao(docPJe.getTipoDocumento());
					tipoDoc.setTribunal(tribunal.getKey());
					tipoDoc.setTipoJuizo(tipoJuizo);
				}
				documento.setTipoDocumento(tipoDoc);
				if (incluirDocumentos && docPJe.getConteudo() != null) {
					documento.setConteudo(Hex.encodeHexString(docPJe.getConteudo()));
				}
				documentos.add(documento);
			}
			processo.setDocumentos(documentos);
		}
		return processo;		
	}
	
	/**
	 * @param processo
	 * @param processoJudicial
	 */
	public void verificaAtualizacaoENotifica(Processo processo, TipoProcessoJudicial processoJudicial) {
		Collections.sort(processo.getProcessoJudicial().getMovimento(), 
				Collections.reverseOrder(new TipoMovimentoProcessualComparator()));
		SimpleDateFormat movimentoDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dataUltimoMovAnterior = null;
		try {
			dataUltimoMovAnterior = movimentoDateFormat.parse(
					processo.getProcessoJudicial().getMovimento().get(0).getDataHora());
			logger.info(String.format("NPU: %s, Data ultimo movimento anterior: %s", processo.getNpu(), dataUltimoMovAnterior));
		} catch (Exception e1) {
			logger.log(Level.SEVERE, "Falha ao converter data da movimentacao", e1);
		}
		Date dataUltimoMovAtual = null;
		try {
			dataUltimoMovAtual = movimentoDateFormat.parse(
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
	 * Consulta um {@link TipoProcessoJudicial} no serviço do tribunal.
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
				fixDocumentosNulos(processoJudicial);
			} catch(WebServiceException e) {
				logger.log(Level.SEVERE, String.format("Serviço temporariamente indisponível no tribunal %s, %s", idTribunal, tipoJuizo.name()), e);
				throw new ServiceUnavailableException("O serviço de consulta neste tribunal está temporariamente indisponível!");
			} catch(Exception e) {
				logger.log(Level.SEVERE, String.format("Falha ao consultar processo %s, %s, %s no servico", npu, idTribunal, tipoJuizo.name()), e);
			}
		} else {
			throw new NotFoundException("Serviço não disponivel para o Tipo de Juízo informado.");
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
						.param("tipoNotificacao", "movimentacao")
						.param("email", usuario.getEmail())
						.param("titulo", "e-Advogado: Movimentação")
						.param("mensagem", String.format("%s: %s", tribunal.getSigla(), npu))
						.param("npu", npu)
						.param("idTribunal", idTribunal.toString())
						.param("tipoJuizo", tipoJuizo.name()));
				logger.info(String.format(
						"Queue /enviarNotificacao criada para o email %s", usuario.getEmail()));
			}
		} else {
			throw new NotFoundException("Processo não encontrado!");
		}
	}
	
	private void fixDocumentosNulos(TipoProcessoJudicial processoJudicial) {
		if (processoJudicial != null && !processoJudicial.getDocumento().isEmpty()) {
			ArrayList<TipoDocumento> listaNula = new ArrayList<TipoDocumento>();
			listaNula.add(null);
			processoJudicial.getDocumento().removeAll(listaNula);
		}
	}

	public static void main(String[] args) {
		//TipoProcessoJudicial processoJudicial = PJeServiceUtil.consultarProcessoJudicial("http://pje.trt23.jus.br/primeirograu/intercomunicacao?wsdl", "00001061820135230041", null, true, true, null);
		TipoProcessoJudicial processoJudicial = PJeServiceUtil.consultarProcessoJudicial("http://pje.trt1.jus.br/primeirograu/intercomunicacao?wsdl", "00103519620135010204", null, true, true, null);
		setBinariosToNullBeforePersist(processoJudicial);
	}
}
