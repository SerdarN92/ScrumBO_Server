package service;

import java.util.List;

import dao.BenutzerDao;
import model.Benutzer;

public class BenutzerService {
	
	private BenutzerDao benutzerDao;
	
	public BenutzerService(String hibernateconfigfilename) {
		benutzerDao = new BenutzerDao(hibernateconfigfilename);
	}
	
	public void persist(Benutzer entity) {
		benutzerDao.openCurrentSessionwithTransaction();
		benutzerDao.persist(entity);
		benutzerDao.closeCurrentSessionwithTransaction();
	}
	
	public void update(Benutzer entity) {
		benutzerDao.openCurrentSessionwithTransaction();
		benutzerDao.update(entity);
		benutzerDao.closeCurrentSessionwithTransaction();
	}
	
	public Benutzer findById(Integer id) {
		benutzerDao.openCurrentSession();
		Benutzer benutzer = benutzerDao.findById(id);
		benutzerDao.closeCurrentSession();
		return benutzer;
	}
	
	public Benutzer findByEmail(String email) {
		benutzerDao.openCurrentSession();
		Benutzer a = benutzerDao.findByEmail(email);
		benutzerDao.closeCurrentSession();
		return a;
	}
	
	public void delete(Integer id) {
		benutzerDao.openCurrentSessionwithTransaction();
		Benutzer benutzer = benutzerDao.findById(id);
		benutzerDao.delete(benutzer);
		benutzerDao.closeCurrentSessionwithTransaction();
	}
	
	public List<Benutzer> findAll() {
		benutzerDao.openCurrentSession();
		List<Benutzer> benutzerListe = benutzerDao.findAll();
		benutzerDao.closeCurrentSession();
		return benutzerListe;
	}
	
	public void deleteAll() {
		benutzerDao.openCurrentSessionwithTransaction();
		benutzerDao.deleteAll();
		benutzerDao.closeCurrentSessionwithTransaction();
	}
	
	public BenutzerDao benutzerDao() {
		return benutzerDao;
	}
	
}