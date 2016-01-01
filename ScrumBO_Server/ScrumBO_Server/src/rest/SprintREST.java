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
import service.ScrumprojektService;
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
		Integer sprintnummer = 0;
		sprintnummer = sprintService.countSprintsToProject(scrumprojektid);
		if (sprintnummer > 0) {
			sprint = sprintService.findByProjectIdandSprintNumber(scrumprojektid, sprintnummer);
			sprintDTO = new SprintDTO(sprint.getId(), sprint.getSprintnummer(), sprint.getStatus());
			if (sprint.getBurndownChart() != null) {
				BurndownChartDTO burndownchartDTO = new BurndownChartDTO(sprint.getBurndownChart().getId(),
						sprint.getBurndownChart().getTage());
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
			sprintDTO = new SprintDTO(sprint.getId(), sprint.getSprintnummer(), sprint.getStatus());
			BurndownChartDTO burndownchartDTO = new BurndownChartDTO();
			burndownchartDTO.setId(sprint.getBurndownChart().getId());
			burndownchartDTO.setTage(sprint.getBurndownChart().getTage());
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
	
	@Path("/create/{scrumprojektid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getNewSprint(@PathParam("scrumprojektid") Integer scrumprojektid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		SprintService sprintService = new SprintService(hibernateconfigfilename);
		ScrumprojektService scrumprojektService = new ScrumprojektService(hibernateconfigfilename);
		Sprint sprint = null;
		SprintDTO sprintDTO = null;
		Integer sprintnummer = 0;
		sprintnummer = sprintService.countSprintsAnzahlToProject(scrumprojektid);
		sprint = new Sprint();
		sprint.setSprintnummer(sprintnummer + 1);
		sprint.setScrumprojekt(scrumprojektService.findById(scrumprojektid));
		sprintService.persist(sprint);
		sprint = sprintService.findByProjectIdandSprintNumber(scrumprojektid, sprintnummer + 1);
		sprintDTO = new SprintDTO(sprint.getId(), sprint.getSprintnummer(), sprint.getStatus());
		if (sprint.getBurndownChart() != null) {
			BurndownChartDTO burndownchartDTO = new BurndownChartDTO();
			burndownchartDTO.setId(sprint.getBurndownChart().getId());
			burndownchartDTO.setTage(sprint.getBurndownChart().getTage());
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
			burndownChart.setTage(0);
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
			
			Integer tag = burndownChart.getTage();
			burndownChartService.update(burndownChart);
			
			Integer aufwand = 0;
			
			List<UserStory> userstoryList = userstoryService.findAllBySprintId(sprintid);
			
			for (int i = 0; i < userstoryList.size(); i++) {
				List<UserStoryTask> userstorytaskList = userstorytaskService
						.findAllByUserStoryId(userstoryList.get(i).getId());
				for (int j = 0; j < userstorytaskList.size(); j++) {
					if ((userstorytaskList.get(j).getTaskstatus().getBeschreibung().equals("Offen"))
							|| (userstorytaskList.get(j).getTaskstatus().getBeschreibung().equals("In Arbeit"))) {
						aufwand += userstorytaskList.get(j).getAufwandinstunden();
					}
				}
			}
			
			burndownChartPoint.setX(sprint.getBurndownChart().getTage());
			burndownChartPoint.setY(aufwand);
			burndownChartPoint.setBurndownChart(burndownChart);
			burndownChartPointService.persist(burndownChartPoint);
			
			sprint.getBurndownChart().setTage(sprint.getBurndownChart().getTage() + 1);
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
	
}
