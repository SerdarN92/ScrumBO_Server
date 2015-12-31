package scrumbo.de.controller;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Timer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentBenutzer;

public class AdminViewController implements Initializable {
	
	Parent				root;
	Scene				scene;
	@FXML
	private TextField	txtConfigField;
	@FXML
	private Text		txtSuccess;
	@FXML
	private Button		buttonSave;
	@FXML
	private Button		buttonBack;
	@FXML
	private Button		buttonAddSM;
						
	@FXML
	private void handleBackButton(ActionEvent event) throws Exception {
		CurrentBenutzer.benutzerID = -1;
		CurrentBenutzer.vorname = null;
		CurrentBenutzer.nachname = null;
		CurrentBenutzer.email = null;
		CurrentBenutzer.passwort = null;
		CurrentBenutzer.benutzerrollenID = -1;
		CurrentBenutzer.benutzerrolle = null;
		CurrentBenutzer.projekte = null;
		CurrentBenutzer.isSM = false;
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonBack.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleAddSMButton(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/scrumbo/de/gui/UserCreateScrumMaster.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleSaveButton(ActionEvent event) throws Exception {
		String changedConfigurationName = txtConfigField.getText();
		ScrumBOClient.setDatabaseconfigfile(changedConfigurationName);
		txtSuccess.setVisible(true);
		Timer timer = new Timer(2000, new ActionListener() {
			
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				txtSuccess.setVisible(false);
				
			}
			
		});
		timer.setRepeats(false);
		timer.start();
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtConfigField.setText(ScrumBOClient.getDatabaseconfigfile());
	}
	
}