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
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scrumbo.de.entity.Benutzer;
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.service.BenutzerService;

public class UserChangePasswordController implements Initializable {
	
	Parent					root;
	Scene					scene;
	BenutzerService			benutzerService	= null;
	@FXML
	private Button			buttonSave;
	@FXML
	private PasswordField	txtPassword;
	@FXML
	private PasswordField	txtPassword2;
	@FXML
	private Text			txtName;
	@FXML
	private Text			txtPasswortValid;
							
	@FXML
	private void handleButtonSave(ActionEvent event) throws Exception {
		if (passwordCheck()) {
			Benutzer benutzer = new Benutzer();
			benutzer.setId(CurrentBenutzer.benutzerID);
			benutzer.setVorname(CurrentBenutzer.vorname);
			benutzer.setNachname(CurrentBenutzer.nachname);
			benutzer.setEmail(CurrentBenutzer.email);
			benutzer.setPasswort(txtPassword.getText());
			
			if (benutzerService.changeDefaultPassword(benutzer)) {
				if (CurrentBenutzer.isSM) {
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
			}
		}
	}
	
	private Boolean passwordCheck() {
		Boolean check = false;
		if (txtPassword.getText().isEmpty() || txtPassword2.getText().isEmpty()) {
			txtPasswortValid.setText("Bitte füllen Sie beide Felder mit einem identischem neuem Passwort.");
			if (!(txtPasswortValid.isVisible()))
				txtPasswortValid.setVisible(true);
		} else if (!(txtPassword.getText().equals(txtPassword2.getText()))) {
			txtPasswortValid.setText("Die von Ihnen eingegebenen Passwörter sind nicht identisch");
			if (!(txtPasswortValid.isVisible()))
				txtPasswortValid.setVisible(true);
		} else if (txtPassword.getText().equals("12345678")) {
			txtPasswortValid.setText(
					"Das von Ihnen eingegebene Passwort ist identisch mit dem Default-Kennwort, welches unzulässig ist.");
			if (!(txtPasswortValid.isVisible()))
				txtPasswortValid.setVisible(true);
		} else if (txtPassword.getText().length() < 8) {
			txtPasswortValid
					.setText("Das von Ihnen eingegebene Passwort ist kleiner als die Länge 8, welches unzulässig ist.");
			if (!(txtPasswortValid.isVisible()))
				txtPasswortValid.setVisible(true);
				
		} else {
			if ((txtPasswortValid.isVisible()))
				txtPasswortValid.setVisible(false);
			check = true;
		}
		return check;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		benutzerService = StartwindowController.getBenutzerService();
		txtName.setText(CurrentBenutzer.vorname + " " + CurrentBenutzer.nachname + ",");
		
	}
	
}
