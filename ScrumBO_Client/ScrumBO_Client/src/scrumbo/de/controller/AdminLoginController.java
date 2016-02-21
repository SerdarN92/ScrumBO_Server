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
import scrumbo.de.service.UserService;

/**
 * FXML Controller class
 *
 * Controller Klasse für den Admin Login
 *
 * @author Serdar
 */
public class AdminLoginController implements Initializable {
	
	private Parent			root;
	private Scene			scene;
	private UserService		benutzerService;
	private User			benutzer;
							
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
	private void handleBackButton() {
		try {
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonBack.getScene().getWindow();
			stage.setScene(scene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			try {
				handleLogin();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@FXML
	private void handleLoginButton() {
		try {
			handleLogin();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleLogin() throws JSONException, IOException, Exception {
		if (checkEmail() && checkPassword()) {
			if (!benutzerService.checkIfEmailExists(txtFieldEmail.getText())) {
				emailValidFail
						.setText("Benutzer mit dieser E-Mail Adresse existiert nicht! Bitte registrieren Sie sich.");
				emailValidFail.setVisible(true);
				txtFieldEmail.setStyle("-fx-border-color:#FF0000;");
			} else {
				txtFieldEmail.setStyle(null);
				benutzer = benutzerService.getBenutzer();
				if (pswtFieldPassword.getText().equals(Encryptor.decrypt(benutzer.getPasswort()))) {
					pswtFieldPassword.setStyle(null);
					emailValidFail.setVisible(false);
					passwordValidFail.setVisible(false);
					CurrentUser.userId = benutzer.getId();
					CurrentUser.prename = benutzer.getVorname();
					CurrentUser.lastname = benutzer.getNachname();
					CurrentUser.email = benutzer.getEmail();
					CurrentUser.password = benutzer.getPasswort();
					if (benutzerService.checkAdmin()) {
						this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/AdminView.fxml"));
						this.scene = new Scene(root);
						Stage stage = (Stage) buttonLoginUser.getScene().getWindow();
						stage.setScene(scene);
					} else {
						emailValidFail.setText("Benutzer hat keine Admin-Rechte!");
						emailValidFail.setVisible(true);
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
	public void initialize(URL arg0, ResourceBundle arg1) {
		benutzerService = StartwindowController.getBenutzerService();
	}
	
}
