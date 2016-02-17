package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.json.JSONException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.entity.CurrentUserStory;
import scrumbo.de.entity.DefinitionOfDone;
import scrumbo.de.entity.UserStory;
import scrumbo.de.service.DefinitionOfDoneService;

public class DefinitionOfDoneCreateController implements Initializable {
	
	Parent					root;
	Scene					scene;
	DefinitionOfDoneService	definitionofdoneService	= null;
													
	@FXML
	private TextField		kriterium;
	@FXML
	private Button			buttonAbbort;
	@FXML
	private Button			buttonAdd;
	@FXML
	private Text			txtKriterium;
							
	@FXML
	private void handleKeyPressed(KeyEvent event) throws JSONException, IOException, Exception {
		if (event.getCode().equals(KeyCode.ENTER))
			createDod();
	}
	
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		if (!kriterium.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Definition of Done erstellen");
			alert.setHeaderText(null);
			alert.setContentText("Wollen Sie fortfahren ohne zu speichern?");
			
			ButtonType buttonTypeOne = new ButtonType("Ja");
			ButtonType buttonTypeTwo = new ButtonType("Nein");
			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeOne) {
				alert.close();
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} else {
				alert.close();
			}
		} else {
			Stage stage = (Stage) buttonAbbort.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
	}
	
	@FXML
	private void handleButtonAdd(ActionEvent event) throws Exception {
		createDod();
	}
	
	private void createDod() {
		if (checkKriterium()) {
			DefinitionOfDone definitionOfDone = new DefinitionOfDone();
			definitionOfDone.setKriterium(kriterium.getText());
			definitionOfDone.setStatus(false);
			UserStory userstory = new UserStory();
			userstory.setId(CurrentUserStory.userstoryID);
			definitionOfDone.setUserstory(userstory);
			
			if (definitionofdoneService.createDefinitionOfDone(definitionOfDone)) {
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} else {
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.close();
			}
			
		}
	}
	
	private Boolean checkKriterium() {
		if (kriterium.getText().isEmpty()) {
			txtKriterium.setText("Bitte ein Kriterium eingeben");
			return false;
		} else {
			txtKriterium.setVisible(false);
			return true;
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		definitionofdoneService = StartwindowController.getDefinitionofdoneService();
	}
	
}
