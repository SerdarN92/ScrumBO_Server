package rest;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import dto.BenutzerrolleDTO;
import model.Benutzerrolle;
import service.BenutzerrolleService;

@Path("/benutzerrolle")
public class BenutzerrolleREST {
	
	@Path("/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getBenutzerrolleAll(@PathParam("hibernateconfigfilename") String hibernateconfigfilename)
			throws JSONException {
		BenutzerrolleService benutzerrolleService = new BenutzerrolleService(hibernateconfigfilename);
		List<Benutzerrolle> benutzerrolle = benutzerrolleService.findAll();
		List<BenutzerrolleDTO> benutzerrolleDTOListe = new LinkedList<>();
		for (int i = 0; i < benutzerrolle.size(); i++) {
			BenutzerrolleDTO benutzerrolleDTO = new BenutzerrolleDTO();
			benutzerrolleDTO.setId(benutzerrolle.get(i).getBenutzerrollenid());
			benutzerrolleDTO.setBezeichnung(benutzerrolle.get(i).getBezeichnung());
			benutzerrolleDTOListe.add(benutzerrolleDTO);
		}
		
		JSONObject jsonObject = new JSONObject();
		Gson gson = new Gson();
		String ab = gson.toJson(benutzerrolleDTOListe);
		
		return Response.status(200).entity(ab).build();
	}
	
}
