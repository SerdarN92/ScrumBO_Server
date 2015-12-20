package service;

import java.util.List;

import dao.UserStoryTaskDao;
import model.UserStoryTask;

public class UserStoryTaskService {
	
	private UserStoryTaskDao userstorytaskDao;
	
	public UserStoryTaskService(String hibernateconfigfilename) {
		userstorytaskDao = new UserStoryTaskDao(hibernateconfigfilename);
	}
	
	public void persist(UserStoryTask entity) {
		userstorytaskDao.openCurrentSessionwithTransaction();
		userstorytaskDao.persist(entity);
		userstorytaskDao.closeCurrentSessionwithTransaction();
	}
	
	public void update(UserStoryTask entity) {
		userstorytaskDao.openCurrentSessionwithTransaction();
		userstorytaskDao.update(entity);
		userstorytaskDao.closeCurrentSessionwithTransaction();
	}
	
	public UserStoryTask findById(Integer id) {
		userstorytaskDao.openCurrentSession();
		UserStoryTask userstorytask = userstorytaskDao.findById(id);
		userstorytaskDao.closeCurrentSession();
		return userstorytask;
	}
	
	public void delete(Integer id) {
		userstorytaskDao.openCurrentSessionwithTransaction();
		UserStoryTask userstorytask = userstorytaskDao.findById(id);
		userstorytaskDao.delete(userstorytask);
		userstorytaskDao.closeCurrentSessionwithTransaction();
	}
	
	public List<UserStoryTask> findAll() {
		userstorytaskDao.openCurrentSession();
		List<UserStoryTask> userstorytaskListe = userstorytaskDao.findAll();
		userstorytaskDao.closeCurrentSession();
		return userstorytaskListe;
	}
	
	public void deleteAll() {
		userstorytaskDao.openCurrentSessionwithTransaction();
		userstorytaskDao.deleteAll();
		userstorytaskDao.closeCurrentSessionwithTransaction();
	}
	
	public UserStoryTaskDao userstorytaskDao() {
		return userstorytaskDao;
	}
	
}
