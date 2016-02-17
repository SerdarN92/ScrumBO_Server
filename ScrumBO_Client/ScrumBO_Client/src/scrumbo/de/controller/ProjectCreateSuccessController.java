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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ProjectCreateSuccessController implements Initializable {
	
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
	private void handleKeyPressed(KeyEvent event) throws JSONException, IOException, Exception {
		if (event.getCode().equals(KeyCode.ENTER))
			forward();
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
	private void handleButtonForward(ActionEvent event) throws Exception {
		forward();
	}
	
	private void forward() throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Project.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonForward.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
}
