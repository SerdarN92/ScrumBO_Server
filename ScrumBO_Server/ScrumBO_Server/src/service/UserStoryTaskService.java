package service;

import java.util.List;

import dao.UserStoryTaskDao;
import model.UserStoryTask;

public class UserStoryTaskService {
	
	private UserStoryTaskDao userstorytaskDao;
	
	public UserStoryTaskService(String hibernateconfigfilename) {
		this.userstorytaskDao = new UserStoryTaskDao(hibernateconfigfilename);
	}
	
	public void persist(UserStoryTask entity) {
		try {
			userstorytaskDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(UserStoryTask entity) {
		try {
			userstorytaskDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UserStoryTask findById(Integer id) {
		UserStoryTask userstorytask = null;
		try {
			userstorytask = userstorytaskDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userstorytask;
	}
	
	public void delete(Integer id) {
		try {
			UserStoryTask userstorytask = userstorytaskDao.findById(id);
			userstorytaskDao.delete(userstorytask);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<UserStoryTask> findAll() {
		try {
			return userstorytaskDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<UserStoryTask> findAllByUserStoryId(Integer userstoryId) {
		try {
			return userstorytaskDao.findAllByUserStoryId(userstoryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			userstorytaskDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UserStoryTaskDao getUserstorytaskDao() {
		return userstorytaskDao;
	}
	
}
