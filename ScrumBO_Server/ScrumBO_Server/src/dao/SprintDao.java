package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.Sprint;

public class SprintDao implements DaoInterface<Sprint, Integer> {
	
	private Session		currentSession			= null;
	private Transaction	currentTransaction		= null;
	private String		hibernateconfigfilename	= "";
												
	public SprintDao(String hibernateconfigfilename) {
		this.hibernateconfigfilename = hibernateconfigfilename;
	}
	
	public Session openCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;
	}
	
	public Session openCurrentSessionwithTransaction() {
		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	public void closeCurrentSession() {
		currentSession.close();
		getSessionFactory().close();
	}
	
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
		getSessionFactory().close();
	}
	
	public SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure(hibernateconfigfilename);
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}
	
	public Session getCurrentSession() {
		return currentSession;
	}
	
	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}
	
	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}
	
	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}
	
	public void persist(Sprint entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(Sprint entity) {
		getCurrentSession().update(entity);
	}
	
	public Sprint findById(Integer id) {
		Sprint sprint = (Sprint) getCurrentSession().get(Sprint.class, id);
		return sprint;
	}
	
	public Sprint findByProjectIdandSprintNumber(Integer scrumprojektid, Integer sprintnumber) {
		Sprint sprint = (Sprint) getCurrentSession().createQuery("from Sprint where scrumprojekt_id like '"
				+ scrumprojektid + "' AND sprint_nummer like'" + sprintnumber + "'").uniqueResult();
		return sprint;
	}
	
	public void delete(Sprint entity) {
		getCurrentSession().delete(entity);
	}
	
	public List<Sprint> findAll() {
		List<Sprint> sprintListe = (List<Sprint>) getCurrentSession().createQuery("from Sprint").list();
		return sprintListe;
	}
	
	public void deleteAll() {
		List<Sprint> sprintListe = findAll();
		for (Sprint sprint : sprintListe) {
			delete(sprint);
		}
	}
	
	// Gibt die h�chste SprintNummer eines Projekts zur�ck
	public Integer countSprintsToProject(Integer scrumprojektid) {
		Integer sprintnummer = 0;
		List<Sprint> sprintListe = (List<Sprint>) getCurrentSession()
				.createQuery("from Sprint where scrumprojekt_id like'" + scrumprojektid + "'").list();
		for (int i = 0; i < sprintListe.size(); i++) {
			if (sprintListe.get(i).getSprintnummer() > sprintnummer) {
				sprintnummer = sprintListe.get(i).getSprintnummer();
			}
		}
		return sprintnummer;
	}
	
	// Gibt die Anzahl von Sprints eines Projekts zur�ck
	public Integer countSprintsAnzahlToProject(Integer scrumprojektid) {
		Integer count = 0;
		List<Sprint> sprintListe = (List<Sprint>) getCurrentSession()
				.createQuery("from Sprint where scrumprojekt_id like'" + scrumprojektid + "'").list();
		count = sprintListe.size();
		return count;
	}
	
}
