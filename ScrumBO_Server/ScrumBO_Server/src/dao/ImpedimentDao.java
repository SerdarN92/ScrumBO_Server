package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.Impediment;

public class ImpedimentDao implements DaoInterface<Impediment, Integer> {
	
	private String			hibernateconfigfilename	= "";
	private HibernateUtil	hibernateutil			= null;
													
	public ImpedimentDao(String hibernateconfigfilename) {
		this.setHibernateconfigfilename(hibernateconfigfilename);
		this.setHibernateutil(new HibernateUtil(hibernateconfigfilename));
	}
	
	public void persist(Impediment entity) {
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
	
	public void update(Impediment entity) {
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
	
	public Impediment findById(Integer id) {
		Impediment entity = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				entity = (Impediment) s.get(Impediment.class, id);
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
	
	@SuppressWarnings("unchecked")
	public List<Impediment> findByProjectId(Integer projectId) {
		List<Impediment> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = (List<Impediment>) s.createQuery("FROM Impediment WHERE project_id LIKE'" + projectId + "'")
						.list();
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
	
	public void delete(Impediment entity) {
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
	public List<Impediment> findAll() {
		List<Impediment> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = (List<Impediment>) s.createQuery("FROM Impediment").list();
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
		List<Impediment> list = findAll();
		for (Impediment entity : list) {
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
