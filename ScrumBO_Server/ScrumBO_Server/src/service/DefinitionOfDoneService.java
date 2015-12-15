package service;

import java.util.List;

import dao.DefinitionOfDoneDao;
import model.DefinitionOfDone;

public class DefinitionOfDoneService {
	
	private static DefinitionOfDoneDao definitionofdoneDao;
	
	public DefinitionOfDoneService(String hibernateconfigfilename) {
		definitionofdoneDao = new DefinitionOfDoneDao(hibernateconfigfilename);
	}
	
	public void persist(DefinitionOfDone entity) {
		definitionofdoneDao.openCurrentSessionwithTransaction();
		definitionofdoneDao.persist(entity);
		definitionofdoneDao.closeCurrentSessionwithTransaction();
	}
	
	public void update(DefinitionOfDone entity) {
		definitionofdoneDao.openCurrentSessionwithTransaction();
		definitionofdoneDao.update(entity);
		definitionofdoneDao.closeCurrentSessionwithTransaction();
	}
	
	public DefinitionOfDone findById(Integer id) {
		definitionofdoneDao.openCurrentSession();
		DefinitionOfDone definitionofdone = definitionofdoneDao.findById(id);
		definitionofdoneDao.closeCurrentSession();
		return definitionofdone;
	}
	
	public List<DefinitionOfDone> findByUserstoryId(Integer id) {
		definitionofdoneDao.openCurrentSession();
		List<DefinitionOfDone> definitionofdoneListe = definitionofdoneDao.findByUserstoryId(id);
		definitionofdoneDao.closeCurrentSession();
		return definitionofdoneListe;
	}
	
	public void delete(Integer id) {
		definitionofdoneDao.openCurrentSessionwithTransaction();
		DefinitionOfDone definitionofdone = definitionofdoneDao.findById(id);
		definitionofdoneDao.delete(definitionofdone);
		definitionofdoneDao.closeCurrentSessionwithTransaction();
	}
	
	public List<DefinitionOfDone> findAll() {
		definitionofdoneDao.openCurrentSession();
		List<DefinitionOfDone> definitionofdoneListe = definitionofdoneDao.findAll();
		definitionofdoneDao.closeCurrentSession();
		return definitionofdoneListe;
	}
	
	public void deleteAll() {
		definitionofdoneDao.openCurrentSessionwithTransaction();
		definitionofdoneDao.deleteAll();
		definitionofdoneDao.closeCurrentSessionwithTransaction();
	}
	
	public DefinitionOfDoneDao definitionofdoneDao() {
		return definitionofdoneDao;
	}
	
}
