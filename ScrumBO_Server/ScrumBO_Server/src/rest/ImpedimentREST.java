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
	public Response getImpedimentByScrumprojectId(@PathParam("scrumprojektid") Integer id,
			@PathParam("hibernateconfigfilename") String hibernateconfigfilename) {
		ImpedimentService service = new ImpedimentService(hibernateconfigfilename);
		List<Impediment> impedimentList = service.findByProjectId(id);
		List<ImpedimentDTO> impedimentDTOList = new LinkedList<ImpedimentDTO>();
		for (int i = 0; i < impedimentList.size(); i++) {
			ImpedimentDTO dto = new ImpedimentDTO();
			dto.setId(impedimentList.get(i).getId());
			dto.setPriorität(impedimentList.get(i).getPriorität());
			dto.setBeschreibung(impedimentList.get(i).getBeschreibung());
			dto.setMitarbeiter(impedimentList.get(i).getMitarbeiter());
			dto.setDatumDesAuftretens(impedimentList.get(i).getDatumDesAuftretens());
			dto.setDatumDerBehebung(impedimentList.get(i).getDatumDerBehebung());
			dto.setKommentar(impedimentList.get(i).getKommentar());
			impedimentDTOList.add(dto);
		}
		Gson gson = new Gson();
		String output = gson.toJson(impedimentDTOList);
		return Response.status(200).entity(output).build();
	}
	
	@POST
	@Path("/create/{scrumprojektid}/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response createImpediment(@PathParam("scrumprojektid") Integer id,
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
		Impediment impediment = new Impediment();
		ImpedimentService impedimentService = new ImpedimentService(hibernateconfigfilename);
		
		impediment.setPriorität(impedimentDTO.getPriorität());
		impediment.setMitarbeiter(impedimentDTO.getMitarbeiter());
		impediment.setBeschreibung(impedimentDTO.getBeschreibung());
		impediment.setDatumDesAuftretens(impedimentDTO.getDatumDesAuftretens());
		
		ScrumprojektService scrumprojektService = new ScrumprojektService(hibernateconfigfilename);
		Scrumprojekt scrumprojekt = scrumprojektService.findById(id);
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
		String impedimentdetails = b.toString();
		Gson gson = new Gson();
		ImpedimentDTO impedimentDTO = gson.fromJson(impedimentdetails, ImpedimentDTO.class);
		Impediment impediment = new Impediment();
		ImpedimentService impedimentService = new ImpedimentService(hibernateconfigfilename);
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
