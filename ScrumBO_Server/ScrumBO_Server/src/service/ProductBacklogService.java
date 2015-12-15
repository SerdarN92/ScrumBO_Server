package service;

import java.util.List;

import dao.ProductBacklogDao;
import model.ProductBacklog;

public class ProductBacklogService {
	
	private static ProductBacklogDao productbacklogDao;
	
	public ProductBacklogService(String hibernateconfigfilename) {
		productbacklogDao = new ProductBacklogDao(hibernateconfigfilename);
	}
	
	public void persist(ProductBacklog entity) {
		productbacklogDao.openCurrentSessionwithTransaction();
		productbacklogDao.persist(entity);
		productbacklogDao.closeCurrentSessionwithTransaction();
	}
	
	public void update(ProductBacklog entity) {
		productbacklogDao.openCurrentSessionwithTransaction();
		productbacklogDao.update(entity);
		productbacklogDao.closeCurrentSessionwithTransaction();
	}
	
	public ProductBacklog findById(Integer id) {
		productbacklogDao.openCurrentSession();
		ProductBacklog productbacklog = productbacklogDao.findById(id);
		productbacklogDao.closeCurrentSession();
		return productbacklog;
	}
	
	public void delete(Integer id) {
		productbacklogDao.openCurrentSessionwithTransaction();
		ProductBacklog productbacklog = productbacklogDao.findById(id);
		productbacklogDao.delete(productbacklog);
		productbacklogDao.closeCurrentSessionwithTransaction();
	}
	
	public List<ProductBacklog> findAll() {
		productbacklogDao.openCurrentSession();
		List<ProductBacklog> productbacklogListe = productbacklogDao.findAll();
		productbacklogDao.closeCurrentSession();
		return productbacklogListe;
	}
	
	public List<ProductBacklog> findAllByProjectId(Integer id) {
		productbacklogDao.openCurrentSession();
		List<ProductBacklog> productbacklogListe = productbacklogDao.findAllByProjectId(id);
		productbacklogDao.closeCurrentSession();
		return productbacklogListe;
	}
	
	public void deleteAll() {
		productbacklogDao.openCurrentSessionwithTransaction();
		productbacklogDao.deleteAll();
		productbacklogDao.closeCurrentSessionwithTransaction();
	}
	
	public ProductBacklogDao productbacklogDao() {
		return productbacklogDao;
	}
	
}
