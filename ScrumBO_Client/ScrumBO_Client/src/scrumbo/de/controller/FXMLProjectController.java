package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scrumbo.de.entity.CurrentBenutzer;

public class FXMLProjectController implements Initializable {
	
	Parent				root;
	Scene				scene;
	@FXML
	private Text		vorname;
	@FXML
	private Text		nachname;
	@FXML
	private Text		benutzerrolle;
	@FXML
	private ImageView	createProjectImage;
	@FXML
	private ImageView	openProjectImage;
	@FXML
	private Button		buttonLogout;
	@FXML
	private Button		buttonBack;
	
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
	private void handleCreateProjectButton(MouseEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLProjectCreate.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) createProjectImage.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleOpenProjectButton(MouseEvent event) throws Exception {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLProjectLogin.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) createProjectImage.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		vorname.setText(CurrentBenutzer.vorname);
		nachname.setText(CurrentBenutzer.nachname);
		benutzerrolle.setText(CurrentBenutzer.benutzerrolle);
	}
	
}
