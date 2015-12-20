package service;

import java.util.List;

import dao.ScrumprojektDao;
import model.Scrumprojekt;

public class ScrumprojektService {
	
	private ScrumprojektDao scrumprojektDao;
	
	public ScrumprojektService(String hibernateconfigfilename) {
		scrumprojektDao = new ScrumprojektDao(hibernateconfigfilename);
	}
	
	public void persist(Scrumprojekt entity) {
		scrumprojektDao.openCurrentSessionwithTransaction();
		scrumprojektDao.persist(entity);
		scrumprojektDao.closeCurrentSessionwithTransaction();
	}
	
	public void update(Scrumprojekt entity) {
		scrumprojektDao.openCurrentSessionwithTransaction();
		scrumprojektDao.update(entity);
		scrumprojektDao.closeCurrentSessionwithTransaction();
	}
	
	public Scrumprojekt findById(Integer id) {
		scrumprojektDao.openCurrentSession();
		Scrumprojekt scrumprojekt = scrumprojektDao.findById(id);
		scrumprojektDao.closeCurrentSession();
		return scrumprojekt;
	}
	
	public Scrumprojekt findByProjectname(String projectname) {
		scrumprojektDao.openCurrentSession();
		Scrumprojekt scrumprojekt = scrumprojektDao.findByProjectname(projectname);
		scrumprojektDao.closeCurrentSession();
		return scrumprojekt;
	}
	
	public void delete(Integer id) {
		scrumprojektDao.openCurrentSessionwithTransaction();
		Scrumprojekt scrumprojekt = scrumprojektDao.findById(id);
		scrumprojektDao.delete(scrumprojekt);
		scrumprojektDao.closeCurrentSessionwithTransaction();
	}
	
	public List<Scrumprojekt> findAll() {
		scrumprojektDao.openCurrentSession();
		List<Scrumprojekt> scrumprojektListe = scrumprojektDao.findAll();
		scrumprojektDao.closeCurrentSession();
		return scrumprojektListe;
	}
	
	public void deleteAll() {
		scrumprojektDao.openCurrentSessionwithTransaction();
		scrumprojektDao.deleteAll();
		scrumprojektDao.closeCurrentSessionwithTransaction();
	}
	
	public ScrumprojektDao ScrumprojektDao() {
		return scrumprojektDao;
	}
	
}
