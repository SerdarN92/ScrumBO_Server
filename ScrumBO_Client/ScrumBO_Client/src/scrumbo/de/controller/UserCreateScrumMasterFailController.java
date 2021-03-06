package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UserCreateScrumMasterFailController implements Initializable {
	
	Parent			root;
	Scene			scene;
					
	@FXML
	private Button	buttonForward;
					
	@FXML
	private void handleButtonForward(ActionEvent event) throws IOException {
		Stage stage = (Stage) buttonForward.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}