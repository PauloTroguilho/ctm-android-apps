/**
 * 
 */
package com.ctm.eadvogado.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import com.ctm.eadvogado.model.Processo;
import com.ctm.eadvogado.model.TipoJuizo;
import com.ctm.eadvogado.model.Tribunal;
import com.ctm.eadvogado.model.Usuario;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * @author Cleber
 *
 */
@Named
public class ProcessoDao extends BaseDao<Processo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param entityClass
	 */
	public ProcessoDao() {
		super(Processo.class);
	}
	
	/**
	 * Busca um processo pelo NPU
	 * @param npu
	 * @return
	 * @throws PersistenceException
	 */
	public Processo findByNpuTribunalTipoJuizo(String npu, Long idTribunal, TipoJuizo tipoJuizo) throws PersistenceException {
		Query query = entityManager.createNamedQuery("processoPorNpuTribunalTipoJuizo");
		query.setParameter("npu", npu.replaceAll("[.-]", ""));
		query.setParameter("idTribunal", 
				KeyFactory.createKey(Tribunal.class.getSimpleName(), idTribunal));
		query.setParameter("tipoJuizo", tipoJuizo);
		Processo processo = null;
		try {
			processo = (Processo) query.getSingleResult();
		} catch(NoResultException e) {
			logger.info(String.format("Processo não encontrado. %s, %s, %s", npu, idTribunal, tipoJuizo.name()));
		}
		return processo;
	}
	
	/**
	 * Busca os Processos do Usuário
	 * @param usuario
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
	public List<Processo> findByUsuario(Usuario usuario) throws PersistenceException {
		List<Processo> resultList = new ArrayList<Processo>();
		Query query = entityManager.createNamedQuery("processosPorUsuario");
		query.setParameter("idUsuario", usuario.getKey());
		List<Key> processosKeys = new ArrayList<Key>(); 
		try {
			processosKeys = query.getResultList();
		} catch(NoResultException e) {
			logger.log(Level.INFO, "Nenhum processo por usuario: "+ usuario.getEmail(), e);
		}
		if (!processosKeys.isEmpty()) {
			resultList = findByKeys(processosKeys);
		}
		return resultList;
	}
	
	
	/**
	 * @param processosKeys
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
	public List<Processo> findByKeys(List<Key> processosKeys) throws PersistenceException {
		List<Processo> resultList = new ArrayList<Processo>();
		Query query = entityManager.createNamedQuery("processosInElements");
		query.setParameter("processos", processosKeys);
		try {
			resultList = query.getResultList();
		} catch(PersistenceException e) {
			logger.log(Level.SEVERE, "Erro ao consultar processosInElements!", e);
		}
		return resultList;
	}
	
	/**
	 * @return
	 * @throws PersistenceException
	 */
	@SuppressWarnings("unchecked")
	public List<Processo> findAllAssociados() throws PersistenceException {
		List<Processo> resultList = new ArrayList<Processo>();
		Query query = entityManager.createNamedQuery("processosAssociados");
		List<Key> processosKeys = new ArrayList<Key>(); 
		try {
			processosKeys = query.getResultList();
		} catch(NoResultException e) {
			logger.log(Level.INFO, "Nenhum processo associado a usuario encontrado.", e);
		}
		Set<Key> processosKeysSet = new HashSet<Key>();
		processosKeysSet.addAll(processosKeys);
		if (!processosKeysSet.isEmpty()) {
			logger.log(Level.INFO, String.format("Foram encontrados %s processos associados a usuários.", processosKeysSet.size()));
			List<Key> list = Arrays.asList(processosKeysSet.toArray(new Key[0]));
			resultList = findByKeys(list);
		}
		return resultList;
	}
	
}
