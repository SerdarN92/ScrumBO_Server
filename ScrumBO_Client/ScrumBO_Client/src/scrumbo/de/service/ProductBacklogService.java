package scrumbo.de.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.ProductBacklog;

public class ProductBacklogService {
	
	private Gson gson = new Gson();
	
	/*
	 * Erhält vom Server die Id des Product Backlogs des Projekts in Form eines
	 * ProductBacklog-Objekts mit der Id und speichert Sie zum
	 * CurrentProject-Objekt, damit diese für spätere Abfragen genutzt werden
	 * kann.
	 */
	public void getProductBacklogForProject() {
		String output = "";
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/scrumprojekt/suche/" + CurrentProject.projectId + "/productbacklog" + "/"
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
		ProductBacklog productbacklog = gson.fromJson(output, ProductBacklog.class);
		CurrentProject.productbacklog = productbacklog;
	}
	
	/*
	 * Die Methode lädt anhand der Id des Product Backlogs alle zum Product
	 * Backlog gehörenden User Stories und speichert diese zum
	 * CurrentProject-Objekt.
	 */
	public void loadProductBacklog() {
		String output = "";
		try {
			
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/productbacklog/suche/" + CurrentProject.productbacklog.getId() + "/"
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
		ProductBacklog productbacklog = gson.fromJson(output, ProductBacklog.class);
		CurrentProject.productbacklog = productbacklog;
		
	}
}
