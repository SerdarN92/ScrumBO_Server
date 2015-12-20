package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.Benutzerrolle;

public class BenutzerrolleDao implements DaoInterface<Benutzerrolle, Integer> {
	
	private Session			currentSession			= null;
	private Transaction		currentTransaction		= null;
	private static String	hibernateconfigfilename	= "";
													
	public BenutzerrolleDao(String hibernateconfigfilename) {
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
	
	public void persist(Benutzerrolle entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(Benutzerrolle entity) {
		getCurrentSession().update(entity);
	}
	
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
