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
import javafx.stage.Stage;

public class ProjectController implements Initializable {
	
	Parent				root;
	Scene				scene;
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
		StartwindowController.logout();
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		
	}
	
	@FXML
	private void handleCreateProjectButton(MouseEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/ProjectCreate.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) createProjectImage.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleOpenProjectButton(MouseEvent event) throws Exception {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/ProjectLogin.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) createProjectImage.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
}
