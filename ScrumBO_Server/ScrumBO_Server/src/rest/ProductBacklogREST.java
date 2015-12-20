package rest;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dto.BenutzerDTO;
import dto.ProductBacklogDTO;
import dto.TaskstatusDTO;
import dto.UserStoryDTO;
import dto.UserStoryTaskDTO;
import model.Benutzer;
import model.ProductBacklog;
import model.Taskstatus;
import service.BenutzerService;
import service.ProductBacklogService;
import service.TaskstatusService;

@Path("/productbacklog")
public class ProductBacklogREST {
	
	@Path("/suche/{productbacklogid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getProductBacklogById(@PathParam("productbacklogid") Integer productbacklogid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		ProductBacklogService productbacklogService = new ProductBacklogService(hibernateconfigfilename);
		ProductBacklog productbacklog = productbacklogService.findById(productbacklogid);
		ProductBacklogDTO productbacklogDTO = new ProductBacklogDTO();
		productbacklogDTO.setId(productbacklog.getId());
		List<UserStoryDTO> userstoryDTOListe = new LinkedList<UserStoryDTO>();
		for (int i = 0; i < productbacklog.getUserstory().size(); i++) {
			UserStoryDTO userstoryDTO = new UserStoryDTO(productbacklog.getUserstory().get(i).getId(),
					productbacklog.getUserstory().get(i).getPrioritaet(),
					productbacklog.getUserstory().get(i).getThema(),
					productbacklog.getUserstory().get(i).getBeschreibung(),
					productbacklog.getUserstory().get(i).getAufwandintagen(),
					productbacklog.getUserstory().get(i).getAkzeptanzkriterien(),
					productbacklog.getUserstory().get(i).getStatus());
			System.out.println("STATUS:" + productbacklog.getUserstory().get(i).getStatus());
			userstoryDTOListe.add(userstoryDTO);
		}
		productbacklogDTO.setUserstory(userstoryDTOListe);
		
		Gson gson = new Gson();
		String output = gson.toJson(productbacklogDTO);
		
		return Response.status(200).entity(output).build();
		
	}
	
	@Path("/sucheSB/{productbacklogid}/{sprintid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getProductBacklogByIdForSB(@PathParam("sprintid") Integer sprintid,
			@PathParam("productbacklogid") Integer productbacklogid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		ProductBacklogService productbacklogService = new ProductBacklogService(hibernateconfigfilename);
		ProductBacklog productbacklog = productbacklogService.findById(productbacklogid);
		ProductBacklogDTO productbacklogDTO = new ProductBacklogDTO();
		productbacklogDTO.setId(productbacklog.getId());
		List<UserStoryDTO> userstoryDTOListe = new LinkedList<UserStoryDTO>();
		
		for (int i = 0; i < productbacklog.getUserstory().size(); i++) {
			UserStoryDTO userstoryDTO = new UserStoryDTO(productbacklog.getUserstory().get(i).getId(),
					productbacklog.getUserstory().get(i).getPrioritaet(),
					productbacklog.getUserstory().get(i).getThema(),
					productbacklog.getUserstory().get(i).getBeschreibung(),
					productbacklog.getUserstory().get(i).getAufwandintagen(),
					productbacklog.getUserstory().get(i).getAkzeptanzkriterien(),
					productbacklog.getUserstory().get(i).getStatus());
			System.out.println("STATUS:" + productbacklog.getUserstory().get(i).getStatus());
			BenutzerService benutzerService = new BenutzerService(hibernateconfigfilename);
			TaskstatusService taskstatusService = new TaskstatusService(hibernateconfigfilename);
			List<UserStoryTaskDTO> userstoryTaskDTOListe = new LinkedList<UserStoryTaskDTO>();
			for (int j = 0; j < productbacklog.getUserstory().get(i).getUserstorytask().size(); j++) {
				if (productbacklog.getUserstory().get(i).getSprint().getId() == sprintid) {
					Benutzer benutzer = productbacklog.getUserstory().get(i).getUserstorytask().get(j).getBenutzer();
					BenutzerDTO benutzerDTO = new BenutzerDTO(benutzer.getId(), benutzer.getVorname(),
							benutzer.getNachname(), benutzer.getPasswort(), benutzer.getEmail());
					Taskstatus taskstatus = productbacklog.getUserstory().get(i).getUserstorytask().get(j)
							.getTaskstatus();
					TaskstatusDTO taskstatusDTO = new TaskstatusDTO(taskstatus.getId(), taskstatus.getBeschreibung());
					UserStoryTaskDTO userstoryTaskDTO = new UserStoryTaskDTO();
					userstoryTaskDTO.setId(productbacklog.getUserstory().get(i).getUserstorytask().get(j).getId());
					userstoryTaskDTO.setBeschreibung(
							productbacklog.getUserstory().get(i).getUserstorytask().get(j).getBeschreibung());
					userstoryTaskDTO.setAufwandinstunden(
							productbacklog.getUserstory().get(i).getUserstorytask().get(j).getAufwandinstunden());
							
					userstoryTaskDTO.setBenutzer(benutzerDTO);
					
					userstoryTaskDTO.setTaskstatus(taskstatusDTO);
					userstoryTaskDTOListe.add(userstoryTaskDTO);
				}
			}
			userstoryDTO.setUserstorytask(userstoryTaskDTOListe);
			userstoryDTOListe.add(userstoryDTO);
		}
		productbacklogDTO.setUserstory(userstoryDTOListe);
		
		Gson gson = new Gson();
		String output = gson.toJson(productbacklogDTO);
		
		return Response.status(200).entity(output).build();
		
	}
}
