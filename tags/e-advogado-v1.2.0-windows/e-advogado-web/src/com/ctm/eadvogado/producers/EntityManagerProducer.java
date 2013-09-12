package com.ctm.eadvogado.producers;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ctm.eadvogado.qualifiers.EntityManagerQualifier;

@RequestScoped  
public class EntityManagerProducer {
	
	private static final Logger log = Logger.getLogger(EntityManagerProducer.class.getSimpleName());
  
	private static final EntityManagerFactory EMF = Persistence
			.createEntityManagerFactory("transactions-optional");
	
    public EntityManagerProducer() {  
    }  
  
    @Produces  
    @RequestScoped  
    @EntityManagerQualifier  
    public EntityManager createEntityManager() {  
    	EntityManager entityManager = EMF.createEntityManager();
    	log.info("EntityManager criado"); 
        return entityManager;  
    }  
  
    public void dispose(  
            @Disposes @EntityManagerQualifier EntityManager entityManager) {  
    	log.info("EntityManager fechado");
        entityManager.close();  
    }  
}