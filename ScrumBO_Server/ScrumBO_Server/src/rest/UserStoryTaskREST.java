package rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import dto.UserStoryTaskDTO;
import model.Benutzer;
import model.UserStoryTask;
import service.BenutzerService;
import service.UserStoryTaskService;

@Path("/userstorytask")
public class UserStoryTaskREST {
	
	@POST
	@Path("/update/{hibernateconfigfilename}")
	@Consumes("application/json" + ";charset=utf-8")
	public Response updateUserStoryTask(InputStream input,
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
		String userstorytaskdetails = b.toString();
		System.out.println(userstorytaskdetails);
		
		Gson gson = new Gson();
		UserStoryTaskDTO userstorytaskDTO = gson.fromJson(userstorytaskdetails, UserStoryTaskDTO.class);
		
		UserStoryTaskService userstorytaskService = new UserStoryTaskService(hibernateconfigfilename);
		UserStoryTask userstorytask = new UserStoryTask();
		userstorytask = userstorytaskService.findById(userstorytaskDTO.getId());
		userstorytask.setAufwandinstunden(userstorytaskDTO.getAufwandinstunden());
		BenutzerService benutzerService = new BenutzerService(hibernateconfigfilename);
		Benutzer old = benutzerService.findById(userstorytaskDTO.getBenutzer().getId());
		List<UserStoryTask> liste = old.getUserstorytask();
		benutzerService.update(userstorytask.getBenutzer());
		String output = "";
		try {
			userstorytaskService.update(userstorytask);
			output = "User Story Task erfolgreich geupdated";
		} catch (Exception e) {
			e.printStackTrace();
			output = "User Story Task wurde nicht erfolgreich geupdated";
		}
		return Response.status(200).entity(output).build();
	}
	
}
