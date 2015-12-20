package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.Benutzer_Benutzerrolle_Scrumprojekt;

public class Benutzer_Benutzerrolle_ScrumprojektDao
		implements DaoInterface<Benutzer_Benutzerrolle_Scrumprojekt, Integer> {
		
	private Session			currentSession			= null;
	private Transaction		currentTransaction		= null;
	private static String	hibernateconfigfilename	= "";
													
	public Benutzer_Benutzerrolle_ScrumprojektDao(String hibernateconfigfilename) {
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
	
	public static SessionFactory getSessionFactory() {
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
	
	@Override
	public void persist(Benutzer_Benutzerrolle_Scrumprojekt entity) {
		getCurrentSession().save(entity);
		
	}
	
	@Override
	public void update(Benutzer_Benutzerrolle_Scrumprojekt entity) {
		getCurrentSession().update(entity);
		
	}
	
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findListByBenutzerId(Integer benutzerId) {
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzer_benutzerrolle_scrumprojekt_Liste = (List<Benutzer_Benutzerrolle_Scrumprojekt>) getCurrentSession()
				.createQuery("FROM Benutzer_Benutzerrolle_Scrumprojekt WHERE benutzerId LIKE'" + benutzerId + "'")
				.list();
		return benutzer_benutzerrolle_scrumprojekt_Liste;
	}
	
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findListByProjectId(Integer scrumprojektid) {
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzer_benutzerrolle_scrumprojekt_Liste = (List<Benutzer_Benutzerrolle_Scrumprojekt>) getCurrentSession()
				.createQuery(
						"from Benutzer_Benutzerrolle_Scrumprojekt where scrumprojektId like'" + scrumprojektid + "'")
				.list();
		return benutzer_benutzerrolle_scrumprojekt_Liste;
	}
	
	@Override
	public void delete(Benutzer_Benutzerrolle_Scrumprojekt entity) {
		getCurrentSession().delete(entity);
		
	}
	
	@Override
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findAll() {
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzerListe = (List<Benutzer_Benutzerrolle_Scrumprojekt>) getCurrentSession()
				.createQuery("from Benutzer_Benutzerrolle_Scrumprojekt").list();
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
		Benutzer_Benutzerrolle_Scrumprojekt benutzer = (Benutzer_Benutzerrolle_Scrumprojekt) getCurrentSession()
				.get(Benutzer_Benutzerrolle_Scrumprojekt.class, id);
		return benutzer;
	}
	
}
