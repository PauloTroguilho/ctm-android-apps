package com.ctm.eadvogado.endpoints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import org.apache.commons.codec.binary.Hex;

import br.jus.cnj.pje.v1.TipoDocumento;
import br.jus.cnj.pje.v1.TipoProcessoJudicial;

import com.ctm.eadvogado.exception.NegocioException;
import com.ctm.eadvogado.model.Documento;
import com.ctm.eadvogado.model.Processo;
import com.ctm.eadvogado.model.ProcessoUsuario;
import com.ctm.eadvogado.model.TipoJuizo;
import com.ctm.eadvogado.model.Tribunal;
import com.ctm.eadvogado.model.Usuario;
import com.ctm.eadvogado.negocio.ProcessoNegocio;
import com.ctm.eadvogado.negocio.TribunalNegocio;
import com.ctm.eadvogado.negocio.UsuarioNegocio;
import com.ctm.eadvogado.util.PJeServiceUtil;
import com.ctm.eadvogado.util.WeldUtils;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.InternalServerErrorException;
import com.google.api.server.spi.response.NotFoundException;
import com.google.api.server.spi.response.ServiceUnavailableException;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

@Api(name = "processoEndpoint", namespace = @ApiNamespace(ownerDomain = "eadvogado.ctm.com", ownerName = "eadvogado.ctm.com", packagePath = "endpoints"))
public class ProcessoEndpoint extends BaseEndpoint<Processo, ProcessoNegocio> {
	
	private static Cache cache;
	
	private TribunalNegocio tribunalNegocio;
	private UsuarioNegocio usuarioNegocio;

	public ProcessoEndpoint() {
		setNegocio(WeldUtils.getBean(ProcessoNegocio.class));
		tribunalNegocio = WeldUtils.getBean(TribunalNegocio.class);
		usuarioNegocio = WeldUtils.getBean(UsuarioNegocio.class);
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Cache getCache() {
		if (cache == null) {
			Map props = new HashMap();
	        props.put(GCacheFactory.EXPIRATION_DELTA, 60 * 60);
	        try {
	            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
	            cache = cacheFactory.createCache(props);
	        } catch (CacheException e) {
	            e.printStackTrace();
	        }
		}
        return cache;
	}
	
	/**
	 * @param npu
	 * @param tipoJuizo
	 * @param idTribunal
	 * @return
	 */
	private String getProcessoCacheKey(String npu, TipoJuizo tipoJuizo, 
			Long idTribunal) {
		return String.format("%s-%s-%s", npu, tipoJuizo.name(), idTribunal);
	}
	
	/**
	 * @param npu
	 * @param tipoJuizo
	 * @param idTribunal
	 * @return
	 */
	private Processo getProcessoFromCache(String npu, TipoJuizo tipoJuizo, 
			Long idTribunal) {
		Processo processo = null;
		String processoCacheKey = getProcessoCacheKey(npu, tipoJuizo, idTribunal);
		Cache cache = getCache();
		if (cache != null && cache.containsKey(processoCacheKey)) {
			processo = (Processo) cache.get(processoCacheKey);
		}
		return processo;
	}
	
	/**
	 * @param npu
	 * @param tipoJuizo
	 * @param idTribunal
	 * @param processo
	 */
	private void putProcessoToCache(String npu, TipoJuizo tipoJuizo, 
			Long idTribunal, Processo processo) {
		String processoCacheKey = getProcessoCacheKey(npu, tipoJuizo, idTribunal);
		Cache cache = getCache();
		cache.put(processoCacheKey, processo);
	}
	
	/**
	 * Faz a consulta de um processo.
	 * @param npu
	 * @param idTribunal
	 * @param tipoJuizo
	 * @return
	 */
	@ApiMethod(name = "consultarProcesso")
	public Processo consultarProcesso(@Named("npu") String npu,
			@Named("idTribunal") Long idTribunal,
			@Named("tipoJuizo") TipoJuizo tipoJuizo,
			@Named("ignorarCache") Boolean ignorarCache) throws NotFoundException, ServiceUnavailableException, UnauthorizedException {
		boolean falhaNoServico = false;
		Processo processo = null;
		if (!ignorarCache) {
			processo = getProcessoFromCache(npu, tipoJuizo, idTribunal);
		}
		if (processo == null) {
			processo = getNegocio().findByNpuTribunalTipoJuizo(npu, idTribunal, tipoJuizo);
		}
		if (processo == null || (processo != null && ignorarCache)) {
			Tribunal tribunal = tribunalNegocio.findByID(idTribunal);
			String endpoint = null;
			switch (tipoJuizo) {
				case PRIMEIRO_GRAU:
					endpoint = tribunal.getPje1gEndpoint();
					break;
				case SEGUNDO_GRAU:
					endpoint = tribunal.getPje2gEndpoint();
					break;
			}
			TipoProcessoJudicial processoJudicial = null;
			if (endpoint != null) {
				try {
					processoJudicial = PJeServiceUtil.consultarProcessoJudicial(endpoint, npu);
				} catch(Exception e) {
					falhaNoServico = true;
					logger.log(Level.SEVERE, String.format("Falha ao consultar processo %s, %s, %s no servico", npu, idTribunal, tipoJuizo.name()), e);
				}
			} else {
				throw new NotFoundException("Serviço não disponivel para o Tipo de Juízo informado.");
			}
			if (processoJudicial != null) {
				if (processoJudicial.getDadosBasicos().getNivelSigilo() == 0) {
					if (processo != null) {
						processo.setProcessoJudicial(processoJudicial);
						getNegocio().update(processo);
					} else {
						processo = new Processo();
						processo.setNpu(npu);
						processo.setTipoJuizo(tipoJuizo);
						processo.setTribunal(tribunal.getKey());
						processo.setProcessoJudicial(processoJudicial);
						getNegocio().insert(processo);
					}
				} else {
					throw new UnauthorizedException("Desculpe! O processo informado não pode ser acessado!");
				}
			} else {
				if (ignorarCache) {
					processo = getProcessoFromCache(npu, tipoJuizo, idTribunal);
					if (processo == null) {
						processo = getNegocio().findByNpuTribunalTipoJuizo(npu, idTribunal, tipoJuizo);
					}
				}
			}
		}
		if (processo != null) {
			try {
				putProcessoToCache(npu, tipoJuizo, idTribunal, processo);
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
	 * Faz a consulta de um processo.
	 * @param npu
	 * @param idTribunal
	 * @param tipoJuizo
	 * @return
	 */
	@ApiMethod(name = "associarProcessoAoUsuario")
	public void associarProcessoAoUsuario(@Named("email") String email,
			@Named("idProcesso") Long idProcesso) throws NotFoundException, UnauthorizedException,
			InternalServerErrorException {
		try {
			getNegocio().associarProcessoAoUsuario(idProcesso, email);
		} catch(NegocioException e) {
			throw new UnauthorizedException("Desculpe! Você não possui saldo para inclusão de processos!", e);
		} catch (Exception e) {
			throw new InternalServerErrorException(
					"Falha ao associar processo ao usuário!", e);
		}
	}
	
	@ApiMethod(name = "removerProcessoDoUsuario")
	public void removerProcessoDoUsuario(@Named("email") String email,
			@Named("idProcesso") Long idProcesso) throws NotFoundException,
			InternalServerErrorException {
		Usuario usuario = null;
		try {
			usuario = usuarioNegocio.findByEmail(email);
		} catch (NoResultException e) {
			throw new NotFoundException("Usuário não encontrado!");
		}
		if (usuario != null) {
			Processo processo = getNegocio().findByID(idProcesso);
			if (processo != null) {
				if (usuario.getProcessos().remove(processo.getKey())) {
					try {
						usuarioNegocio.update(usuario);
					} catch (PersistenceException e) {
						throw new InternalServerErrorException(
								"Falha ao associar processo ao usuário!", e);
					}
				}
			} else {
				throw new NotFoundException("Processo não encontrado!");
			}
		}
	}
	
	
	/**
	 * Faz a consulta de um processo.
	 * @param npu
	 * @param idTribunal
	 * @param tipoJuizo
	 * @return
	 */
	@ApiMethod(name = "consultarDocumento")
	public Documento consultarDocumento(@Named("npu") String npu,
			@Named("idTribunal") Long idTribunal,
			@Named("tipoJuizo") TipoJuizo tipoJuizo,
			@Named("idDocumento") String idDocumento) throws NotFoundException, ServiceUnavailableException, UnauthorizedException {
		Tribunal tribunal = tribunalNegocio.findByID(idTribunal);
		String endpoint = null;
		switch (tipoJuizo) {
			case PRIMEIRO_GRAU:
				endpoint = tribunal.getPje1gEndpoint();
				break;
			case SEGUNDO_GRAU:
				endpoint = tribunal.getPje2gEndpoint();
				break;
		}
		TipoDocumento tipoDoc = null;
		if (endpoint != null) {
			try {
				tipoDoc = PJeServiceUtil.consultarDocumento(endpoint, npu, idDocumento);
			} catch(Exception e) {
				logger.log(Level.SEVERE, String.format("Falha ao consultar documento %s, do processo %s, %s, %s no servico.", idDocumento, npu, idTribunal, tipoJuizo.name()), e);
				throw new ServiceUnavailableException("Não foi possível consultar o documento neste momento!");
			}
		} else {
			throw new ServiceUnavailableException("Serviço não disponivel para o Tipo de Juízo informado.");
		}
		if (tipoDoc != null) {
			if (tipoDoc.getNivelSigilo().equals(0)) {
				Documento documento = new Documento();
				documento.setConteudo(Hex.encodeHexString(tipoDoc.getConteudo()));
				documento.setIdDocumento(idDocumento);
				documento.setMimeType(tipoDoc.getMimetype());
				return documento;
			} else {
				throw new UnauthorizedException("Desculpe! O documento informado não pode ser acessado!");
			}
		} else {
			throw new NotFoundException("Desculpe! O documento informado não foi localizado!");
		}
	}
	
	
	@ApiMethod(name = "consultarProcessosDoUsuario")
	public List<ProcessoUsuario> consultarProcessosDoUsuario(@Named("email") String email,
			@Named("senha") String senha) throws NotFoundException, UnauthorizedException{
		Usuario usuario = null;
		try {
			usuario = usuarioNegocio.autenticar(email, senha);
		} catch(NoResultException e) {
			throw new NotFoundException("Usuário não encontrado!");
		} catch (SecurityException e) {
			throw new UnauthorizedException("Usuário e/ou senha inválidos!");
		}
		List<ProcessoUsuario> processosList = new ArrayList<ProcessoUsuario>();
		if (usuario != null) {
			List<Processo> processos = getNegocio().findByUsuario(usuario);
			for (Processo processo : processos) {
				ProcessoUsuario pu = new ProcessoUsuario();
				pu.setNpu(processo.getNpu());
				pu.setIdTribunal(processo.getTribunal().getId());
				pu.setTipoJuizo(processo.getTipoJuizo());
				processosList.add(pu);
			}
		}
		return processosList;
	}
}
