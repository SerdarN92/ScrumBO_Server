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

public class ProjectService {
	
	private Project	project	= null;
	private Gson	gson	= new Gson();
	private Type	listType;
					
	/*
	 * Methode zum Prüfen, ob ein Projekt mit exakt dem Projektnamen existiert.
	 * Falls ja, wird true zurückgegeben und das erhaltene Project-Objekt wird
	 * zwischengespeichert. Falls nein, wird ein false zurückgegeben.
	 */
	public boolean checkIfProjectnameExists(String projectname) throws Exception {
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
		
		if (output.equals("Projekt nicht vorhanden")) {
			return false;
			
		} else {
			project = gson.fromJson(output, Project.class);
			if (projectname.equalsIgnoreCase(project.getProjektname())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	/*
	 * Methode zum Erstellen eines Projekts. Die Id des eingeloggten Benutzers
	 * wird mitgegeben, damit das Projekt dem User zugeordnet werden kann.
	 */
	public Boolean createProject(Project project) {
		boolean status = false;
		String output = gson.toJson(project);
		
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/scrumprojekt/create/" + CurrentUser.userId + "/"
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
	
	/*
	 * Methode zum Löschen eines Projekts.
	 */
	public boolean deleteProject(Project project) {
		boolean status = false;
		String output = "";
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/scrumprojekt/delete/" + project.getId() + "/"
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
			
			if (output.equals("Projekt gelöscht")) {
				status = true;
			}
			conn.disconnect();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return status;
	}
	
	/*
	 * Methode zum Laden aller vorhandenen Projekte auf der Datenbank.
	 */
	public List<Project> loadProjects() {
		List<Project> projectList;
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
		
		listType = new TypeToken<LinkedList<Project>>() {
		}.getType();
		projectList = gson.fromJson(output, listType);
		
		return projectList;
	}
	
	public Project getScrumproject() {
		return project;
	}
	
	public void setScrumproject(Project project) {
		this.project = project;
	}
	
}
