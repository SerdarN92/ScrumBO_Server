package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.Scrumprojekt;

public class ScrumprojektDao implements DaoInterface<Scrumprojekt, Integer> {
	
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public ScrumprojektDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfig);
	}
	
	public void persist(Scrumprojekt entity) {
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
	
	public void update(Scrumprojekt entity) {
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
	
	public Scrumprojekt findById(Integer id) {
		Scrumprojekt scrumprojekt = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				scrumprojekt = (Scrumprojekt) s.get(Scrumprojekt.class, id);
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
		return scrumprojekt;
	}
	
	public Scrumprojekt findByProjectname(String projectname) {
		List<Scrumprojekt> projectList = findAll();
		Scrumprojekt a = null;
		for (int i = 0; i < projectList.size(); i++) {
			if (projectList.get(i).getProjektname().equals(projectname))
				a = projectList.get(i);
		}
		return a;
	}
	
	public void delete(Scrumprojekt entity) {
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
	
	public List<Scrumprojekt> findAll() {
		List<Scrumprojekt> scrumprojektListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				scrumprojektListe = (List<Scrumprojekt>) s.createQuery("from Scrumprojekt").list();
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
		return scrumprojektListe;
	}
	
	public void deleteAll() {
		List<Scrumprojekt> scrumprojektListe = findAll();
		for (Scrumprojekt scrumprojekt : scrumprojektListe) {
			delete(scrumprojekt);
		}
	}
	
}
