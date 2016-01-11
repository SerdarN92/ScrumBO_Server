package dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.ProductBacklog;
import model.UserStory;

public class ProductBacklogDao implements DaoInterface<ProductBacklog, Integer> {
	
	private String			hibernateconfigfilename	= "";
	private HibernateUtil	hibernateutil			= null;
													
	public ProductBacklogDao(String hibernateconfigfilename) {
		this.setHibernateconfigfilename(hibernateconfigfilename);
		this.setHibernateutil(new HibernateUtil(hibernateconfigfilename));
	}
	
	public void persist(ProductBacklog entity) {
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
	
	public void update(ProductBacklog entity) {
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
	
	// LinkedHashSet, damit keine doppelten UserStorys in der Liste vorhanden
	// sind
	public ProductBacklog findById(Integer id) {
		ProductBacklog entity = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				entity = (ProductBacklog) s.get(ProductBacklog.class, id);
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
		List<UserStory> list = entity.getUserstory();
		List<UserStory> list2 = new ArrayList<UserStory>(new LinkedHashSet<UserStory>(list));
		entity.setUserstory(list2);
		return entity;
	}
	
	public void delete(ProductBacklog entity) {
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
	public List<ProductBacklog> findAll() {
		List<ProductBacklog> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = (List<ProductBacklog>) s.createQuery("FROM Productbacklog").list();
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
	public List<ProductBacklog> findAllByProjectId(Integer projectId) {
		List<ProductBacklog> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = (List<ProductBacklog>) s
						.createQuery("FROM Productbacklog WHERE project_id LIKE'" + projectId + "'").list();
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
		List<ProductBacklog> list = findAll();
		for (ProductBacklog entity : list) {
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
