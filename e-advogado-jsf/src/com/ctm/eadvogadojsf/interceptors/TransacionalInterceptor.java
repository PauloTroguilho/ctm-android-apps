package com.ctm.eadvogadojsf.interceptors;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.ctm.eadvogadojsf.qualifiers.EntityManagerQualifier;

@Interceptor  
@Transacional  
public class TransacionalInterceptor implements Serializable{  
    /** 
     *  
     */  
    private static final long serialVersionUID = 1L;  
    private @Inject  
    @EntityManagerQualifier  
    EntityManager entityManager;  
  
    @AroundInvoke  
    public Object invoke(InvocationContext context) throws Exception {  
        EntityTransaction transaction = entityManager.getTransaction();  
  
        try {  
            if (!transaction.isActive()) {  
                transaction.begin();  
            }  
  
            return context.proceed();  
  
        } catch (Exception e) {  
            System.out.println("Chamando transação no método:" + e);  
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