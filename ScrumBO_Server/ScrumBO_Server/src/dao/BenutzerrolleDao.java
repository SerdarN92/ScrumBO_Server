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
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.save(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public void update(Benutzerrolle entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.update(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public Benutzerrolle findById(Integer id) {
		Benutzerrolle benutzerrolle = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		benutzerrolle = (Benutzerrolle) s.get(Benutzerrolle.class, id);
		s.getTransaction().commit();
		s.close();
		return benutzerrolle;
	}
	
	public void delete(Benutzerrolle entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.delete(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public List<Benutzerrolle> findAll() {
		List<Benutzerrolle> benutzerrolleListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		benutzerrolleListe = (List<Benutzerrolle>) s.createQuery("from Benutzerrolle").list();
		s.getTransaction().commit();
		s.close();
		return benutzerrolleListe;
	}
	
	public void deleteAll() {
		List<Benutzerrolle> BenutzerrolleListe = findAll();
		for (Benutzerrolle Benutzerrolle : BenutzerrolleListe) {
			delete(Benutzerrolle);
		}
	}
	
}
