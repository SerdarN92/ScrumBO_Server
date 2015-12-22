package scrumbo.de.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.Benutzerrolle;
import scrumbo.de.entity.CurrentBenutzer;

public class BenutzerrolleService {
	
	List<Benutzerrolle> liste = null;
	
	public BenutzerrolleService() {
		ladeRollen();
	}
	
	public List<Benutzerrolle> getListe() {
		return liste;
	}
	
	public void ladeRollen() {
		URL url2 = null;
		HttpURLConnection conn = null;
		
		try {
			url2 = new URL("http://localhost:8080/ScrumBO_Server/rest/benutzerrolle" + "/"
					+ ScrumBOClient.getDatabaseconfigfile());
			conn = (HttpURLConnection) url2.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "";
			JSONArray o = null;
			while ((output = br.readLine()) != null) {
				o = new JSONArray(output);
			}
			conn.disconnect();
			
			Gson gson = new Gson();
			Type listType = new TypeToken<LinkedList<Benutzerrolle>>() {
			}.getType();
			this.liste = gson.fromJson(o.toString(), listType);
			
		} catch (
		
		Exception e)
		
		{
			e.printStackTrace();
		}
	}
	
	public Boolean checkRole(String email) throws JSONException {
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/benutzer/suche/" + email + "/rolle/"
					+ ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(5000);
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = br.readLine();
			conn.disconnect();
			
			if (output.equals("User ist Scrum Master")) {
				CurrentBenutzer.isSM = true;
			}
			if (output.equals("User ist Product Owner")) {
				CurrentBenutzer.isPO = true;
			}
			if (output.equals("User ist Entwickler")) {
				CurrentBenutzer.isDev = true;
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
