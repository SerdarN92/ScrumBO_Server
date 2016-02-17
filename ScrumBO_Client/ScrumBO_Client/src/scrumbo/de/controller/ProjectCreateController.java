package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
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
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.Project;
import scrumbo.de.entity.User;
import scrumbo.de.service.ScrumprojektService;

public class ProjectCreateController implements Initializable {
	
	Parent				root;
	Scene				scene;
	ScrumprojektService	scrumprojektService	= null;
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
	private void handleKeyPressed(KeyEvent event) throws JSONException, IOException, Exception {
		if (event.getCode().equals(KeyCode.ENTER))
			createProject();
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
	private void handleButtonCreateProject(ActionEvent event) throws Exception {
		createProject();
	}
	
	private void createProject() throws IOException, Exception {
		if (checkProjectname() && checkPassword()) {
			if (!scrumprojektService.checkIfProjectnameExists(txtFieldProjectname.getText().toString())) {
				txtFieldProjectname.setStyle(null);
				projectnameValidFail.setVisible(false);
				Project scrumproject = new Project();
				scrumproject.setProjektname(txtFieldProjectname.getText());
				scrumproject.setPasswort(pswtFieldPassword.getText());
				List<User> benutzerliste = new LinkedList<User>();
				User currBen = new User();
				currBen.setId(CurrentUser.userId);
				benutzerliste.add(currBen);
				scrumproject.setBenutzer(benutzerliste);
				
				if (scrumprojektService.createProject(scrumproject)) {
					this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/ProjectCreateSuccess.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonLogout.getScene().getWindow();
					stage.setScene(scene);
				} else {
					this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/ProjectCreateFail.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonLogout.getScene().getWindow();
					stage.setScene(scene);
				}
				
			} else {
				projectnameValidFail.setText("Projekt mit diesem Namen existiert bereits.");
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
	}
	
}
