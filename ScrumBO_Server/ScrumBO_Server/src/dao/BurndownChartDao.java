package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.BurndownChart;

public class BurndownChartDao implements DaoInterface<BurndownChart, Integer> {
	
	private String			hibernateconfigfilename	= "";
	private HibernateUtil	hibernateutil			= null;
													
	public BurndownChartDao(String hibernateconfigfilename) {
		this.hibernateconfigfilename = hibernateconfigfilename;
		this.hibernateutil = new HibernateUtil(hibernateconfigfilename);
	}
	
	@Override
	public void persist(BurndownChart entity) {
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
	public void update(BurndownChart entity) {
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
	public BurndownChart findById(Integer id) {
		BurndownChart burndownChart = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				burndownChart = (BurndownChart) s.get(BurndownChart.class, id);
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
		return burndownChart;
	}
	
	@Override
	public void delete(BurndownChart entity) {
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
	public List<BurndownChart> findAll() {
		List<BurndownChart> burndownChartListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				burndownChartListe = (List<BurndownChart>) s.createQuery("from Burndownchart").list();
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
		return burndownChartListe;
	}
	
	@Override
	public void deleteAll() {
		List<BurndownChart> burndownChartListe = findAll();
		for (BurndownChart burndownChart : burndownChartListe) {
			delete(burndownChart);
		}
	}
	
}
