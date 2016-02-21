package scrumbo.de.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONException;

import com.google.gson.Gson;

import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.Role;

public class RoleService {
	
	private List<Role>	listOfRoles	= null;
	private Gson		gson		= new Gson();
	private Type		listType;
						
	public List<Role> getListOfRoles() {
		return listOfRoles;
	}
	
	public Boolean checkRole(String email) throws JSONException {
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/benutzer/suche/" + email + "/rolle/"
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
				CurrentUser.isSM = true;
			}
			if (output.equals("User ist Product Owner")) {
				CurrentUser.isPO = true;
			}
			if (output.equals("User ist Entwickler")) {
				CurrentUser.isDev = true;
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
