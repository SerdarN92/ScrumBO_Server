package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.SprintBacklog;

public class SprintBacklogDao implements DaoInterface<SprintBacklog, Integer> {
	
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public SprintBacklogDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfig);
	}
	
	public void persist(SprintBacklog entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.save(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public void update(SprintBacklog entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.update(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public SprintBacklog findById(Integer id) {
		SprintBacklog sprintbacklog = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		sprintbacklog = (SprintBacklog) s.get(SprintBacklog.class, id);
		s.getTransaction().commit();
		s.close();
		return sprintbacklog;
	}
	
	public void delete(SprintBacklog entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.delete(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public List<SprintBacklog> findAll() {
		List<SprintBacklog> sprintbacklogListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		sprintbacklogListe = (List<SprintBacklog>) s.createQuery("from SprintBacklog").list();
		s.getTransaction().commit();
		s.close();
		return sprintbacklogListe;
	}
	
	public void deleteAll() {
		List<SprintBacklog> sprintbacklogListe = findAll();
		for (SprintBacklog sprintbacklog : sprintbacklogListe) {
			delete(sprintbacklog);
		}
	}
}
