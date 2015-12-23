package scrumbo.de.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
import scrumbo.de.entity.Sprint;
import scrumbo.de.entity.UserStory;

public class SprintbacklogService {
	
	public Sprint addNewSprintToSprintBacklog() {
		String output = "";
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/sprint/create/"
					+ CurrentScrumprojekt.scrumprojektID + "/" + ScrumBOClient.getDatabaseconfigfile());
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
		Sprint sprint = gson.fromJson(output, Sprint.class);
		
		return sprint;
	}
	
	public void ladeAltenSprint(Integer sprintnummer) {
		String output = "";
		try {
			URL url = new URL(
					"http://localhost:8080/ScrumBO_Server/rest/sprint/suche/" + CurrentScrumprojekt.scrumprojektID + "/"
							+ sprintnummer + "/" + ScrumBOClient.getDatabaseconfigfile());
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
		Sprint sprint = gson.fromJson(output, Sprint.class);
		CurrentSprint.id = sprint.getId();
		CurrentSprint.sprintnummer = sprint.getSprintnummer();
	}
	
	public void ladeSprint() {
		String output = "";
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/sprint/suche/"
					+ CurrentScrumprojekt.scrumprojektID + "/" + ScrumBOClient.getDatabaseconfigfile());
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
		Sprint sprint = gson.fromJson(output, Sprint.class);
		CurrentSprint.id = sprint.getId();
		CurrentSprint.sprintnummer = sprint.getSprintnummer();
	}
	
	public Integer ladeAnzahlSprints() {
		String output = "";
		
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/sprint/suche/"
					+ CurrentScrumprojekt.scrumprojektID + "/anzahl/" + ScrumBOClient.getDatabaseconfigfile());
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
		return Integer.parseInt(output);
		
	}
	
	public List<UserStory> ladeSprintBacklog() {
		String output = "";
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/userstory/suche/sprintid/" + CurrentSprint.id
					+ "/" + ScrumBOClient.getDatabaseconfigfile());
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
		List<UserStory> liste = gson.fromJson(output, listType);
		
		return liste;
	}
}