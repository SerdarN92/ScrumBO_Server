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
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.CurrentUser;
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
		CurrentUser.userId = -1;
		CurrentUser.prename = null;
		CurrentUser.lastname = null;
		CurrentUser.email = null;
		CurrentUser.password = null;
		CurrentUser.roleId = -1;
		CurrentUser.role = null;
		CurrentUser.projects = null;
		CurrentUser.isSM = false;
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		
	}
	
	@FXML
	private void handleBackButton(ActionEvent event) throws Exception {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Project.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleButtonOpenProject(ActionEvent event) throws Exception {
		if (checkProjectname() && checkPassword()) {
			if (scrumprojektService.checkIfProjectnameExists(txtFieldProjectname.getText().toString())) {
				projectnameValidFail.setVisible(false);
				txtFieldProjectname.setStyle(null);
				CurrentProject.projectId = scrumprojektService.getScrumproject().getScrumProjektID();
				CurrentProject.projectname = scrumprojektService.getScrumproject().getProjektname();
				if (benutzerService.checkAdmission()) {
					txtFieldProjectname.setStyle(null);
					productbacklogService.getProductbacklog();
					impedimentService.getImpedimentBacklog();
					if (CurrentUser.isSM) {
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
					txtFieldProjectname.setStyle("-fx-border-color:#FF0000;");
				}
			} else {
				projectnameValidFail.setText("Projekt mit diesem Namen existiert nicht.");
				projectnameValidFail.setVisible(true);
				txtFieldProjectname.setStyle("-fx-border-color:#FF0000;");
			}
		}
	}
	
	private Boolean checkPassword() throws Exception {
		if (!(pswtFieldPassword.getText().isEmpty())) {
			passwordValidFail.setText(null);
			pswtFieldPassword.setStyle(null);
			if (pswtFieldPassword.getText().length() >= 6) {
				passwordValidFail.setText(null);
				pswtFieldPassword.setStyle(null);
				// encryptPassword(pswtFieldPassword.getText());
				return true;
			} else {
				passwordValidFail.setText("Passwort ist zu kurz");
				pswtFieldPassword.setStyle("-fx-border-color:#FF0000;");
				return false;
			}
		} else {
			passwordValidFail.setText("Bitte ein Passwort eingeben");
			pswtFieldPassword.setStyle("-fx-border-color:#FF0000;");
			return false;
		}
		
	}
	
	private Boolean checkProjectname() {
		if (!(txtFieldProjectname.getText().isEmpty())) {
			projectnameValidFail.setText(null);
			txtFieldProjectname.setStyle(null);
			return true;
		} else {
			projectnameValidFail.setText("Bitte einen Projektnamen eingeben");
			txtFieldProjectname.setStyle("-fx-border-color:#FF0000;");
			return false;
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		scrumprojektService = StartwindowController.getScrumprojektService();
		productbacklogService = StartwindowController.getProductbacklogService();
		impedimentService = StartwindowController.getImpedimentService();
		benutzerService = StartwindowController.getBenutzerService();
	}
	
}
