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
import model.Sprint;
import model.SprintBacklog;
import service.BenutzerService;
import service.Benutzer_Benutzerrolle_ScrumprojektService;
import service.ProductBacklogService;
import service.ScrumprojektService;
import service.SprintBacklogService;
import service.SprintService;

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
			ProductBacklogDTO productbacklogDTO = new ProductBacklogDTO();
			productbacklogDTO.setId(scrumprojekt.getProductbacklog().getId());
			scrumprojektDTO.setProductbacklog(productbacklogDTO);
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
			ProductBacklogDTO productbacklogDTO = new ProductBacklogDTO();
			productbacklogDTO.setId(scrumprojektListe.get(i).getProductbacklog().getId());
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
			
			// scrumprojektDTO.setBenutzer(benutzerDTOListe);
			scrumprojektDTO.setSprint(sprintDTOListe);
			scrumprojektDTO.setImpediment(impedimentDTOListe);
			scrumprojektDTO.setProductbacklog(productbacklogDTO);
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
		
		ScrumprojektService sps = new ScrumprojektService(hibernateconfigfilename);
		BenutzerService bs = new BenutzerService(hibernateconfigfilename);
		Benutzer_Benutzerrolle_ScrumprojektService bss = new Benutzer_Benutzerrolle_ScrumprojektService(
				hibernateconfigfilename);
		SprintBacklogService sprintbacklogService = new SprintBacklogService(hibernateconfigfilename);
		SprintService sprintService = new SprintService(hibernateconfigfilename);
		
		ScrumprojektDTO scrumprojektDTO = gson.fromJson(projectdetails, ScrumprojektDTO.class);
		
		Scrumprojekt scrumprojekt = new Scrumprojekt();
		Benutzer benutzer = bs.findByEmail(email);
		ProductBacklog productbacklog = new ProductBacklog();
		Benutzer_Benutzerrolle_Scrumprojekt bssmodel = new Benutzer_Benutzerrolle_Scrumprojekt();
		SprintBacklog sprintbacklog = new SprintBacklog();
		Sprint sprint = new Sprint();
		
		Integer sprintbacklogid = sprintbacklogService.findAll().size() + 1;
		sprintbacklog.setId(sprintbacklogid);
		sprintbacklogService.persist(sprintbacklog);
		sprintbacklog = sprintbacklogService.findById(sprintbacklogid);
		
		List<Benutzer_Benutzerrolle_Scrumprojekt> bssliste = bss.findAll();
		
		scrumprojekt.setProjektname(scrumprojektDTO.getProjektname());
		scrumprojekt.setPasswort(scrumprojektDTO.getPasswort());
		scrumprojekt.setProductbacklog(productbacklog);
		
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
			
			sprint.setSprintnummer(1);
			sprint.setScrumprojekt(sps.findById(projectid));
			sprint.setSprintbacklog(sprintbacklog);
			sprintService.persist(sprint);
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
		Integer productbacklogid = scrumprojekt.getProductbacklog().getId();
		ProductBacklogService productbacklogService = new ProductBacklogService(hibernateconfigfilename);
		ProductBacklog productbacklog = new ProductBacklog();
		productbacklog = productbacklogService.findById(productbacklogid);
		
		ProductBacklogDTO productbacklogDTO = new ProductBacklogDTO();
		productbacklogDTO.setId(productbacklog.getId());
		
		Gson gson = new Gson();
		String output = gson.toJson(productbacklogDTO);
		
		return Response.status(200).entity(output).build();
		
	}
	
}
