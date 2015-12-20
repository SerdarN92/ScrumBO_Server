package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.SprintBacklog;

public class SprintBacklogDao implements DaoInterface<SprintBacklog, Integer> {
	
	private Session			currentSession			= null;
	private Transaction		currentTransaction		= null;
	private static String	hibernateconfigfilename	= "";
													
	public SprintBacklogDao(String hibernateconfigfilename) {
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
	
	public void persist(SprintBacklog entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(SprintBacklog entity) {
		getCurrentSession().update(entity);
	}
	
	public SprintBacklog findById(Integer id) {
		SprintBacklog sprintbacklog = (SprintBacklog) getCurrentSession().get(SprintBacklog.class, id);
		return sprintbacklog;
	}
	
	public void delete(SprintBacklog entity) {
		getCurrentSession().delete(entity);
	}
	
	public List<SprintBacklog> findAll() {
		List<SprintBacklog> sprintbacklogListe = (List<SprintBacklog>) getCurrentSession()
				.createQuery("from SprintBacklog").list();
		return sprintbacklogListe;
	}
	
	public void deleteAll() {
		List<SprintBacklog> sprintbacklogListe = findAll();
		for (SprintBacklog sprintbacklog : sprintbacklogListe) {
			delete(sprintbacklog);
		}
	}
}
