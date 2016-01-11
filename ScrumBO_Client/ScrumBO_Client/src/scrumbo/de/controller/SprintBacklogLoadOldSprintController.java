package scrumbo.de.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.entity.CurrentSprint;

public class SprintBacklogLoadOldSprintController implements Initializable {
	
	Parent						root;
	Scene						scene;
	@FXML
	private Button				buttonAbbort;
	@FXML
	private Button				buttonLoad;
	@FXML
	private ComboBox<Integer>	comboBoxSprintNumber	= new ComboBox<>();
														
	private Integer				anzahlSprints;
								
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		Stage stage = (Stage) buttonAbbort.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleButtonLoad(ActionEvent event) throws Exception {
		CurrentSprint.sprintnumber = comboBoxSprintNumber.getValue();
		Stage stage = (Stage) buttonLoad.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		anzahlSprints = SprintBacklogController.getAnzahlSprints();
		
		ObservableList<Integer> options = FXCollections.observableArrayList();
		for (int i = 0; i < anzahlSprints; i++) {
			options.add(i + 1);
		}
		comboBoxSprintNumber.setValue(CurrentSprint.sprintnumber);
		comboBoxSprintNumber.getItems().addAll(options);
		comboBoxSprintNumber.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				comboBoxSprintNumber.requestFocus();
			}
		});
	}
	
}
