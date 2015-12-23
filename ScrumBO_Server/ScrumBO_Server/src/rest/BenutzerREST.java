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

import dto.BenutzerDTO;
import model.Benutzer;
import model.Benutzer_Benutzerrolle_Scrumprojekt;
import model.Benutzerrolle;
import service.BenutzerService;
import service.Benutzer_Benutzerrolle_ScrumprojektService;
import service.BenutzerrolleService;

@Path("/benutzer")
public class BenutzerREST {
	
	@GET
	@Path("/alle/{hibernateconfigfilename}")
	@Produces("application/json" + ";charset=utf-8")
	public Response getBenutzerAll(@PathParam("hibernateconfigfilename") String hibernateconfigfilename)
			throws JSONException {
		BenutzerService benutzerService = new BenutzerService(hibernateconfigfilename);
		
		List<BenutzerDTO> benutzerDTOListe = new LinkedList<BenutzerDTO>();
		List<Benutzer> benutzerListe = benutzerService.findAll();
		for (int i = 0; i < benutzerListe.size(); i++) {
			BenutzerDTO benutzerDTO = new BenutzerDTO(benutzerListe.get(i).getId(), benutzerListe.get(i).getVorname(),
					benutzerListe.get(i).getNachname(), benutzerListe.get(i).getPasswort(),
					benutzerListe.get(i).getEmail());
			benutzerDTOListe.add(benutzerDTO);
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(benutzerDTOListe);
		
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/alle/scrumprojekt/{scrumprojektid}/{hibernateconfigfilename}")
	@Produces("application/json" + ";charset=utf-8")
	public Response getBenutzerOfProject(@PathParam("scrumprojektid") Integer scrumprojektid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) throws JSONException {
		Benutzer_Benutzerrolle_ScrumprojektService bbsService = new Benutzer_Benutzerrolle_ScrumprojektService(
				hibernateconfigfilename);
		BenutzerService benutzerService = new BenutzerService(hibernateconfigfilename);
		List<Benutzer_Benutzerrolle_Scrumprojekt> bbsListe = bbsService.findListByProjectId(scrumprojektid);
		List<Benutzer> benutzerListe = new LinkedList<Benutzer>();
		for (int i = 0; i < bbsListe.size(); i++) {
			benutzerListe.add(benutzerService.findById(bbsListe.get(i).getPk().getBenutzerId()));
		}
		
		List<BenutzerDTO> benutzerDTOListe = new LinkedList<BenutzerDTO>();
		for (int i = 0; i < benutzerListe.size(); i++) {
			BenutzerDTO benutzerDTO = new BenutzerDTO(benutzerListe.get(i).getId(), benutzerListe.get(i).getVorname(),
					benutzerListe.get(i).getNachname(), benutzerListe.get(i).getPasswort(),
					benutzerListe.get(i).getEmail());
			benutzerDTOListe.add(benutzerDTO);
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(benutzerDTOListe);
		return Response.status(200).entity(output).build();
	}
	
	@Path("/suche/{email}/rolle/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getBenutzerrolleByEmail(@PathParam("email") String email,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		BenutzerService benutzerService = new BenutzerService(hibernateconfigfilename);
		Benutzer benutzer = benutzerService.findByEmail(email);
		String output = "User ist kein Scrum Master";
		if (benutzer != null) {
			Benutzer_Benutzerrolle_ScrumprojektService bbsService = new Benutzer_Benutzerrolle_ScrumprojektService(
					hibernateconfigfilename);
			List<Benutzer_Benutzerrolle_Scrumprojekt> bbsListe = bbsService.findListByBenutzerId(benutzer.getId());
			for (int i = 0; i < bbsListe.size(); i++) {
				if (bbsListe.get(i).getPk().getBenutzerrollenId() == 1)
					output = "User ist Scrum Master";
				if (bbsListe.get(i).getPk().getBenutzerrollenId() == 2)
					output = "User ist Product Owner";
				if (bbsListe.get(i).getPk().getBenutzerrollenId() == 3)
					output = "User ist Entwickler";
			}
		}
		return Response.status(200).entity(output).build();
	}
	
	@Path("/suche/{email}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getBenutzerByEmail(@PathParam("email") String email,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		BenutzerService benutzerService = new BenutzerService(hibernateconfigfilename);
		Benutzer benutzer = benutzerService.findByEmail(email);
		String output = "User ist nicht vorhanden";
		if (benutzer != null) {
			BenutzerDTO benutzerDTO = new BenutzerDTO(benutzer.getId(), benutzer.getVorname(), benutzer.getNachname(),
					benutzer.getPasswort(), benutzer.getEmail());
					
			Gson gson = new Gson();
			output = gson.toJson(benutzerDTO);
		}
		return Response.status(200).entity(output).build();
	}
	
	@Path("/updatePassword/{email}/{hibernateconfigfilename}")
	@POST
	@Consumes("application/json" + ";charset=utf-8")
	public Response updateBenutzerPasswordByEmail(@PathParam("email") String email,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename, InputStream input) {
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
		String benutzerDetails = stringBuilder.toString();
		
		Gson gson = new Gson();
		BenutzerDTO benutzerDTO = gson.fromJson(benutzerDetails, BenutzerDTO.class);
		Benutzer benutzer = new Benutzer();
		BenutzerService benutzerService = new BenutzerService(hibernateconfigfilename);
		benutzer = benutzerService.findByEmail(email);
		benutzer.setPasswort(benutzerDTO.getPasswort());
		benutzerService.update(benutzer);
		
		String output = "Passwort vom Benutzer geupdated";
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/create/{benutzerrollenid}/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response createScrumMaster(@PathParam("benutzerrollenid") Integer benutzerrollenId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename, InputStream input) {
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
		String benutzerDetails = stringBuilder.toString();
		
		Gson gson = new Gson();
		BenutzerDTO benutzerDTO = gson.fromJson(benutzerDetails, BenutzerDTO.class);
		Benutzerrolle benutzerrolle = new Benutzerrolle();
		Benutzer benutzer = new Benutzer(benutzerDTO.getId(), benutzerDTO.getVorname(), benutzerDTO.getNachname(),
				benutzerDTO.getPasswort(), benutzerDTO.getEmail());
				
		BenutzerService benutzerService = new BenutzerService(hibernateconfigfilename);
		benutzerService.persist(benutzer);
		
		BenutzerrolleService benutzerrolleService = new BenutzerrolleService(hibernateconfigfilename);
		benutzerrolle = benutzerrolleService.findById(benutzerrollenId);
		
		Benutzer_Benutzerrolle_ScrumprojektService bbsService = new Benutzer_Benutzerrolle_ScrumprojektService(
				hibernateconfigfilename);
		Benutzer_Benutzerrolle_Scrumprojekt bbs = new Benutzer_Benutzerrolle_Scrumprojekt();
		Benutzer_Benutzerrolle_Scrumprojekt.Pk pk = new Benutzer_Benutzerrolle_Scrumprojekt.Pk(benutzer.getId(),
				benutzerrolle.getBenutzerrollenid(), 0);
		bbs.setPk(pk);
		bbsService.persist(bbs);
		
		String output = "User erfolgreich erstellt";
		return Response.status(200).entity(output).build();
		
	}
	
	@POST
	@Path("/create/{benutzerrollenid}/{scrumprojektid}/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response createUserForProject(@PathParam("scrumprojektid") Integer scrumprojektId,
			@PathParam("benutzerrollenid") Integer benutzerrollenId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename, InputStream input) {
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
		String benutzerDetails = stringBuilder.toString();
		
		Gson gson = new Gson();
		BenutzerDTO benutzerDTO = gson.fromJson(benutzerDetails, BenutzerDTO.class);
		
		Benutzer benutzer = new Benutzer(benutzerDTO.getId(), benutzerDTO.getVorname(), benutzerDTO.getNachname(),
				benutzerDTO.getPasswort(), benutzerDTO.getEmail());
				
		BenutzerService bs = new BenutzerService(hibernateconfigfilename);
		bs.persist(benutzer);
		
		BenutzerrolleService brs = new BenutzerrolleService(hibernateconfigfilename);
		Benutzerrolle benutzerrolle = new Benutzerrolle();
		benutzerrolle = brs.findById(benutzerrollenId);
		
		Benutzer_Benutzerrolle_ScrumprojektService bbsService = new Benutzer_Benutzerrolle_ScrumprojektService(
				hibernateconfigfilename);
		Benutzer_Benutzerrolle_Scrumprojekt bbs = new Benutzer_Benutzerrolle_Scrumprojekt();
		Benutzer_Benutzerrolle_Scrumprojekt.Pk pk = new Benutzer_Benutzerrolle_Scrumprojekt.Pk(benutzer.getId(),
				benutzerrolle.getBenutzerrollenid(), scrumprojektId);
		bbs.setPk(pk);
		bbsService.persist(bbs);
		
		String output = "User erfolgreich erstellt";
		return Response.status(200).entity(output).build();
		
	}
	
}