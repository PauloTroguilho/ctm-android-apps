/**
 * 
 */
package com.ctm.eadvogado.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.jboss.weld.environment.servlet.Listener;

/**
 * @author Cleber
 *
 */
public class ContextListener extends Listener {
	
	public static ServletContext SERVLET_CONTEXT;

	public ContextListener() {
		super();
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		SERVLET_CONTEXT = sce.getServletContext();
		super.contextInitialized(sce);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		super.contextDestroyed(sce);
	}
}
