package service;

import java.util.List;

import dao.TaskstatusDao;
import model.Taskstatus;

public class TaskstatusService {
	
	private TaskstatusDao taskstatusDao;
	
	public TaskstatusService(String hibernateconfigfilename) {
		taskstatusDao = new TaskstatusDao(hibernateconfigfilename);
	}
	
	public void persist(Taskstatus entity) {
		try {
			taskstatusDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(Taskstatus entity) {
		try {
			taskstatusDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Taskstatus findById(Integer id) {
		Taskstatus taskstatus = null;
		try {
			taskstatus = taskstatusDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return taskstatus;
	}
	
	public void delete(Integer id) {
		try {
			Taskstatus taskstatus = taskstatusDao.findById(id);
			taskstatusDao.delete(taskstatus);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Taskstatus> findAll() {
		try {
			return taskstatusDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			taskstatusDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public TaskstatusDao taskstatusDao() {
		return taskstatusDao;
	}
	
}
