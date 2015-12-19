package service;

import java.util.List;

import dao.Benutzer_Benutzerrolle_ScrumprojektDao;
import model.Benutzer_Benutzerrolle_Scrumprojekt;

public class Benutzer_Benutzerrolle_ScrumprojektService {
	
	private static Benutzer_Benutzerrolle_ScrumprojektDao service;
	
	public Benutzer_Benutzerrolle_ScrumprojektService(String hibernateconfigfilename) {
		service = new Benutzer_Benutzerrolle_ScrumprojektDao(hibernateconfigfilename);
	}
	
	public void persist(Benutzer_Benutzerrolle_Scrumprojekt entity) {
		service.openCurrentSessionwithTransaction();
		service.persist(entity);
		service.closeCurrentSessionwithTransaction();
	}
	
	public void update(Benutzer_Benutzerrolle_Scrumprojekt entity) {
		service.openCurrentSessionwithTransaction();
		service.update(entity);
		service.closeCurrentSessionwithTransaction();
	}
	
	public Benutzer_Benutzerrolle_Scrumprojekt findById(Integer id) {
		service.openCurrentSession();
		Benutzer_Benutzerrolle_Scrumprojekt benutzer = service.findById(id);
		service.closeCurrentSession();
		return benutzer;
	}
	
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findListById(Integer id) {
		service.openCurrentSession();
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzer = service.findListById(id);
		service.closeCurrentSession();
		return benutzer;
	}
	
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findListByProjectId(Integer scrumprojektid) {
		service.openCurrentSession();
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzer = service.findListByProjectId(scrumprojektid);
		service.closeCurrentSession();
		return benutzer;
	}
	
	public void delete(Integer id) {
		service.openCurrentSessionwithTransaction();
		Benutzer_Benutzerrolle_Scrumprojekt benutzer = service.findById(id);
		service.delete(benutzer);
		service.closeCurrentSessionwithTransaction();
	}
	
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findAll() {
		service.openCurrentSession();
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzerListe = service.findAll();
		service.closeCurrentSession();
		return benutzerListe;
	}
	
	public void deleteAll() {
		service.openCurrentSessionwithTransaction();
		service.deleteAll();
		service.closeCurrentSessionwithTransaction();
	}
	
	public Benutzer_Benutzerrolle_ScrumprojektDao serviceDao() {
		return service;
	}
	
}
