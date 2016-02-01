package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.User;

/*
 * Author: Serdar Nurgün
 *
 * This class allows to manipulate the Database Table "User".
 */
public class UserDao implements DaoInterface<User, Integer> {
	
	private String			hibernateconfigfilename	= "";
	private HibernateUtil	hibernateutil			= null;
													
	public UserDao(String hibernateconfigfilename) {
		this.setHibernateconfigfilename(hibernateconfigfilename);
		this.setHibernateutil(new HibernateUtil(hibernateconfigfilename));
	}
	
	public void persist(User entity) {
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
	
	public void update(User entity) {
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
	
	public User findById(Integer id) {
		User entity = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				entity = (User) s.get(User.class, id);
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
	
	public User findByEmail(String email) {
		List<User> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = findAll();
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
		User entity = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getEmail().equals(email))
				entity = list.get(i);
		}
		return entity;
	}
	
	public boolean checkIfEmailAlreadyExists(String email) {
		boolean status = false;
		List<User> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = findAll();
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
		Integer anzahl = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getEmail().equalsIgnoreCase(email)) {
				anzahl++;
			}
		}
		if (anzahl >= 1) {
			status = true;
		}
		return status;
	}
	
	public void delete(User entity) {
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
	public List<User> findAll() {
		List<User> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = (List<User>) s.createQuery("from User").list();
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
	
	public void deleteAll() {
		List<User> list = findAll();
		for (User entity : list) {
			delete(entity);
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
