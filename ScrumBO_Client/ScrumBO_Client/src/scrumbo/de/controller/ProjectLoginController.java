package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scrumbo.de.common.Encryptor;
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.service.ImpedimentService;
import scrumbo.de.service.ProductBacklogService;
import scrumbo.de.service.ProjectService;
import scrumbo.de.service.UserService;

public class ProjectLoginController implements Initializable {
	
	Parent					root;
	Scene					scene;
	ProjectService			scrumprojektService		= null;
	ImpedimentService		impedimentService		= null;
	ProductBacklogService	productbacklogService	= null;
	UserService				benutzerService			= null;
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
	private void handleKeyPressed(KeyEvent event) throws JSONException, IOException, Exception {
		if (event.getCode().equals(KeyCode.ENTER))
			openProject();
	}
	
	@FXML
	private void handleButtonLogout(ActionEvent event) throws Exception {
		StartwindowController.logout();
		
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
		openProject();
	}
	
	private void openProject() throws JSONException, IOException, Exception {
		if (checkProjectname() && checkPassword()) {
			if (scrumprojektService.checkIfProjectnameExists(txtFieldProjectname.getText().toString())) {
				projectnameValidFail.setVisible(false);
				txtFieldProjectname.setStyle(null);
				if (Encryptor.decrypt(scrumprojektService.getScrumproject().getPasswort())
						.equals(pswtFieldPassword.getText().toString())) {
					CurrentProject.projectId = scrumprojektService.getScrumproject().getScrumProjektID();
					CurrentProject.projectname = scrumprojektService.getScrumproject().getProjektname();
					if (benutzerService.checkAdmission()) {
						txtFieldProjectname.setStyle(null);
						productbacklogService.getProductBacklogForProject();
						impedimentService.getImpedimentsForProject();
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
					passwordValidFail.setText("Passwort ist falsch.");
					passwordValidFail.setVisible(true);
					pswtFieldPassword.setStyle("-fx-border-color:#FF0000;");
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
