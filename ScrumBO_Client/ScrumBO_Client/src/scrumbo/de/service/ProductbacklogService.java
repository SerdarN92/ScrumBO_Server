package scrumbo.de.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;

import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.ProductBacklog;

public class ProductbacklogService {
	
	public void getProductbacklog() {
		String output = "";
		try {
			URL url = new URL(
					"http://localhost:8080/ScrumBO_Server/rest/scrumprojekt/suche/" + CurrentScrumprojekt.scrumprojektID
							+ "/productbacklog" + "/" + ScrumBOClient.getDatabaseconfigfile());
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
		ProductBacklog liste = gson.fromJson(output, ProductBacklog.class);
		CurrentScrumprojekt.productbacklog = liste;
	}
	
	public void loadProductBacklog() {
		String output = "";
		Integer platz = -1;
		try {
			
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/productbacklog/suche/"
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
		ProductBacklog a = gson.fromJson(output, ProductBacklog.class);
		CurrentScrumprojekt.productbacklog = a;
		
	}
}
