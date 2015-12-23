package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.Sprint;

public class SprintDao implements DaoInterface<Sprint, Integer> {
	
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public SprintDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfig);
	}
	
	public void persist(Sprint entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.save(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public void update(Sprint entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.update(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public Sprint findById(Integer id) {
		Sprint sprint = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		sprint = (Sprint) s.get(Sprint.class, id);
		s.getTransaction().commit();
		s.close();
		return sprint;
	}
	
	public Sprint findByProjectIdandSprintNumber(Integer scrumprojektid, Integer sprintnumber) {
		Sprint sprint = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		sprint = (Sprint) s.createQuery("from Sprint where scrumprojekt_id like '" + scrumprojektid
				+ "' AND sprint_nummer like'" + sprintnumber + "'").uniqueResult();
		s.getTransaction().commit();
		s.close();
		return sprint;
	}
	
	public void delete(Sprint entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.delete(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public List<Sprint> findAll() {
		List<Sprint> sprintListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		sprintListe = (List<Sprint>) s.createQuery("from Sprint").list();
		s.getTransaction().commit();
		s.close();
		return sprintListe;
	}
	
	public void deleteAll() {
		List<Sprint> sprintListe = findAll();
		for (Sprint sprint : sprintListe) {
			delete(sprint);
		}
	}
	
	// Gibt die höchste SprintNummer eines Projekts zurück
	public Integer countSprintsToProject(Integer scrumprojektid) {
		Integer sprintnummer = 0;
		List<Sprint> sprintListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		sprintListe = (List<Sprint>) s.createQuery("from Sprint where scrumprojekt_id like'" + scrumprojektid + "'")
				.list();
		s.getTransaction().commit();
		s.close();
		for (int i = 0; i < sprintListe.size(); i++) {
			if (sprintListe.get(i).getSprintnummer() > sprintnummer) {
				sprintnummer = sprintListe.get(i).getSprintnummer();
			}
		}
		return sprintnummer;
	}
	
	// Gibt die Anzahl von Sprints eines Projekts zurück
	public Integer countSprintsAnzahlToProject(Integer scrumprojektid) {
		Integer count = 0;
		List<Sprint> sprintListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		sprintListe = (List<Sprint>) s.createQuery("from Sprint where scrumprojekt_id like'" + scrumprojektid + "'")
				.list();
		s.getTransaction().commit();
		s.close();
		count = sprintListe.size();
		return count;
	}
	
}
