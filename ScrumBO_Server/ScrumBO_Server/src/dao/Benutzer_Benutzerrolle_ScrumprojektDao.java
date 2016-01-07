package dao;

import java.util.List;

import org.hibernate.Session;

import hibernate.HibernateUtil;
import model.Benutzer_Benutzerrolle_Scrumprojekt;

public class Benutzer_Benutzerrolle_ScrumprojektDao
		implements DaoInterface<Benutzer_Benutzerrolle_Scrumprojekt, Integer> {
		
	private String			hibernateconfig	= "";
	private HibernateUtil	hibernateutil	= null;
											
	public Benutzer_Benutzerrolle_ScrumprojektDao(String hibernateconfigfilename) {
		this.hibernateconfig = hibernateconfig;
		this.hibernateutil = new HibernateUtil(hibernateconfigfilename);
	}
	
	@Override
	public void persist(Benutzer_Benutzerrolle_Scrumprojekt entity) {
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				s.save(entity);
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(Benutzer_Benutzerrolle_Scrumprojekt entity) {
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				s.update(entity);
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findListByBenutzerId(Integer benutzerId) {
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzer_benutzerrolle_scrumprojekt_Liste = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				benutzer_benutzerrolle_scrumprojekt_Liste = (List<Benutzer_Benutzerrolle_Scrumprojekt>) s
						.createQuery(
								"FROM Benutzer_Benutzerrolle_Scrumprojekt WHERE benutzerId LIKE'" + benutzerId + "'")
						.list();
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return benutzer_benutzerrolle_scrumprojekt_Liste;
	}
	
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findListByProjectId(Integer scrumprojektid) {
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzer_benutzerrolle_scrumprojekt_Liste = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				benutzer_benutzerrolle_scrumprojekt_Liste = (List<Benutzer_Benutzerrolle_Scrumprojekt>) s.createQuery(
						"from Benutzer_Benutzerrolle_Scrumprojekt where scrumprojektId like'" + scrumprojektid + "'")
						.list();
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return benutzer_benutzerrolle_scrumprojekt_Liste;
	}
	
	@Override
	public void delete(Benutzer_Benutzerrolle_Scrumprojekt entity) {
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				s.delete(entity);
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findAll() {
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzerListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				benutzerListe = (List<Benutzer_Benutzerrolle_Scrumprojekt>) s
						.createQuery("from Benutzer_Benutzerrolle_Scrumprojekt").list();
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return benutzerListe;
	}
	
	public List<Benutzer_Benutzerrolle_Scrumprojekt> findAllWithBenutzerId(Integer benutzerId) {
		List<Benutzer_Benutzerrolle_Scrumprojekt> benutzerListe = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				benutzerListe = (List<Benutzer_Benutzerrolle_Scrumprojekt>) s.createQuery(
						"from Benutzer_Benutzerrolle_Scrumprojekt where benutzerId LIKE LIKE '" + benutzerId + "'")
						.list();
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return benutzerListe;
	}
	
	@Override
	public void deleteAll() {
		try {
			List<Benutzer_Benutzerrolle_Scrumprojekt> benutzerListe = findAll();
			for (Benutzer_Benutzerrolle_Scrumprojekt benutzer : benutzerListe) {
				delete(benutzer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Benutzer_Benutzerrolle_Scrumprojekt findById(Integer id) {
		Benutzer_Benutzerrolle_Scrumprojekt benutzer = null;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				benutzer = (Benutzer_Benutzerrolle_Scrumprojekt) s.get(Benutzer_Benutzerrolle_Scrumprojekt.class, id);
				s.getTransaction().commit();
				s.close();
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return benutzer;
	}
	
	public boolean checkAdmission(Integer benutzerId, Integer scrumprojektId) {
		Benutzer_Benutzerrolle_Scrumprojekt benutzer = null;
		boolean admission = false;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				benutzer = (Benutzer_Benutzerrolle_Scrumprojekt) s
						.createQuery("from Benutzer_Benutzerrolle_Scrumprojekt WHERE benutzerId LIKE '" + benutzerId
								+ "' AND scrumprojektId LIKE '" + scrumprojektId + "'")
						.uniqueResult();
				s.getTransaction().commit();
				s.close();
				if (benutzer != null)
					admission = true;
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admission;
	}
	
	public void deleteBenutzer(Integer benutzerId) {
		try {
			List<Benutzer_Benutzerrolle_Scrumprojekt> benutzerListe = findListByBenutzerId(benutzerId);
			for (Benutzer_Benutzerrolle_Scrumprojekt benutzer : benutzerListe) {
				delete(benutzer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteProject(Integer scrumprojectId) {
		try {
			List<Benutzer_Benutzerrolle_Scrumprojekt> benutzerListe = findListByProjectId(scrumprojectId);
			for (Benutzer_Benutzerrolle_Scrumprojekt benutzer : benutzerListe) {
				delete(benutzer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean checkAdmin(Integer benutzerId) {
		Benutzer_Benutzerrolle_Scrumprojekt benutzer = null;
		boolean admin = false;
		try {
			Session s = HibernateUtil.openSession();
			try {
				s.beginTransaction();
				benutzer = (Benutzer_Benutzerrolle_Scrumprojekt) s
						.createQuery(
								"from Benutzer_Benutzerrolle_Scrumprojekt WHERE benutzerId LIKE '" + benutzerId + "'")
						.uniqueResult();
				s.getTransaction().commit();
				s.close();
				if (benutzer != null) {
					if (benutzer.getPk().getBenutzerrollenId() == 4) {
						admin = true;
					}
				}
			} catch (Exception e) {
				s.getTransaction().rollback();
				s.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admin;
	}
	
}
