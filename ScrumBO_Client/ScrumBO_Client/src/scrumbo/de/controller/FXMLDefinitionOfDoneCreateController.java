package scrumbo.de.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.entity.CurrentUserStory;
import scrumbo.de.entity.DefinitionOfDone;
import scrumbo.de.entity.UserStory;
import scrumbo.de.service.DefinitionOfDoneService;

public class FXMLDefinitionOfDoneCreateController implements Initializable {
	
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
							
	private Boolean checkKriterium() {
		if (kriterium.getText().isEmpty()) {
			txtKriterium.setText("Bitte ein Kriterium eingeben");
			return false;
		} else {
			txtKriterium.setVisible(false);
			return true;
		}
	}
	
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		Stage stage = (Stage) buttonAbbort.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleButtonAdd(ActionEvent event) throws Exception {
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
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		definitionofdoneService = FXMLStartController.getDefinitionofdoneService();
		
	}
	
}
