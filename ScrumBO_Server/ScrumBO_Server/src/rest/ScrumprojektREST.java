package rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import com.google.gson.Gson;

import dto.BenutzerDTO;
import dto.ImpedimentDTO;
import dto.ProductBacklogDTO;
import dto.ScrumprojektDTO;
import dto.SprintDTO;
import model.Benutzer;
import model.Benutzer_Benutzerrolle_Scrumprojekt;
import model.ProductBacklog;
import model.Scrumprojekt;
import service.BenutzerService;
import service.Benutzer_Benutzerrolle_ScrumprojektService;
import service.ProductBacklogService;
import service.ScrumprojektService;

@Path("/scrumprojekt")
public class ScrumprojektREST {
	
	@Path("/suche/{projectname}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getProjectByProjectname(@PathParam("projectname") String projectname,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		ScrumprojektService scrumprojektService = new ScrumprojektService(hibernateconfigfilename);
		Scrumprojekt scrumprojekt = scrumprojektService.findByProjectname(projectname);
		String output = "Projekt nicht vorhanden";
		if (scrumprojekt != null) {
			ScrumprojektDTO scrumprojektDTO = new ScrumprojektDTO();
			scrumprojektDTO.setId(scrumprojekt.getId());
			scrumprojektDTO.setProjektname(scrumprojekt.getProjektname());
			scrumprojektDTO.setPasswort(scrumprojekt.getPasswort());
			// List<BenutzerDTO> benutzerDTOListe = new
			// LinkedList<BenutzerDTO>();
			// for (int i = 0; i < scrumprojekt.getBenutzer().size(); i++) {
			// BenutzerDTO benutzerDTO = new BenutzerDTO();
			// benutzerDTO.setId(scrumprojekt.getBenutzer().get(i).getId());
			// benutzerDTO.setVorname(scrumprojekt.getBenutzer().get(i).getVorname());
			// benutzerDTO.setNachname(scrumprojekt.getBenutzer().get(i).getNachname());
			// benutzerDTO.setPasswort(scrumprojekt.getBenutzer().get(i).getPasswort());
			// benutzerDTO.setEmail(scrumprojekt.getBenutzer().get(i).getEmail());
			// BenutzerrolleDTO benutzerrolleDTO = new BenutzerrolleDTO();
			// benutzerrolleDTO.setId(scrumprojekt.getBenutzer().get(i).getBenutzerrolle().getBenutzerrollenid());
			// benutzerrolleDTO.setBezeichnung(scrumprojekt.getBenutzer().get(i).getBenutzerrolle().getBezeichnung());
			// benutzerDTO.setBenutzerrolle(benutzerrolleDTO);
			// benutzerDTOListe.add(benutzerDTO);
			// }
			// scrumprojektDTO.setBenutzer(benutzerDTOListe);
			List<ImpedimentDTO> impedimentDTOListe = new LinkedList<ImpedimentDTO>();
			for (int i = 0; i < scrumprojekt.getImpediment().size(); i++) {
				ImpedimentDTO impedimentDTO = new ImpedimentDTO();
				impedimentDTO.setId(scrumprojekt.getImpediment().get(i).getId());
				// impedimentDTO.setBeschreibung(scrumprojekt.getImpediment().get(i).getBeschreibung());
				// impedimentDTO.setDatumDesAuftretens(scrumprojekt.getImpediment().get(i).getDatumDesAuftretens());
				// impedimentDTO.setDatumDerBehebung(scrumprojekt.getImpediment().get(i).getDatumDerBehebung());
				// impedimentDTOListe.add(impedimentDTO);
			}
			scrumprojektDTO.setImpediment(impedimentDTOListe);
			// UserStoryService userstoryService = new UserStoryService();
			List<ProductBacklogDTO> productbacklogDTOListe = new LinkedList<ProductBacklogDTO>();
			for (int i = 0; i < scrumprojekt.getProductbacklog().size(); i++) {
				ProductBacklogDTO productbacklogDTO = new ProductBacklogDTO();
				productbacklogDTO.setId(scrumprojekt.getProductbacklog().get(i).getId());
				productbacklogDTO.setVersion(scrumprojekt.getProductbacklog().get(i).getVersion());
				// List<UserStoryDTO> userstoryDTOList = new
				// LinkedList<UserStoryDTO>();
				// for (int m = 0; m < userstoryService.findAll().size(); m++) {
				// UserStoryDTO userstoryDTO = new UserStoryDTO();
				// userstoryDTO.setId(userstoryService.findAll().get(m).getId());
				// userstoryDTO.setPrioritaet(userstoryService.findAll().get(m).getPrioritaet());
				// userstoryDTO.setThema(userstoryService.findAll().get(m).getThema());
				// userstoryDTO.setBeschreibung(userstoryService.findAll().get(m).getBeschreibung());
				// userstoryDTO.setAkzeptanzkriterien(userstoryService.findAll().get(m).getAkzeptanzkriterien());
				// userstoryDTO.setAufwandintagen(userstoryService.findAll().get(m).getAufwandintagen());
				// userstoryDTOList.add(userstoryDTO);
				// }
				// productbacklogDTO.setUserstory(userstoryDTOList);
				productbacklogDTOListe.add(productbacklogDTO);
			}
			scrumprojektDTO.setProductbacklog(productbacklogDTOListe);
			List<SprintDTO> sprintDTOListe = new LinkedList<SprintDTO>();
			for (int i = 0; i < scrumprojekt.getSprint().size(); i++) {
				SprintDTO sprintDTO = new SprintDTO();
				sprintDTO.setId(scrumprojekt.getSprint().get(i).getId());
				sprintDTOListe.add(sprintDTO);
			}
			scrumprojektDTO.setSprint(sprintDTOListe);
			
			Gson gson = new Gson();
			output = gson.toJson(scrumprojektDTO);
		}
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/alle/{hibernateconfigfilename}")
	@Produces("application/json" + ";charset=utf-8")
	public Response getScrumprojekteAll(@PathParam("hibernateconfigfilename") String hibernateconfigfilename)
			throws JSONException {
		ScrumprojektService scrumprojektService = new ScrumprojektService(hibernateconfigfilename);
		
		List<ScrumprojektDTO> scrumprojektDTOListe = new LinkedList<ScrumprojektDTO>();
		List<Scrumprojekt> scrumprojektListe = scrumprojektService.findAll();
		for (int i = 0; i < scrumprojektListe.size(); i++) {
			ScrumprojektDTO scrumprojektDTO = new ScrumprojektDTO();
			scrumprojektDTO.setId(scrumprojektListe.get(i).getId());
			scrumprojektDTO.setProjektname(scrumprojektListe.get(i).getProjektname());
			scrumprojektDTO.setPasswort(scrumprojektListe.get(i).getPasswort());
			List<BenutzerDTO> benutzerDTOListe = new LinkedList<BenutzerDTO>();
			List<SprintDTO> sprintDTOListe = new LinkedList<SprintDTO>();
			List<ImpedimentDTO> impedimentDTOListe = new LinkedList<ImpedimentDTO>();
			List<ProductBacklogDTO> productbacklogDTOListe = new LinkedList<ProductBacklogDTO>();
			// for (int j = 0; j <
			// scrumprojektListe.get(i).getBenutzer().size(); j++) {
			// BenutzerDTO benutzerDTO = new BenutzerDTO();
			// benutzerDTO.setId(scrumprojektListe.get(i).getBenutzer().get(j).getId());
			// benutzerDTO.setVorname(scrumprojektListe.get(i).getBenutzer().get(j).getVorname());
			// benutzerDTO.setNachname(scrumprojektListe.get(i).getBenutzer().get(j).getNachname());
			// benutzerDTO.setPasswort(scrumprojektListe.get(i).getBenutzer().get(j).getPasswort());
			// benutzerDTO.setEmail(scrumprojektListe.get(i).getBenutzer().get(j).getEmail());
			// BenutzerrolleDTO benutzerrolleDTO = new BenutzerrolleDTO();
			// benutzerrolleDTO
			// .setId(scrumprojektListe.get(i).getBenutzer().get(j).getBenutzerrolle().getBenutzerrollenid());
			// benutzerrolleDTO.setBezeichnung(
			// scrumprojektListe.get(i).getBenutzer().get(j).getBenutzerrolle().getBezeichnung());
			// benutzerDTO.setBenutzerrolle(benutzerrolleDTO);
			// benutzerDTOListe.add(benutzerDTO);
			// }
			for (int j = 0; j < scrumprojektListe.get(i).getSprint().size(); j++) {
				SprintDTO sprintDTO = new SprintDTO();
				sprintDTO.setId(scrumprojektListe.get(i).getSprint().get(j).getId());
				sprintDTOListe.add(sprintDTO);
			}
			for (int j = 0; j < scrumprojektListe.get(i).getImpediment().size(); j++) {
				ImpedimentDTO impedimentDTO = new ImpedimentDTO();
				impedimentDTO.setId(scrumprojektListe.get(i).getImpediment().get(j).getId());
				// impedimentDTO.setBeschreibung(scrumprojektListe.get(i).getImpediment().get(j).getBeschreibung());
				// impedimentDTO
				// .setDatumDesAuftretens(scrumprojektListe.get(i).getImpediment().get(j).getDatumDesAuftretens());
				// impedimentDTO
				// .setDatumDerBehebung(scrumprojektListe.get(i).getImpediment().get(j).getDatumDerBehebung());
				impedimentDTOListe.add(impedimentDTO);
			}
			// UserStoryService userstoryService = new UserStoryService();
			for (int j = 0; j < scrumprojektListe.get(i).getProductbacklog().size(); j++) {
				ProductBacklogDTO productbacklogDTO = new ProductBacklogDTO();
				productbacklogDTO.setId(scrumprojektListe.get(i).getProductbacklog().get(j).getId());
				// List<UserStoryDTO> list = new LinkedList<UserStoryDTO>();
				// for (int m = 0; m < userstoryService.findAll().size(); m++) {
				// UserStoryDTO userstoryDTO = new UserStoryDTO();
				// userstoryDTO.setId(userstoryService.findAll().get(m).getId());
				// userstoryDTO.setThema(userstoryService.findAll().get(m).getThema());
				// userstoryDTO.setBeschreibung(userstoryService.findAll().get(m).getBeschreibung());
				// userstoryDTO.setAkzeptanzkriterien(userstoryService.findAll().get(m).getAkzeptanzkriterien());
				// userstoryDTO.setAufwandintagen(userstoryService.findAll().get(m).getAufwandintagen());
				// list.add(userstoryDTO);
				// }
				// productbacklogDTO.setUserstory(list);
				productbacklogDTOListe.add(productbacklogDTO);
			}
			// scrumprojektDTO.setBenutzer(benutzerDTOListe);
			scrumprojektDTO.setSprint(sprintDTOListe);
			scrumprojektDTO.setImpediment(impedimentDTOListe);
			scrumprojektDTO.setProductbacklog(productbacklogDTOListe);
			scrumprojektDTOListe.add(scrumprojektDTO);
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(scrumprojektDTOListe);
		
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/create/{email}/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response createProject(@PathParam("email") String email, InputStream input,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		StringBuilder b = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				b.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String projectdetails = b.toString();
		
		Gson gson = new Gson();
		ScrumprojektDTO scrumprojektDTO = gson.fromJson(projectdetails, ScrumprojektDTO.class);
		Scrumprojekt scrumprojekt = new Scrumprojekt();
		BenutzerService benServ = new BenutzerService(hibernateconfigfilename);
		List<Benutzer> benutzerListe = new LinkedList<Benutzer>();
		// for (int i = 0; i < scrumprojektDTO.getBenutzer().size(); i++) {
		// Benutzer benutzer = new Benutzer();
		// benutzer =
		// benServ.findById(scrumprojektDTO.getBenutzer().get(i).getId());
		// benutzerListe.add(benutzer);
		// }
		List<ProductBacklog> productbacklogListe = new LinkedList<ProductBacklog>();
		ProductBacklog productbacklog = new ProductBacklog();
		productbacklog.setVersion(1);
		scrumprojekt.setProjektname(scrumprojektDTO.getProjektname());
		scrumprojekt.setPasswort(scrumprojektDTO.getPasswort());
		// scrumprojekt.setBenutzer(benutzerListe);
		productbacklog.setScrumprojekt(scrumprojekt);
		productbacklogListe.add(productbacklog);
		scrumprojekt.setProductbacklog(productbacklogListe);
		
		ScrumprojektService sps = new ScrumprojektService(hibernateconfigfilename);
		BenutzerService bs = new BenutzerService(hibernateconfigfilename);
		Benutzer benutzer = bs.findByEmail(email);
		Benutzer_Benutzerrolle_ScrumprojektService bss = new Benutzer_Benutzerrolle_ScrumprojektService(
				hibernateconfigfilename);
		List<Benutzer_Benutzerrolle_Scrumprojekt> bssliste = bss.findAll();
		Benutzer_Benutzerrolle_Scrumprojekt bssmodel = null;
		for (int i = 0; i < bssliste.size(); i++) {
			if (bssliste.get(i).getPk().getBenutzerId() == benutzer.getId()
					&& bssliste.get(i).getPk().getBenutzerrollenId() == 1) {
				bssmodel = bssliste.get(i);
			}
		}
		
		String output = "";
		try {
			sps.persist(scrumprojekt);
			Integer projectid = sps.findByProjectname(scrumprojekt.getProjektname()).getId();
			Benutzer_Benutzerrolle_Scrumprojekt.Pk pk = new Benutzer_Benutzerrolle_Scrumprojekt.Pk();
			pk.setBenutzerId(bssmodel.getPk().getBenutzerId());
			pk.setBenutzerrollenId(bssmodel.getPk().getBenutzerrollenId());
			pk.setScrumprojektId(projectid);
			bssmodel.setPk(pk);
			bss.persist(bssmodel);
			output = "Project erfolgreich erstellt";
		} catch (Exception e) {
			e.printStackTrace();
			output = "Projekt wurde nicht erfolgreich erstellt";
		}
		
		return Response.status(200).entity(output).build();
		
	}
	
	@Path("/suche/{projectid}/productbacklog/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getProductbacklogByProjectname(@PathParam("projectid") Integer projectid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		ScrumprojektService scrumprojektService = new ScrumprojektService(hibernateconfigfilename);
		Scrumprojekt scrumprojekt = scrumprojektService.findById(projectid);
		ProductBacklogService productbacklogService = new ProductBacklogService(hibernateconfigfilename);
		List<ProductBacklog> productbacklogList = productbacklogService.findAllByProjectId(projectid);
		List<ProductBacklogDTO> productbacklogDTOList = new LinkedList<ProductBacklogDTO>();
		for (int i = 0; i < productbacklogList.size(); i++) {
			ProductBacklogDTO productbacklogDTO = new ProductBacklogDTO();
			productbacklogDTO.setId(productbacklogList.get(i).getId());
			productbacklogDTO.setVersion(productbacklogList.get(i).getVersion());
			productbacklogDTOList.add(productbacklogDTO);
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(productbacklogDTOList);
		
		return Response.status(200).entity(output).build();
		
	}
	
}
