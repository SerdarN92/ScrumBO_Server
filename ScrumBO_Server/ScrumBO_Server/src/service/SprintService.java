package service;

import java.util.List;

import dao.SprintDao;
import model.Sprint;

public class SprintService {
	
	private static SprintDao sprintDao;
	
	public SprintService(String hibernateconfigfilename) {
		sprintDao = new SprintDao(hibernateconfigfilename);
	}
	
	public void persist(Sprint entity) {
		sprintDao.openCurrentSessionwithTransaction();
		sprintDao.persist(entity);
		sprintDao.closeCurrentSessionwithTransaction();
	}
	
	public void update(Sprint entity) {
		sprintDao.openCurrentSessionwithTransaction();
		sprintDao.update(entity);
		sprintDao.closeCurrentSessionwithTransaction();
	}
	
	public Sprint findById(Integer id) {
		sprintDao.openCurrentSession();
		Sprint sprint = sprintDao.findById(id);
		sprintDao.closeCurrentSession();
		return sprint;
	}
	
	public Sprint findByProjectIdandCount(Integer count, Integer scrumprojektid) {
		sprintDao.openCurrentSession();
		Sprint sprint = sprintDao.findByProjectIdandCount(count, scrumprojektid);
		sprintDao.closeCurrentSession();
		return sprint;
	}
	
	public Sprint findByProjectIdandSprintNumber(Integer scrumprojektid, Integer sprintnumber) {
		sprintDao.openCurrentSession();
		Sprint sprint = sprintDao.findByProjectIdandSprintNumber(scrumprojektid, sprintnumber);
		sprintDao.closeCurrentSession();
		return sprint;
	}
	
	public void delete(Integer id) {
		sprintDao.openCurrentSessionwithTransaction();
		Sprint sprint = sprintDao.findById(id);
		sprintDao.delete(sprint);
		sprintDao.closeCurrentSessionwithTransaction();
	}
	
	public List<Sprint> findAll() {
		sprintDao.openCurrentSession();
		List<Sprint> sprintListe = sprintDao.findAll();
		sprintDao.closeCurrentSession();
		return sprintListe;
	}
	
	public void deleteAll() {
		sprintDao.openCurrentSessionwithTransaction();
		sprintDao.deleteAll();
		sprintDao.closeCurrentSessionwithTransaction();
	}
	
	public SprintDao sprintDao() {
		return sprintDao;
	}
	
	public Integer countSprintsToProject(Integer scrumprojektid) {
		Integer count = 0;
		sprintDao.openCurrentSession();
		count = sprintDao.countSprintsToProject(scrumprojektid);
		sprintDao.closeCurrentSession();
		return count;
	}
	
	public Integer countSprintsAnzahlToProject(Integer scrumprojektid) {
		Integer count = 0;
		sprintDao.openCurrentSession();
		count = sprintDao.countSprintsAnzahlToProject(scrumprojektid);
		sprintDao.closeCurrentSession();
		return count;
	}
	
}
