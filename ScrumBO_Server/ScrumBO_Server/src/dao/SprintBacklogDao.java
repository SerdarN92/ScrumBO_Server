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
		this.hibernateutil = new HibernateUtil(hibernateconfigfilename);
	}
	
	public void persist(SprintBacklog entity) {
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				s.save(entity);
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(SprintBacklog entity) {
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				s.update(entity);
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SprintBacklog findById(Integer id) {
		SprintBacklog sprintbacklog = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				sprintbacklog = (SprintBacklog) s.get(SprintBacklog.class, id);
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sprintbacklog;
	}
	
	public void delete(SprintBacklog entity) {
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				s.delete(entity);
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<SprintBacklog> findAll() {
		List<SprintBacklog> sprintbacklogListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				sprintbacklogListe = (List<SprintBacklog>) s.createQuery("from SprintBacklog").list();
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sprintbacklogListe;
	}
	
	public void deleteAll() {
		List<SprintBacklog> sprintbacklogListe = findAll();
		for (SprintBacklog sprintbacklog : sprintbacklogListe) {
			delete(sprintbacklog);
		}
	}
}
