package service;

import java.util.List;

import dao.ImpedimentDao;
import model.Impediment;

public class ImpedimentService {
	
	private static ImpedimentDao impedimentDao;
	
	public ImpedimentService(String hibernateconfigfilename) {
		impedimentDao = new ImpedimentDao(hibernateconfigfilename);
	}
	
	public void persist(Impediment entity) {
		impedimentDao.openCurrentSessionwithTransaction();
		impedimentDao.persist(entity);
		impedimentDao.closeCurrentSessionwithTransaction();
	}
	
	public void update(Impediment entity) {
		impedimentDao.openCurrentSessionwithTransaction();
		impedimentDao.update(entity);
		impedimentDao.closeCurrentSessionwithTransaction();
	}
	
	public Impediment findById(Integer id) {
		impedimentDao.openCurrentSession();
		Impediment impediment = impedimentDao.findById(id);
		impedimentDao.closeCurrentSession();
		return impediment;
	}
	
	public List<Impediment> findByProjectId(Integer id) {
		impedimentDao.openCurrentSession();
		List<Impediment> impedimentListe = impedimentDao.findByProjectId(id);
		impedimentDao.closeCurrentSession();
		return impedimentListe;
	}
	
	public void delete(Integer id) {
		impedimentDao.openCurrentSessionwithTransaction();
		Impediment impediment = impedimentDao.findById(id);
		impedimentDao.delete(impediment);
		impedimentDao.closeCurrentSessionwithTransaction();
	}
	
	public List<Impediment> findAll() {
		impedimentDao.openCurrentSession();
		List<Impediment> impedimentListe = impedimentDao.findAll();
		impedimentDao.closeCurrentSession();
		return impedimentListe;
	}
	
	public void deleteAll() {
		impedimentDao.openCurrentSessionwithTransaction();
		impedimentDao.deleteAll();
		impedimentDao.closeCurrentSessionwithTransaction();
	}
	
	public ImpedimentDao impedimentDao() {
		return impedimentDao;
	}
	
}
