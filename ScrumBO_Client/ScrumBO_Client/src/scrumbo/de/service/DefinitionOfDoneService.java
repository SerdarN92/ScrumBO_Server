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
import scrumbo.de.entity.CurrentUserStory;
import scrumbo.de.entity.DefinitionOfDone;

public class DefinitionOfDoneService {
	
	public void ladeDefinitionOfDone() {
		String output = "";
		try {
			URL url = new URL("http://"+ScrumBOClient.getHost()+":"+ScrumBOClient.getPort()+"/ScrumBO_Server/rest/definitionofdone/"
					+ CurrentUserStory.userstoryID + "/" + ScrumBOClient.getDatabaseconfigfile());
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
		Type listType = new TypeToken<LinkedList<DefinitionOfDone>>() {
		}.getType();
		List<DefinitionOfDone> liste = gson.fromJson(output, listType);
		CurrentUserStory.dod = liste;
	}
	
	public boolean createDefinitionOfDone(DefinitionOfDone dod) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(dod);
		
		try {
			URL url = new URL("http://"+ScrumBOClient.getHost()+":"+ScrumBOClient.getPort()+"/ScrumBO_Server/rest/definitionofdone/create/"
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
	
	public boolean deleteDefinitionOfDone(DefinitionOfDone dod) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(dod);
		try {
			URL url = new URL("http://"+ScrumBOClient.getHost()+":"+ScrumBOClient.getPort()+"/ScrumBO_Server/rest/definitionofdone/delete/"
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
	
	public boolean updateDefinitionOfDone(DefinitionOfDone dod) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(dod);
		
		try {
			URL url = new URL("http://"+ScrumBOClient.getHost()+":"+ScrumBOClient.getPort()+"/ScrumBO_Server/rest/definitionofdone/update/"
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
