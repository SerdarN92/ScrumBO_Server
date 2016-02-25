package dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.Sprint;
import model.UserStory;
import model.UserStoryTask;

public class UserStoryDao implements DaoInterface<UserStory, Integer> {
	
	private String			hibernateconfigfilename	= "";
	private HibernateUtil	hibernateutil			= null;
													
	public UserStoryDao(String hibernateconfigfilename) {
		this.setHibernateconfigfilename(hibernateconfigfilename);
		this.setHibernateutil(new HibernateUtil(hibernateconfigfilename));
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
				UserStory entityUserStory = (UserStory) s.get(UserStory.class, userstoryId);
				Sprint entitySprint = null;
				entityUserStory.setSprint(entitySprint);
				s.update(entityUserStory);
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
		UserStory entity = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				entity = (UserStory) s.get(UserStory.class, id);
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
		return entity;
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
	
	@SuppressWarnings("unchecked")
	public List<UserStory> findAll() {
		List<UserStory> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = (List<UserStory>) s.createQuery("FROM UserStory").list();
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
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserStory> findAllByProductBacklogId(Integer productbacklogId) {
		List<UserStory> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = (List<UserStory>) s
						.createQuery("FROM UserStory WHERE productbacklog_id LIKE'" + productbacklogId + "'").list();
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
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserStory> findAllBySprintId(Integer sprintId) {
		List<UserStory> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = (List<UserStory>) s.createQuery("FROM UserStory WHERE sprint_id LIKE'" + sprintId + "'").list();
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
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserStory> findAllNULLwithProductBacklogId(Integer productbacklogid) {
		List<UserStory> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = (List<UserStory>) s.createQuery("FROM UserStory WHERE productbacklog_id LIKE'" + productbacklogid
						+ "'" + "AND sprint_id IS NULL").list();
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
		return list;
	}
	
	public Integer getUserStoryStatus(Integer userstoryId) {
		UserStory entity = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				entity = (UserStory) s.createQuery("FROM UserStory WHERE userstory_id LIKE '" + userstoryId + "'")
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
		return entity.getStatus();
	}
	
	public void deleteAll() {
		List<UserStory> userstoryListe = findAll();
		for (UserStory userstory : userstoryListe) {
			delete(userstory);
		}
	}
	
	public String getHibernateconfigfilename() {
		return hibernateconfigfilename;
	}
	
	public void setHibernateconfigfilename(String hibernateconfigfilename) {
		this.hibernateconfigfilename = hibernateconfigfilename;
	}
	
	public HibernateUtil getHibernateutil() {
		return hibernateutil;
	}
	
	public void setHibernateutil(HibernateUtil hibernateutil) {
		this.hibernateutil = hibernateutil;
	}
}
