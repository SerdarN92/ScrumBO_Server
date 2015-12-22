package scrumbo.de.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.entity.Scrumprojekt;

public class ScrumprojektService {
	
	Scrumprojekt scrumproject = null;
	
	public Boolean checkIfProjectnameExists(String projectname) throws Exception {
		URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/scrumprojekt/suche/" + projectname + "/"
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
			scrumproject = gson.fromJson(output, Scrumprojekt.class);
			if (projectname.equals(scrumproject.getProjektname())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public Boolean checkIfProjectnameExistsForProjectLogin(String projectname) throws Exception {
		URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/scrumprojekt/suche/" + projectname + "/"
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
			scrumproject = gson.fromJson(output, Scrumprojekt.class);
			if (projectname.equals(scrumproject.getProjektname())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public Boolean createProject(Scrumprojekt scrumproject) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(scrumproject);
		
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/scrumprojekt/create/" + CurrentBenutzer.email
					+ "/" + ScrumBOClient.getDatabaseconfigfile());
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
	
	public Scrumprojekt getScrumproject() {
		return scrumproject;
	}
	
	public void setScrumproject(Scrumprojekt scrumproject) {
		this.scrumproject = scrumproject;
	}
	
}
