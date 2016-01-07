package service;

import java.util.List;

import dao.BurndownChartDao;
import model.BurndownChart;

public class BurndownChartService {
	
	private BurndownChartDao burndownChartDao = null;
	
	public BurndownChartService(String hibernateconfigfilename) {
		this.burndownChartDao = new BurndownChartDao(hibernateconfigfilename);
	}
	
	public void persist(BurndownChart entity) {
		try {
			burndownChartDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(BurndownChart entity) {
		try {
			burndownChartDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BurndownChart findById(Integer id) {
		try {
			return burndownChartDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(Integer id) {
		try {
			BurndownChart burndownChart = burndownChartDao.findById(id);
			burndownChartDao.delete(burndownChart);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteWithNull() {
		try {
			burndownChartDao.deleteAllNull();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<BurndownChart> findAll() {
		try {
			return burndownChartDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			burndownChartDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BurndownChartDao burndownChartDao() {
		return burndownChartDao;
	}
	
}
