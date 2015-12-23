package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.DefinitionOfDone;

public class DefinitionOfDoneDao implements DaoInterface<DefinitionOfDone, Integer> {
	
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public DefinitionOfDoneDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfig);
	}
	
	public void persist(DefinitionOfDone entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.save(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public void update(DefinitionOfDone entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.update(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public DefinitionOfDone findById(Integer id) {
		DefinitionOfDone definitionofdone = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		definitionofdone = (DefinitionOfDone) s.get(DefinitionOfDone.class, id);
		s.getTransaction().commit();
		s.close();
		return definitionofdone;
	}
	
	public List<DefinitionOfDone> findByUserstoryId(Integer userstoryId) {
		List<DefinitionOfDone> definitionofdoneListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		definitionofdoneListe = (List<DefinitionOfDone>) s
				.createQuery("from DefinitionOfDone where userstory_id like '" + userstoryId + "'").list();
		s.getTransaction().commit();
		s.close();
		return definitionofdoneListe;
	}
	
	public void delete(DefinitionOfDone entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.delete(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public List<DefinitionOfDone> findAll() {
		List<DefinitionOfDone> definitionofdoneListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		definitionofdoneListe = (List<DefinitionOfDone>) s.createQuery("from DefinitionOfDone").list();
		s.getTransaction().commit();
		s.close();
		return definitionofdoneListe;
	}
	
	public void deleteAll() {
		List<DefinitionOfDone> definitionofdoneListe = findAll();
		for (DefinitionOfDone definitionofdone : definitionofdoneListe) {
			delete(definitionofdone);
		}
	}
}
