package com.ctm.eadvogadojsf.producers;

import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ctm.eadvogadojsf.qualifiers.EntityManagerQualifier;

@RequestScoped  
public class EntityManagerProducer {
	
	private static final Logger log = Logger.getLogger(EntityManagerProducer.class.getSimpleName());
  
    private @PersistenceContext(unitName = "BancoX")  
    EntityManager entityManager;  
  
    public EntityManagerProducer() {  
    }  
  
    @Produces  
    @RequestScoped  
    @EntityManagerQualifier  
    public EntityManager createEntityManager() {  
    	log.info("EntityManager criado");  
        return entityManager;  
    }  
  
    public void dispose(  
            @Disposes @EntityManagerQualifier EntityManager entityManager) {  
    	log.info("EntityManager fechado");
        entityManager.close();  
    }  
}