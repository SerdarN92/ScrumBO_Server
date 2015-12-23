package dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.ProductBacklog;
import model.UserStory;

public class ProductBacklogDao implements DaoInterface<ProductBacklog, Integer> {
	
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public ProductBacklogDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfig);
	}
	
	public void persist(ProductBacklog entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.save(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public void update(ProductBacklog entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.update(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	// LinkedHashSet, damit keine doppelten UserStorys in der Liste vorhanden
	// sind
	public ProductBacklog findById(Integer id) {
		ProductBacklog productbacklog = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		productbacklog = (ProductBacklog) s.get(ProductBacklog.class, id);
		s.getTransaction().commit();
		s.close();
		List<UserStory> liste = productbacklog.getUserstory();
		List<UserStory> liste2 = new ArrayList<UserStory>(new LinkedHashSet<UserStory>(liste));
		productbacklog.setUserstory(liste2);
		return productbacklog;
	}
	
	public void delete(ProductBacklog entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.delete(entity);
		s.getTransaction().commit();
		s.close();
		
	}
	
	public List<ProductBacklog> findAll() {
		List<ProductBacklog> productbacklogListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		productbacklogListe = (List<ProductBacklog>) s.createQuery("from ProductBacklog").list();
		s.getTransaction().commit();
		s.close();
		
		return productbacklogListe;
	}
	
	public List<ProductBacklog> findAllByProjectId(Integer projectId) {
		List<ProductBacklog> productbacklogListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		productbacklogListe = (List<ProductBacklog>) s
				.createQuery("from ProductBacklog where scrumprojekt_id like'" + projectId + "'").list();
		s.getTransaction().commit();
		s.close();
		
		return productbacklogListe;
	}
	
	public void deleteAll() {
		List<ProductBacklog> productbacklogListe = findAll();
		for (ProductBacklog productbacklog : productbacklogListe) {
			delete(productbacklog);
		}
	}
	
}
