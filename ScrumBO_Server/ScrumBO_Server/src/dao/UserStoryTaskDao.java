package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.UserStoryTask;

public class UserStoryTaskDao implements DaoInterface<UserStoryTask, Integer> {
	
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public UserStoryTaskDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfig);
	}
	
	public void persist(UserStoryTask entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.save(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public void update(UserStoryTask entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.update(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public UserStoryTask findById(Integer id) {
		UserStoryTask userstorytask = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		userstorytask = (UserStoryTask) s.get(UserStoryTask.class, id);
		s.getTransaction().commit();
		s.close();
		return userstorytask;
	}
	
	public void delete(UserStoryTask entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.delete(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public List<UserStoryTask> findAll() {
		List<UserStoryTask> userstorytaskListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		userstorytaskListe = (List<UserStoryTask>) s.createQuery("from UserStoryTask").list();
		s.getTransaction().commit();
		s.close();
		return userstorytaskListe;
	}
	
	public List<UserStoryTask> findAllByUserStoryId(Integer userstoryId) {
		List<UserStoryTask> userstorytaskListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		userstorytaskListe = (List<UserStoryTask>) s
				.createQuery("from UserStoryTask WHERE userstory_id like '" + userstoryId + "'").list();
		s.getTransaction().commit();
		s.close();
		return userstorytaskListe;
	}
	
	public void deleteAll() {
		List<UserStoryTask> userstorytaskListe = findAll();
		for (UserStoryTask userstorytask : userstorytaskListe) {
			delete(userstorytask);
		}
	}
}
