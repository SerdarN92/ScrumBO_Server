package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.Impediment;

public class ImpedimentDao implements DaoInterface<Impediment, Integer> {
	
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public ImpedimentDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfig);
	}
	
	public void persist(Impediment entity) {
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				s.save(entity);
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Impediment entity) {
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				s.update(entity);
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Impediment findById(Integer id) {
		Impediment impediment = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				impediment = (Impediment) s.get(Impediment.class, id);
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return impediment;
	}
	
	public List<Impediment> findByProjectId(Integer projectId) {
		List<Impediment> impedimentListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				impedimentListe = (List<Impediment>) s
						.createQuery("from Impediment where scrumprojekt_id like'" + projectId + "'").list();
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return impedimentListe;
	}
	
	public void delete(Impediment entity) {
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				s.delete(entity);
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Impediment> findAll() {
		List<Impediment> impedimentListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				impedimentListe = (List<Impediment>) s.createQuery("from Impediment").list();
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return impedimentListe;
	}
	
	public void deleteAll() {
		List<Impediment> impedimentListe = findAll();
		for (Impediment impediment : impedimentListe) {
			delete(impediment);
		}
	}
}
