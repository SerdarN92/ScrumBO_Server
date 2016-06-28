package scrumbo.de.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.common.Encryptor;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.User;
import scrumbo.de.service.UserService;

public class AdminChangePasswordController implements Initializable {
	
	private Parent			root;
	private Scene			scene;
	private UserService		userService;
							
	@FXML
	private PasswordField	password1;
	@FXML
	private PasswordField	password2;
	@FXML
	private Text			txtError;
	@FXML
	private Button			buttonSave;
	@FXML
	private Button			buttonAbbort;
							
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userService = StartwindowController.getBenutzerService();
		
	}
	
	private boolean checkPasswords() {
		if (!password1.getText().isEmpty() && !password2.getText().isEmpty()) {
			if (password1.getText().equals(password2.getText())) {
				cleanError();
				return true;
			} else {
				showError();
				return false;
			}
		} else {
			showError();
			return false;
		}
	}
	
	@FXML
	private void handleButtonAbbort() {
		Stage stage = (Stage) buttonAbbort.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleButtonSave() throws Exception {
		if (checkPasswords()) {
			User benutzer = new User(CurrentUser.userId, CurrentUser.prename, CurrentUser.lastname,
					Encryptor.encrypt(password1.getText()), CurrentUser.email);
					
			if (userService.changeDefaultPassword(benutzer)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Passwort Änderung");
				alert.setHeaderText(null);
				alert.setContentText("Ihr Passwort wurde erfolgreich geändert.");
				alert.showAndWait();
				handleButtonAbbort();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Passwort Änderung");
				alert.setHeaderText(null);
				alert.setContentText("Fehler! Ihr Passwort wurde nicht erfolgreich geändert.");
				alert.showAndWait();
				
				this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
				this.scene = new Scene(root);
				Stage stage = (Stage) buttonSave.getScene().getWindow();
				stage.setScene(scene);
			}
		}
	}
	
	private void showError() {
		txtError.setText("Bitte füllen Sie beide Felder mit einem identischem neuem Passwort.");
		password1.setStyle("-fx-border-color:#FF0000;");
		password2.setStyle("-fx-border-color:#FF0000;");
	}
	
	private void cleanError() {
		txtError.setText(null);
		password1.setStyle(null);
		password2.setStyle(null);
	}
}
