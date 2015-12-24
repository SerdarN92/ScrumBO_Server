package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.Benutzer;

public class BenutzerDao implements DaoInterface<Benutzer, Integer> {
	
	private String			hibernateconfigfilename	= "";
	private HibernateUtil	hibernateutil			= null;
													
	public BenutzerDao(String hibernateconfigfilename) {
		this.hibernateconfigfilename = hibernateconfigfilename;
		this.hibernateutil = new HibernateUtil(hibernateconfigfilename);
	}
	
	public void persist(Benutzer entity) {
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
	
	public void update(Benutzer entity) {
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
	
	public Benutzer findById(Integer id) {
		Benutzer benutzer = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				benutzer = (Benutzer) s.get(Benutzer.class, id);
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
		return benutzer;
	}
	
	public Benutzer findByEmail(String email) {
		List<Benutzer> benutzerList = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				benutzerList = findAll();
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
		Benutzer benutzer = null;
		for (int i = 0; i < benutzerList.size(); i++) {
			if (benutzerList.get(i).getEmail().equals(email))
				benutzer = benutzerList.get(i);
		}
		return benutzer;
	}
	
	public void delete(Benutzer entity) {
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
	
	public List<Benutzer> findAll() {
		List<Benutzer> benutzerListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				benutzerListe = (List<Benutzer>) s.createQuery("from Benutzer").list();
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
		return benutzerListe;
	}
	
	public void deleteAll() {
		List<Benutzer> benutzerListe = findAll();
		for (Benutzer benutzer : benutzerListe) {
			delete(benutzer);
		}
	}
	
}
