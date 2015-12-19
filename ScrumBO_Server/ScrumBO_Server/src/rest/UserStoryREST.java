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

import dto.BenutzerDTO;
import dto.TaskstatusDTO;
import dto.UserStoryDTO;
import dto.UserStoryTaskDTO;
import model.Benutzer;
import model.ProductBacklog;
import model.Sprint;
import model.Taskstatus;
import model.UserStory;
import model.UserStoryTask;
import service.BenutzerService;
import service.ProductBacklogService;
import service.SprintService;
import service.TaskstatusService;
import service.UserStoryService;
import service.UserStoryTaskService;

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
	
	@Path("/suche/sprintid/{sprintid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getUserStoryBySprintId(@PathParam("sprintid") Integer sprintid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		UserStoryService userstoryService = new UserStoryService(hibernateconfigfilename);
		List<UserStory> userstoryListe = userstoryService.findAllBySprintId(sprintid);
		List<UserStoryDTO> userstoryDTOListe = new LinkedList<UserStoryDTO>();
		for (int i = 0; i < userstoryListe.size(); i++) {
			UserStoryDTO userstoryDTO = new UserStoryDTO();
			userstoryDTO.setId(userstoryListe.get(i).getId());
			userstoryDTO.setPrioritaet(userstoryListe.get(i).getPrioritaet());
			userstoryDTO.setThema(userstoryListe.get(i).getThema());
			userstoryDTO.setBeschreibung(userstoryListe.get(i).getBeschreibung());
			userstoryDTO.setAkzeptanzkriterien(userstoryListe.get(i).getAkzeptanzkriterien());
			userstoryDTO.setAufwandintagen(userstoryListe.get(i).getAufwandintagen());
			List<UserStoryTaskDTO> userstoryTaskDTOListe = new LinkedList<UserStoryTaskDTO>();
			for (int j = 0; j < userstoryListe.get(i).getUserstorytask().size(); j++) {
				if (userstoryListe.get(i).getSprint().getId() == sprintid) {
					UserStoryTaskDTO userstoryTaskDTO = new UserStoryTaskDTO();
					userstoryTaskDTO.setId(userstoryListe.get(i).getUserstorytask().get(j).getId());
					userstoryTaskDTO.setBeschreibung(userstoryListe.get(i).getUserstorytask().get(j).getBeschreibung());
					userstoryTaskDTO
							.setAufwandinstunden(userstoryListe.get(i).getUserstorytask().get(j).getAufwandinstunden());
					Benutzer benutzer = userstoryListe.get(i).getUserstorytask().get(j).getBenutzer();
					BenutzerDTO benutzerDTO = new BenutzerDTO(benutzer.getId(), benutzer.getVorname(),
							benutzer.getNachname(), benutzer.getPasswort(), benutzer.getEmail());
					userstoryTaskDTO.setBenutzer(benutzerDTO);
					Taskstatus taskstatus = userstoryListe.get(i).getUserstorytask().get(j).getTaskstatus();
					TaskstatusDTO taskstatusDTO = new TaskstatusDTO(taskstatus.getId(), taskstatus.getBeschreibung());
					userstoryTaskDTO.setTaskstatus(taskstatusDTO);
					userstoryTaskDTOListe.add(userstoryTaskDTO);
				}
			}
			userstoryDTO.setUserstorytask(userstoryTaskDTOListe);
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
	@Path("/updateTasks/{sprintid}/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response updateUserStoryTasks(@PathParam("sprintid") Integer sprintId, InputStream input,
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
		UserStory userstory = new UserStory();
		userstory = userstoryService.findById(userstoryDTO.getId());
		
		UserStoryTaskService userstorytaskService = new UserStoryTaskService(hibernateconfigfilename);
		TaskstatusService taskstatusService = new TaskstatusService(hibernateconfigfilename);
		BenutzerService benutzerService = new BenutzerService(hibernateconfigfilename);
		SprintService sprintService = new SprintService(hibernateconfigfilename);
		Sprint sprint = new Sprint();
		sprint = sprintService.findById(sprintId);
		
		for (int i = 0; i < userstoryDTO.getUserstorytask().size(); i++) {
			if (!(userstory.getUserstorytask().contains(userstoryDTO.getUserstorytask().get(i)))) {
				System.out.println(userstoryDTO.getUserstorytask().get(i).getBeschreibung());
				UserStoryTask userstorytask = new UserStoryTask(
						userstoryDTO.getUserstorytask().get(i).getBeschreibung(), taskstatusService.findById(1),
						userstoryDTO.getUserstorytask().get(i).getAufwandinstunden(),
						benutzerService.findById(userstoryDTO.getUserstorytask().get(i).getBenutzer().getId()),
						userstory);
				userstorytaskService.persist(userstorytask);
			}
		}
		
		userstory.setSprint(sprint);
		
		String output = "";
		try {
			userstoryService.update(userstory);
			output = "Tasks erfolgreich geupdated";
		} catch (Exception e) {
			e.printStackTrace();
			output = "Tasks wurden nicht erfolgreich geupdated";
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
			output = "User Story erfolgreich gelöscht";
		} catch (Exception e) {
			e.printStackTrace();
			output = "User Story wurde nicht erfolgreich gelöscht";
		}
		return Response.status(200).entity(output).build();
	}
	
	@Path("/sucheNULL/productbacklogid/{productbacklogid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getUserStoryNULLByProductBacklogId(@PathParam("productbacklogid") Integer productbacklogid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		UserStoryService userstoryService = new UserStoryService(hibernateconfigfilename);
		List<UserStory> userstoryListe = userstoryService.findAllNULLwithProductBacklogId(productbacklogid);
		List<UserStoryDTO> userstoryDTOListe = new LinkedList<UserStoryDTO>();
		for (int i = 0; i < userstoryListe.size(); i++) {
			UserStoryDTO userstoryDTO = new UserStoryDTO();
			userstoryDTO.setId(userstoryListe.get(i).getId());
			userstoryDTO.setPrioritaet(userstoryListe.get(i).getPrioritaet());
			userstoryDTO.setThema(userstoryListe.get(i).getThema());
			userstoryDTO.setBeschreibung(userstoryListe.get(i).getBeschreibung());
			userstoryDTO.setAkzeptanzkriterien(userstoryListe.get(i).getAkzeptanzkriterien());
			userstoryDTO.setAufwandintagen(userstoryListe.get(i).getAufwandintagen());
			List<UserStoryTaskDTO> userstoryTaskDTOListe = new LinkedList<UserStoryTaskDTO>();
			for (int j = 0; j < userstoryListe.get(i).getUserstorytask().size(); j++) {
				if (userstoryListe.get(i).getProductbacklog().getId() == productbacklogid) {
					UserStoryTaskDTO userstoryTaskDTO = new UserStoryTaskDTO();
					userstoryTaskDTO.setId(userstoryListe.get(i).getUserstorytask().get(j).getId());
					userstoryTaskDTO.setBeschreibung(userstoryListe.get(i).getUserstorytask().get(j).getBeschreibung());
					userstoryTaskDTO
							.setAufwandinstunden(userstoryListe.get(i).getUserstorytask().get(j).getAufwandinstunden());
					Benutzer benutzer = userstoryListe.get(i).getUserstorytask().get(j).getBenutzer();
					BenutzerDTO benutzerDTO = new BenutzerDTO(benutzer.getId(), benutzer.getVorname(),
							benutzer.getNachname(), benutzer.getPasswort(), benutzer.getEmail());
					userstoryTaskDTO.setBenutzer(benutzerDTO);
					Taskstatus taskstatus = userstoryListe.get(i).getUserstorytask().get(j).getTaskstatus();
					TaskstatusDTO taskstatusDTO = new TaskstatusDTO(taskstatus.getId(), taskstatus.getBeschreibung());
					userstoryTaskDTO.setTaskstatus(taskstatusDTO);
					userstoryTaskDTOListe.add(userstoryTaskDTO);
				}
			}
			userstoryDTO.setUserstorytask(userstoryTaskDTOListe);
			userstoryDTOListe.add(userstoryDTO);
		}
		Gson gson = new Gson();
		String output = gson.toJson(userstoryDTOListe);
		return Response.status(200).entity(output).build();
	}
	
}
