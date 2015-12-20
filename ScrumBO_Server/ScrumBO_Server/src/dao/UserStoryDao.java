package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.UserStory;

public class UserStoryDao implements DaoInterface<UserStory, Integer> {
	
	private Session			currentSession			= null;
	private Transaction		currentTransaction		= null;
	private static String	hibernateconfigfilename	= "";
													
	public UserStoryDao(String hibernateconfigfilename) {
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
	
	public void persist(UserStory entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(UserStory entity) {
		getCurrentSession().update(entity);
	}
	
	public UserStory findById(Integer id) {
		UserStory userstory = (UserStory) getCurrentSession().get(UserStory.class, id);
		return userstory;
	}
	
	public void delete(UserStory entity) {
		getCurrentSession().delete(entity);
	}
	
	public List<UserStory> findAll() {
		List<UserStory> userstoryListe = (List<UserStory>) getCurrentSession().createQuery("from UserStory").list();
		return userstoryListe;
	}
	
	public List<UserStory> findAllByProductBacklogId(Integer productbacklogId) {
		List<UserStory> userstoryListe = (List<UserStory>) getCurrentSession()
				.createQuery("from UserStory where productbacklog_id like'" + productbacklogId + "'").list();
		return userstoryListe;
	}
	
	public List<UserStory> findAllBySprintId(Integer sprintId) {
		List<UserStory> userstoryListe = (List<UserStory>) getCurrentSession()
				.createQuery("from UserStory where sprint_id like'" + sprintId + "'").list();
		return userstoryListe;
	}
	
	public List<UserStory> findAllNULLwithProductBacklogId(Integer productbacklogid) {
		List<UserStory> userstoryListe = (List<UserStory>) getCurrentSession().createQuery(
				"from UserStory where productbacklog_id like'" + productbacklogid + "'" + "AND sprint_id IS NULL")
				.list();
		return userstoryListe;
	}
	
	public Integer getUserStoryStatus(Integer userstoryId) {
		UserStory userstory = (UserStory) getCurrentSession()
				.createQuery("FROM UserStory where userstory_id like '" + userstoryId + "'").uniqueResult();
		return userstory.getStatus();
	}
	
	public void deleteAll() {
		List<UserStory> userstoryListe = findAll();
		for (UserStory userstory : userstoryListe) {
			delete(userstory);
		}
	}
}
