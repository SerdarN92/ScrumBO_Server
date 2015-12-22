package dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.ProductBacklog;
import model.UserStory;

public class ProductBacklogDao implements DaoInterface<ProductBacklog, Integer> {
	
	private Session		currentSession			= null;
	private Transaction	currentTransaction		= null;
	private String		hibernateconfigfilename	= "";
												
	public ProductBacklogDao(String hibernateconfigfilename) {
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
	
	public void persist(ProductBacklog entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(ProductBacklog entity) {
		getCurrentSession().update(entity);
	}
	
	// LinkedHashSet, damit keine doppelten UserStorys in der Liste vorhanden
	// sind
	public ProductBacklog findById(Integer id) {
		ProductBacklog productbacklog = (ProductBacklog) getCurrentSession().get(ProductBacklog.class, id);
		List<UserStory> liste = productbacklog.getUserstory();
		List<UserStory> liste2 = new ArrayList<UserStory>(new LinkedHashSet<UserStory>(liste));
		productbacklog.setUserstory(liste2);
		return productbacklog;
	}
	
	public void delete(ProductBacklog entity) {
		getCurrentSession().delete(entity);
	}
	
	public List<ProductBacklog> findAll() {
		List<ProductBacklog> productbacklogListe = (List<ProductBacklog>) getCurrentSession()
				.createQuery("from ProductBacklog").list();
		return productbacklogListe;
	}
	
	public List<ProductBacklog> findAllByProjectId(Integer projectId) {
		List<ProductBacklog> productbacklogListe = (List<ProductBacklog>) getCurrentSession()
				.createQuery("from ProductBacklog where scrumprojekt_id like'" + projectId + "'").list();
		return productbacklogListe;
	}
	
	public void deleteAll() {
		List<ProductBacklog> productbacklogListe = findAll();
		for (ProductBacklog productbacklog : productbacklogListe) {
			delete(productbacklog);
		}
	}
	
}
