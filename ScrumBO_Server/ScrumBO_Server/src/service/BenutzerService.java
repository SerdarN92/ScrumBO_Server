package service;

import java.util.List;

import dao.BenutzerDao;
import model.Benutzer;

public class BenutzerService {
	
	private BenutzerDao benutzerDao = null;
	
	public BenutzerService(String hibernateconfig) {
		this.benutzerDao = new BenutzerDao(hibernateconfig);
	}
	
	public void persist(Benutzer entity) {
		try {
			benutzerDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Benutzer entity) {
		try {
			benutzerDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Benutzer findById(Integer id) {
		try {
			return benutzerDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Benutzer findByEmail(String email) {
		try {
			return benutzerDao.findByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(Integer id) {
		try {
			Benutzer benutzer = benutzerDao.findById(id);
			benutzerDao.delete(benutzer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Benutzer> findAll() {
		try {
			return benutzerDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			benutzerDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BenutzerDao benutzerDao() {
		return benutzerDao;
	}
	
}