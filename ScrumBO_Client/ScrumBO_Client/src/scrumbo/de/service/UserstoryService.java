package scrumbo.de.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.CurrentSprint;
import scrumbo.de.entity.UserStory;

public class UserstoryService {
	
	public boolean createUserStory(UserStory userstory) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(userstory);
		
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/userstory/create/"
					+ CurrentScrumprojekt.productbacklog.getId() + "/" + ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("POST");
			
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			out.write(output);
			out.close();
			
			if (conn.getResponseMessage().equals("OK"))
				status = true;
			conn.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return status;
	}
	
	public boolean deleteUserStory(UserStory userstory) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(userstory);
		
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/userstory/delete" + "/"
					+ ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("POST");
			
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			out.write(output);
			out.close();
			
			if (conn.getResponseMessage().equals("OK"))
				status = true;
			conn.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
	
	public boolean updateUserStory(UserStory userstory) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(userstory);
		
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/userstory/update" + "/"
					+ ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("POST");
			
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			out.write(output);
			out.close();
			
			if (conn.getResponseMessage().equals("OK"))
				status = true;
			conn.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return status;
	}
	
	public List<UserStory> ladeUserStory() {
		String output = "";
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/userstory/sucheNULL/productbacklogid/"
					+ CurrentScrumprojekt.productbacklog.getId() + "/" + ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json" + ";charset=utf-8");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
			output = br.readLine();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		Type listType = new TypeToken<LinkedList<UserStory>>() {
		}.getType();
		List<UserStory> userstoryList = new LinkedList<UserStory>();
		userstoryList = gson.fromJson(output, listType);
		
		return userstoryList;
	}
	
	public boolean updateTask(UserStory userstory) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(userstory);
		
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/userstory/updateTasks/" + CurrentSprint.id
					+ "/" + ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			conn.setRequestMethod("POST");
			
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			out.write(output);
			out.close();
			
			if (conn.getResponseMessage().equals("OK"))
				status = true;
			conn.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return status;
	}
	
	public boolean setSprintNull(Integer userstoryid) {
		boolean status = false;
		
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/userstory/deleteFromSprint/" + userstoryid
					+ "/" + ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			conn.setRequestMethod("GET");
			
			if (conn.getResponseMessage().equals("OK"))
				status = true;
			conn.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return status;
	}
}
