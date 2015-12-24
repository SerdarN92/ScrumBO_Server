package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.Benutzerrolle;

public class BenutzerrolleDao implements DaoInterface<Benutzerrolle, Integer> {
	
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public BenutzerrolleDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfigfilename);
	}
	
	public void persist(Benutzerrolle entity) {
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
	
	public void update(Benutzerrolle entity) {
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
	
	public Benutzerrolle findById(Integer id) {
		Benutzerrolle benutzerrolle = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				benutzerrolle = (Benutzerrolle) s.get(Benutzerrolle.class, id);
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
		return benutzerrolle;
	}
	
	public void delete(Benutzerrolle entity) {
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
	
	public List<Benutzerrolle> findAll() {
		List<Benutzerrolle> benutzerrolleListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				benutzerrolleListe = (List<Benutzerrolle>) s.createQuery("from Benutzerrolle").list();
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
		return benutzerrolleListe;
	}
	
	public void deleteAll() {
		List<Benutzerrolle> BenutzerrolleListe = findAll();
		for (Benutzerrolle Benutzerrolle : BenutzerrolleListe) {
			delete(Benutzerrolle);
		}
	}
	
}
