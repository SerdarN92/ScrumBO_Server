/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.common.LetterTextField;
import scrumbo.de.entity.User;
import scrumbo.de.entity.Role;
import scrumbo.de.service.BenutzerService;
import scrumbo.de.service.BenutzerrolleService;

/**
 * FXML Controller Klasse für die Erstellung eines Benutzers
 *
 * @author Serdar
 */
public class UserCreateScrumMasterController implements Initializable {
	
	Parent					root;
	Scene					scene;
	BenutzerService			benutzerService			= null;
	BenutzerrolleService	benutzerrolleService	= null;
	List<Role>		liste					= null;
	@FXML
	private LetterTextField	txtFieldPrename;
	@FXML
	private LetterTextField	txtFieldLastname;
	@FXML
	private TextField		txtFieldEmail;
	@FXML
	private Button			buttonAbbort;
	@FXML
	private Button			buttonCreateSM;
	@FXML
	private Text			txtPrename;
	@FXML
	private Text			txtLastname;
	@FXML
	private Text			txtEmail;
							
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws IOException {
		Stage stage = (Stage) buttonAbbort.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleButtonCreateSM(ActionEvent event) throws Exception {
		if (checkPreName() && checkLastName() && checkEmail()) {
			if (benutzerService.checkIfEmailExists(txtFieldEmail.getText())) {
				txtEmail.setText("E-Mail Adresse bereits vorhanden.");
				txtEmail.setVisible(true);
				txtFieldEmail.setStyle("-fx-border-color:#FF0000;");
			} else {
				txtFieldEmail.setStyle(null);
				txtEmail.setVisible(false);
				
				User benutzer = new User();
				benutzer.setVorname(txtFieldPrename.getText());
				benutzer.setNachname(txtFieldLastname.getText());
				benutzer.setEmail(txtFieldEmail.getText());
				benutzer.setPasswort("12345678");
				
				if (benutzerService.createScrumMaster(benutzer)) {
					this.root = FXMLLoader
							.load(getClass().getResource("/scrumbo/de/gui/UserCreateScrumMasterSuccess.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonAbbort.getScene().getWindow();
					stage.setScene(scene);
				} else {
					this.root = FXMLLoader
							.load(getClass().getResource("/scrumbo/de/gui/UserCreateScrumMasterFail.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonAbbort.getScene().getWindow();
					stage.setScene(scene);
				}
			}
		}
	}
	
	private Boolean checkEmail() {
		if (txtFieldEmail.getText().contains("@")) {
			txtEmail.setText(null);
			txtFieldEmail.setStyle(null);
			return true;
		} else {
			txtEmail.setText("Ungültige E-Mail");
			txtFieldEmail.setStyle("-fx-border-color:#FF0000;");
			return false;
		}
	}
	
	private Boolean checkPreName() {
		if (!(txtFieldPrename.getText().isEmpty())) {
			txtPrename.setText(null);
			txtFieldPrename.setStyle(null);
			return true;
		} else {
			txtPrename.setText("Bitte einen Vornamen eingeben");
			txtFieldPrename.setStyle("-fx-border-color:#FF0000;");
			return false;
		}
	}
	
	private Boolean checkLastName() {
		if (!(txtFieldLastname.getText().isEmpty())) {
			txtLastname.setText(null);
			txtFieldLastname.setStyle(null);
			return true;
		} else {
			txtLastname.setText("Bitte einen Nachnamen eingeben");
			txtFieldLastname.setStyle("-fx-border-color:#FF0000;");
			return false;
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		benutzerService = StartwindowController.getBenutzerService();
		benutzerrolleService = StartwindowController.getBenutzerrolleService();
	}
	
}
