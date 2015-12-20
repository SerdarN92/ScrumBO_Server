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
import model.Taskstatus;
import model.UserStory;
import model.UserStoryTask;
import service.TaskstatusService;
import service.UserStoryService;
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
		
		Gson gson = new Gson();
		UserStoryTaskDTO userstorytaskDTO = gson.fromJson(userstorytaskdetails, UserStoryTaskDTO.class);
		
		UserStoryTaskService userstorytaskService = new UserStoryTaskService(hibernateconfigfilename);
		UserStoryTask userstorytask = new UserStoryTask();
		userstorytask = userstorytaskService.findById(userstorytaskDTO.getId());
		TaskstatusService taskstatusService = new TaskstatusService(hibernateconfigfilename);
		Taskstatus taskstatusold = new Taskstatus();
		Taskstatus taskstatusnew = new Taskstatus();
		taskstatusold = taskstatusService.findById(userstorytask.getTaskstatus().getId());
		taskstatusnew = taskstatusService.findById(userstorytaskDTO.getTaskstatus().getId());
		if (taskstatusold.getId() != taskstatusnew.getId()) {
			userstorytask.setTaskstatus(taskstatusnew);
		}
		
		UserStoryService userstoryService = new UserStoryService(hibernateconfigfilename);
		UserStory userstory = userstoryService.findById(userstorytask.getUserstory().getId());
		
		String output = "";
		try {
			if (taskstatusold.getId() != taskstatusnew.getId()) {
				// userstoryService.update(userstory);
				userstorytaskService.update(userstorytask);
				
				List<UserStoryTask> userstorytaskListe = userstorytaskService.findAllByUserStoryId(userstory.getId());
				boolean open = false;
				boolean inwork = false;
				boolean done = false;
				
				for (int i = 0; i < userstorytaskListe.size(); i++) {
					if (userstorytaskListe.get(i).getTaskstatus().getId() == 1) {
						open = true;
					}
					if (userstorytaskListe.get(i).getTaskstatus().getId() == 2) {
						inwork = true;
					}
					
					if (userstorytaskListe.get(i).getTaskstatus().getId() == 3) {
						done = true;
					}
					
				}
				
				if (open && !inwork && !done)
					userstory.setStatus(0);
				if (!open && inwork && !done)
					userstory.setStatus(1);
				if (!open && !inwork && done)
					userstory.setStatus(2);
				if (open && inwork && !done)
					userstory.setStatus(1);
				if (open && inwork && done)
					userstory.setStatus(1);
				if (!open && inwork && done)
					userstory.setStatus(1);
					
				userstoryService.update(userstory);
				userstorytaskService.update(userstorytask);
				
			}
			output = "User Story Task erfolgreich geupdated";
		} catch (Exception e) {
			e.printStackTrace();
			output = "User Story Task wurde nicht erfolgreich geupdated";
		}
		return Response.status(200).entity(output).build();
	}
	
}
