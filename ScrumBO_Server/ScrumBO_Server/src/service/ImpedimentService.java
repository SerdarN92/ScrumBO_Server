package service;

import java.util.List;

import dao.ImpedimentDao;
import model.Impediment;

public class ImpedimentService {
	
	private ImpedimentDao impedimentDao = null;
	
	public ImpedimentService(String hibernateconfigfilename) {
		this.impedimentDao = new ImpedimentDao(hibernateconfigfilename);
	}
	
	public void persist(Impediment entity) {
		try {
			impedimentDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Impediment entity) {
		try {
			impedimentDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Impediment findById(Integer id) {
		try {
			return impedimentDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Impediment> findByProjectId(Integer projectId) {
		try {
			return impedimentDao.findByProjectId(projectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(Integer id) {
		try {
			Impediment impediment = impedimentDao.findById(id);
			impedimentDao.delete(impediment);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Impediment> findAll() {
		try {
			return impedimentDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			impedimentDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ImpedimentDao impedimentDao() {
		return impedimentDao;
	}
	
}
