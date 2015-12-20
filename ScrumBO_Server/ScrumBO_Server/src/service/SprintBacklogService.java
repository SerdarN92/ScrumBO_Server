package service;

import java.util.List;

import dao.SprintBacklogDao;
import model.SprintBacklog;

public class SprintBacklogService {
	
	private SprintBacklogDao sprintbacklogDao;
	
	public SprintBacklogService(String hibernateconfigfilename) {
		sprintbacklogDao = new SprintBacklogDao(hibernateconfigfilename);
	}
	
	public void persist(SprintBacklog entity) {
		sprintbacklogDao.openCurrentSessionwithTransaction();
		sprintbacklogDao.persist(entity);
		sprintbacklogDao.closeCurrentSessionwithTransaction();
	}
	
	public void update(SprintBacklog entity) {
		sprintbacklogDao.openCurrentSessionwithTransaction();
		sprintbacklogDao.update(entity);
		sprintbacklogDao.closeCurrentSessionwithTransaction();
	}
	
	public SprintBacklog findById(Integer id) {
		sprintbacklogDao.openCurrentSession();
		SprintBacklog sprintbacklog = sprintbacklogDao.findById(id);
		sprintbacklogDao.closeCurrentSession();
		return sprintbacklog;
	}
	
	public void delete(Integer id) {
		sprintbacklogDao.openCurrentSessionwithTransaction();
		SprintBacklog sprintbacklog = sprintbacklogDao.findById(id);
		sprintbacklogDao.delete(sprintbacklog);
		sprintbacklogDao.closeCurrentSessionwithTransaction();
	}
	
	public List<SprintBacklog> findAll() {
		sprintbacklogDao.openCurrentSession();
		List<SprintBacklog> sprintbacklogListe = sprintbacklogDao.findAll();
		sprintbacklogDao.closeCurrentSession();
		return sprintbacklogListe;
	}
	
	public void deleteAll() {
		sprintbacklogDao.openCurrentSessionwithTransaction();
		sprintbacklogDao.deleteAll();
		sprintbacklogDao.closeCurrentSessionwithTransaction();
	}
	
	public SprintBacklogDao sprintbacklogDao() {
		return sprintbacklogDao;
	}
	
}
