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

import dto.BurndownChartDTO;
import dto.BurndownChartPointDTO;
import dto.ImpedimentDTO;
import dto.ProductBacklogDTO;
import dto.ProjectDTO;
import dto.SprintDTO;
import model.BurndownChart;
import model.ProductBacklog;
import model.Project;
import model.Sprint;
import model.SprintBacklog;
import model.User;
import model.User_Role_Project;
import service.ProductBacklogService;
import service.ProjectService;
import service.SprintBacklogService;
import service.SprintService;
import service.UserService;
import service.User_Role_ProjectService;

@Path("/scrumprojekt")
public class ScrumprojektREST {
	
	@Path("/suche/{projectname}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getProjectByProjectname(@PathParam("projectname") String projectname,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		ProjectService projectService = new ProjectService(hibernateconfigfilename);
		Project project = projectService.findByProjectname(projectname);
		String output = "Projekt nicht vorhanden";
		if (project != null) {
			ProjectDTO projectDTO = new ProjectDTO(project.getId(), project.getProjectname(), project.getPassword());
			List<ImpedimentDTO> impedimentDTOList = new LinkedList<ImpedimentDTO>();
			for (int i = 0; i < project.getImpediment().size(); i++) {
				ImpedimentDTO impedimentDTO = new ImpedimentDTO();
				impedimentDTO.setId(project.getImpediment().get(i).getId());
			}
			projectDTO.setImpediment(impedimentDTOList);
			ProductBacklogDTO productbacklogDTO = new ProductBacklogDTO();
			productbacklogDTO.setId(project.getProductbacklog().getId());
			projectDTO.setProductbacklog(productbacklogDTO);
			List<SprintDTO> sprintDTOList = new LinkedList<SprintDTO>();
			for (int i = 0; i < project.getSprint().size(); i++) {
				SprintDTO sprintDTO = new SprintDTO();
				sprintDTO.setId(project.getSprint().get(i).getId());
				if (project.getSprint().get(i).getBurndownChart() != null) {
					BurndownChartDTO burndownchartDTO = new BurndownChartDTO();
					burndownchartDTO.setId(project.getSprint().get(i).getBurndownChart().getId());
					burndownchartDTO.setDays(project.getSprint().get(i).getBurndownChart().getDays());
					List<BurndownChartPointDTO> burndownchartPointDTOList = new LinkedList<BurndownChartPointDTO>();
					for (int j = 0; j < project.getSprint().get(i).getBurndownChart().getBurndownChartPoint()
							.size(); j++) {
						BurndownChartPointDTO burndownchartPointDTO = new BurndownChartPointDTO();
						burndownchartPointDTO.setId(
								project.getSprint().get(i).getBurndownChart().getBurndownChartPoint().get(j).getId());
						burndownchartPointDTO.setX(
								project.getSprint().get(i).getBurndownChart().getBurndownChartPoint().get(j).getX());
						burndownchartPointDTO.setY(
								project.getSprint().get(i).getBurndownChart().getBurndownChartPoint().get(j).getY());
						burndownchartPointDTOList.add(burndownchartPointDTO);
					}
					burndownchartDTO.setBurndownChartPoint(burndownchartPointDTOList);
					sprintDTO.setBurndownChart(burndownchartDTO);
				}
				sprintDTOList.add(sprintDTO);
			}
			projectDTO.setSprint(sprintDTOList);
			
			Gson gson = new Gson();
			output = gson.toJson(projectDTO);
		}
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/alle/{hibernateconfigfilename}")
	@Produces("application/json" + ";charset=utf-8")
	public Response getScrumprojekteAll(@PathParam("hibernateconfigfilename") String hibernateconfigfilename)
			throws JSONException {
		ProjectService projectService = new ProjectService(hibernateconfigfilename);
		
		List<ProjectDTO> projectDTOList = new LinkedList<ProjectDTO>();
		List<Project> projectList = projectService.findAll();
		for (int i = 0; i < projectList.size(); i++) {
			ProjectDTO projectDTO = new ProjectDTO();
			projectDTO.setId(projectList.get(i).getId());
			projectDTO.setProjectname(projectList.get(i).getProjectname());
			projectDTO.setPassword(projectList.get(i).getPassword());
			List<SprintDTO> sprintDTOList = new LinkedList<SprintDTO>();
			List<ImpedimentDTO> impedimentDTOList = new LinkedList<ImpedimentDTO>();
			
			for (int j = 0; j < projectList.get(i).getSprint().size(); j++) {
				SprintDTO sprintDTO = new SprintDTO();
				sprintDTO.setId(projectList.get(i).getSprint().get(j).getId());
				BurndownChartDTO burndownchartDTO = new BurndownChartDTO();
				if (projectList.get(i).getSprint().get(j).getBurndownChart() != null) {
					burndownchartDTO.setId(projectList.get(i).getSprint().get(j).getBurndownChart().getId());
					burndownchartDTO.setDays(projectList.get(i).getSprint().get(j).getBurndownChart().getDays());
					List<BurndownChartPointDTO> burndownchartPointDTOList = new LinkedList<BurndownChartPointDTO>();
					for (int k = 0; k < projectList.get(i).getSprint().get(j).getBurndownChart().getBurndownChartPoint()
							.size(); k++) {
						BurndownChartPointDTO burndownchartPointDTO = new BurndownChartPointDTO();
						burndownchartPointDTO.setId(projectList.get(i).getSprint().get(j).getBurndownChart()
								.getBurndownChartPoint().get(k).getId());
						burndownchartPointDTO.setX(projectList.get(i).getSprint().get(j).getBurndownChart()
								.getBurndownChartPoint().get(k).getX());
						burndownchartPointDTO.setY(projectList.get(i).getSprint().get(j).getBurndownChart()
								.getBurndownChartPoint().get(k).getY());
						burndownchartPointDTOList.add(burndownchartPointDTO);
					}
					burndownchartDTO.setBurndownChartPoint(burndownchartPointDTOList);
					sprintDTO.setBurndownChart(burndownchartDTO);
				}
				sprintDTOList.add(sprintDTO);
			}
			for (int j = 0; j < projectList.get(i).getImpediment().size(); j++) {
				ImpedimentDTO impedimentDTO = new ImpedimentDTO();
				impedimentDTO.setId(projectList.get(i).getImpediment().get(j).getId());
				
				impedimentDTOList.add(impedimentDTO);
			}
			
			ProductBacklogDTO productbacklogDTO = new ProductBacklogDTO();
			productbacklogDTO.setId(projectList.get(i).getProductbacklog().getId());
			
			projectDTO.setSprint(sprintDTOList);
			projectDTO.setImpediment(impedimentDTOList);
			projectDTO.setProductbacklog(productbacklogDTO);
			projectDTOList.add(projectDTO);
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(projectDTOList);
		
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/create/{userId}/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response createProject(@PathParam("userId") Integer userId, InputStream input,
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
		
		ProjectService sps = new ProjectService(hibernateconfigfilename);
		UserService bs = new UserService(hibernateconfigfilename);
		User_Role_ProjectService bss = new User_Role_ProjectService(hibernateconfigfilename);
		SprintBacklogService sprintbacklogService = new SprintBacklogService(hibernateconfigfilename);
		SprintService sprintService = new SprintService(hibernateconfigfilename);
		
		ProjectDTO projectDTO = gson.fromJson(projectdetails, ProjectDTO.class);
		
		Project scrumprojekt = new Project();
		User benutzer = bs.findById(userId);
		ProductBacklog productbacklog = new ProductBacklog();
		User_Role_Project bssmodel = new User_Role_Project();
		SprintBacklog sprintbacklog = new SprintBacklog();
		Sprint sprint = new Sprint();
		
		Integer sprintbacklogid = 1;
		if (sprintbacklogService.findAll() != null) {
			sprintbacklogid = sprintbacklogService.findAll().size() + 1;
		}
		sprintbacklog.setId(sprintbacklogid);
		sprintbacklogService.persist(sprintbacklog);
		sprintbacklog = sprintbacklogService.findById(sprintbacklogid);
		
		List<User_Role_Project> bssliste = bss.findAll();
		
		scrumprojekt.setProjectname(projectDTO.getProjectname());
		scrumprojekt.setPassword(projectDTO.getPassword());
		scrumprojekt.setProductbacklog(productbacklog);
		
		for (int i = 0; i < bssliste.size(); i++) {
			if (bssliste.get(i).getPk().getUserId() == benutzer.getId() && bssliste.get(i).getPk().getRoleId() == 1) {
				bssmodel = bssliste.get(i);
			}
		}
		
		String output = "";
		try {
			sps.persist(scrumprojekt);
			Integer projectid = sps.findByProjectname(scrumprojekt.getProjectname()).getId();
			User_Role_Project.Pk pk = new User_Role_Project.Pk();
			pk.setUserId(bssmodel.getPk().getUserId());
			pk.setRoleId(bssmodel.getPk().getRoleId());
			pk.setProjectId(projectid);
			bssmodel.setPk(pk);
			bss.persist(bssmodel);
			
			sprint.setSprintnumber(1);
			sprint.setStatus(false);
			sprint.setProject(sps.findById(projectid));
			sprint.setSprintbacklog(sprintbacklog);
			
			BurndownChart burndownChart = new BurndownChart();
			sprint.setBurndownChart(burndownChart);
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
		ProjectService projectService = new ProjectService(hibernateconfigfilename);
		Project scrumprojekt = projectService.findById(projectid);
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
	
	@Path("/delete/{scrumprojektid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response delete(@PathParam("scrumprojektid") Integer scrumprojektid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		User_Role_ProjectService urpService = new User_Role_ProjectService(hibernateconfigfilename);
		ProjectService projectService = new ProjectService(hibernateconfigfilename);
		Project scrumprojekt = projectService.findById(scrumprojektid);
		
		boolean success = false;
		
		try {
			urpService.deleteProject(scrumprojekt.getId());
			projectService.delete(scrumprojekt.getId());
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String output = "Projekt nicht gelöscht";
		if (success) {
			output = "Projekt gelöscht";
		}
		
		return Response.status(200).entity(output).build();
		
	}
	//
	// @POST
	// @Path("/delete/{hibernateconfigfilename}")
	// @Consumes("application/json" + ";charset=utf-8")
	// public Response deleteProject(InputStream input,
	// @PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
	// StringBuilder b = new StringBuilder();
	// try {
	// BufferedReader in = new BufferedReader(new InputStreamReader(input));
	// String line = null;
	// while ((line = in.readLine()) != null) {
	// b.append(line);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// String scrumprojektDetails = b.toString();
	// Gson gson = new Gson();
	// ProjectDTO projectDTO = gson.fromJson(scrumprojektDetails,
	// ProjectDTO.class);
	// User_Role_ProjectService urpService = new
	// User_Role_ProjectService(hibernateconfigfilename);
	// ProjectService projectService = new
	// ProjectService(hibernateconfigfilename);
	// Project scrumprojekt = projectService.findById(projectDTO.getId());
	// boolean success = false;
	// String output = "Projekt nicht gelöscht";
	//
	// try {
	// urpService.deleteProject(scrumprojekt.getId());
	// projectService.delete(scrumprojekt.getId());
	// success = true;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// if (success) {
	// output = "Projekt gelöscht";
	// }
	//
	// return Response.status(200).entity(output).build();
	// }
	
}
