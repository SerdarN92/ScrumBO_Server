package service;

import java.util.List;

import dao.BenutzerrolleDao;
import model.Benutzerrolle;

/*
 * Dieser Service wird genutzt, um Zugriff �ber das Data Access Object der Benutzerrolle
 * auf der Datenbank Zugriff auf die Tabelle Benutzerrolle zu haben.
 */
public class BenutzerrolleService {
	
	private static BenutzerrolleDao BenutzerrolleDao;
	
	public BenutzerrolleService(String hibernateconfigfilename) {
		BenutzerrolleDao = new BenutzerrolleDao(hibernateconfigfilename);
	}
	
	public void persist(Benutzerrolle entity) {
		BenutzerrolleDao.openCurrentSessionwithTransaction();
		BenutzerrolleDao.persist(entity);
		BenutzerrolleDao.closeCurrentSessionwithTransaction();
	}
	
	public void update(Benutzerrolle entity) {
		BenutzerrolleDao.openCurrentSessionwithTransaction();
		BenutzerrolleDao.update(entity);
		BenutzerrolleDao.closeCurrentSessionwithTransaction();
	}
	
	public Benutzerrolle findById(Integer id) {
		BenutzerrolleDao.openCurrentSession();
		Benutzerrolle Benutzerrolle = BenutzerrolleDao.findById(id);
		BenutzerrolleDao.closeCurrentSession();
		return Benutzerrolle;
	}
	
	public void delete(Integer id) {
		BenutzerrolleDao.openCurrentSessionwithTransaction();
		Benutzerrolle Benutzerrolle = BenutzerrolleDao.findById(id);
		BenutzerrolleDao.delete(Benutzerrolle);
		BenutzerrolleDao.closeCurrentSessionwithTransaction();
	}
	
	public List<Benutzerrolle> findAll() {
		BenutzerrolleDao.openCurrentSession();
		List<Benutzerrolle> BenutzerrolleListe = BenutzerrolleDao.findAll();
		BenutzerrolleDao.closeCurrentSession();
		return BenutzerrolleListe;
	}
	
	public void deleteAll() {
		BenutzerrolleDao.openCurrentSessionwithTransaction();
		BenutzerrolleDao.deleteAll();
		BenutzerrolleDao.closeCurrentSessionwithTransaction();
	}
	
	public BenutzerrolleDao BenutzerrolleDao() {
		return BenutzerrolleDao;
	}
	
}
