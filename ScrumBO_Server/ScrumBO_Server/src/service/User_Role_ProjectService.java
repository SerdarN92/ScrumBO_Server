package service;

import java.util.List;

import dao.User_Role_ProjectDao;
import model.User_Role_Project;

public class User_Role_ProjectService {
	
	private User_Role_ProjectDao uspDao = null;
	
	public User_Role_ProjectService(String hibernateconfigfilename) {
		this.uspDao = new User_Role_ProjectDao(hibernateconfigfilename);
	}
	
	public void persist(User_Role_Project entity) {
		try {
			uspDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(User_Role_Project entity) {
		try {
			uspDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User_Role_Project findById(Integer id) {
		try {
			return uspDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<User_Role_Project> findListByUserId(Integer userId) {
		
		try {
			return uspDao.findListByUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<User_Role_Project> findListByProjectId(Integer projectId) {
		try {
			return uspDao.findListByProjectId(projectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(Integer id) {
		try {
			User_Role_Project entity = uspDao.findById(id);
			uspDao.delete(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteBenutzer(Integer userId) {
		try {
			uspDao.deleteWithUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteProject(Integer projectId) {
		try {
			uspDao.deleteProject(projectId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<User_Role_Project> findAll() {
		try {
			return uspDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			uspDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkAdmission(Integer userId, Integer projectId) {
		boolean admission = false;
		try {
			if (uspDao.checkAdmission(userId, projectId)) {
				admission = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admission;
	}
	
	public boolean checkAdmin(Integer userId) {
		boolean admin = false;
		try {
			if (uspDao.checkAdmin(userId)) {
				admin = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
	}
	
	public User_Role_ProjectDao getUspDao() {
		return uspDao;
	}
	
}
