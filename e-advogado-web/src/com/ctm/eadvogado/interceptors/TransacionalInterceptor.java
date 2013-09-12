package com.ctm.eadvogado.interceptors;

import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.RollbackException;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.ctm.eadvogado.qualifiers.EntityManagerQualifier;
import com.google.api.server.spi.response.ServiceUnavailableException;
import com.google.apphosting.api.ApiProxy.OverQuotaException;

@Interceptor  
@Transacional  
public class TransacionalInterceptor implements Serializable{  
	
	private static final Logger log = Logger.getLogger(TransacionalInterceptor.class.getSimpleName());
    /** 
     *  
     */  
    private static final long serialVersionUID = 1L;  
    private @Inject  
    @EntityManagerQualifier  
    EntityManager entityManager;  
  
    @AroundInvoke  
    public Object invoke(InvocationContext context) throws Exception {  
    	log.fine("Interceptando metodo transacional: " + context.getMethod().toString());
        EntityTransaction transaction = entityManager.getTransaction();
        Object result = null;
        try {  
            if (!transaction.isActive()) {  
                transaction.begin();  
            }  
            result = context.proceed();  
        } catch (OverQuotaException e) {  
			log.log(Level.SEVERE, String.format(
					"Excedeu o limite de Quota: %s, metodo: %s",
					e.getMessage(), context.getMethod()), e);  
			throw new ServiceUnavailableException(
					"Desculpe! O servi�o est� temporariamente em manuten��o. Favor tentar novamente em algumas horas.");
        } catch (Exception e) {  
        	log.log(Level.SEVERE, "Chamando transa��o no m�todo: " + context.getMethod(), e);  
            if (transaction != null) {  
                transaction.rollback();  
            }  
            throw e;
        } finally {  
            commitTransaction(transaction, true);
        }
        return result;
    }  
    
    private void commitTransaction(EntityTransaction transaction, boolean retryIfError) throws ServiceUnavailableException {
    	if (transaction != null && transaction.isActive()) {  
        	try {
        		transaction.commit();
        	} catch (OverQuotaException e) {  
				log.log(Level.SEVERE,
						String.format("Nao foi possivel comitar a transacao. Excedeu o limite de Quota: %s",
								e.getMessage()), e);  
    			throw new ServiceUnavailableException(
    					"Desculpe! O servi�o est� temporariamente em manuten��o. Favor tentar novamente em algumas horas.");
            } catch (RollbackException e) {
            	Throwable rootCause = ExceptionUtils.getRootCause(e);
            	if (rootCause instanceof ConcurrentModificationException && retryIfError) {
            		log.log(Level.SEVERE, "Nao foi possivel comitar a transacao... tentando novamente.", rootCause);
            		commitTransaction(transaction, false);
            	}
        	}
        } else {
        	log.log(Level.SEVERE, String.format("N�o foi possivel comitar a transa��o. isNull: %s, isActive: %s", transaction == null, transaction.isActive()));  
        }
    }
}