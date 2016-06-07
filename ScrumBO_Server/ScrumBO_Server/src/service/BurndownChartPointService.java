package service;

import java.util.List;

import dao.BurndownChartPointDao;
import model.BurndownChartPoint;

public class BurndownChartPointService {
	
	private BurndownChartPointDao burndownChartPointDao = null;
	
	public BurndownChartPointService(String hibernateconfigfilename) {
		this.burndownChartPointDao = new BurndownChartPointDao(hibernateconfigfilename);
	}
	
	public void persist(BurndownChartPoint entity) {
		try {
			burndownChartPointDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(BurndownChartPoint entity) {
		try {
			burndownChartPointDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BurndownChartPoint findById(Integer id) {
		try {
			return burndownChartPointDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(Integer id) {
		try {
			BurndownChartPoint burndownChartPoint = burndownChartPointDao.findById(id);
			burndownChartPointDao.delete(burndownChartPoint);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<BurndownChartPoint> findAll() {
		try {
			return burndownChartPointDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<BurndownChartPoint> findAllWithBurndownChartId(int burndownchartId) {
		try {
			return burndownChartPointDao.findAllWithBurndownChartId(burndownchartId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			burndownChartPointDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public BurndownChartPointDao burndownChartPointDao() {
		return burndownChartPointDao;
	}
	
}
