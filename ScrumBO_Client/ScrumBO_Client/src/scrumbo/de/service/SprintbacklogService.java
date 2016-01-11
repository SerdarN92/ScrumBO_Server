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
import scrumbo.de.entity.CurrentBurndownChart;
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.CurrentSprint;
import scrumbo.de.entity.Sprint;
import scrumbo.de.entity.UserStory;

public class SprintbacklogService {
	
	public Sprint addNewSprintToSprintBacklog() {
		String output = "";
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/sprint/create/" + CurrentProject.projectId + "/"
					+ ScrumBOClient.getDatabaseconfigfile());
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
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/sprint/suche/" + CurrentProject.projectId + "/" + sprintnummer + "/"
					+ ScrumBOClient.getDatabaseconfigfile());
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
		CurrentSprint.sprintnumber = sprint.getSprintnummer();
		CurrentSprint.status = sprint.getStatus();
		if (sprint.getBurndownChart() != null) {
			CurrentBurndownChart.id = sprint.getBurndownChart().getId();
			CurrentBurndownChart.days = sprint.getBurndownChart().getDays();
			CurrentBurndownChart.points = sprint.getBurndownChart().getBurndownChartPoint();
		} else {
			CurrentBurndownChart.id = null;
			CurrentBurndownChart.days = null;
			CurrentBurndownChart.points = null;
		}
	}
	
	public void ladeSprint() {
		String output = "";
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/sprint/suche/" + CurrentProject.projectId + "/"
					+ ScrumBOClient.getDatabaseconfigfile());
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
		CurrentSprint.sprintnumber = sprint.getSprintnummer();
		CurrentSprint.status = sprint.getStatus();
		if (sprint.getBurndownChart() != null) {
			CurrentBurndownChart.id = sprint.getBurndownChart().getId();
			CurrentBurndownChart.days = sprint.getBurndownChart().getDays();
			CurrentBurndownChart.points = sprint.getBurndownChart().getBurndownChartPoint();
		} else {
			CurrentBurndownChart.id = null;
			CurrentBurndownChart.days = null;
			CurrentBurndownChart.points = null;
		}
	}
	
	public Integer ladeAnzahlSprints() {
		String output = "";
		
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/sprint/suche/" + CurrentProject.projectId + "/anzahl/"
					+ ScrumBOClient.getDatabaseconfigfile());
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
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/userstory/suche/sprintid/" + CurrentSprint.id + "/"
					+ ScrumBOClient.getDatabaseconfigfile());
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
	
	public boolean createBurndownChart() {
		boolean status = false;
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/sprint/createBurndownChart/" + CurrentSprint.id + "/"
					+ ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json" + ";charset=utf-8");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
			String output = br.readLine();
			if (output.equals("true"))
				status = true;
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
		
	}
	
	public boolean endDay() {
		boolean status = false;
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/sprint/endDay/" + CurrentSprint.id + "/"
					+ ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json" + ";charset=utf-8");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
			String output = br.readLine();
			if (output.equals("true"))
				status = true;
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
		
	}
}
