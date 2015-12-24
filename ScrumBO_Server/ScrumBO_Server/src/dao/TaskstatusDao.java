package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.Taskstatus;

public class TaskstatusDao implements DaoInterface<Taskstatus, Integer> {
	
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public TaskstatusDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfigfilename);
	}
	
	public void persist(Taskstatus entity) {
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
	
	public void update(Taskstatus entity) {
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
	
	public Taskstatus findById(Integer id) {
		Taskstatus taskstatus = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				taskstatus = (Taskstatus) s.get(Taskstatus.class, id);
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
		return taskstatus;
	}
	
	public void delete(Taskstatus entity) {
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
	
	public List<Taskstatus> findAll() {
		List<Taskstatus> taskstatusListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				taskstatusListe = (List<Taskstatus>) s.createQuery("from Taskstatus").list();
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
		return taskstatusListe;
	}
	
	public void deleteAll() {
		List<Taskstatus> taskstatusListe = findAll();
		for (Taskstatus taskstatus : taskstatusListe) {
			delete(taskstatus);
		}
	}
}
