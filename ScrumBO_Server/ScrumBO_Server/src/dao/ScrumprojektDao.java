package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.Scrumprojekt;

public class ScrumprojektDao implements DaoInterface<Scrumprojekt, Integer> {
	
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public ScrumprojektDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfig);
	}
	
	public void persist(Scrumprojekt entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.save(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public void update(Scrumprojekt entity) {
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.update(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public Scrumprojekt findById(Integer id) {
		Scrumprojekt scrumprojekt = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		scrumprojekt = (Scrumprojekt) s.get(Scrumprojekt.class, id);
		s.getTransaction().commit();
		s.close();
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
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		s.delete(entity);
		s.getTransaction().commit();
		s.close();
	}
	
	public List<Scrumprojekt> findAll() {
		List<Scrumprojekt> scrumprojektListe = null;
		Session s = HibernateUtil.openSession();
		s.beginTransaction();
		scrumprojektListe = (List<Scrumprojekt>) s.createQuery("from Scrumprojekt").list();
		s.getTransaction().commit();
		s.close();
		return scrumprojektListe;
	}
	
	public void deleteAll() {
		List<Scrumprojekt> scrumprojektListe = findAll();
		for (Scrumprojekt scrumprojekt : scrumprojektListe) {
			delete(scrumprojekt);
		}
	}
	
}
