package service;

import java.util.List;

import dao.ProductBacklogDao;
import model.ProductBacklog;

public class ProductBacklogService {
	
	private ProductBacklogDao productbacklogDao;
	
	public ProductBacklogService(String hibernateconfigfilename) {
		this.productbacklogDao = new ProductBacklogDao(hibernateconfigfilename);
	}
	
	public void persist(ProductBacklog entity) {
		try {
			productbacklogDao.persist(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void update(ProductBacklog entity) {
		try {
			productbacklogDao.update(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ProductBacklog findById(Integer id) {
		try {
			return productbacklogDao.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void delete(Integer id) {
		try {
			ProductBacklog productbacklog = productbacklogDao.findById(id);
			productbacklogDao.delete(productbacklog);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ProductBacklog> findAll() {
		try {
			return productbacklogDao.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<ProductBacklog> findAllByProjectId(Integer id) {
		try {
			return productbacklogDao.findAllByProjectId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void deleteAll() {
		try {
			productbacklogDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ProductBacklogDao productbacklogDao() {
		return productbacklogDao;
	}
	
}
