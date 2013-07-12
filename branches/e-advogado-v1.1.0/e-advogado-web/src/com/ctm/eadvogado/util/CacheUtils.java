package com.ctm.eadvogado.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.ctm.eadvogado.model.Processo;
import com.ctm.eadvogado.model.TipoJuizo;
import com.google.appengine.api.memcache.jsr107cache.GCacheFactory;

/**
 * @author Cleber
 *
 */
public class CacheUtils {
	
	private static CacheUtils INSTANCE = null;
	
	private Cache cache;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private CacheUtils() {
		Map props = new HashMap();
        props.put(GCacheFactory.EXPIRATION_DELTA, 60 * 60);
        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(props);
        } catch (CacheException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 
	 */
	public static CacheUtils getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CacheUtils();
		}
		return INSTANCE;
	}
	
	/**
	 * @return
	 */
	public Cache getCache() {
		return cache;
	}
	
	/**
	 * @param npu
	 * @param tipoJuizo
	 * @param idTribunal
	 * @return
	 */
	private static String getProcessoCacheKey(String npu, TipoJuizo tipoJuizo, 
			Long idTribunal) {
		return String.format("%s-%s-%s", npu, tipoJuizo.name(), idTribunal);
	}
	
	/**
	 * Recupera um processo da cache caso exista.
	 * 
	 * @param npu
	 * @param tipoJuizo
	 * @param idTribunal
	 * @return
	 */
	public Processo getProcessoFromCache(String npu, TipoJuizo tipoJuizo, 
			Long idTribunal) {
		Processo processo = null;
		String processoCacheKey = getProcessoCacheKey(npu, tipoJuizo, idTribunal);
		if (cache.containsKey(processoCacheKey)) {
			processo = (Processo) cache.get(processoCacheKey);
		}
		return processo;
	}
	
	/**
	 * Coloca um processo na cache.
	 * 
	 * @param processo
	 */
	public void putProcessoToCache(Processo processo) {
		String processoCacheKey = getProcessoCacheKey(processo.getNpu(),
				processo.getTipoJuizo(), processo.getTribunal().getId());
		cache.put(processoCacheKey, processo);
	}

}
