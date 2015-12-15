package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.DefinitionOfDone;

public class DefinitionOfDoneDao implements DaoInterface<DefinitionOfDone, Integer> {
	
	private Session			currentSession		= null;
	private Transaction		currentTransaction	= null;
	private static String	hibernateconfigfilename;
	
	public DefinitionOfDoneDao(String hibernateconfigfilename) {
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
	
	public void persist(DefinitionOfDone entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(DefinitionOfDone entity) {
		getCurrentSession().update(entity);
	}
	
	public DefinitionOfDone findById(Integer id) {
		DefinitionOfDone definitionofdone = (DefinitionOfDone) getCurrentSession().get(DefinitionOfDone.class, id);
		return definitionofdone;
	}
	
	public List<DefinitionOfDone> findByUserstoryId(Integer id) {
		List<DefinitionOfDone> definitionofdoneListe = (List<DefinitionOfDone>) getCurrentSession()
				.createQuery("from DefinitionOfDone where userstory_id like '" + id + "'").list();
		return definitionofdoneListe;
	}
	
	public void delete(DefinitionOfDone entity) {
		getCurrentSession().delete(entity);
	}
	
	public List<DefinitionOfDone> findAll() {
		List<DefinitionOfDone> definitionofdoneListe = (List<DefinitionOfDone>) getCurrentSession()
				.createQuery("from DefinitionOfDone").list();
		return definitionofdoneListe;
	}
	
	public void deleteAll() {
		List<DefinitionOfDone> definitionofdoneListe = findAll();
		for (DefinitionOfDone definitionofdone : definitionofdoneListe) {
			delete(definitionofdone);
		}
	}
}
