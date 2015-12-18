package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dto.SprintDTO;
import model.Sprint;
import service.ScrumprojektService;
import service.SprintService;

@Path("/sprint")
public class SprintREST {
	
	@Path("/suche/{scrumprojektid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getCurrentSprinttoProject(@PathParam("scrumprojektid") Integer scrumprojektid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		SprintService sprintService = new SprintService(hibernateconfigfilename);
		Sprint sprint = null;
		SprintDTO sprintDTO = null;
		Integer count = 0;
		count = sprintService.countSprintsToProject(scrumprojektid);
		if (count > 0) {
			sprint = sprintService.findByProjectIdandCount(count, scrumprojektid);
			sprintDTO = new SprintDTO(sprint.getId(), sprint.getSprintnummer());
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(sprintDTO);
		
		return Response.status(200).entity(output).build();
	}
	
	@Path("/suche/{scrumprojektid}/anzahl/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getAnzahlSprintsToProject(@PathParam("scrumprojektid") Integer scrumprojektid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		SprintService sprintService = new SprintService(hibernateconfigfilename);
		Integer count = 0;
		count = sprintService.countSprintsAnzahlToProject(scrumprojektid);
		String output = count.toString();
		
		return Response.status(200).entity(output).build();
	}
	
	@Path("/suche/{scrumprojektid}/{sprintnummer}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getSprintID(@PathParam("scrumprojektid") Integer scrumprojektid,
			@PathParam("sprintnummer") Integer sprintnummer,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		SprintService sprintService = new SprintService(hibernateconfigfilename);
		Sprint sprint = null;
		SprintDTO sprintDTO = null;
		sprint = sprintService.findByProjectIdandSprintNumber(scrumprojektid, sprintnummer);
		if (sprint != null) {
			sprintDTO = new SprintDTO(sprint.getId(), sprint.getSprintnummer());
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(sprintDTO);
		
		return Response.status(200).entity(output).build();
	}
	
	@Path("/create/{scrumprojektid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getNewSprint(@PathParam("scrumprojektid") Integer scrumprojektid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		SprintService sprintService = new SprintService(hibernateconfigfilename);
		ScrumprojektService scrumprojektService = new ScrumprojektService(hibernateconfigfilename);
		Sprint sprint = null;
		SprintDTO sprintDTO = null;
		Integer count = 0;
		count = sprintService.countSprintsAnzahlToProject(scrumprojektid);
		sprint = new Sprint();
		sprint.setSprintnummer(count + 1);
		sprint.setScrumprojekt(scrumprojektService.findById(scrumprojektid));
		sprintService.persist(sprint);
		sprint = sprintService.findByProjectIdandCount(count + 1, scrumprojektid);
		System.out.println("SPRINTID:" + sprint.getId());
		sprintDTO = new SprintDTO(sprint.getId(), sprint.getSprintnummer());
		
		Gson gson = new Gson();
		String output = gson.toJson(sprintDTO);
		
		return Response.status(200).entity(output).build();
	}
	
}
