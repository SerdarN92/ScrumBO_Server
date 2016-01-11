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
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.Project;

public class ScrumprojektService {
	
	Project scrumproject = null;
	
	public Boolean checkIfProjectnameExists(String projectname) throws Exception {
		URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
				+ "/ScrumBO_Server/rest/scrumprojekt/suche/" + projectname + "/"
				+ ScrumBOClient.getDatabaseconfigfile());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String output = br.readLine();
		conn.disconnect();
		
		Gson gson = new Gson();
		
		if (output.equals("Projekt nicht vorhanden")) {
			return false;
			
		} else {
			scrumproject = gson.fromJson(output, Project.class);
			if (projectname.equals(scrumproject.getProjektname())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public Boolean checkIfProjectnameExistsForProjectLogin(String projectname) throws Exception {
		URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
				+ "/ScrumBO_Server/rest/scrumprojekt/suche/" + projectname + "/"
				+ ScrumBOClient.getDatabaseconfigfile());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		String output = br.readLine();
		conn.disconnect();
		
		Gson gson = new Gson();
		
		if (output.equals("Projekt nicht vorhanden")) {
			
			return false;
			
		} else {
			scrumproject = gson.fromJson(output, Project.class);
			if (projectname.equals(scrumproject.getProjektname())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public Boolean createProject(Project scrumproject) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(scrumproject);
		
		System.out.println(output);
		
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/scrumprojekt/create/" + CurrentUser.email + "/"
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
	
	public Project getScrumproject() {
		return scrumproject;
	}
	
	public void setScrumproject(Project scrumproject) {
		this.scrumproject = scrumproject;
	}
	
	public boolean deleteProject(Project scrumproject) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(scrumproject);
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/scrumprojekt/delete/" + ScrumBOClient.getDatabaseconfigfile());
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
	
	public List<Project> ladeProjekte() {
		List<Project> projekteListe = new LinkedList<Project>();
		String output = "";
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/scrumprojekt/alle/" + ScrumBOClient.getDatabaseconfigfile());
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
		Type listType = new TypeToken<LinkedList<Project>>() {
		}.getType();
		projekteListe = gson.fromJson(output, listType);
		
		return projekteListe;
	}
	
}
