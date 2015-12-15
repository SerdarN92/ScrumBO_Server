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
	
}
