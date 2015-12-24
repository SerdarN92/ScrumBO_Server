package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.DefinitionOfDone;

public class DefinitionOfDoneDao implements DaoInterface<DefinitionOfDone, Integer> {
	
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public DefinitionOfDoneDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfigfilename);
	}
	
	public void persist(DefinitionOfDone entity) {
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
	
	public void update(DefinitionOfDone entity) {
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
	
	public DefinitionOfDone findById(Integer id) {
		DefinitionOfDone definitionofdone = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				definitionofdone = (DefinitionOfDone) s.get(DefinitionOfDone.class, id);
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
		return definitionofdone;
	}
	
	public List<DefinitionOfDone> findByUserstoryId(Integer userstoryId) {
		List<DefinitionOfDone> definitionofdoneListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				definitionofdoneListe = (List<DefinitionOfDone>) s
						.createQuery("from DefinitionOfDone where userstory_id like '" + userstoryId + "'").list();
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
		return definitionofdoneListe;
	}
	
	public void delete(DefinitionOfDone entity) {
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
	
	public List<DefinitionOfDone> findAll() {
		List<DefinitionOfDone> definitionofdoneListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				definitionofdoneListe = (List<DefinitionOfDone>) s.createQuery("from DefinitionOfDone").list();
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
		return definitionofdoneListe;
	}
	
	public void deleteAll() {
		List<DefinitionOfDone> definitionofdoneListe = findAll();
		for (DefinitionOfDone definitionofdone : definitionofdoneListe) {
			delete(definitionofdone);
		}
	}
}
