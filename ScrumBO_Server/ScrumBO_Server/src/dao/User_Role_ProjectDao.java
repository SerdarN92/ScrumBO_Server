package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.User_Role_Project;

/*
 * Author: Serdar Nurgün
 *
 * This class allows to manipulate the Database Table "User_Role_Project".
 */
public class User_Role_ProjectDao implements DaoInterface<User_Role_Project, Integer> {
	
	private String			hibernateconfigfilename	= "";
	private HibernateUtil	hibernateutil			= null;
													
	public User_Role_ProjectDao(String hibernateconfigfilename) {
		this.setHibernateconfigfilename(hibernateconfigfilename);
		this.setHibernateutil(new HibernateUtil(hibernateconfigfilename));
	}
	
	/*
	 * Creates a new User_Role_Project entity.
	 */
	@Override
	public void persist(User_Role_Project entity) {
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
	
	/*
	 * Updates an existing User_Role_Project entity.
	 */
	
	@Override
	public void update(User_Role_Project entity) {
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
	
	/*
	 * Returns a List of User_Role_Project Entities with the passed userId.
	 */
	@SuppressWarnings("unchecked")
	public List<User_Role_Project> findListByUserId(Integer userId) {
		List<User_Role_Project> entity = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				entity = (List<User_Role_Project>) s
						.createQuery("FROM User_Role_Project WHERE userId LIKE'" + userId + "'").list();
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
	
	/*
	 * Returns a List of User_Role_Project Entities with the passed projectId.
	 */
	@SuppressWarnings("unchecked")
	public List<User_Role_Project> findListByProjectId(Integer projectId) {
		List<User_Role_Project> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = (List<User_Role_Project>) s
						.createQuery("FROM User_Role_Project WHERE projectId LIKE'" + projectId + "'").list();
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
	
	/*
	 * Deletes a User_Role_Project entity.
	 */
	@Override
	public void delete(User_Role_Project entity) {
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
	
	/*
	 * Returns a List of all User_Role_Project Entities.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User_Role_Project> findAll() {
		List<User_Role_Project> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = (List<User_Role_Project>) s.createQuery("FROM User_Role_Project").list();
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/*
	 * Deletes all User_Role_Project Entities.
	 */
	@Override
	public void deleteAll() {
		try {
			List<User_Role_Project> list = findAll();
			for (User_Role_Project entity : list) {
				delete(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * This method checks, if a user with the passed userId has admission for
	 * the project with the passed projectId. If yes, it will return true. Else
	 * it will return false.
	 */
	public boolean checkAdmission(Integer userId, Integer projectId) {
		User_Role_Project entity = null;
		boolean admission = false;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				entity = (User_Role_Project) s.createQuery("FROM User_Role_Project WHERE userId LIKE '" + userId
						+ "' AND projectId LIKE '" + projectId + "'").uniqueResult();
				s.getTransaction().commit();
				s.close();
				if (entity != null)
					admission = true;
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admission;
	}
	
	/*
	 * Method to delete a Entity with the passed userId from the Table
	 * User_Role_Project.
	 */
	public void deleteWithUserId(Integer userId) {
		try {
			List<User_Role_Project> list = findListByUserId(userId);
			for (User_Role_Project entity : list) {
				delete(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Method to delete a Project with the passed projectId from the Table
	 * User_Role_Project.
	 */
	public void deleteProject(Integer projectId) {
		try {
			List<User_Role_Project> list = findListByProjectId(projectId);
			for (User_Role_Project entity : list) {
				delete(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Method to check, if the user with the passed userId is an Admin or not.
	 * If yes, the method will return the boolean true. Else the method will
	 * return the boolean false.
	 */
	public boolean checkAdmin(Integer userId) {
		User_Role_Project entity = null;
		boolean admin = false;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				entity = (User_Role_Project) s.createQuery("FROM User_Role_Project WHERE userId LIKE '" + userId + "'")
						.uniqueResult();
				s.getTransaction().commit();
				s.close();
				if (entity != null) {
					if (entity.getPk().getRoleId() == 4) {
						admin = true;
					}
				}
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
	}
	
	/*
	 * Returns the String hibernateconfigfilename.
	 */
	public String getHibernateconfigfilename() {
		return hibernateconfigfilename;
	}
	
	/*
	 * Sets the passed String as the hibernateconfigfilename.
	 */
	public void setHibernateconfigfilename(String hibernateconfigfilename) {
		this.hibernateconfigfilename = hibernateconfigfilename;
	}
	
	/*
	 * Returns the HibernateUtil.
	 */
	public HibernateUtil getHibernateutil() {
		return hibernateutil;
	}
	
	/*
	 * Sets the passed HibernateUtil as the hibernateUtil.
	 */
	public void setHibernateutil(HibernateUtil hibernateutil) {
		this.hibernateutil = hibernateutil;
	}
	
	@Override
	public User_Role_Project findById(Integer id) {
		// Not used, cause there is no Id.
		return null;
	}
	
}
