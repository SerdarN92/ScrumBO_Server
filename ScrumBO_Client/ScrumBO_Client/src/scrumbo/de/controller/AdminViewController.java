package scrumbo.de.controller;

import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Timer;

import javafx.event.EventHandler;
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
import javafx.stage.WindowEvent;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.service.ProjectService;
import scrumbo.de.service.UserService;

public class AdminViewController implements Initializable {
	
	private Parent			root;
	private Scene			scene;
	private ProjectService	scrumprojektService;
	private UserService		benutzerService;
							
	@FXML
	private TextField		txtConfigField;
	@FXML
	private TextField		hostField;
	@FXML
	private TextField		portField;
	@FXML
	private Text			txtSuccess;
	@FXML
	private Button			buttonSave;
	@FXML
	private Button			buttonBack;
	@FXML
	private Button			buttonAddSM;
	@FXML
	private Button			buttonDeleteUser;
	@FXML
	private Button			buttonDeleteProject;
	@FXML
	private Text			txtFail;
							
	@FXML
	private void handleBackButton() throws Exception {
		try {
			StartwindowController.logout();
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonBack.getScene().getWindow();
			stage.setScene(scene);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleButtonDeleteUser() throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/UserDelete.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.show();
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						checkProjects();
						checkUsers();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleButtonDeleteProject() throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/ProjectDelete.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.show();
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						checkProjects();
						checkUsers();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleAddSMButton() throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/scrumbo/de/gui/UserCreateScrumMaster.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.show();
			stage.setResizable(false);
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						checkProjects();
						checkUsers();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		checkProjects();
		checkUsers();
	}
	
	@FXML
	private void handleSaveButton() throws Exception {
		if (txtConfigField.getText().isEmpty()) {
			txtFail.setVisible(true);
			txtConfigField.setStyle("-fx-border-color:#FF0000;");
		} else {
			txtFail.setVisible(false);
			txtConfigField.setStyle(null);
			ScrumBOClient.setDatabaseconfigfile(txtConfigField.getText(), hostField.getText(), portField.getText());
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
		checkProjects();
		checkUsers();
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtConfigField.setText(ScrumBOClient.getDatabaseconfigfile());
		hostField.setText(ScrumBOClient.getHost());
		portField.setText(ScrumBOClient.getPort());
		buttonSave.setDisable(true);
		buttonDeleteProject.setDisable(true);
		buttonDeleteUser.setDisable(true);
		txtConfigField.textProperty().addListener((observable, oldValue, newValue) -> {
			buttonSave.setDisable(false);
		});
		hostField.textProperty().addListener((observable, oldValue, newValue) -> {
			buttonSave.setDisable(false);
		});
		portField.textProperty().addListener((observable, oldValue, newValue) -> {
			buttonSave.setDisable(false);
		});
		
		checkProjects();
		checkUsers();
		
	}
	
	private void checkProjects() {
		scrumprojektService = StartwindowController.getScrumprojektService();
		if (scrumprojektService.loadProjects().size() > 0) {
			buttonDeleteProject.setDisable(false);
		} else {
			buttonDeleteProject.setDisable(true);
		}
	}
	
	private void checkUsers() {
		benutzerService = StartwindowController.getBenutzerService();
		if (benutzerService.ladeBenutzerOhneAdmin().size() > 0) {
			buttonDeleteUser.setDisable(false);
		} else {
			buttonDeleteUser.setDisable(true);
		}
	}
	
}
