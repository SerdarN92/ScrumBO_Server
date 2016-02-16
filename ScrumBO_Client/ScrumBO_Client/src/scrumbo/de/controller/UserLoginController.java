/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scrumbo.de.common.Encryptor;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.User;
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
	User					benutzer				= null;
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
	private void handleKeyPressed(KeyEvent event) throws JSONException, IOException, Exception {
		if (event.getCode().equals(KeyCode.ENTER))
			login();
	}
	
	private void login() throws JSONException, IOException, Exception {
		if (checkEmail() && checkPassword()) {
			if (!benutzerService.checkIfEmailExists(txtFieldEmail.getText())) {
				emailValidFail.setText("Benutzer mit dieser E-Mail existiert nicht.");
				emailValidFail.setVisible(true);
				txtFieldEmail.setStyle("-fx-border-color:#FF0000;");
			} else {
				txtFieldEmail.setStyle(null);
				emailValidFail.setVisible(false);
				benutzer = benutzerService.getBenutzer();
				CurrentUser.userId = benutzer.getId();
				CurrentUser.prename = benutzer.getVorname();
				CurrentUser.lastname = benutzer.getNachname();
				CurrentUser.email = benutzer.getEmail();
				CurrentUser.password = benutzer.getPasswort();
				benutzerrolleService.checkRole(CurrentUser.email);
				if (pswtFieldPassword.getText().equals(Encryptor.decrypt(CurrentUser.password))) {
					pswtFieldPassword.setStyle(null);
					emailValidFail.setVisible(false);
					passwordValidFail.setVisible(false);
					if (Encryptor.decrypt(CurrentUser.password).equals("12345678")) {
						this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/UserChangePassword.fxml"));
						this.scene = new Scene(root);
						Stage stage = (Stage) buttonLoginUser.getScene().getWindow();
						stage.setScene(scene);
					} else {
						if (CurrentUser.isSM) {
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
	
	@FXML
	private void handleLoginButton(ActionEvent event) throws Exception {
		login();
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
