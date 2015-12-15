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

import com.google.gson.Gson;

import dto.UserStoryDTO;
import model.ProductBacklog;
import model.UserStory;
import service.ProductBacklogService;
import service.UserStoryService;

@Path("/userstory")
public class UserStoryREST {
	
	@Path("/suche/productbacklogid/{productbacklogid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getUserStoryByProductBacklogId(@PathParam("productbacklogid") Integer id,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		UserStoryService userstoryService = new UserStoryService(hibernateconfigfilename);
		List<UserStory> userstoryListe = userstoryService.findAllByProductBacklogId(id);
		List<UserStoryDTO> userstoryDTOListe = new LinkedList<UserStoryDTO>();
		for (int i = 0; i < userstoryListe.size(); i++) {
			UserStoryDTO userstoryDTO = new UserStoryDTO();
			userstoryDTO.setId(userstoryListe.get(i).getId());
			userstoryDTO.setPrioritaet(userstoryListe.get(i).getPrioritaet());
			userstoryDTO.setThema(userstoryListe.get(i).getThema());
			userstoryDTO.setBeschreibung(userstoryListe.get(i).getBeschreibung());
			userstoryDTO.setAkzeptanzkriterien(userstoryListe.get(i).getAkzeptanzkriterien());
			userstoryDTO.setAufwandintagen(userstoryListe.get(i).getAufwandintagen());
			userstoryDTOListe.add(userstoryDTO);
		}
		Gson gson = new Gson();
		String output = gson.toJson(userstoryDTOListe);
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/create/{productbacklogId}/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response createUserStory(@PathParam("productbacklogId") Integer id, InputStream input,
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
		String userstorydetails = b.toString();
		Gson gson = new Gson();
		UserStoryDTO userstoryDTO = gson.fromJson(userstorydetails, UserStoryDTO.class);
		UserStory userstory = new UserStory();
		UserStoryService userstoryService = new UserStoryService(hibernateconfigfilename);
		ProductBacklogService productbacklogService = new ProductBacklogService(hibernateconfigfilename);
		ProductBacklog productbacklog = productbacklogService.findById(id);
		userstory.setPrioritaet(userstoryDTO.getPrioritaet());
		userstory.setThema(userstoryDTO.getThema());
		userstory.setBeschreibung(userstoryDTO.getBeschreibung());
		userstory.setAkzeptanzkriterien(userstoryDTO.getAkzeptanzkriterien());
		userstory.setAufwandintagen(userstoryDTO.getAufwandintagen());
		userstory.setProductbacklog(productbacklog);
		// DefinitionOfDone dod = new DefinitionOfDone();
		// DefinitionOfDoneService dodService = new
		// DefinitionOfDoneService(hibernateconfigfilename);
		
		productbacklog.getUserstory().add(userstory);
		String output = "";
		try {
			userstoryService.persist(userstory);
			productbacklogService.update(productbacklog);
			// dodService.persist(dod);
			// dod.setUserstory(userstory);
			// dodService.update(dod);
			output = "User Story erfolgreich erstellt";
		} catch (Exception e) {
			e.printStackTrace();
			output = "User Story wurde nicht erfolgreich erstellt";
		}
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/update/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response updateUserStory(InputStream input,
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
		String userstorydetails = b.toString();
		Gson gson = new Gson();
		UserStoryDTO userstoryDTO = gson.fromJson(userstorydetails, UserStoryDTO.class);
		UserStory userstory = new UserStory();
		UserStoryService userstoryService = new UserStoryService(hibernateconfigfilename);
		userstory = userstoryService.findById(userstoryDTO.getId());
		userstory.setPrioritaet(userstoryDTO.getPrioritaet());
		userstory.setThema(userstoryDTO.getThema());
		userstory.setBeschreibung(userstoryDTO.getBeschreibung());
		userstory.setAkzeptanzkriterien(userstoryDTO.getAkzeptanzkriterien());
		userstory.setAufwandintagen(userstoryDTO.getAufwandintagen());
		String output = "";
		try {
			userstoryService.update(userstory);
			output = "User Story erfolgreich geupdated";
		} catch (Exception e) {
			e.printStackTrace();
			output = "User Story wurde nicht erfolgreich geupdated";
		}
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/delete/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response deleteUserStory(InputStream input,
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
		String userstorydetails = b.toString();
		Gson gson = new Gson();
		UserStoryDTO userstoryDTO = gson.fromJson(userstorydetails, UserStoryDTO.class);
		UserStoryService userstoryService = new UserStoryService(hibernateconfigfilename);
		
		String output = "";
		try {
			userstoryService.delete(userstoryDTO.getId());
			output = "User Story erfolgreich gel�scht";
		} catch (Exception e) {
			e.printStackTrace();
			output = "User Story wurde nicht erfolgreich gel�scht";
		}
		return Response.status(200).entity(output).build();
	}
	
}
