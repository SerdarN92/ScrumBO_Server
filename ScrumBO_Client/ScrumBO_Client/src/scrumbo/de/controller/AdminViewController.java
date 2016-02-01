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
import scrumbo.de.entity.CurrentUser;

public class AdminViewController implements Initializable {
	
	Parent				root;
	Scene				scene;
	@FXML
	private TextField	txtConfigField;
	@FXML
	private TextField	hostField;
	@FXML
	private TextField	portField;
	@FXML
	private Text		txtSuccess;
	@FXML
	private Button		buttonSave;
	@FXML
	private Button		buttonBack;
	@FXML
	private Button		buttonAddSM;
	@FXML
	private Button		buttonDeleteUser;
	@FXML
	private Button		buttonDeleteProject;
	@FXML
	private Text		txtFail;
						
	@FXML
	private void handleBackButton(ActionEvent event) throws Exception {
		CurrentUser.userId = -1;
		CurrentUser.prename = null;
		CurrentUser.lastname = null;
		CurrentUser.email = null;
		CurrentUser.password = null;
		CurrentUser.roleId = -1;
		CurrentUser.role = null;
		CurrentUser.projects = null;
		CurrentUser.isSM = false;
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonBack.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleButtonDeleteUser(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/UserDelete.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.show();
			stage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleButtonDeleteProject(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/ProjektLoeschen.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.show();
			stage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			stage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleSaveButton(ActionEvent event) throws Exception {
		if (txtConfigField.getText().isEmpty()) {
			txtFail.setVisible(true);
			txtConfigField.setStyle("-fx-border-color:#FF0000;");
		} else {
			txtFail.setVisible(false);
			txtConfigField.setStyle(null);
			String changedConfigurationName = txtConfigField.getText();
			String changedHost = hostField.getText();
			String changedPort = portField.getText();
			ScrumBOClient.setDatabaseconfigfile(changedConfigurationName, changedHost, changedPort);
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
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtConfigField.setText(ScrumBOClient.getDatabaseconfigfile());
		hostField.setText(ScrumBOClient.getHost());
		portField.setText(ScrumBOClient.getPort());
		
		buttonSave.setDisable(true);
		
		txtConfigField.textProperty().addListener((observable, oldValue, newValue) -> {
			buttonSave.setDisable(false);
		});
		hostField.textProperty().addListener((observable, oldValue, newValue) -> {
			buttonSave.setDisable(false);
		});
		portField.textProperty().addListener((observable, oldValue, newValue) -> {
			buttonSave.setDisable(false);
		});
	}
	
}
