package service;

import java.util.List;

import dao.UserDao;
import model.User;

public class UserService {
	
	private UserDao userDao = null;
	
	public UserService(String hibernateconfigfilename) {
		this.userDao = new UserDao(hibernateconfigfilename);
	}
	
	public void persist(User entity) {
		try {
			userDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(User entity) {
		try {
			userDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public User findById(Integer id) {
		try {
			return userDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public User findByEmail(String email) {
		try {
			return userDao.findByEmail(email);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(Integer id) {
		try {
			User benutzer = userDao.findById(id);
			userDao.delete(benutzer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<User> findAll() {
		try {
			return userDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			userDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UserDao userDao() {
		return userDao;
	}
	
}