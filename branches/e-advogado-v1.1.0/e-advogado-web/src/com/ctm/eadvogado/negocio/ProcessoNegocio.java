/**
 * 
 */
package com.ctm.eadvogado.negocio;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import com.ctm.eadvogado.dao.LancamentoDao;
import com.ctm.eadvogado.dao.ProcessoDao;
import com.ctm.eadvogado.dao.UsuarioDao;
import com.ctm.eadvogado.exception.DAOException;
import com.ctm.eadvogado.exception.NegocioException;
import com.ctm.eadvogado.interceptors.Transacional;
import com.ctm.eadvogado.model.Lancamento;
import com.ctm.eadvogado.model.Processo;
import com.ctm.eadvogado.model.TipoConta;
import com.ctm.eadvogado.model.TipoJuizo;
import com.ctm.eadvogado.model.TipoLancamento;
import com.ctm.eadvogado.model.Usuario;

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
}
