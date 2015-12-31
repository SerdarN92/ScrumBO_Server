package scrumbo.de.controller;

import java.net.URL;
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
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.service.BenutzerService;
import scrumbo.de.service.ImpedimentService;
import scrumbo.de.service.ProductbacklogService;
import scrumbo.de.service.ScrumprojektService;

public class ProjectLoginController implements Initializable {
	
	Parent					root;
	Scene					scene;
	ScrumprojektService		scrumprojektService		= null;
	ImpedimentService		impedimentService		= null;
	ProductbacklogService	productbacklogService	= null;
	BenutzerService			benutzerService			= null;
	@FXML
	private Text			vorname;
	@FXML
	private Text			nachname;
	@FXML
	private Text			benutzerrolle;
	@FXML
	private Button			buttonLogout;
	@FXML
	private Button			buttonBack;
	@FXML
	private Button			buttonOpenProject;
	@FXML
	private TextField		txtFieldProjectname;
	@FXML
	private TextField		pswtFieldPassword;
	@FXML
	private Text			projectnameValidFail;
	@FXML
	private Text			passwordValidFail;
							
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
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
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
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleButtonOpenProject(ActionEvent event) throws Exception {
		if (checkProjectname() && checkPassword()) {
			if (scrumprojektService.checkIfProjectnameExists(txtFieldProjectname.getText().toString())) {
				projectnameValidFail.setVisible(false);
				CurrentScrumprojekt.scrumprojektID = scrumprojektService.getScrumproject().getScrumProjektID();
				CurrentScrumprojekt.projektname = scrumprojektService.getScrumproject().getProjektname();
				if (benutzerService.checkAdmission()) {
					productbacklogService.getProductbacklog();
					impedimentService.getImpedimentBacklog();
					if (CurrentBenutzer.isSM) {
						this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/ScrumSM.fxml"));
						this.scene = new Scene(root);
						Stage stage = (Stage) buttonLogout.getScene().getWindow();
						stage.setScene(scene);
					} else {
						this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Scrum.fxml"));
						this.scene = new Scene(root);
						Stage stage = (Stage) buttonLogout.getScene().getWindow();
						stage.setScene(scene);
					}
				} else {
					projectnameValidFail.setText("Sie haben keine Zugriffsrechte auf dieses Projekt");
					projectnameValidFail.setVisible(true);
				}
			} else {
				projectnameValidFail.setText("Projekt mit diesem Namen existiert nicht.");
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
		scrumprojektService = StartwindowController.getScrumprojektService();
		productbacklogService = StartwindowController.getProductbacklogService();
		impedimentService = StartwindowController.getImpedimentService();
		benutzerService = StartwindowController.getBenutzerService();
		vorname.setText(CurrentBenutzer.vorname);
		nachname.setText(CurrentBenutzer.nachname);
		benutzerrolle.setText(CurrentBenutzer.benutzerrolle);
	}
	
}
