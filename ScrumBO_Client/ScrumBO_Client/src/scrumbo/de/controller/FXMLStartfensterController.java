package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FXMLStartfensterController implements Initializable {
	
	Parent				root;
	Scene				scene;
	@FXML
	private ImageView	adminImage;
	@FXML
	private ImageView	loginUserImage;
	
	@FXML
	private void handleCreateUserAction(MouseEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLAdminLogin.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) adminImage.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleLoginUserAction(MouseEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLUserLogin.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) loginUserImage.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
	}
	
}
