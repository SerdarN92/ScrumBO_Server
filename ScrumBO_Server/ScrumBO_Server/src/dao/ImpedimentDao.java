package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.Impediment;

public class ImpedimentDao implements DaoInterface<Impediment, Integer> {
	
	private Session			currentSession			= null;
	private Transaction		currentTransaction		= null;
	private static String	hibernateconfigfilename	= "";
													
	public ImpedimentDao(String hibernateconfigfilename) {
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
	
	public void persist(Impediment entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(Impediment entity) {
		getCurrentSession().update(entity);
	}
	
	public Impediment findById(Integer id) {
		Impediment impediment = (Impediment) getCurrentSession().get(Impediment.class, id);
		return impediment;
	}
	
	public List<Impediment> findByProjectId(Integer projectId) {
		List<Impediment> impedimentListe = (List<Impediment>) getCurrentSession()
				.createQuery("from Impediment where scrumprojekt_id like'" + projectId + "'").list();
		return impedimentListe;
	}
	
	public void delete(Impediment entity) {
		getCurrentSession().delete(entity);
	}
	
	public List<Impediment> findAll() {
		List<Impediment> impedimentListe = (List<Impediment>) getCurrentSession().createQuery("from Impediment").list();
		return impedimentListe;
	}
	
	public void deleteAll() {
		List<Impediment> impedimentListe = findAll();
		for (Impediment impediment : impedimentListe) {
			delete(impediment);
		}
	}
}
