package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.Sprint;
import model.UserStory;

public class UserStoryDao implements DaoInterface<UserStory, Integer> {
	
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public UserStoryDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfigfilename);
	}
	
	public void persist(UserStory entity) {
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
	
	public void update(UserStory entity) {
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
	
	public boolean setSprintNull(Integer userstoryId) {
		boolean status = false;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				UserStory userstory = (UserStory) s.get(UserStory.class, userstoryId);
				Sprint sprint = null;
				userstory.setSprint(sprint);
				s.update(userstory);
				s.getTransaction().commit();
				s.close();
				status = true;
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public UserStory findById(Integer id) {
		UserStory userstory = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				userstory = (UserStory) s.get(UserStory.class, id);
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
		return userstory;
	}
	
	public void delete(UserStory entity) {
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
	
	public List<UserStory> findAll() {
		List<UserStory> userstoryListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				userstoryListe = (List<UserStory>) s.createQuery("from UserStory").list();
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
		return userstoryListe;
	}
	
	public List<UserStory> findAllByProductBacklogId(Integer productbacklogId) {
		List<UserStory> userstoryListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				userstoryListe = (List<UserStory>) s
						.createQuery("from UserStory where productbacklog_id like'" + productbacklogId + "'").list();
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
		return userstoryListe;
	}
	
	public List<UserStory> findAllBySprintId(Integer sprintId) {
		List<UserStory> userstoryListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				userstoryListe = (List<UserStory>) s
						.createQuery("from UserStory where sprint_id like'" + sprintId + "'").list();
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
		return userstoryListe;
	}
	
	public List<UserStory> findAllNULLwithProductBacklogId(Integer productbacklogid) {
		List<UserStory> userstoryListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				userstoryListe = (List<UserStory>) s.createQuery("from UserStory where productbacklog_id like'"
						+ productbacklogid + "'" + "AND sprint_id IS NULL").list();
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
		return userstoryListe;
	}
	
	public Integer getUserStoryStatus(Integer userstoryId) {
		UserStory userstory = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				userstory = (UserStory) s.createQuery("FROM UserStory where userstory_id like '" + userstoryId + "'")
						.uniqueResult();
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
		return userstory.getStatus();
	}
	
	public void deleteAll() {
		List<UserStory> userstoryListe = findAll();
		for (UserStory userstory : userstoryListe) {
			delete(userstory);
		}
	}
}
