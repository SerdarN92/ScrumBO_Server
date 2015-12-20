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

import dto.BenutzerDTO;
import dto.BenutzerrolleDTO;
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
		BenutzerService bs = new BenutzerService(hibernateconfigfilename);
		
		List<BenutzerDTO> listeBenutzerDTO = new LinkedList<BenutzerDTO>();
		List<Benutzer> listeBenutzer = bs.findAll();
		for (int i = 0; i < listeBenutzer.size(); i++) {
			BenutzerDTO benutzerDTO = new BenutzerDTO();
			benutzerDTO.setId(listeBenutzer.get(i).getId());
			benutzerDTO.setVorname(listeBenutzer.get(i).getVorname());
			benutzerDTO.setNachname(listeBenutzer.get(i).getNachname());
			benutzerDTO.setPasswort(listeBenutzer.get(i).getPasswort());
			benutzerDTO.setEmail(listeBenutzer.get(i).getEmail());
			listeBenutzerDTO.add(benutzerDTO);
		}
		
		JSONObject jsonObject = new JSONObject();
		Gson gson = new Gson();
		String listeBenutzerDTOJSON = gson.toJson(listeBenutzerDTO);
		
		return Response.status(200).entity(listeBenutzerDTOJSON).build();
	}
	
	@GET
	@Path("/alle/scrumprojekt/{scrumprojektid}/{hibernateconfigfilename}")
	@Produces("application/json" + ";charset=utf-8")
	public Response getBenutzerOfProject(@PathParam("scrumprojektid") Integer scrumprojektid,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) throws JSONException {
		Benutzer_Benutzerrolle_ScrumprojektService bbsService = new Benutzer_Benutzerrolle_ScrumprojektService(
				hibernateconfigfilename);
				
		BenutzerService bs = new BenutzerService(hibernateconfigfilename);
		
		List<Benutzer_Benutzerrolle_Scrumprojekt> listeAll = bbsService.findListByProjectId(scrumprojektid);
		List<Benutzer> listeBenutzer = new LinkedList<Benutzer>();
		for (int i = 0; i < listeAll.size(); i++) {
			listeBenutzer.add(bs.findById(listeAll.get(i).getPk().getBenutzerId()));
		}
		
		List<BenutzerDTO> listeBenutzerDTO = new LinkedList<BenutzerDTO>();
		for (int i = 0; i < listeBenutzer.size(); i++) {
			BenutzerDTO benutzerDTO = new BenutzerDTO();
			benutzerDTO.setId(listeBenutzer.get(i).getId());
			benutzerDTO.setVorname(listeBenutzer.get(i).getVorname());
			benutzerDTO.setNachname(listeBenutzer.get(i).getNachname());
			benutzerDTO.setPasswort(listeBenutzer.get(i).getPasswort());
			benutzerDTO.setEmail(listeBenutzer.get(i).getEmail());
			listeBenutzerDTO.add(benutzerDTO);
		}
		
		JSONObject jsonObject = new JSONObject();
		Gson gson = new Gson();
		String listeBenutzerDTOJSON = gson.toJson(listeBenutzerDTO);
		
		return Response.status(200).entity(listeBenutzerDTOJSON).build();
	}
	
	@Path("/suche/{email}/rolle/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getBenutzerrolleByEmail(@PathParam("email") String email,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		BenutzerService benutzerService = new BenutzerService(hibernateconfigfilename);
		Benutzer benutzer = benutzerService.findByEmail(email);
		String output = "User kein Scrum Master";
		if (benutzer != null) {
			// BenutzerDTO benutzerDTO = new BenutzerDTO();
			// benutzerDTO.setId(benutzer.getId());
			// benutzerDTO.setVorname(benutzer.getVorname());
			// benutzerDTO.setNachname(benutzer.getNachname());
			// benutzerDTO.setPasswort(benutzer.getPasswort());
			// benutzerDTO.setEmail(benutzer.getEmail());
			// BenutzerrolleDTO bd = new BenutzerrolleDTO();
			
			Benutzer_Benutzerrolle_ScrumprojektService bbs = new Benutzer_Benutzerrolle_ScrumprojektService(
					hibernateconfigfilename);
			List<Benutzer_Benutzerrolle_Scrumprojekt> list = bbs.findListByBenutzerId(benutzer.getId());
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getPk().getBenutzerrollenId() == 1)
					output = "User ist Scrum Master";
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
		String output = "User nicht vorhanden";
		if (benutzer != null) {
			BenutzerDTO benutzerDTO = new BenutzerDTO();
			benutzerDTO.setId(benutzer.getId());
			benutzerDTO.setVorname(benutzer.getVorname());
			benutzerDTO.setNachname(benutzer.getNachname());
			benutzerDTO.setPasswort(benutzer.getPasswort());
			benutzerDTO.setEmail(benutzer.getEmail());
			BenutzerrolleDTO bd = new BenutzerrolleDTO();
			
			Gson gson = new Gson();
			output = gson.toJson(benutzerDTO);
		}
		return Response.status(200).entity(output).build();
	}
	
	@Path("/update/{email}/{hibernateconfigfilename}")
	@POST
	@Consumes("application/json" + ";charset=utf-8")
	public Response updateBenutzerByEmail(@PathParam("email") String email,
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
		String userdetails = stringBuilder.toString();
		
		Gson gson = new Gson();
		BenutzerDTO benutzerDTO = gson.fromJson(userdetails, BenutzerDTO.class);
		Benutzer benutzer = new Benutzer();
		BenutzerService bs = new BenutzerService(hibernateconfigfilename);
		benutzer = bs.findByEmail(email);
		benutzer.setPasswort(benutzerDTO.getPasswort());
		bs.update(benutzer);
		
		String output = "User geupdated";
		
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
		String userdetails = stringBuilder.toString();
		
		Gson gson = new Gson();
		BenutzerDTO benutzerDTO = gson.fromJson(userdetails, BenutzerDTO.class);
		Benutzerrolle benutzerrolle = new Benutzerrolle();
		Benutzer benutzer = new Benutzer();
		benutzer.setId(benutzerDTO.getId());
		benutzer.setVorname(benutzerDTO.getVorname());
		benutzer.setNachname(benutzerDTO.getNachname());
		benutzer.setPasswort(benutzerDTO.getPasswort());
		benutzer.setEmail(benutzerDTO.getEmail());
		
		BenutzerService bs = new BenutzerService(hibernateconfigfilename);
		bs.persist(benutzer);
		
		BenutzerrolleService brs = new BenutzerrolleService(hibernateconfigfilename);
		benutzerrolle = brs.findById(benutzerrollenId);
		
		Benutzer_Benutzerrolle_ScrumprojektService bbs = new Benutzer_Benutzerrolle_ScrumprojektService(
				hibernateconfigfilename);
		Benutzer_Benutzerrolle_Scrumprojekt bbsmodel = new Benutzer_Benutzerrolle_Scrumprojekt();
		Benutzer_Benutzerrolle_Scrumprojekt.Pk pk = new Benutzer_Benutzerrolle_Scrumprojekt.Pk();
		pk.setBenutzerId(benutzer.getId());
		pk.setBenutzerrollenId(benutzerrolle.getBenutzerrollenid());
		pk.setScrumprojektId(0);
		bbsmodel.setPk(pk);
		bbs.persist(bbsmodel);
		
		String output = "User erfolgreich erstellt";
		
		return Response.status(200).entity(output).build();
		
	}
	
	@POST
	@Path("/create/{benutzerrollenid}/{scrumprojektid}/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response createUser(@PathParam("scrumprojektid") Integer scrumprojektId,
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
		String userdetails = stringBuilder.toString();
		
		Gson gson = new Gson();
		BenutzerDTO benutzerDTO = gson.fromJson(userdetails, BenutzerDTO.class);
		Benutzerrolle benutzerrolle = new Benutzerrolle();
		Benutzer benutzer = new Benutzer();
		benutzer.setId(benutzerDTO.getId());
		benutzer.setVorname(benutzerDTO.getVorname());
		benutzer.setNachname(benutzerDTO.getNachname());
		benutzer.setPasswort(benutzerDTO.getPasswort());
		benutzer.setEmail(benutzerDTO.getEmail());
		
		BenutzerService bs = new BenutzerService(hibernateconfigfilename);
		bs.persist(benutzer);
		
		BenutzerrolleService brs = new BenutzerrolleService(hibernateconfigfilename);
		benutzerrolle = brs.findById(benutzerrollenId);
		
		Benutzer_Benutzerrolle_ScrumprojektService bbs = new Benutzer_Benutzerrolle_ScrumprojektService(
				hibernateconfigfilename);
		Benutzer_Benutzerrolle_Scrumprojekt bbsmodel = new Benutzer_Benutzerrolle_Scrumprojekt();
		Benutzer_Benutzerrolle_Scrumprojekt.Pk pk = new Benutzer_Benutzerrolle_Scrumprojekt.Pk();
		pk.setBenutzerId(benutzer.getId());
		pk.setBenutzerrollenId(benutzerrolle.getBenutzerrollenid());
		pk.setScrumprojektId(scrumprojektId);
		bbsmodel.setPk(pk);
		bbs.persist(bbsmodel);
		
		String output = "User erfolgreich erstellt";
		
		return Response.status(200).entity(output).build();
		
	}
	
}