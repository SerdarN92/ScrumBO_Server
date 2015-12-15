package scrumbo.de.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.google.gson.Gson;

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
import scrumbo.de.entity.Benutzer;
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.entity.Scrumprojekt;

public class FXMLProjectCreateController implements Initializable {
	
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
	private Button		buttonCreateProject;
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
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLProject.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleButtonCreateProject(ActionEvent event) throws Exception {
		if (checkProjectname() && checkPassword()) {
			if (!checkIfProjectnameExists()) {
				Scrumprojekt scrumproject = new Scrumprojekt();
				scrumproject.setProjektname(txtFieldProjectname.getText());
				scrumproject.setPasswort(pswtFieldPassword.getText());
				List<Benutzer> benutzerliste = new LinkedList<Benutzer>();
				Benutzer currBen = new Benutzer();
				currBen.setId(CurrentBenutzer.benutzerID);
				benutzerliste.add(currBen);
				scrumproject.setBenutzer(benutzerliste);
				
				Gson gson = new Gson();
				String output = gson.toJson(scrumproject);
				
				JSONObject jsonObject = new JSONObject(output);
				
				try {
					URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/scrumprojekt/create/"
							+ CurrentBenutzer.email + "/" + ScrumBOClient.getDatabaseconfigfile());
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(5000);
					conn.setRequestMethod("POST");
					
					OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
					out.write(jsonObject.toString());
					out.close();
					
					if (conn.getResponseMessage().equals("Project erfolgreich erstellt"))
						System.out.println("\nRest Service Invoked Successfully..");
					conn.disconnect();
					
					this.root = FXMLLoader
							.load(getClass().getResource("/scrumbo/de/gui/FXMLProjectCreateSuccess.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonLogout.getScene().getWindow();
					stage.setScene(scene);
				} catch (Exception e) {
					System.out.println("\nError while calling Rest service");
					e.printStackTrace();
					this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLProjectCreateFail.fxml"));
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
	
	private Boolean checkIfProjectnameExists() throws Exception {
		String projectname = txtFieldProjectname.getText();
		URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/scrumprojekt/suche/" + projectname + "/"
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
		Scrumprojekt scrumproject = null;
		if (output.equals("Projekt nicht vorhanden")) {
			projectnameValidFail.setVisible(false);
			return false;
			
		} else {
			scrumproject = gson.fromJson(output, Scrumprojekt.class);
			if (projectname.equals(scrumproject.getProjektname())) {
				projectnameValidFail.setText("Projekt mit diesem Namen existiert bereits.");
				projectnameValidFail.setVisible(true);
				return true;
			} else {
				projectnameValidFail.setVisible(false);
				return false;
			}
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		vorname.setText(CurrentBenutzer.vorname);
		nachname.setText(CurrentBenutzer.nachname);
		benutzerrolle.setText(CurrentBenutzer.benutzerrolle);
		// ArrayList<Scrumprojekt> a = CurrentBenutzer.projekte;
	}
	
}
