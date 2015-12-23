package service;

import java.util.List;

import dao.SprintDao;
import model.Sprint;

public class SprintService {
	
	private SprintDao sprintDao;
	
	public SprintService(String hibernateconfigfilename) {
		this.sprintDao = new SprintDao(hibernateconfigfilename);
	}
	
	public void persist(Sprint entity) {
		try {
			sprintDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void update(Sprint entity) {
		try {
			sprintDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Sprint findById(Integer id) {
		try {
			return sprintDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Sprint findByProjectIdandSprintNumber(Integer scrumprojektid, Integer sprintnumber) {
		try {
			return sprintDao.findByProjectIdandSprintNumber(scrumprojektid, sprintnumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void delete(Integer id) {
		try {
			Sprint sprint = sprintDao.findById(id);
			sprintDao.delete(sprint);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Sprint> findAll() {
		try {
			return sprintDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void deleteAll() {
		try {
			sprintDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public SprintDao sprintDao() {
		return sprintDao;
	}
	
	public Integer countSprintsToProject(Integer scrumprojektid) {
		Integer count = 0;
		try {
			count = sprintDao.countSprintsToProject(scrumprojektid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public Integer countSprintsAnzahlToProject(Integer scrumprojektid) {
		Integer count = 0;
		try {
			count = sprintDao.countSprintsAnzahlToProject(scrumprojektid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
}
