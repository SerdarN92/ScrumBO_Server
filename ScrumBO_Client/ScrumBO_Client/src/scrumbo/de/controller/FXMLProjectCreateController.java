package scrumbo.de.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

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
import scrumbo.de.entity.Benutzer;
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.entity.Scrumprojekt;
import scrumbo.de.service.ScrumprojektService;

public class FXMLProjectCreateController implements Initializable {
	
	Parent				root;
	Scene				scene;
	ScrumprojektService	scrumprojektService	= null;
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
			if (!scrumprojektService.checkIfProjectnameExists(txtFieldProjectname.getText().toString())) {
				projectnameValidFail.setVisible(false);
				Scrumprojekt scrumproject = new Scrumprojekt();
				scrumproject.setProjektname(txtFieldProjectname.getText());
				scrumproject.setPasswort(pswtFieldPassword.getText());
				List<Benutzer> benutzerliste = new LinkedList<Benutzer>();
				Benutzer currBen = new Benutzer();
				currBen.setId(CurrentBenutzer.benutzerID);
				benutzerliste.add(currBen);
				scrumproject.setBenutzer(benutzerliste);
				
				if (scrumprojektService.createProject(scrumproject)) {
					this.root = FXMLLoader
							.load(getClass().getResource("/scrumbo/de/gui/FXMLProjectCreateSuccess.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonLogout.getScene().getWindow();
					stage.setScene(scene);
				} else {
					this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLProjectCreateFail.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonLogout.getScene().getWindow();
					stage.setScene(scene);
				}
				
			} else {
				projectnameValidFail.setText("Projekt mit diesem Namen existiert bereits.");
				projectnameValidFail.setVisible(true);
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
		scrumprojektService = FXMLStartController.getScrumprojektService();
		vorname.setText(CurrentBenutzer.vorname);
		nachname.setText(CurrentBenutzer.nachname);
		benutzerrolle.setText(CurrentBenutzer.benutzerrolle);
	}
	
}
