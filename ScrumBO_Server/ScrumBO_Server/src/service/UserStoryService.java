package service;

import java.util.List;

import dao.UserStoryDao;
import model.UserStory;

public class UserStoryService {
	
	private UserStoryDao userstoryDao;
	
	public UserStoryService(String hibernateconfigfilename) {
		userstoryDao = new UserStoryDao(hibernateconfigfilename);
	}
	
	public void persist(UserStory entity) {
		userstoryDao.openCurrentSessionwithTransaction();
		userstoryDao.persist(entity);
		userstoryDao.closeCurrentSessionwithTransaction();
	}
	
	public void update(UserStory entity) {
		userstoryDao.openCurrentSessionwithTransaction();
		userstoryDao.update(entity);
		userstoryDao.closeCurrentSessionwithTransaction();
	}
	
	public UserStory findById(Integer id) {
		userstoryDao.openCurrentSession();
		UserStory userstory = userstoryDao.findById(id);
		userstoryDao.closeCurrentSession();
		return userstory;
	}
	
	public void delete(Integer id) {
		userstoryDao.openCurrentSessionwithTransaction();
		UserStory userstory = userstoryDao.findById(id);
		userstoryDao.delete(userstory);
		userstoryDao.closeCurrentSessionwithTransaction();
	}
	
	public List<UserStory> findAll() {
		userstoryDao.openCurrentSession();
		List<UserStory> userstoryListe = userstoryDao.findAll();
		userstoryDao.closeCurrentSession();
		return userstoryListe;
	}
	
	public List<UserStory> findAllByProductBacklogId(Integer id) {
		userstoryDao.openCurrentSession();
		List<UserStory> userstoryListe = userstoryDao.findAllByProductBacklogId(id);
		userstoryDao.closeCurrentSession();
		return userstoryListe;
	}
	
	public List<UserStory> findAllBySprintId(Integer id) {
		userstoryDao.openCurrentSession();
		List<UserStory> userstoryListe = userstoryDao.findAllBySprintId(id);
		userstoryDao.closeCurrentSession();
		return userstoryListe;
	}
	
	public List<UserStory> findAllNULLwithProductBacklogId(Integer productbacklogid) {
		userstoryDao.openCurrentSession();
		List<UserStory> userstoryListe = userstoryDao.findAllNULLwithProductBacklogId(productbacklogid);
		userstoryDao.closeCurrentSession();
		return userstoryListe;
	}
	
	public Integer getUserStoryStatus(Integer userstoryId) {
		userstoryDao.openCurrentSession();
		Integer status = userstoryDao.getUserStoryStatus(userstoryId);
		userstoryDao.closeCurrentSession();
		return status;
	}
	
	public void deleteAll() {
		userstoryDao.openCurrentSessionwithTransaction();
		userstoryDao.deleteAll();
		userstoryDao.closeCurrentSessionwithTransaction();
	}
	
	public UserStoryDao userstoryDao() {
		return userstoryDao;
	}
	
}
