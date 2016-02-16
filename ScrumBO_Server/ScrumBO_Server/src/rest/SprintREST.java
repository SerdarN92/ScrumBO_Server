package rest;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dto.BurndownChartDTO;
import dto.BurndownChartPointDTO;
import dto.SprintDTO;
import model.BurndownChart;
import model.BurndownChartPoint;
import model.Sprint;
import model.UserStory;
import model.UserStoryTask;
import service.BurndownChartPointService;
import service.BurndownChartService;
import service.ProjectService;
import service.SprintService;
import service.UserStoryService;
import service.UserStoryTaskService;

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
		Integer sprintnumber = 0;
		sprintnumber = sprintService.countSprintsToProject(scrumprojektid);
		if (sprintnumber > 0) {
			sprint = sprintService.findByProjectIdandSprintNumber(scrumprojektid, sprintnumber);
			sprintDTO = new SprintDTO(sprint.getId(), sprint.getSprintnumber(), sprint.getStatus());
			if (sprint.getBurndownChart() != null) {
				BurndownChartDTO burndownchartDTO = new BurndownChartDTO(sprint.getBurndownChart().getId(),
						sprint.getBurndownChart().getDays());
				List<BurndownChartPointDTO> burndownchartPointDTOListe = new LinkedList<BurndownChartPointDTO>();
				for (int i = 0; i < sprint.getBurndownChart().getBurndownChartPoint().size(); i++) {
					BurndownChartPointDTO burndownchartPointDTO = new BurndownChartPointDTO();
					burndownchartPointDTO.setId(sprint.getBurndownChart().getBurndownChartPoint().get(i).getId());
					burndownchartPointDTO.setX(sprint.getBurndownChart().getBurndownChartPoint().get(i).getX());
					burndownchartPointDTO.setY(sprint.getBurndownChart().getBurndownChartPoint().get(i).getY());
					burndownchartPointDTOListe.add(burndownchartPointDTO);
				}
				burndownchartDTO.setBurndownChartPoint(burndownchartPointDTOListe);
				sprintDTO.setBurndownChart(burndownchartDTO);
			}
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(sprintDTO);
		return Response.status(200).entity(output).build();
	}
	
	@Path("/suche/{projectId}/anzahl/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getAnzahlSprintsToProject(@PathParam("projectId") Integer projectId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		SprintService sprintService = new SprintService(hibernateconfigfilename);
		Integer count = 0;
		count = sprintService.countNumberOfSprintsOfProject(projectId);
		String output = count.toString();
		return Response.status(200).entity(output).build();
	}
	
	@Path("/suche/{projectId}/{sprintnummer}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getSprintID(@PathParam("projectId") Integer projectId,
			@PathParam("sprintnummer") Integer sprintnummer,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		SprintService sprintService = new SprintService(hibernateconfigfilename);
		Sprint sprint = null;
		SprintDTO sprintDTO = null;
		sprint = sprintService.findByProjectIdandSprintNumber(projectId, sprintnummer);
		if (sprint != null) {
			sprintDTO = new SprintDTO(sprint.getId(), sprint.getSprintnumber(), sprint.getStatus());
			if (sprint.getBurndownChart() != null) {
				BurndownChartDTO burndownchartDTO = new BurndownChartDTO();
				burndownchartDTO.setId(sprint.getBurndownChart().getId());
				burndownchartDTO.setDays(sprint.getBurndownChart().getDays());
				List<BurndownChartPointDTO> burndownchartPointDTOListe = new LinkedList<BurndownChartPointDTO>();
				for (int i = 0; i < sprint.getBurndownChart().getBurndownChartPoint().size(); i++) {
					BurndownChartPointDTO burndownchartPointDTO = new BurndownChartPointDTO();
					burndownchartPointDTO.setId(sprint.getBurndownChart().getBurndownChartPoint().get(i).getId());
					burndownchartPointDTO.setX(sprint.getBurndownChart().getBurndownChartPoint().get(i).getX());
					burndownchartPointDTO.setY(sprint.getBurndownChart().getBurndownChartPoint().get(i).getY());
					burndownchartPointDTOListe.add(burndownchartPointDTO);
				}
				burndownchartDTO.setBurndownChartPoint(burndownchartPointDTOListe);
				sprintDTO.setBurndownChart(burndownchartDTO);
			}
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(sprintDTO);
		return Response.status(200).entity(output).build();
	}
	
	@Path("/create/{projectId}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getNewSprint(@PathParam("projectId") Integer projectId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		SprintService sprintService = new SprintService(hibernateconfigfilename);
		ProjectService scrumprojektService = new ProjectService(hibernateconfigfilename);
		Sprint sprint = null;
		SprintDTO sprintDTO = null;
		Integer sprintnummer = 0;
		sprintnummer = sprintService.countNumberOfSprintsOfProject(projectId);
		sprint = new Sprint();
		sprint.setSprintnumber(sprintnummer + 1);
		sprint.setProject(scrumprojektService.findById(projectId));
		sprintService.persist(sprint);
		sprint = sprintService.findByProjectIdandSprintNumber(projectId, sprintnummer + 1);
		sprintDTO = new SprintDTO(sprint.getId(), sprint.getSprintnumber(), sprint.getStatus());
		if (sprint.getBurndownChart() != null) {
			BurndownChartDTO burndownchartDTO = new BurndownChartDTO();
			burndownchartDTO.setId(sprint.getBurndownChart().getId());
			burndownchartDTO.setDays(sprint.getBurndownChart().getDays());
			List<BurndownChartPointDTO> burndownchartPointDTOListe = new LinkedList<BurndownChartPointDTO>();
			for (int i = 0; i < sprint.getBurndownChart().getBurndownChartPoint().size(); i++) {
				BurndownChartPointDTO burndownchartPointDTO = new BurndownChartPointDTO();
				burndownchartPointDTO.setId(sprint.getBurndownChart().getBurndownChartPoint().get(i).getId());
				burndownchartPointDTO.setX(sprint.getBurndownChart().getBurndownChartPoint().get(i).getX());
				burndownchartPointDTO.setY(sprint.getBurndownChart().getBurndownChartPoint().get(i).getY());
				burndownchartPointDTOListe.add(burndownchartPointDTO);
			}
			burndownchartDTO.setBurndownChartPoint(burndownchartPointDTOListe);
			sprintDTO.setBurndownChart(burndownchartDTO);
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(sprintDTO);
		return Response.status(200).entity(output).build();
	}
	
	@Path("/createBurndownChart/{sprintid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response createBurndownChart(@PathParam("sprintid") Integer sprintid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		boolean status = false;
		try {
			SprintService sprintService = new SprintService(hibernateconfigfilename);
			Sprint sprint = sprintService.findById(sprintid);
			BurndownChartService burndownChartService = new BurndownChartService(hibernateconfigfilename);
			BurndownChart burndownChart = new BurndownChart();
			burndownChart.setDays(0);
			burndownChartService.persist(burndownChart);
			sprint.setBurndownChart(burndownChart);
			sprint.setStatus(true);
			sprintService.update(sprint);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String output = "false";
		if (status) {
			output = "true";
		}
		return Response.status(200).entity(output).build();
	}
	
	@Path("/endDay/{sprintid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response endDay(@PathParam("sprintid") Integer sprintid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		boolean status = false;
		try {
			SprintService sprintService = new SprintService(hibernateconfigfilename);
			BurndownChartService burndownChartService = new BurndownChartService(hibernateconfigfilename);
			UserStoryTaskService userstorytaskService = new UserStoryTaskService(hibernateconfigfilename);
			UserStoryService userstoryService = new UserStoryService(hibernateconfigfilename);
			BurndownChartPointService burndownChartPointService = new BurndownChartPointService(
					hibernateconfigfilename);
					
			Sprint sprint = sprintService.findById(sprintid);
			
			BurndownChart burndownChart = new BurndownChart();
			burndownChart = burndownChartService.findById(sprint.getBurndownChart().getId());
			
			BurndownChartPoint burndownChartPoint = new BurndownChartPoint();
			
			Integer tag = burndownChart.getDays();
			burndownChartService.update(burndownChart);
			
			Integer aufwand = 0;
			
			List<UserStory> userstoryList = userstoryService.findAllBySprintId(sprintid);
			
			for (int i = 0; i < userstoryList.size(); i++) {
				List<UserStoryTask> userstorytaskList = userstorytaskService
						.findAllByUserStoryId(userstoryList.get(i).getId());
				for (int j = 0; j < userstorytaskList.size(); j++) {
					if ((userstorytaskList.get(j).getTaskstatus().getDescription().equals("Offen"))
							|| (userstorytaskList.get(j).getTaskstatus().getDescription().equals("In Arbeit"))) {
						aufwand += userstorytaskList.get(j).getEffortInHours();
					}
				}
			}
			
			burndownChartPoint.setX(sprint.getBurndownChart().getDays());
			burndownChartPoint.setY(aufwand);
			burndownChartPoint.setBurndownChart(burndownChart);
			burndownChartPointService.persist(burndownChartPoint);
			
			sprint.getBurndownChart().setDays(sprint.getBurndownChart().getDays() + 1);
			sprintService.update(sprint);
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		String output = "false";
		if (status) {
			output = "true";
		}
		return Response.status(200).entity(output).build();
	}
	
	@Path("/removeIncompleteUserStories/{sprintid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response removeIncompleteUserStories(@PathParam("sprintid") Integer sprintid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		String output = "";
		UserStoryService userStoryService = new UserStoryService(hibernateconfigfilename);
		List<UserStory> list = userStoryService.findAllBySprintId(sprintid);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getStatus() == 0 || list.get(i).getStatus() == 1) {
				list.get(i).setSprint(null);
				for (int j = 0; j < list.get(i).getUserstorytask().size(); j++) {
					list.get(i).getUserstorytask().remove(j);
				}
			}
			userStoryService.update(list.get(i));
		}
		
		return Response.status(200).entity(output).build();
	}
	
}
