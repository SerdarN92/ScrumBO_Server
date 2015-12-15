/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumbo.de.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.common.LetterTextField;
import scrumbo.de.entity.Benutzer;
import scrumbo.de.entity.Benutzerrolle;

/**
 * FXML Controller Klasse für die Erstellung eines Benutzers
 *
 * @author Serdar
 */
public class FXMLUserCreateScrumMasterController implements Initializable {
	
	Parent					root;
	Scene					scene;
	List<Benutzerrolle>		liste	= null;
	@FXML
	private LetterTextField	txtFieldPrename;
	@FXML
	private LetterTextField	txtFieldLastname;
	@FXML
	private TextField		txtFieldEmail;
	@FXML
	private Button			buttonAbbort;
	@FXML
	private Button			buttonCreateSM;
	@FXML
	private Text			txtPrename;
	@FXML
	private Text			txtLastname;
	@FXML
	private Text			txtEmail;
	
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLAdminView.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonAbbort.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleButtonCreateSM(ActionEvent event) throws Exception {
		if (checkPreName() && checkLastName() && checkEmail()) {
			if (checkIfEmailExists(txtFieldEmail.getText())) {
				txtEmail.setText("E-Mail Adresse bereits vorhanden.");
				txtEmail.setVisible(true);
			} else {
				txtEmail.setVisible(false);
				
				Benutzer benutzer = new Benutzer();
				benutzer.setVorname(txtFieldPrename.getText());
				benutzer.setNachname(txtFieldLastname.getText());
				benutzer.setEmail(txtFieldEmail.getText());
				benutzer.setPasswort("12345678");
				
				Gson gson = new Gson();
				String output = gson.toJson(benutzer);
				
				JSONObject jsonObject = new JSONObject(output);
				
				try {
					URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/benutzer/create/1" + "/"
							+ ScrumBOClient.getDatabaseconfigfile());
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(5000);
					conn.setRequestMethod("POST");
					
					OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
					out.write(jsonObject.toString());
					out.close();
					
					if (conn.getResponseMessage().equals("User erfolgreich erstellt"))
						System.out.println("\nRest Service Invoked Successfully..");
					conn.disconnect();
					
					this.root = FXMLLoader
							.load(getClass().getResource("/scrumbo/de/gui/FXMLUserCreateScrumMasterSuccess.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonAbbort.getScene().getWindow();
					stage.setScene(scene);
				} catch (Exception e) {
					System.out.println("\nError while calling Rest service");
					e.printStackTrace();
					this.root = FXMLLoader
							.load(getClass().getResource("/scrumbo/de/gui/FXMLUserCreateMasterFail.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonAbbort.getScene().getWindow();
					stage.setScene(scene);
				}
				
			}
		}
	}
	
	private Boolean checkEmail() {
		if (txtFieldEmail.getText().contains("@")) {
			txtEmail.setText(null);
			return true;
		} else {
			txtEmail.setText("Ungültige E-Mail");
			return false;
		}
	}
	
	private Boolean checkPreName() {
		if (!(txtFieldPrename.getText().isEmpty())) {
			txtPrename.setText(null);
			return true;
		} else {
			txtPrename.setText("Bitte einen Vornamen eingeben");
			return false;
		}
	}
	
	private Boolean checkLastName() {
		if (!(txtFieldLastname.getText().isEmpty())) {
			txtLastname.setText(null);
			return true;
		} else {
			txtLastname.setText("Bitte einen Nachnamen eingeben");
			return false;
		}
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
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ladeRollen();
	}
	
	private Boolean checkIfEmailExists(String email) throws JSONException {
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/benutzer/suche/" + email + "/"
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
			Benutzer a = null;
			if (output.equals("User nicht vorhanden")) {
				return false;
				
			} else {
				a = gson.fromJson(output, Benutzer.class);
				if (email.equals(a.getEmail())) {
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
	
}
