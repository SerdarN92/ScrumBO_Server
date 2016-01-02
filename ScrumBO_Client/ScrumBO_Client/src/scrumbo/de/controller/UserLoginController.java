/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scrumbo.de.entity.Benutzer;
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.service.BenutzerService;
import scrumbo.de.service.BenutzerrolleService;

/**
 * FXML Controller class
 *
 * @author Serdar
 */
public class UserLoginController implements Initializable {
	
	Parent					root;
	Scene					scene;
	Stage					stage;
	BenutzerService			benutzerService			= null;
	Benutzer				benutzer				= null;
	BenutzerrolleService	benutzerrolleService	= null;
													
	@FXML
	private TextField		txtFieldEmail;
	@FXML
	private PasswordField	pswtFieldPassword;
	@FXML
	private Text			emailValidFail;
	@FXML
	private Text			passwordValidFail;
	@FXML
	private Button			buttonBack;
	@FXML
	private Button			buttonLoginUser;
							
	@FXML
	private void handleBackButton(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonBack.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleLoginButton(ActionEvent event) throws Exception {
		if (checkEmail() && checkPassword()) {
			if (!benutzerService.checkIfEmailExists(txtFieldEmail.getText())) {
				emailValidFail.setText("Benutzer mit dieser E-Mail existiert nicht.");
				emailValidFail.setVisible(true);
				txtFieldEmail.setStyle("-fx-border-color:#FF0000;");
			} else {
				txtFieldEmail.setStyle(null);
				emailValidFail.setVisible(false);
				benutzer = benutzerService.getBenutzer();
				CurrentBenutzer.benutzerID = benutzer.getId();
				CurrentBenutzer.vorname = benutzer.getVorname();
				CurrentBenutzer.nachname = benutzer.getNachname();
				CurrentBenutzer.email = benutzer.getEmail();
				CurrentBenutzer.passwort = benutzer.getPasswort();
				benutzerrolleService.checkRole(CurrentBenutzer.email);
				if (pswtFieldPassword.getText().equals(CurrentBenutzer.passwort)) {
					pswtFieldPassword.setStyle(null);
					emailValidFail.setVisible(false);
					passwordValidFail.setVisible(false);
					if (CurrentBenutzer.passwort.equals("12345678")) {
						this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/UserChangePassword.fxml"));
						this.scene = new Scene(root);
						Stage stage = (Stage) buttonLoginUser.getScene().getWindow();
						stage.setScene(scene);
					} else {
						if (CurrentBenutzer.isSM) {
							this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Project.fxml"));
							this.scene = new Scene(root);
							Stage stage = (Stage) buttonLoginUser.getScene().getWindow();
							stage.setScene(scene);
						} else {
							this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/ProjectLogin.fxml"));
							this.scene = new Scene(root);
							Stage stage = (Stage) buttonLoginUser.getScene().getWindow();
							stage.setScene(scene);
						}
						
					}
				} else {
					passwordValidFail.setText("Passwort ist falsch");
					passwordValidFail.setVisible(true);
					pswtFieldPassword.setStyle("-fx-border-color:#FF0000;");
				}
				
			}
		}
	}
	
	private Boolean checkEmail() {
		if (txtFieldEmail.getText().contains("@")) {
			emailValidFail.setText(null);
			txtFieldEmail.setStyle(null);
			return true;
		} else {
			emailValidFail.setText("Ungültige E-Mail");
			txtFieldEmail.setStyle("-fx-border-color:#FF0000;");
			return false;
		}
	}
	
	private Boolean checkPassword() throws Exception {
		if (!(pswtFieldPassword.getText().isEmpty())) {
			passwordValidFail.setText(null);
			pswtFieldPassword.setStyle(null);
			return true;
		} else {
			passwordValidFail.setText("Bitte ein Passwort eingeben");
			pswtFieldPassword.setStyle("-fx-border-color:#FF0000;");
			return false;
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		benutzerService = StartwindowController.getBenutzerService();
		benutzerrolleService = StartwindowController.getBenutzerrolleService();
	}
	
}
