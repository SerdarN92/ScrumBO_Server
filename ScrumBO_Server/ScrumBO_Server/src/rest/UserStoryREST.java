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

import dto.TaskstatusDTO;
import dto.UserDTO;
import dto.UserStoryDTO;
import dto.UserStoryTaskDTO;
import model.ProductBacklog;
import model.Sprint;
import model.Taskstatus;
import model.User;
import model.UserStory;
import model.UserStoryTask;
import service.ProductBacklogService;
import service.SprintService;
import service.TaskstatusService;
import service.UserService;
import service.UserStoryService;
import service.UserStoryTaskService;

@Path("/userstory")
public class UserStoryREST {
	
	private Gson				gson				= new Gson();
	private UserStoryService	userStoryService	= null;
	private UserStory			userstory			= null;
	private UserStoryDTO		userstoryDTO		= null;
													
	@Path("/suche/productbacklogid/{productbacklogid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getUserStoryByProductBacklogId(@PathParam("productbacklogid") Integer id,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		UserStoryService userstoryService = new UserStoryService(hibernateconfigfilename);
		List<UserStory> userstoryListe = userstoryService.findAllByProductBacklogId(id);
		List<UserStoryDTO> userstoryDTOListe = new LinkedList<UserStoryDTO>();
		for (int i = 0; i < userstoryListe.size(); i++) {
			UserStoryDTO userstoryDTO = new UserStoryDTO(userstoryListe.get(i).getId(),
					userstoryListe.get(i).getPriority(), userstoryListe.get(i).getTheme(),
					userstoryListe.get(i).getDescription(), userstoryListe.get(i).getEffortInDays(),
					userstoryListe.get(i).getAcceptanceCriteria(), userstoryListe.get(i).getStatus());
			userstoryDTOListe.add(userstoryDTO);
		}
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
			userstoryDTO.setPriority(userstoryListe.get(i).getPriority());
			userstoryDTO.setTheme(userstoryListe.get(i).getTheme());
			userstoryDTO.setDescription(userstoryListe.get(i).getDescription());
			userstoryDTO.setAcceptanceCriteria(userstoryListe.get(i).getAcceptanceCriteria());
			userstoryDTO.setEffortInDays(userstoryListe.get(i).getEffortInDays());
			userstoryDTO.setStatus(userstoryListe.get(i).getStatus());
			List<UserStoryTaskDTO> userstoryTaskDTOListe = new LinkedList<UserStoryTaskDTO>();
			for (int j = 0; j < userstoryListe.get(i).getUserstorytask().size(); j++) {
				if (userstoryListe.get(i).getSprint().getId() == sprintid) {
					UserStoryTaskDTO userstoryTaskDTO = new UserStoryTaskDTO();
					userstoryTaskDTO.setId(userstoryListe.get(i).getUserstorytask().get(j).getId());
					userstoryTaskDTO.setDescription(userstoryListe.get(i).getUserstorytask().get(j).getDescription());
					userstoryTaskDTO
							.setEffortInHours(userstoryListe.get(i).getUserstorytask().get(j).getEffortInHours());
					User user = null;
					UserDTO userDTO = null;
					if (userstoryListe.get(i).getUserstorytask().get(j).getUser() != null) {
						user = userstoryListe.get(i).getUserstorytask().get(j).getUser();
						userDTO = new UserDTO(user.getId(), user.getPrename(), user.getLastname(), user.getPassword(),
								user.getEmail());
					}
					userstoryTaskDTO.setUser(userDTO);
					Taskstatus taskstatus = userstoryListe.get(i).getUserstorytask().get(j).getTaskstatus();
					TaskstatusDTO taskstatusDTO = new TaskstatusDTO(taskstatus.getId(), taskstatus.getDescription());
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
		
		UserStoryDTO userstoryDTO = gson.fromJson(userstorydetails, UserStoryDTO.class);
		UserStory userstory = new UserStory(userstoryDTO.getPriority(), userstoryDTO.getTheme(),
				userstoryDTO.getDescription(), userstoryDTO.getEffortInDays(), userstoryDTO.getAcceptanceCriteria(), 0);
		userStoryService = new UserStoryService(hibernateconfigfilename);
		ProductBacklogService productbacklogService = new ProductBacklogService(hibernateconfigfilename);
		ProductBacklog productbacklog = productbacklogService.findById(id);
		
		userstory.setProductbacklog(productbacklog);
		
		productbacklog.getUserstory().add(userstory);
		String output = "";
		try {
			userStoryService.persist(userstory);
			productbacklogService.update(productbacklog);
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
		userstory.setPriority(userstoryDTO.getPriority());
		userstory.setTheme(userstoryDTO.getTheme());
		userstory.setDescription(userstoryDTO.getDescription());
		userstory.setAcceptanceCriteria(userstoryDTO.getAcceptanceCriteria());
		userstory.setEffortInDays(userstoryDTO.getEffortInDays());
		userstory.setStatus(userstoryDTO.getStatus());
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
		UserService benutzerService = new UserService(hibernateconfigfilename);
		SprintService sprintService = new SprintService(hibernateconfigfilename);
		Sprint sprint = new Sprint();
		sprint = sprintService.findById(sprintId);
		
		// Prüfung, ob eine UserStory bereits UserstoryTasks hat
		if (userstory.getUserstorytask().size() > 0) {
			if (userstoryDTO.getUserstorytask().isEmpty()) {
				for (int i = 0; i < userstory.getUserstorytask().size(); i++) {
					userstorytaskService.delete(userstory.getUserstorytask().get(i).getId());
					userstory.getUserstorytask().remove(i);
				}
			} else {
				List<UserStoryTask> list = userstorytaskService.findAllByUserStoryId(userstory.getId());
				for (int i = 0; i < list.size(); i++) {
					userstory.getUserstorytask().remove(list.get(i));
				}
				for (int i = 0; i < userstoryDTO.getUserstorytask().size(); i++) {
					UserStoryTask userstorytask = null;
					if (userstoryDTO.getUserstorytask().get(i).getUser() != null) {
						if (userstoryDTO.getUserstorytask().get(i).getUser().getId() != null) {
							userstorytask = new UserStoryTask(userstoryDTO.getUserstorytask().get(i).getDescription(),
									taskstatusService
											.findById(userstoryDTO.getUserstorytask().get(i).getTaskstatus().getId()),
									userstoryDTO.getUserstorytask().get(i).getEffortInHours(),
									benutzerService.findById(userstoryDTO.getUserstorytask().get(i).getUser().getId()),
									userstory);
						} else {
							userstorytask = new UserStoryTask(userstoryDTO.getUserstorytask().get(i).getDescription(),
									taskstatusService.findById(1),
									userstoryDTO.getUserstorytask().get(i).getEffortInHours(), null, userstory);
						}
					} else {
						userstorytask = new UserStoryTask(userstoryDTO.getUserstorytask().get(i).getDescription(),
								taskstatusService.findById(1),
								userstoryDTO.getUserstorytask().get(i).getEffortInHours(), null, userstory);
					}
					userstory.getUserstorytask().add(userstorytask);
					userstorytaskService.persist(userstorytask);
				}
				
			}
		} else {
			if (userstoryDTO.getUserstorytask().size() > 0) {
				for (int i = 0; i < userstoryDTO.getUserstorytask().size(); i++) {
					UserStoryTask userstorytask = null;
					if (userstoryDTO.getUserstorytask().get(i).getUser().getId() != null) {
						userstorytask = new UserStoryTask(userstoryDTO.getUserstorytask().get(i).getDescription(),
								taskstatusService.findById(1),
								userstoryDTO.getUserstorytask().get(i).getEffortInHours(),
								benutzerService.findById(userstoryDTO.getUserstorytask().get(i).getUser().getId()),
								userstory);
					} else {
						userstorytask = new UserStoryTask(userstoryDTO.getUserstorytask().get(i).getDescription(),
								taskstatusService.findById(1),
								userstoryDTO.getUserstorytask().get(i).getEffortInHours(), null, userstory);
					}
					userstory.getUserstorytask().add(userstorytask);
					userstorytaskService.persist(userstorytask);
				}
			}
		}
		
		userstory.setSprint(sprint);
		
		String output = "";
		try {
			if (!userstory.getUserstorytask().isEmpty()) {
				userstoryService.update(userstory);
			}
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
			userstoryDTO.setPriority(userstoryListe.get(i).getPriority());
			userstoryDTO.setTheme(userstoryListe.get(i).getTheme());
			userstoryDTO.setDescription(userstoryListe.get(i).getDescription());
			userstoryDTO.setAcceptanceCriteria(userstoryListe.get(i).getAcceptanceCriteria());
			userstoryDTO.setEffortInDays(userstoryListe.get(i).getEffortInDays());
			List<UserStoryTaskDTO> userstoryTaskDTOListe = new LinkedList<UserStoryTaskDTO>();
			for (int j = 0; j < userstoryListe.get(i).getUserstorytask().size(); j++) {
				if (userstoryListe.get(i).getProductbacklog().getId() == productbacklogid) {
					UserStoryTaskDTO userstoryTaskDTO = new UserStoryTaskDTO();
					userstoryTaskDTO.setId(userstoryListe.get(i).getUserstorytask().get(j).getId());
					userstoryTaskDTO.setDescription(userstoryListe.get(i).getUserstorytask().get(j).getDescription());
					userstoryTaskDTO
							.setEffortInHours(userstoryListe.get(i).getUserstorytask().get(j).getEffortInHours());
					User benutzer = userstoryListe.get(i).getUserstorytask().get(j).getUser();
					if (benutzer != null) {
						UserDTO benutzerDTO = new UserDTO(benutzer.getId(), benutzer.getPrename(),
								benutzer.getLastname(), benutzer.getPassword(), benutzer.getEmail());
						userstoryTaskDTO.setUser(benutzerDTO);
					}
					Taskstatus taskstatus = userstoryListe.get(i).getUserstorytask().get(j).getTaskstatus();
					TaskstatusDTO taskstatusDTO = new TaskstatusDTO(taskstatus.getId(), taskstatus.getDescription());
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
	
	@Path("/deleteFromSprint/{userstoryid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response deleteUserStoryFromSprint(@PathParam("userstoryid") Integer userstoryId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		String output = "Fehler";
		UserStoryTaskService userstorytaskService = new UserStoryTaskService(hibernateconfigfilename);
		UserStoryService userstoryService = new UserStoryService(hibernateconfigfilename);
		UserStory userstory = null;
		try {
			userstory = userstoryService.findById(userstoryId);
			for (int i = 0; i < userstory.getUserstorytask().size(); i++) {
				userstorytaskService.delete(userstory.getUserstorytask().get(i).getId());
			}
			
			if (userstoryService.setSprintNull(userstoryId))
				output = "User Story aus Sprint entfernt";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.status(200).entity(output).build();
	}
	
	@Path("/setUserStoryOnUserId/{userstoryid}/{userid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response setUserStoryOnUserId(@PathParam("userid") Integer userId,
			@PathParam("userstoryid") Integer userstoryId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		String output = "Fehler";
		UserStoryTaskService userstorytaskService = new UserStoryTaskService(hibernateconfigfilename);
		UserStoryService userstoryService = new UserStoryService(hibernateconfigfilename);
		UserStory userstory = null;
		try {
			userstory = userstoryService.findById(userstoryId);
			for (int i = 0; i < userstory.getUserstorytask().size(); i++) {
				userstorytaskService.delete(userstory.getUserstorytask().get(i).getId());
			}
			
			if (userstoryService.setSprintNull(userstoryId))
				output = "User Story aus Sprint entfernt";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.status(200).entity(output).build();
	}
	
}
