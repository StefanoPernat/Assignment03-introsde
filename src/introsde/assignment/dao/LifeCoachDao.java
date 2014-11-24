package introsde.assignment.dao;

import introsde.assignment.model.Measure;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public enum LifeCoachDao {
	instance;
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	private LifeCoachDao(){
		if(emf != null){
			emf.close();
		}
		emf = Persistence.createEntityManagerFactory("jpa-assignment03");	
	}
	
	public EntityManager getEntityManager(){
		if(em == null){
			em = emf.createEntityManager();
		}
		return em;
	}
	
	public void destroyEntityManager(){
		if(em != null){
			em.close();
			em = null;
		}
	}
	
	public List<Measure> getCurrentHealthMeasures(int personId){
		ArrayList<Measure> res = new ArrayList<Measure>();
		List<Measure> result = getEntityManager().createNamedQuery("Measure.getCurrentHealth", Measure.class)
		  		 								 .setParameter("id", personId).getResultList(); 
		res.addAll(result);
		destroyEntityManager();
		return res;
		
	}
	
	public List<Measure> getHistoryHealthMeasures(int personId){
		ArrayList<Measure> res = new ArrayList<Measure>();
		List<Measure> result = getEntityManager().createNamedQuery("Measure.getHistoryHealth", Measure.class)
						  		 				 .setParameter("id", personId).getResultList();
		res.addAll(result);
		destroyEntityManager();
		return res;
	}
	
}
