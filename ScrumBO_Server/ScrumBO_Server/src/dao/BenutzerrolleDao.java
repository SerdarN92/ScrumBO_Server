package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.Benutzerrolle;

/*
 * Das Data Access Object der Benutzerrolle ist dafür da,
 * um die Information der Datenbank Benutzerrolle auszulesen oder zu verändern.
 * Die Klasse BenutzerrolleDao implementiert das Data Access Object Interface und
 * somit auch die Methoden des Interfaces.
 */
public class BenutzerrolleDao implements DaoInterface<Benutzerrolle, Integer> {
	
	private Session		currentSession		= null;
	private Transaction	currentTransaction	= null;
	
	private static String hibernateconfigfilename = "";
	
	/*
	 * Default Konstruktor
	 */
	public BenutzerrolleDao(String hibernateconfigfilename) {
		this.hibernateconfigfilename = hibernateconfigfilename;
	}
	
	// Methode um eine Session zu öffnen
	public Session openCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;
	}
	
	// Methode um eine Session mit Transaktion zu öffnen
	public Session openCurrentSessionwithTransaction() {
		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	// Methode um die aktive Session zu schließen
	public void closeCurrentSession() {
		currentSession.close();
	}
	
	// Methode um die aktive Session mit Transaktion zu schließen
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}
	
	/*
	 * SessionFactory lädt die Konfiguration und die Abbildungen.
	 */
	public static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure(hibernateconfigfilename);
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}
	
	// Methode die die aktive Session zurückgibt
	public Session getCurrentSession() {
		return currentSession;
	}
	
	// Methode mit der man die Session setzen kann
	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}
	
	// Methode die die aktive Transaction zurückgibt
	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}
	
	// Methode mit der man die Transaction setzen kann
	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}
	
	// Methode, um die übergebene Benutzerrolle der Datenbank hinzuzufügen
	public void persist(Benutzerrolle entity) {
		getCurrentSession().save(entity);
	}
	
	// Methode, um die übergeben Benutzerrolle zu aktualisieren
	public void update(Benutzerrolle entity) {
		getCurrentSession().update(entity);
	}
	
	// Methode, um eine Benutzerrolle anhand der Id zu finden
	// und zurückzugeben
	public Benutzerrolle findById(Integer id) {
		Benutzerrolle Benutzerrolle = (Benutzerrolle) getCurrentSession().get(Benutzerrolle.class, id);
		return Benutzerrolle;
	}
	
	public void delete(Benutzerrolle entity) {
		getCurrentSession().delete(entity);
	}
	
	public List<Benutzerrolle> findAll() {
		List<Benutzerrolle> BenutzerrolleListe = (List<Benutzerrolle>) getCurrentSession()
				.createQuery("from Benutzerrolle").list();
		return BenutzerrolleListe;
	}
	
	public void deleteAll() {
		List<Benutzerrolle> BenutzerrolleListe = findAll();
		for (Benutzerrolle Benutzerrolle : BenutzerrolleListe) {
			delete(Benutzerrolle);
		}
	}
	
}
