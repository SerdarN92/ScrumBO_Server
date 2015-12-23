package service;

import java.util.List;

import dao.ScrumprojektDao;
import model.Scrumprojekt;

public class ScrumprojektService {
	
	private ScrumprojektDao scrumprojektDao;
	
	public ScrumprojektService(String hibernateconfigfilename) {
		this.scrumprojektDao = new ScrumprojektDao(hibernateconfigfilename);
	}
	
	public void persist(Scrumprojekt entity) {
		try {
			scrumprojektDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Scrumprojekt entity) {
		try {
			scrumprojektDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Scrumprojekt findById(Integer id) {
		try {
			return scrumprojektDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Scrumprojekt findByProjectname(String projectname) {
		try {
			return scrumprojektDao.findByProjectname(projectname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void delete(Integer id) {
		try {
			Scrumprojekt scrumprojekt = scrumprojektDao.findById(id);
			scrumprojektDao.delete(scrumprojekt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Scrumprojekt> findAll() {
		try {
			return scrumprojektDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			scrumprojektDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ScrumprojektDao ScrumprojektDao() {
		return scrumprojektDao;
	}
	
}
