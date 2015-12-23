package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.Benutzer_Benutzerrolle_Scrumprojekt;

public class Benutzer_Benutzerrolle_ScrumprojektDao
		implements DaoInterface<Benutzer_Benutzerrolle_Scrumprojekt, Integer> {
		
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public Benutzer_Benutzerrolle_ScrumprojektDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfig);
	}
	
	@Override
	public void persist(Benutzer_Benutzerrolle_Scrumprojekt entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.save(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	@Override
	public void update(Benutzer_Benutzerrolle_Scrumprojekt entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.update(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findListByBenutzerId(Integer benutzerId) {
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzer_benutzerrolle_scrumprojekt_Liste = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		benutzer_benutzerrolle_scrumprojekt_Liste = (List<Benutzer_Benutzerrolle_Scrumprojekt>) s
				.createQuery("FROM Benutzer_Benutzerrolle_Scrumprojekt WHERE benutzerId LIKE'" + benutzerId + "'")
				.list();
		s.getTransaction().commit();
		s.close();
		return benutzer_benutzerrolle_scrumprojekt_Liste;
	}
	
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findListByProjectId(Integer scrumprojektid) {
		
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzer_benutzerrolle_scrumprojekt_Liste = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		benutzer_benutzerrolle_scrumprojekt_Liste = (List<Benutzer_Benutzerrolle_Scrumprojekt>) s
				.createQuery(
						"from Benutzer_Benutzerrolle_Scrumprojekt where scrumprojektId like'" + scrumprojektid + "'")
				.list();
		s.getTransaction().commit();
		s.close();
		return benutzer_benutzerrolle_scrumprojekt_Liste;
	}
	
	@Override
	public void delete(Benutzer_Benutzerrolle_Scrumprojekt entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.delete(entity);
		s.getTransaction().commit();
		s.close();
		
	}
	
	@Override
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findAll() {
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzerListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		benutzerListe = (List<Benutzer_Benutzerrolle_Scrumprojekt>) s
				.createQuery("from Benutzer_Benutzerrolle_Scrumprojekt").list();
		s.getTransaction().commit();
		s.close();
		return benutzerListe;
	}
	
	@Override
	public void deleteAll() {
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzerListe = findAll();
		for (Benutzer_Benutzerrolle_Scrumprojekt benutzer : benutzerListe) {
			delete(benutzer);
		}
	}
	
	@Override
	public Benutzer_Benutzerrolle_Scrumprojekt findById(Integer id) {
		Benutzer_Benutzerrolle_Scrumprojekt benutzer = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		benutzer = (Benutzer_Benutzerrolle_Scrumprojekt) s.get(Benutzer_Benutzerrolle_Scrumprojekt.class, id);
		s.getTransaction().commit();
		s.close();
		return benutzer;
	}
	
}
