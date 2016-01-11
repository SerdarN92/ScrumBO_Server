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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scrumbo.de.entity.CurrentUser;

public class ProjectCreateFailController implements Initializable {
	
	Parent			root;
	Scene			scene;
	@FXML
	private Text	vorname;
	@FXML
	private Text	nachname;
	@FXML
	private Text	benutzerrolle;
	@FXML
	private Button	buttonLogout;
	@FXML
	private Button	buttonForward;
					
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
	private void handleButtonForward(ActionEvent event) throws Exception {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Project.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonForward.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		vorname.setText(CurrentUser.prename);
		nachname.setText(CurrentUser.lastname);
		benutzerrolle.setText(CurrentUser.role);
	}
	
}
