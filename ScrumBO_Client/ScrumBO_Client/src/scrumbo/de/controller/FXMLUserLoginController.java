/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumbo.de.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONException;

import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.Benutzer;
import scrumbo.de.entity.CurrentBenutzer;

/**
 * FXML Controller class
 *
 * @author Serdar
 */
public class FXMLUserLoginController implements Initializable {
	
	Parent					root;
	Scene					scene;
	Benutzer				benutzer	= null;
										
	@FXML
	private TextField		txtFieldEmail;
	@FXML
	private PasswordField	pswtFieldPassword;
	@FXML
	private Text			emailValidFail;
	@FXML
	private Text			passwordValidFail;
	@FXML
	private Button			buttonBack;
	@FXML
	private Button			buttonLoginUser;
							
	@FXML
	private void handleBackButton(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLStartfenster.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonBack.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleLoginButton(ActionEvent event) throws Exception {
		if (checkEmail() && checkPassword()) {
			if (!checkIfEmailExists(txtFieldEmail.getText())) {
				emailValidFail
						.setText("Benutzer mit dieser E-Mail Adresse existiert nicht! Bitte registrieren Sie sich.");
				emailValidFail.setVisible(true);
			} else {
				CurrentBenutzer.benutzerID = benutzer.getId();
				CurrentBenutzer.vorname = benutzer.getVorname();
				CurrentBenutzer.nachname = benutzer.getNachname();
				CurrentBenutzer.email = benutzer.getEmail();
				CurrentBenutzer.passwort = benutzer.getPasswort();
				checkRole(CurrentBenutzer.email);
				if (pswtFieldPassword.getText().equals(CurrentBenutzer.passwort)) {
					emailValidFail.setVisible(false);
					passwordValidFail.setVisible(false);
					if (CurrentBenutzer.passwort.equals("12345678")) {
						this.root = FXMLLoader
								.load(getClass().getResource("/scrumbo/de/gui/FXMLUserChangePassword.fxml"));
						this.scene = new Scene(root);
						Stage stage = (Stage) buttonLoginUser.getScene().getWindow();
						stage.setScene(scene);
					} else {
						if (CurrentBenutzer.isSM) {
							this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLProject.fxml"));
							this.scene = new Scene(root);
							Stage stage = (Stage) buttonLoginUser.getScene().getWindow();
							stage.setScene(scene);
						} else {
							this.root = FXMLLoader
									.load(getClass().getResource("/scrumbo/de/gui/FXMLProjectLogin.fxml"));
							this.scene = new Scene(root);
							Stage stage = (Stage) buttonLoginUser.getScene().getWindow();
							stage.setScene(scene);
						}
						
					}
				} else {
					passwordValidFail.setText("Passwort ist falsch");
					passwordValidFail.setVisible(true);
				}
				
			}
		}
	}
	
	private Boolean checkEmail() {
		if (txtFieldEmail.getText().contains("@")) {
			emailValidFail.setText(null);
			return true;
		} else {
			emailValidFail.setText("Ungültige E-Mail");
			return false;
		}
	}
	
	private Boolean checkPassword() throws Exception {
		if (!(pswtFieldPassword.getText().isEmpty())) {
			passwordValidFail.setText(null);
			return true;
		} else {
			passwordValidFail.setText("Bitte ein Passwort eingeben");
			return false;
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}
	
	private Boolean checkIfEmailExists(String email) throws JSONException {
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/benutzer/suche/" + email + "/"
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
			if (output.equals("User ist nicht vorhanden")) {
				return false;
				
			} else {
				
				benutzer = gson.fromJson(output, Benutzer.class);
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
	
	private Boolean checkRole(String email) throws JSONException {
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
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
