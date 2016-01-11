package service;

import java.util.List;

import dao.ProjectDao;
import model.Project;

public class ProjectService {
	
	private ProjectDao projectDao;
	
	public ProjectService(String hibernateconfigfilename) {
		this.projectDao = new ProjectDao(hibernateconfigfilename);
	}
	
	public void persist(Project entity) {
		try {
			projectDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Project entity) {
		try {
			projectDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Project findById(Integer id) {
		try {
			return projectDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Project findByProjectname(String projectname) {
		try {
			return projectDao.findByProjectname(projectname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void delete(Integer id) {
		try {
			Project scrumprojekt = projectDao.findById(id);
			projectDao.delete(scrumprojekt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Project> findAll() {
		try {
			return projectDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			projectDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ProjectDao getScrumprojektDao() {
		return projectDao;
	}
	
}
