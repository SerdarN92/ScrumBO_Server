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
import org.json.JSONObject;

import com.google.gson.Gson;

import dto.DefinitionOfDoneDTO;
import model.DefinitionOfDone;
import model.UserStory;
import service.DefinitionOfDoneService;
import service.UserStoryService;

@Path("/definitionofdone")
public class DefinitionOfDoneREST {
	
	@Path("/{userstoryid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getDoDByUserstoryId(@PathParam("userstoryid") Integer userstoryId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) throws JSONException {
		DefinitionOfDoneService dodService = new DefinitionOfDoneService(hibernateconfigfilename);
		UserStoryService userstoryService = new UserStoryService(hibernateconfigfilename);
		UserStory userstory = userstoryService.findById(userstoryId);
		List<DefinitionOfDone> dodliste = dodService.findByUserstoryId(userstoryId);
		List<DefinitionOfDoneDTO> dodDTOliste = new LinkedList<DefinitionOfDoneDTO>();
		for (int i = 0; i < dodliste.size(); i++) {
			DefinitionOfDoneDTO dodDTO = new DefinitionOfDoneDTO();
			dodDTO.setId(dodliste.get(i).getId());
			dodDTO.setKriterium(dodliste.get(i).getKriterium());
			dodDTO.setStatus(dodliste.get(i).getStatus());
			dodDTOliste.add(dodDTO);
			
		}
		
		JSONObject jsonObject = new JSONObject();
		Gson gson = new Gson();
		String ab = gson.toJson(dodDTOliste);
		
		return Response.status(200).entity(ab).build();
	}
	
	@POST
	@Path("/create/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response createDefinitionOfDone(InputStream input,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) throws JSONException {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String definitionOfDoneDetails = stringBuilder.toString();
		Gson gson = new Gson();
		DefinitionOfDoneDTO definitionOfDoneDTO = gson.fromJson(definitionOfDoneDetails, DefinitionOfDoneDTO.class);
		
		UserStoryService userstoryService = new UserStoryService(hibernateconfigfilename);
		UserStory userstory = userstoryService.findById(definitionOfDoneDTO.getUserstory().getId());
		DefinitionOfDoneService definitionOfDoneService = new DefinitionOfDoneService(hibernateconfigfilename);
		DefinitionOfDone definitionOfDone = new DefinitionOfDone();
		definitionOfDone.setKriterium(definitionOfDoneDTO.getKriterium());
		definitionOfDone.setStatus(definitionOfDoneDTO.getStatus());
		definitionOfDone.setUserstory(userstory);
		
		definitionOfDoneService.persist(definitionOfDone);
		
		String output = "Definition Of Done erfolgreich erstellt";
		
		return Response.status(200).entity(output).build();
		
	}
	
	@POST
	@Path("/update/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response updateDefinitionOfDone(InputStream input,
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
		String definitionOfDoneDetails = b.toString();
		Gson gson = new Gson();
		DefinitionOfDoneDTO definitionOfDoneDTO = gson.fromJson(definitionOfDoneDetails, DefinitionOfDoneDTO.class);
		DefinitionOfDone definitionOfDone = new DefinitionOfDone();
		DefinitionOfDoneService service = new DefinitionOfDoneService(hibernateconfigfilename);
		definitionOfDone = service.findById(definitionOfDoneDTO.getId());
		definitionOfDone.setKriterium(definitionOfDoneDTO.getKriterium());
		definitionOfDone.setStatus(definitionOfDoneDTO.getStatus());
		String output = "";
		try {
			service.update(definitionOfDone);
			output = "Definition Of Done erfolgreich geupdated";
		} catch (Exception e) {
			e.printStackTrace();
			output = "Definition Of Done wurde nicht erfolgreich geupdated";
		}
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/delete/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response deleteDefinitionOfDone(InputStream input,
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
		String definitionOfDoneDetails = b.toString();
		Gson gson = new Gson();
		DefinitionOfDoneDTO definitionOfDoneDTO = gson.fromJson(definitionOfDoneDetails, DefinitionOfDoneDTO.class);
		DefinitionOfDoneService service = new DefinitionOfDoneService(hibernateconfigfilename);
		
		String output = "";
		try {
			service.delete(definitionOfDoneDTO.getId());
			output = "Definition Of Done erfolgreich gelöscht";
		} catch (Exception e) {
			e.printStackTrace();
			output = "Definition Of Done wurde nicht erfolgreich gelöscht";
		}
		return Response.status(200).entity(output).build();
	}
	
}
