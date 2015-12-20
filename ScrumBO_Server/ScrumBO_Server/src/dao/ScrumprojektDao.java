package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import model.Scrumprojekt;

public class ScrumprojektDao implements DaoInterface<Scrumprojekt, Integer> {
	
	private Session			currentSession			= null;
	private Transaction		currentTransaction		= null;
	private static String	hibernateconfigfilename	= "";
													
	public ScrumprojektDao(String hibernateconfigfilename) {
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
	
	public void persist(Scrumprojekt entity) {
		getCurrentSession().save(entity);
	}
	
	public void update(Scrumprojekt entity) {
		getCurrentSession().update(entity);
	}
	
	public Scrumprojekt findById(Integer id) {
		Scrumprojekt scrumprojekt = (Scrumprojekt) getCurrentSession().get(Scrumprojekt.class, id);
		return scrumprojekt;
	}
	
	public Scrumprojekt findByProjectname(String projectname) {
		List<Scrumprojekt> projectList = findAll();
		Scrumprojekt a = null;
		for (int i = 0; i < projectList.size(); i++) {
			if (projectList.get(i).getProjektname().equals(projectname))
				a = projectList.get(i);
		}
		return a;
	}
	
	public void delete(Scrumprojekt entity) {
		getCurrentSession().delete(entity);
	}
	
	public List<Scrumprojekt> findAll() {
		List<Scrumprojekt> scrumprojektListe = (List<Scrumprojekt>) getCurrentSession().createQuery("from Scrumprojekt")
				.list();
		return scrumprojektListe;
	}
	
	public void deleteAll() {
		List<Scrumprojekt> scrumprojektListe = findAll();
		for (Scrumprojekt scrumprojekt : scrumprojektListe) {
			delete(scrumprojekt);
		}
	}
	
}
