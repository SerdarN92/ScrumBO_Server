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

import com.google.gson.Gson;

import dto.ImpedimentDTO;
import model.Impediment;
import model.Scrumprojekt;
import service.ImpedimentService;
import service.ScrumprojektService;

@Path("/impedimentbacklog")
public class ImpedimentREST {
	
	@Path("/suche/{scrumprojektid}/{hibernateconfigfilename}")
	@GET
	@Produces("application/json" + ";charset=utf-8")
	public Response getImpedimentByScrumprojectId(@PathParam("scrumprojektid") Integer scrumprojektId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		ImpedimentService impedimentService = new ImpedimentService(hibernateconfigfilename);
		List<Impediment> impedimentListe = impedimentService.findByProjectId(scrumprojektId);
		List<ImpedimentDTO> impedimentDTOListe = new LinkedList<ImpedimentDTO>();
		for (int i = 0; i < impedimentListe.size(); i++) {
			ImpedimentDTO impedimentDTO = new ImpedimentDTO(impedimentListe.get(i).getId(),
					impedimentListe.get(i).getPriorität(), impedimentListe.get(i).getMitarbeiter(),
					impedimentListe.get(i).getBeschreibung(), impedimentListe.get(i).getDatumDesAuftretens(),
					impedimentListe.get(i).getDatumDerBehebung(), impedimentListe.get(i).getKommentar());
			impedimentDTOListe.add(impedimentDTO);
		}
		Gson gson = new Gson();
		String output = gson.toJson(impedimentDTOListe);
		
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/create/{scrumprojektid}/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response createImpediment(@PathParam("scrumprojektid") Integer scrumprojektId,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename, InputStream input) {
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
		String impedimentdetails = b.toString();
		Gson gson = new Gson();
		ImpedimentDTO impedimentDTO = gson.fromJson(impedimentdetails, ImpedimentDTO.class);
		ImpedimentService impedimentService = new ImpedimentService(hibernateconfigfilename);
		Impediment impediment = new Impediment(impedimentDTO.getPriorität(), impedimentDTO.getMitarbeiter(),
				impedimentDTO.getBeschreibung(), impedimentDTO.getDatumDesAuftretens());
				
		ScrumprojektService scrumprojektService = new ScrumprojektService(hibernateconfigfilename);
		Scrumprojekt scrumprojekt = scrumprojektService.findById(scrumprojektId);
		impediment.setScrumprojekt(scrumprojekt);
		
		String output = "";
		try {
			impedimentService.persist(impediment);
			scrumprojektService.update(scrumprojekt);
			output = "Impediment erfolgreich erstellt";
		} catch (Exception e) {
			e.printStackTrace();
			output = "Impediment wurde nicht erfolgreich erstellt";
		}
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/update/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response updateImpediment(InputStream input,
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
		String impedimentDetails = b.toString();
		Gson gson = new Gson();
		
		ImpedimentDTO impedimentDTO = gson.fromJson(impedimentDetails, ImpedimentDTO.class);
		ImpedimentService impedimentService = new ImpedimentService(hibernateconfigfilename);
		
		Impediment impediment = new Impediment();
		impediment = impedimentService.findById(impedimentDTO.getId());
		impediment.setPriorität(impedimentDTO.getPriorität());
		impediment.setBeschreibung(impedimentDTO.getBeschreibung());
		impediment.setMitarbeiter(impedimentDTO.getMitarbeiter());
		impediment.setDatumDesAuftretens(impedimentDTO.getDatumDesAuftretens());
		impediment.setDatumDerBehebung(impedimentDTO.getDatumDerBehebung());
		impediment.setKommentar(impedimentDTO.getKommentar());
		String output = "";
		try {
			impedimentService.update(impediment);
			output = "Impediment erfolgreich geupdated";
		} catch (Exception e) {
			e.printStackTrace();
			output = "Impediment wurde nicht erfolgreich geupdated";
		}
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/delete/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response deleteImpediment(InputStream input,
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
		String impedimentdetails = b.toString();
		Gson gson = new Gson();
		ImpedimentDTO impedimentDTO = gson.fromJson(impedimentdetails, ImpedimentDTO.class);
		ImpedimentService impedimentService = new ImpedimentService(hibernateconfigfilename);
		
		String output = "";
		try {
			impedimentService.delete(impedimentDTO.getId());
			output = "Impediment erfolgreich gelöscht";
		} catch (Exception e) {
			e.printStackTrace();
			output = "Impediment wurde nicht erfolgreich gelöscht";
		}
		return Response.status(200).entity(output).build();
	}
	
}
