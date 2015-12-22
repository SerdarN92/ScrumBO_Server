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
import scrumbo.de.entity.Impediment;

public class ImpedimentService {
	
	public void getImpedimentBacklog() {
		String output = "";
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/impedimentbacklog/suche/"
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
		Type listType = new TypeToken<LinkedList<Impediment>>() {
		}.getType();
		List<Impediment> liste = gson.fromJson(output, listType);
		CurrentScrumprojekt.impedimentbacklog = liste;
	}
	
	public boolean createImpediment(Impediment impediment) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(impediment);
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/impedimentbacklog/create/"
					+ CurrentScrumprojekt.scrumprojektID + "/" + ScrumBOClient.getDatabaseconfigfile());
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
	
	public boolean deleteImpediment(Impediment impediment) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(impediment);
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/impedimentbacklog/delete/"
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
	
	public boolean updateImpediment(Impediment impediment) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(impediment);
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/impedimentbacklog/update/"
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
}
