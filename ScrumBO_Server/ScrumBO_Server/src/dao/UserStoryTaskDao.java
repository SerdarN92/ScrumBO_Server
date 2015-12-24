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
	
	public void update(UserStoryTask entity) {
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
	
	public UserStoryTask findById(Integer id) {
		UserStoryTask userstorytask = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				userstorytask = (UserStoryTask) s.get(UserStoryTask.class, id);
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
		return userstorytask;
	}
	
	public void delete(UserStoryTask entity) {
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
	
	public List<UserStoryTask> findAll() {
		List<UserStoryTask> userstorytaskListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				userstorytaskListe = (List<UserStoryTask>) s.createQuery("from UserStoryTask").list();
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
		return userstorytaskListe;
	}
	
	public List<UserStoryTask> findAllByUserStoryId(Integer userstoryId) {
		List<UserStoryTask> userstorytaskListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				userstorytaskListe = (List<UserStoryTask>) s
						.createQuery("from UserStoryTask WHERE userstory_id like '" + userstoryId + "'").list();
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
		return userstorytaskListe;
	}
	
	public void deleteAll() {
		List<UserStoryTask> userstorytaskListe = findAll();
		for (UserStoryTask userstorytask : userstorytaskListe) {
			delete(userstorytask);
		}
	}
}
