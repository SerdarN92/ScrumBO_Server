package service;

import java.util.List;

import dao.DefinitionOfDoneDao;
import model.DefinitionOfDone;

public class DefinitionOfDoneService {
	
	private DefinitionOfDoneDao dodDao = null;
	
	public DefinitionOfDoneService(String hibernateconfigfilename) {
		this.dodDao = new DefinitionOfDoneDao(hibernateconfigfilename);
	}
	
	public void persist(DefinitionOfDone entity) {
		try {
			dodDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(DefinitionOfDone entity) {
		try {
			dodDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DefinitionOfDone findById(Integer id) {
		try {
			return dodDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<DefinitionOfDone> findByUserstoryId(Integer userstoryId) {
		try {
			return dodDao.findByUserstoryId(userstoryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(Integer id) {
		try {
			DefinitionOfDone dod = dodDao.findById(id);
			dodDao.delete(dod);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<DefinitionOfDone> findAll() {
		try {
			return dodDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			dodDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DefinitionOfDoneDao getDodDao() {
		return dodDao;
	}
	
}
