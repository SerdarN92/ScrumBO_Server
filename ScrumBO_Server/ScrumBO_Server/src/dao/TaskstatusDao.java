package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.Taskstatus;

public class TaskstatusDao implements DaoInterface<Taskstatus, Integer> {
	
	private Session			currentSession			= null;
	private Transaction		currentTransaction		= null;
	private static String	hibernateconfigfilename	= "";
													
	public TaskstatusDao(String hibernateconfigfilename) {
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
	
	public void persist(Taskstatus entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(Taskstatus entity) {
		getCurrentSession().update(entity);
	}
	
	public Taskstatus findById(Integer id) {
		Taskstatus taskstatus = (Taskstatus) getCurrentSession().get(Taskstatus.class, id);
		return taskstatus;
	}
	
	public void delete(Taskstatus entity) {
		getCurrentSession().delete(entity);
	}
	
	public List<Taskstatus> findAll() {
		List<Taskstatus> taskstatusListe = (List<Taskstatus>) getCurrentSession().createQuery("from Taskstatus").list();
		return taskstatusListe;
	}
	
	public void deleteAll() {
		List<Taskstatus> taskstatusListe = findAll();
		for (Taskstatus taskstatus : taskstatusListe) {
			delete(taskstatus);
		}
	}
}
