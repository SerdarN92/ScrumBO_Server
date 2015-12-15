package scrumbo.de.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

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
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.Impediment;
import scrumbo.de.entity.ProductBacklog;
import scrumbo.de.entity.Scrumprojekt;

public class FXMLProjectLoginController implements Initializable {
	
	Parent				root;
	Scene				scene;
	@FXML
	private Text		vorname;
	@FXML
	private Text		nachname;
	@FXML
	private Text		benutzerrolle;
	@FXML
	private Button		buttonLogout;
	@FXML
	private Button		buttonBack;
	@FXML
	private Button		buttonOpenProject;
	@FXML
	private TextField	txtFieldProjectname;
	@FXML
	private TextField	pswtFieldPassword;
	@FXML
	private Text		projectnameValidFail;
	@FXML
	private Text		passwordValidFail;
	
	@FXML
	private void handleButtonLogout(ActionEvent event) throws Exception {
		CurrentBenutzer.benutzerID = -1;
		CurrentBenutzer.vorname = null;
		CurrentBenutzer.nachname = null;
		CurrentBenutzer.email = null;
		CurrentBenutzer.passwort = null;
		CurrentBenutzer.benutzerrollenID = -1;
		CurrentBenutzer.benutzerrolle = null;
		CurrentBenutzer.projekte = null;
		CurrentBenutzer.isSM = false;
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLStartfenster.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		
	}
	
	@FXML
	private void handleBackButton(ActionEvent event) throws Exception {
		CurrentBenutzer.benutzerID = -1;
		CurrentBenutzer.vorname = null;
		CurrentBenutzer.nachname = null;
		CurrentBenutzer.email = null;
		CurrentBenutzer.passwort = null;
		CurrentBenutzer.benutzerrollenID = -1;
		CurrentBenutzer.benutzerrolle = null;
		CurrentBenutzer.projekte = null;
		CurrentBenutzer.isSM = false;
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLStartfenster.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleButtonOpenProject(ActionEvent event) throws Exception {
		if (checkProjectname() && checkPassword()) {
			if (checkIfProjectnameExists()) {
				if (CurrentBenutzer.isSM) {
					this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLScrumSM.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonLogout.getScene().getWindow();
					stage.setScene(scene);
				} else {
					this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLScrum.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonLogout.getScene().getWindow();
					stage.setScene(scene);
				}
			}
		}
	}
	
	private Boolean checkPassword() throws Exception {
		if (!(pswtFieldPassword.getText().isEmpty())) {
			passwordValidFail.setText(null);
			if (pswtFieldPassword.getText().length() >= 6) {
				passwordValidFail.setText(null);
				// encryptPassword(pswtFieldPassword.getText());
				return true;
			} else {
				passwordValidFail.setText("Passwort ist zu kurz");
				return false;
			}
		} else {
			passwordValidFail.setText("Bitte ein Passwort eingeben");
			return false;
		}
		
	}
	
	private Boolean checkProjectname() {
		if (!(txtFieldProjectname.getText().isEmpty())) {
			projectnameValidFail.setText(null);
			return true;
		} else {
			projectnameValidFail.setText("Bitte einen Projektnamen eingeben");
			return false;
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		vorname.setText(CurrentBenutzer.vorname);
		nachname.setText(CurrentBenutzer.nachname);
		benutzerrolle.setText(CurrentBenutzer.benutzerrolle);
		// ArrayList<Scrumprojekt> a = CurrentBenutzer.projekte;
	}
	
	private Boolean checkIfProjectnameExists() throws Exception {
		String projectname = txtFieldProjectname.getText();
		String output = "";
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/scrumprojekt/suche/" + projectname + "/"
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
		Scrumprojekt a = null;
		if (output.equals("Projekt nicht vorhanden")) {
			projectnameValidFail.setText("Projekt mit diesem Namen existiert nicht.");
			projectnameValidFail.setVisible(true);
			return false;
			
		} else {
			a = gson.fromJson(output, Scrumprojekt.class);
			if (projectname.equals(a.getProjektname())) {
				projectnameValidFail.setVisible(false);
				CurrentScrumprojekt.scrumprojektID = a.getScrumProjektID();
				CurrentScrumprojekt.projektname = a.getProjektname();
				getProductbacklog();
				getImpedimentBacklog();
				return true;
			} else {
				projectnameValidFail.setText("Projekt mit diesem Namen existiert nicht.");
				projectnameValidFail.setVisible(true);
				return false;
			}
		}
		
	}
	
	public static void getImpedimentBacklog() {
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
	
	private void getProductbacklog() {
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
		Type listType = new TypeToken<LinkedList<ProductBacklog>>() {
		}.getType();
		List<ProductBacklog> liste = gson.fromJson(output, listType);
		CurrentScrumprojekt.productbacklog = liste;
	}
	
}
