package rest;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;

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
			BenutzerrolleDTO benutzerrolleDTO = new BenutzerrolleDTO(benutzerrolle.get(i).getBenutzerrollenid(),
					benutzerrolle.get(i).getBezeichnung());
			benutzerrolleDTOListe.add(benutzerrolleDTO);
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(benutzerrolleDTOListe);
		
		System.out.println("OUTPUT:" + output);
		
		return Response.status(200).entity(output).build();
	}
	
}
