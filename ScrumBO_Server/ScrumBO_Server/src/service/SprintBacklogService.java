package service;

import java.util.List;

import dao.SprintBacklogDao;
import model.SprintBacklog;

public class SprintBacklogService {
	
	private SprintBacklogDao sprintbacklogDao;
	
	public SprintBacklogService(String hibernateconfigfilename) {
		this.sprintbacklogDao = new SprintBacklogDao(hibernateconfigfilename);
	}
	
	public void persist(SprintBacklog entity) {
		try {
			sprintbacklogDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(SprintBacklog entity) {
		try {
			sprintbacklogDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SprintBacklog findById(Integer id) {
		try {
			return sprintbacklogDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(Integer id) {
		try {
			SprintBacklog sprintbacklog = sprintbacklogDao.findById(id);
			sprintbacklogDao.delete(sprintbacklog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<SprintBacklog> findAll() {
		try {
			return sprintbacklogDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			sprintbacklogDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SprintBacklogDao sprintbacklogDao() {
		return sprintbacklogDao;
	}
	
}
