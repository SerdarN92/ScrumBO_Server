package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.UserStoryTask;

public class UserStoryTaskDao implements DaoInterface<UserStoryTask, Integer> {
	
	private Session			currentSession		= null;
	private Transaction		currentTransaction	= null;
	private static String	hibernateconfigfilename;
	
	public UserStoryTaskDao(String hibernateconfigfilename) {
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
	}
	
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
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
	
	public void persist(UserStoryTask entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(UserStoryTask entity) {
		getCurrentSession().update(entity);
	}
	
	public UserStoryTask findById(Integer id) {
		UserStoryTask userstorytask = (UserStoryTask) getCurrentSession().get(UserStoryTask.class, id);
		return userstorytask;
	}
	
	public void delete(UserStoryTask entity) {
		getCurrentSession().delete(entity);
	}
	
	public List<UserStoryTask> findAll() {
		List<UserStoryTask> userstorytaskListe = (List<UserStoryTask>) getCurrentSession()
				.createQuery("from UserStoryTask").list();
		return userstorytaskListe;
	}
	
	public void deleteAll() {
		List<UserStoryTask> userstorytaskListe = findAll();
		for (UserStoryTask userstorytask : userstorytaskListe) {
			delete(userstorytask);
		}
	}
}
