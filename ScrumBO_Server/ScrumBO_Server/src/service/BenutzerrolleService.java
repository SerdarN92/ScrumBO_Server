package service;

import java.util.List;

import dao.BenutzerrolleDao;
import model.Benutzerrolle;

public class BenutzerrolleService {
	
	private BenutzerrolleDao benutzerrolleDao = null;
	
	public BenutzerrolleService(String hibernateconfigfilename) {
		this.benutzerrolleDao = new BenutzerrolleDao(hibernateconfigfilename);
	}
	
	public void persist(Benutzerrolle entity) {
		try {
			benutzerrolleDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Benutzerrolle entity) {
		try {
			benutzerrolleDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Benutzerrolle findById(Integer id) {
		try {
			return benutzerrolleDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(Integer id) {
		try {
			Benutzerrolle benutzerrolle = benutzerrolleDao.findById(id);
			benutzerrolleDao.delete(benutzerrolle);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Benutzerrolle> findAll() {
		try {
			return benutzerrolleDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			benutzerrolleDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BenutzerrolleDao benutzerrolleDao() {
		return benutzerrolleDao;
	}
	
}