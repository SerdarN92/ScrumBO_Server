package service;

import java.util.List;

import dao.Benutzer_Benutzerrolle_ScrumprojektDao;
import model.Benutzer_Benutzerrolle_Scrumprojekt;

public class Benutzer_Benutzerrolle_ScrumprojektService {
	
	private Benutzer_Benutzerrolle_ScrumprojektDao bbsDao = null;
	
	public Benutzer_Benutzerrolle_ScrumprojektService(String hibernateconfigfilename) {
		this.bbsDao = new Benutzer_Benutzerrolle_ScrumprojektDao(hibernateconfigfilename);
	}
	
	public void persist(Benutzer_Benutzerrolle_Scrumprojekt entity) {
		try {
			bbsDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Benutzer_Benutzerrolle_Scrumprojekt entity) {
		try {
			bbsDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Benutzer_Benutzerrolle_Scrumprojekt findById(Integer id) {
		try {
			return bbsDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findListByBenutzerId(Integer benutzerId) {
		
		try {
			return bbsDao.findListByBenutzerId(benutzerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findListByProjectId(Integer scrumprojektid) {
		try {
			return bbsDao.findListByProjectId(scrumprojektid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(Integer id) {
		try {
			Benutzer_Benutzerrolle_Scrumprojekt entity = bbsDao.findById(id);
			bbsDao.delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteBenutzer(Integer benutzerId) {
		try {
			bbsDao.deleteBenutzer(benutzerId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteProject(Integer scrumprojektId) {
		try {
			bbsDao.deleteProject(scrumprojektId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findAll() {
		try {
			return bbsDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			bbsDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Benutzer_Benutzerrolle_ScrumprojektDao bbsdaoDao() {
		return bbsDao;
	}
	
	public boolean checkAdmission(Integer benutzerId, Integer scrumprojektId) {
		boolean admission = false;
		try {
			if (bbsDao.checkAdmission(benutzerId, scrumprojektId)) {
				admission = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admission;
	}
	
	public boolean checkAdmin(Integer benutzerId) {
		boolean admin = false;
		try {
			if (bbsDao.checkAdmin(benutzerId)) {
				admin = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
	}
	
}
