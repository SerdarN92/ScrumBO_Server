package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scrumbo.de.common.Encryptor;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.User;
import scrumbo.de.service.UserService;

public class UserChangePasswordController implements Initializable {
	
	Parent					root;
	Scene					scene;
	UserService			benutzerService	= null;
	@FXML
	private Button			buttonSave;
	@FXML
	private PasswordField	txtPassword;
	@FXML
	private PasswordField	txtPassword2;
	@FXML
	private Text			txtName;
	@FXML
	private Text			txtError;
							
	@FXML
	private void handleKeyPressed(KeyEvent event) throws JSONException, IOException, Exception {
		if (event.getCode().equals(KeyCode.ENTER))
			saveChangedPW();
	}
	
	private void saveChangedPW() throws JSONException, IOException {
		if (passwordCheck()) {
			User benutzer = new User();
			benutzer.setId(CurrentUser.userId);
			benutzer.setVorname(CurrentUser.prename);
			benutzer.setNachname(CurrentUser.lastname);
			benutzer.setEmail(CurrentUser.email);
			benutzer.setPasswort(Encryptor.encrypt(txtPassword.getText()));
			
			if (benutzerService.changeDefaultPassword(benutzer)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Passwort Änderung");
				alert.setHeaderText(null);
				alert.setContentText("Ihr Passwort wurde erfolgreich geändert.");
				alert.showAndWait();
				if (CurrentUser.isSM) {
					this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Project.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonSave.getScene().getWindow();
					stage.setScene(scene);
				} else {
					this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/ProjectLogin.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonSave.getScene().getWindow();
					stage.setScene(scene);
				}
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
	
	@FXML
	private void handleButtonSave(ActionEvent event) throws Exception {
		saveChangedPW();
	}
	
	private Boolean passwordCheck() {
		Boolean check = false;
		if (txtPassword.getText().isEmpty() || txtPassword2.getText().isEmpty()) {
			txtError.setText("Bitte füllen Sie beide Felder mit einem identischem neuem Passwort.");
			txtPassword.setStyle("-fx-border-color:#FF0000;");
			txtPassword2.setStyle("-fx-border-color:#FF0000;");
			if (!(txtError.isVisible())) {
				txtError.setVisible(true);
			}
		} else if (!(txtPassword.getText().equals(txtPassword2.getText()))) {
			txtError.setText("Die von Ihnen eingegebenen Passwörter sind nicht identisch");
			txtPassword.setStyle("-fx-border-color:#FF0000;");
			txtPassword2.setStyle("-fx-border-color:#FF0000;");
			if (!(txtError.isVisible())) {
				txtError.setVisible(true);
			}
		} else if (txtPassword.getText().equals("12345678")) {
			txtError.setText(
					"Das von Ihnen eingegebene Passwort ist identisch mit dem Default-Kennwort, welches unzulässig ist.");
			txtPassword.setStyle("-fx-border-color:#FF0000;");
			txtPassword2.setStyle("-fx-border-color:#FF0000;");
			if (!(txtError.isVisible())) {
				txtError.setVisible(true);
			}
		} else if (txtPassword.getText().length() < 8) {
			txtError.setText("Das von Ihnen eingegebene Passwort ist kleiner als die Länge 8, welches unzulässig ist.");
			txtPassword.setStyle("-fx-border-color:#FF0000;");
			txtPassword2.setStyle("-fx-border-color:#FF0000;");
			if (!(txtError.isVisible())) {
				txtError.setVisible(true);
			}
			
		} else {
			if ((txtError.isVisible())) {
				txtError.setVisible(false);
				txtPassword.setStyle(null);
				txtPassword2.setStyle(null);
			}
			check = true;
		}
		return check;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		benutzerService = StartwindowController.getBenutzerService();
		txtName.setText(CurrentUser.prename + " " + CurrentUser.lastname + ",");
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				txtPassword.requestFocus();
			}
		});
		
	}
	
}
