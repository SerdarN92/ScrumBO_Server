package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.BurndownChartPoint;

public class BurndownChartPointDao implements DaoInterface<BurndownChartPoint, Integer> {
	
	private String			hibernateconfigfilename	= "";
	private HibernateUtil	hibernateutil			= null;
													
	public BurndownChartPointDao(String hibernateconfigfilename) {
		this.hibernateconfigfilename = hibernateconfigfilename;
		this.hibernateutil = new HibernateUtil(hibernateconfigfilename);
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
		BurndownChartPoint burndownChartPoint = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				burndownChartPoint = (BurndownChartPoint) s.get(BurndownChartPoint.class, id);
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
		return burndownChartPoint;
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
	
	@Override
	public List<BurndownChartPoint> findAll() {
		List<BurndownChartPoint> burndownChartPointListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				burndownChartPointListe = (List<BurndownChartPoint>) s.createQuery("from Burndownchartpoint").list();
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
		return burndownChartPointListe;
	}
	
	@Override
	public void deleteAll() {
		List<BurndownChartPoint> burndownChartPointListe = findAll();
		for (BurndownChartPoint burndownChartPoint : burndownChartPointListe) {
			delete(burndownChartPoint);
		}
	}
	
}
