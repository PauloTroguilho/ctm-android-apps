package com.ctm.eadvogado.util;

import java.lang.annotation.Annotation;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.servlet.ServletContext;

import org.jboss.weld.environment.servlet.Listener;

import com.ctm.eadvogado.listener.ContextListener;

public class WeldUtils {

	/**
	 * @param servletContext
	 * @return
	 */
	public static BeanManager getBeanManager(ServletContext servletContext) {
		return (BeanManager) servletContext.getAttribute(
				Listener.BEAN_MANAGER_ATTRIBUTE_NAME);
	}
	
	/**
	 * @return
	 */
	public static BeanManager getBeanManager() {
		return getBeanManager(ContextListener.SERVLET_CONTEXT);
	}
	
	/**
	 * Retorna uma instancia do bean
	 * 
	 * @param beanManager
	 * @param beanType
	 * @param qualifiers
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(BeanManager beanManager, Class<T> beanType, Annotation... qualifiers) {
		Bean<T> bean = (Bean<T>) beanManager.getBeans(beanType, qualifiers).iterator().next();
		return (T) bean.create(beanManager.createCreationalContext(bean));
	}
	
	/**
	 * Retorna uma instancia do bean
	 * 
	 * @param beanType
	 * @param qualifiers
	 * @return
	 */
	public static <T> T getBean(Class<T> beanType, Annotation... qualifiers) {
		return getBean(getBeanManager(), beanType, qualifiers);
	}
	
}
