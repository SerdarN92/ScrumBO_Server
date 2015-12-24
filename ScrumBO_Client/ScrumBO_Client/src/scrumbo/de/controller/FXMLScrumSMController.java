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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.entity.CurrentScrumprojekt;

public class FXMLScrumSMController implements Initializable {
	
	Parent						root;
	Scene						scene;
	FXMLSprintBacklogController	controller	= null;
	@FXML
	private Text				vorname;
	@FXML
	private Text				nachname;
	@FXML
	private Text				benutzerrolle;
	@FXML
	private Text				projektname;
	@FXML
	private Button				buttonLogout;
	@FXML
	private Button				buttonAddProjectUser;
								
	@FXML
	private void handleButtonAddProjectUser(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/scrumbo/de/gui/FXMLUserCreateForProject.fxml"));
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
	private void handleButtonLogout(ActionEvent event) throws Exception {
		CurrentBenutzer.benutzerID = -1;
		CurrentBenutzer.vorname = null;
		CurrentBenutzer.nachname = null;
		CurrentBenutzer.email = null;
		CurrentBenutzer.passwort = null;
		CurrentBenutzer.benutzerrollenID = -1;
		CurrentBenutzer.benutzerrolle = null;
		CurrentBenutzer.projekte = null;
		CurrentScrumprojekt.scrumprojektID = -1;
		CurrentScrumprojekt.projektname = null;
		CurrentBenutzer.isSM = false;
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLStartfenster.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		
	}
	
	@FXML
	private void handleImageProductbacklog(MouseEvent event) throws Exception {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLProductBacklog.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleImageSprintbacklog(MouseEvent event) throws Exception {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLSprintBacklog2.fxml"));
		this.scene = new Scene(root);
		// FXMLLoader fxmlloader = new FXMLLoader();
		// URL location =
		// getClass().getResource("/scrumbo/de/gui/FXMLSprintBacklog2.fxml");
		// fxmlloader.setLocation(location);
		// AnchorPane pane = fxmlloader.load();
		// controller = (FXMLSprintBacklogController)
		// fxmlloader.getController();
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		
	}
	
	@FXML
	private void handleImageImpedimentbacklog(MouseEvent event) throws Exception {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLImpedimentBacklog.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		vorname.setText(CurrentBenutzer.vorname);
		nachname.setText(CurrentBenutzer.nachname);
		benutzerrolle.setText(CurrentBenutzer.benutzerrolle);
		projektname.setText(CurrentScrumprojekt.projektname);
		
		if (!CurrentBenutzer.isSM)
			buttonAddProjectUser.setVisible(false);
	}
	
}
