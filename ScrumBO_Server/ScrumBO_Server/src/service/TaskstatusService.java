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
		taskstatusDao.openCurrentSessionwithTransaction();
		taskstatusDao.persist(entity);
		taskstatusDao.closeCurrentSessionwithTransaction();
	}
	
	public void update(Taskstatus entity) {
		taskstatusDao.openCurrentSessionwithTransaction();
		taskstatusDao.update(entity);
		taskstatusDao.closeCurrentSessionwithTransaction();
	}
	
	public Taskstatus findById(Integer id) {
		taskstatusDao.openCurrentSession();
		Taskstatus taskstatus = taskstatusDao.findById(id);
		taskstatusDao.closeCurrentSession();
		return taskstatus;
	}
	
	public void delete(Integer id) {
		taskstatusDao.openCurrentSessionwithTransaction();
		Taskstatus taskstatus = taskstatusDao.findById(id);
		taskstatusDao.delete(taskstatus);
		taskstatusDao.closeCurrentSessionwithTransaction();
	}
	
	public List<Taskstatus> findAll() {
		taskstatusDao.openCurrentSession();
		List<Taskstatus> taskstatusListe = taskstatusDao.findAll();
		taskstatusDao.closeCurrentSession();
		return taskstatusListe;
	}
	
	public void deleteAll() {
		taskstatusDao.openCurrentSessionwithTransaction();
		taskstatusDao.deleteAll();
		taskstatusDao.closeCurrentSessionwithTransaction();
	}
	
	public TaskstatusDao taskstatusDao() {
		return taskstatusDao;
	}
	
}
