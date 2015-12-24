package service;

import java.util.List;

import dao.UserStoryDao;
import model.UserStory;

public class UserStoryService {
	
	private UserStoryDao userstoryDao;
	
	public UserStoryService(String hibernateconfigfilename) {
		this.userstoryDao = new UserStoryDao(hibernateconfigfilename);
	}
	
	public void persist(UserStory entity) {
		try {
			userstoryDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(UserStory entity) {
		try {
			userstoryDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean setSprintNull(Integer userstoryId) {
		try {
			return userstoryDao.setSprintNull(userstoryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public UserStory findById(Integer id) {
		UserStory userstory = null;
		try {
			userstory = userstoryDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userstory;
	}
	
	public void delete(Integer id) {
		try {
			UserStory userstory = userstoryDao.findById(id);
			userstoryDao.delete(userstory);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<UserStory> findAll() {
		try {
			return userstoryDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<UserStory> findAllByProductBacklogId(Integer id) {
		try {
			return userstoryDao.findAllByProductBacklogId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<UserStory> findAllBySprintId(Integer id) {
		try {
			return userstoryDao.findAllBySprintId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<UserStory> findAllNULLwithProductBacklogId(Integer productbacklogid) {
		try {
			return userstoryDao.findAllNULLwithProductBacklogId(productbacklogid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getUserStoryStatus(Integer userstoryId) {
		try {
			return userstoryDao.getUserStoryStatus(userstoryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void deleteAll() {
		try {
			userstoryDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UserStoryDao userstoryDao() {
		return userstoryDao;
	}
	
}
