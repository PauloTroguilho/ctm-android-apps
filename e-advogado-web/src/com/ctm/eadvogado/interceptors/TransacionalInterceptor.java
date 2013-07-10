package com.ctm.eadvogado.interceptors;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.ctm.eadvogado.qualifiers.EntityManagerQualifier;

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
  
        try {  
            if (!transaction.isActive()) {  
                transaction.begin();  
            }  
  
            return context.proceed();  
  
        } catch (Exception e) {  
        	log.log(Level.SEVERE, "Chamando transação no método: " + context.getMethod(), e);  
            if (transaction != null) {  
                transaction.rollback();  
            }  
  
            throw e;  
  
        } finally {  
            if (transaction != null && transaction.isActive()) {  
                transaction.commit();  
            }  
        }  
  
    }  
}