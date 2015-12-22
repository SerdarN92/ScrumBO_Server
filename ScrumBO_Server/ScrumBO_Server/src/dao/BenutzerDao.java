package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.Benutzer;

public class BenutzerDao implements DaoInterface<Benutzer, Integer> {
	
	private Session		currentSession			= null;
	private Transaction	currentTransaction		= null;
	private String		hibernateconfigfilename	= "";
												
	public BenutzerDao() {
	
	}
	
	public BenutzerDao(String hibernateconfigfilename) {
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
	
	public void persist(Benutzer entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(Benutzer entity) {
		getCurrentSession().update(entity);
	}
	
	public Benutzer findById(Integer id) {
		Benutzer benutzer = (Benutzer) getCurrentSession().get(Benutzer.class, id);
		return benutzer;
	}
	
	public Benutzer findByEmail(String email) {
		List<Benutzer> benutzerList = findAll();
		Benutzer a = null;
		for (int i = 0; i < benutzerList.size(); i++) {
			if (benutzerList.get(i).getEmail().equals(email))
				a = benutzerList.get(i);
		}
		return a;
	}
	
	public void delete(Benutzer entity) {
		getCurrentSession().delete(entity);
	}
	
	public List<Benutzer> findAll() {
		List<Benutzer> benutzerListe = (List<Benutzer>) getCurrentSession().createQuery("from Benutzer").list();
		return benutzerListe;
	}
	
	public void deleteAll() {
		List<Benutzer> benutzerListe = findAll();
		for (Benutzer benutzer : benutzerListe) {
			delete(benutzer);
		}
	}
	
}
