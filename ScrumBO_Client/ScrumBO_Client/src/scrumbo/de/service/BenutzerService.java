package scrumbo.de.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.User;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.CurrentProject;

public class BenutzerService {
	
	private User benutzer = null;
	
	public User getBenutzer() {
		return benutzer;
	}
	
	public void setBenutzer(User benutzer) {
		this.benutzer = benutzer;
	}
	
	public Boolean checkIfEmailExists(String email) throws JSONException {
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/benutzer/suche/" + email + "/" + ScrumBOClient.getDatabaseconfigfile());
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
			
			Gson gson = new Gson();
			if (output.equals("User ist nicht vorhanden")) {
				return false;
				
			} else {
				
				benutzer = gson.fromJson(output, User.class);
				if (email.equals(benutzer.getEmail())) {
					return true;
				} else {
					return false;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public Boolean createScrumMaster(User benutzer) throws JSONException {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(benutzer);
		
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/benutzer/create/1" + "/" + ScrumBOClient.getDatabaseconfigfile());
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
			
		} catch (
		
		Exception e)
		
		{
			e.printStackTrace();
		}
		return status;
	}
	
	public Boolean changeDefaultPassword(User benutzer) throws JSONException {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(benutzer);
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/benutzer/updatePassword/" + CurrentUser.email + "/"
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
	
	// nur Benutzer die Entwickler sind
	public List<User> ladeEntwicklerVomAktuellenProjekt() {
		List<User> benutzerList = new LinkedList<User>();
		String output = "";
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/benutzer/alle/scrumprojekt/entwickler/" + CurrentProject.projectId
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
		Type listType = new TypeToken<LinkedList<User>>() {
		}.getType();
		benutzerList = gson.fromJson(output, listType);
		
		return benutzerList;
	}
	
	public List<User> ladeBenutzer() {
		List<User> benutzerList = new LinkedList<User>();
		String output = "";
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/benutzer/alle/" + ScrumBOClient.getDatabaseconfigfile());
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
		Type listType = new TypeToken<LinkedList<User>>() {
		}.getType();
		benutzerList = gson.fromJson(output, listType);
		
		return benutzerList;
	}
	
	public boolean checkAdmission() throws JSONException {
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/benutzer/checkAdmission/" + CurrentUser.userId + "/"
					+ CurrentProject.projectId + "/" + ScrumBOClient.getDatabaseconfigfile());
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
			
			Gson gson = new Gson();
			if (output.equals("Ja")) {
				return true;
				
			} else {
				return false;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean checkAdmin() throws JSONException {
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/benutzer/checkAdmin/" + CurrentUser.userId + "/"
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
			
			Gson gson = new Gson();
			if (output.equals("Ja")) {
				return true;
				
			} else {
				return false;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deleteBenutzer(User benutzer) {
		boolean status = false;
		Gson gson = new Gson();
		String output = gson.toJson(benutzer);
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/benutzer/delete/" + ScrumBOClient.getDatabaseconfigfile());
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
