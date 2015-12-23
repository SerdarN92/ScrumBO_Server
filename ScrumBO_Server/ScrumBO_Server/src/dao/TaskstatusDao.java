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
		this.hibernateutil = new HibernateUtil(hibernateconfig);
	}
	
	public void persist(Taskstatus entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.save(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public void update(Taskstatus entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.update(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public Taskstatus findById(Integer id) {
		Taskstatus taskstatus = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		taskstatus = (Taskstatus) s.get(Taskstatus.class, id);
		s.getTransaction().commit();
		s.close();
		return taskstatus;
	}
	
	public void delete(Taskstatus entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.delete(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public List<Taskstatus> findAll() {
		List<Taskstatus> taskstatusListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		taskstatusListe = (List<Taskstatus>) s.createQuery("from Taskstatus").list();
		s.getTransaction().commit();
		s.close();
		return taskstatusListe;
	}
	
	public void deleteAll() {
		List<Taskstatus> taskstatusListe = findAll();
		for (Taskstatus taskstatus : taskstatusListe) {
			delete(taskstatus);
		}
	}
}
