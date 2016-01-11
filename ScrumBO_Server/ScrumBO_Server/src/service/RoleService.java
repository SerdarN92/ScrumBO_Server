package service;

import java.util.List;

import dao.RoleDao;
import model.Role;

public class RoleService {
	
	private RoleDao roleDao = null;
	
	public RoleService(String hibernateconfigfilename) {
		this.roleDao = new RoleDao(hibernateconfigfilename);
	}
	
	public void persist(Role entity) {
		try {
			roleDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Role entity) {
		try {
			roleDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Role findById(Integer id) {
		try {
			return roleDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(Integer id) {
		try {
			Role role = roleDao.findById(id);
			roleDao.delete(role);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Role> findAll() {
		try {
			return roleDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			roleDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public RoleDao getRoleDao() {
		return roleDao;
	}
	
}