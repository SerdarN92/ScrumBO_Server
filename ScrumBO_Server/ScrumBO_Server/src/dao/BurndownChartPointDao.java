package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.BurndownChartPoint;

public class BurndownChartPointDao implements DaoInterface<BurndownChartPoint, Integer> {
	
	private String			hibernateconfigfilename	= "";
	private HibernateUtil	hibernateutil			= null;
													
	public BurndownChartPointDao(String hibernateconfigfilename) {
		this.setHibernateconfigfilename(hibernateconfigfilename);
		this.setHibernateutil(new HibernateUtil(hibernateconfigfilename));
	}
	
	@Override
	public void persist(BurndownChartPoint entity) {
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				s.save(entity);
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				e.printStackTrace();
				s.getTransaction().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(BurndownChartPoint entity) {
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
	
	@Override
	public BurndownChartPoint findById(Integer id) {
		BurndownChartPoint entity = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				entity = (BurndownChartPoint) s.get(BurndownChartPoint.class, id);
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
	
	@Override
	public void delete(BurndownChartPoint entity) {
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
	@Override
	public List<BurndownChartPoint> findAll() {
		List<BurndownChartPoint> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = (List<BurndownChartPoint>) s.createQuery("from BurndownChartPoint").list();
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
	public List<BurndownChartPoint> findAllWithBurndownChartId(int burndownchartId) {
		List<BurndownChartPoint> list = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				list = (List<BurndownChartPoint>) s
						.createQuery("from BurndownChartPoint WHERE burndownchart_id LIKE'" + burndownchartId + "'")
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
	
	@Override
	public void deleteAll() {
		List<BurndownChartPoint> list = findAll();
		for (BurndownChartPoint entity : list) {
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
