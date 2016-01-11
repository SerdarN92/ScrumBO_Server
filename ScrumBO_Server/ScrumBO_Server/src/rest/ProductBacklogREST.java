package rest;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dto.ProductBacklogDTO;
import dto.SprintDTO;
import dto.TaskstatusDTO;
import dto.UserDTO;
import dto.UserStoryDTO;
import dto.UserStoryTaskDTO;
import model.ProductBacklog;
import model.Sprint;
import model.Taskstatus;
import model.User;
import service.ProductBacklogService;
import service.SprintService;

@Path("/productbacklog")
public class ProductBacklogREST {
	
	@Path("/suche/{productbacklogid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getProductBacklogById(@PathParam("productbacklogid") Integer productbacklogid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		ProductBacklogService productbacklogService = new ProductBacklogService(hibernateconfigfilename);
		SprintService sprintService = new SprintService(hibernateconfigfilename);
		ProductBacklog productbacklog = productbacklogService.findById(productbacklogid);
		ProductBacklogDTO productbacklogDTO = new ProductBacklogDTO();
		productbacklogDTO.setId(productbacklog.getId());
		List<UserStoryDTO> userstoryDTOListe = new LinkedList<UserStoryDTO>();
		for (int i = 0; i < productbacklog.getUserstory().size(); i++) {
			UserStoryDTO userstoryDTO = new UserStoryDTO(productbacklog.getUserstory().get(i).getId(),
					productbacklog.getUserstory().get(i).getPriority(), productbacklog.getUserstory().get(i).getTheme(),
					productbacklog.getUserstory().get(i).getDescription(),
					productbacklog.getUserstory().get(i).getEffortInDays(),
					productbacklog.getUserstory().get(i).getAcceptanceCriteria(),
					productbacklog.getUserstory().get(i).getStatus());
			if (productbacklogService.findById(productbacklogid).getUserstory().get(i).getSprint() != null) {
				Integer sprintid = productbacklogService.findById(productbacklogid).getUserstory().get(i).getSprint()
						.getId();
				Sprint sprint = sprintService.findById(sprintid);
				SprintDTO sprintDTO = new SprintDTO(sprint.getId(), sprint.getSprintnumber(), sprint.getStatus());
				userstoryDTO.setSprintbacklog(sprintDTO);
			}
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
					productbacklog.getUserstory().get(i).getPriority(), productbacklog.getUserstory().get(i).getTheme(),
					productbacklog.getUserstory().get(i).getDescription(),
					productbacklog.getUserstory().get(i).getEffortInDays(),
					productbacklog.getUserstory().get(i).getAcceptanceCriteria(),
					productbacklog.getUserstory().get(i).getStatus());
					
			List<UserStoryTaskDTO> userstoryTaskDTOListe = new LinkedList<UserStoryTaskDTO>();
			for (int j = 0; j < productbacklog.getUserstory().get(i).getUserstorytask().size(); j++) {
				if (productbacklog.getUserstory().get(i).getSprint().getId() == sprintid) {
					User user = productbacklog.getUserstory().get(i).getUserstorytask().get(j).getUser();
					UserDTO userDTO = new UserDTO(user.getId(), user.getPrename(), user.getLastname(),
							user.getPassword(), user.getEmail());
					Taskstatus taskstatus = productbacklog.getUserstory().get(i).getUserstorytask().get(j)
							.getTaskstatus();
					TaskstatusDTO taskstatusDTO = new TaskstatusDTO(taskstatus.getId(), taskstatus.getDescription());
					UserStoryTaskDTO userstoryTaskDTO = new UserStoryTaskDTO();
					userstoryTaskDTO.setId(productbacklog.getUserstory().get(i).getUserstorytask().get(j).getId());
					userstoryTaskDTO.setDescription(
							productbacklog.getUserstory().get(i).getUserstorytask().get(j).getDescription());
					userstoryTaskDTO.setEffortInHours(
							productbacklog.getUserstory().get(i).getUserstorytask().get(j).getEffortInHours());
							
					userstoryTaskDTO.setUser(userDTO);
					
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
