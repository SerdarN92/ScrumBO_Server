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
import dto.ScrumprojektDTO;
import dto.SprintDTO;
import model.Benutzer;
import model.Benutzer_Benutzerrolle_Scrumprojekt;
import model.BurndownChart;
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
			ScrumprojektDTO scrumprojektDTO = new ScrumprojektDTO(scrumprojekt.getId(), scrumprojekt.getProjektname(),
					scrumprojekt.getPasswort());
			List<ImpedimentDTO> impedimentDTOListe = new LinkedList<ImpedimentDTO>();
			for (int i = 0; i < scrumprojekt.getImpediment().size(); i++) {
				ImpedimentDTO impedimentDTO = new ImpedimentDTO();
				impedimentDTO.setId(scrumprojekt.getImpediment().get(i).getId());
			}
			scrumprojektDTO.setImpediment(impedimentDTOListe);
			ProductBacklogDTO productbacklogDTO = new ProductBacklogDTO();
			productbacklogDTO.setId(scrumprojekt.getProductbacklog().getId());
			scrumprojektDTO.setProductbacklog(productbacklogDTO);
			List<SprintDTO> sprintDTOListe = new LinkedList<SprintDTO>();
			for (int i = 0; i < scrumprojekt.getSprint().size(); i++) {
				SprintDTO sprintDTO = new SprintDTO();
				sprintDTO.setId(scrumprojekt.getSprint().get(i).getId());
				if (scrumprojekt.getSprint().get(i).getBurndownChart() != null) {
					BurndownChartDTO burndownchartDTO = new BurndownChartDTO();
					burndownchartDTO.setId(scrumprojekt.getSprint().get(i).getBurndownChart().getId());
					burndownchartDTO.setTage(scrumprojekt.getSprint().get(i).getBurndownChart().getTage());
					List<BurndownChartPointDTO> burndownchartPointDTOListe = new LinkedList<BurndownChartPointDTO>();
					for (int j = 0; j < scrumprojekt.getSprint().get(i).getBurndownChart().getBurndownChartPoint()
							.size(); j++) {
						BurndownChartPointDTO burndownchartPointDTO = new BurndownChartPointDTO();
						burndownchartPointDTO.setId(scrumprojekt.getSprint().get(i).getBurndownChart()
								.getBurndownChartPoint().get(j).getId());
						burndownchartPointDTO.setX(scrumprojekt.getSprint().get(i).getBurndownChart()
								.getBurndownChartPoint().get(j).getX());
						burndownchartPointDTO.setY(scrumprojekt.getSprint().get(i).getBurndownChart()
								.getBurndownChartPoint().get(j).getY());
						burndownchartPointDTOListe.add(burndownchartPointDTO);
					}
					burndownchartDTO.setBurndownChartPoint(burndownchartPointDTOListe);
					sprintDTO.setBurndownChart(burndownchartDTO);
				}
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
			List<SprintDTO> sprintDTOListe = new LinkedList<SprintDTO>();
			List<ImpedimentDTO> impedimentDTOListe = new LinkedList<ImpedimentDTO>();
			
			for (int j = 0; j < scrumprojektListe.get(i).getSprint().size(); j++) {
				SprintDTO sprintDTO = new SprintDTO();
				sprintDTO.setId(scrumprojektListe.get(i).getSprint().get(j).getId());
				BurndownChartDTO burndownchartDTO = new BurndownChartDTO();
				burndownchartDTO.setId(scrumprojektListe.get(i).getSprint().get(j).getBurndownChart().getId());
				burndownchartDTO.setTage(scrumprojektListe.get(i).getSprint().get(j).getBurndownChart().getTage());
				List<BurndownChartPointDTO> burndownchartPointDTOListe = new LinkedList<BurndownChartPointDTO>();
				for (int k = 0; k < scrumprojektListe.get(i).getSprint().get(j).getBurndownChart()
						.getBurndownChartPoint().size(); k++) {
					BurndownChartPointDTO burndownchartPointDTO = new BurndownChartPointDTO();
					burndownchartPointDTO.setId(scrumprojektListe.get(i).getSprint().get(j).getBurndownChart()
							.getBurndownChartPoint().get(k).getId());
					burndownchartPointDTO.setX(scrumprojektListe.get(i).getSprint().get(j).getBurndownChart()
							.getBurndownChartPoint().get(k).getX());
					burndownchartPointDTO.setY(scrumprojektListe.get(i).getSprint().get(j).getBurndownChart()
							.getBurndownChartPoint().get(k).getY());
					burndownchartPointDTOListe.add(burndownchartPointDTO);
				}
				burndownchartDTO.setBurndownChartPoint(burndownchartPointDTOListe);
				sprintDTO.setBurndownChart(burndownchartDTO);
				sprintDTOListe.add(sprintDTO);
			}
			for (int j = 0; j < scrumprojektListe.get(i).getImpediment().size(); j++) {
				ImpedimentDTO impedimentDTO = new ImpedimentDTO();
				impedimentDTO.setId(scrumprojektListe.get(i).getImpediment().get(j).getId());
				
				impedimentDTOListe.add(impedimentDTO);
			}
			
			ProductBacklogDTO productbacklogDTO = new ProductBacklogDTO();
			productbacklogDTO.setId(scrumprojektListe.get(i).getProductbacklog().getId());
			
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
			sprint.setStatus(false);
			sprint.setScrumprojekt(sps.findById(projectid));
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
