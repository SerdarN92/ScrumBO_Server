package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.Benutzer;

public class BenutzerDao implements DaoInterface<Benutzer, Integer> {
	
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public BenutzerDao(String hibernateconfig) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfig);
	}
	
	public void persist(Benutzer entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.save(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public void update(Benutzer entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.update(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public Benutzer findById(Integer id) {
		Benutzer benutzer = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		benutzer = (Benutzer) s.get(Benutzer.class, id);
		s.getTransaction().commit();
		s.close();
		return benutzer;
	}
	
	public Benutzer findByEmail(String email) {
		List<Benutzer> benutzerList = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		benutzerList = findAll();
		s.getTransaction().commit();
		s.close();
		Benutzer benutzer = null;
		for (int i = 0; i < benutzerList.size(); i++) {
			if (benutzerList.get(i).getEmail().equals(email))
				benutzer = benutzerList.get(i);
		}
		return benutzer;
	}
	
	public void delete(Benutzer entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.delete(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public List<Benutzer> findAll() {
		List<Benutzer> benutzerListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		benutzerListe = (List<Benutzer>) s.createQuery("from Benutzer").list();
		s.getTransaction().commit();
		s.close();
		return benutzerListe;
	}
	
	public void deleteAll() {
		List<Benutzer> benutzerListe = findAll();
		for (Benutzer benutzer : benutzerListe) {
			delete(benutzer);
		}
	}
	
}
