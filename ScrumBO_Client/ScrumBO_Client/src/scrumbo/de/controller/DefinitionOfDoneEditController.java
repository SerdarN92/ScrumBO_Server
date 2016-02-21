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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.entity.DefinitionOfDone;
import scrumbo.de.service.DefinitionOfDoneService;

public class DefinitionOfDoneEditController implements Initializable {
	
	Parent						root;
	Scene						scene;
	DefinitionOfDoneService		definitionofdoneService	= null;
	@FXML
	private TextField			kriterium;
	@FXML
	private RadioButton			erfuellt;
	@FXML
	private RadioButton			nichtErfuellt;
	@FXML
	private Button				buttonDelete;
	@FXML
	private Button				buttonAbbort;
	@FXML
	private Button				buttonSave;
	@FXML
	private Text				txtKriterium;
	private DefinitionOfDone	data					= null;
	private final ToggleGroup	group					= new ToggleGroup();
														
	@FXML
	private void handleKeyPressed(KeyEvent event) throws JSONException, IOException, Exception {
		if (event.getCode().equals(KeyCode.ENTER))
			updateDod();
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
	
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		if (!data.getKriterium().equals(kriterium.getText()) || (!data.isStatus() && erfuellt.isSelected())
				|| (data.isStatus() && nichtErfuellt.isSelected())) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Definition of Done bearbeiten");
			alert.setHeaderText(null);
			alert.setContentText("Daten wurden verändert. Wollen Sie fortfahren ohne diese zu speichern?");
			
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
	private void handleButtonDelete(ActionEvent event) throws Exception {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Definition of Done löschen");
		alert.setHeaderText(null);
		alert.setContentText("Wollen Sie diese Definition of Done wirklich löschen?");
		
		ButtonType buttonTypeOne = new ButtonType("Ja");
		ButtonType buttonTypeTwo = new ButtonType("Nein");
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			DefinitionOfDone definitionOfDone = data;
			if (definitionofdoneService.deleteDefinitionOfDone(definitionOfDone)) {
				Stage stage = (Stage) buttonDelete.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} else {
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.close();
			}
		} else {
			alert.close();
		}
		
	}
	
	@FXML
	private void handleButtonSave(ActionEvent event) throws Exception {
		updateDod();
	}
	
	private void updateDod() {
		if (checkKriterium()) {
			DefinitionOfDone definitionOfDone = data;
			definitionOfDone.setKriterium(kriterium.getText());
			if (erfuellt.isSelected()) {
				definitionOfDone.setStatus(true);
			}
			if (nichtErfuellt.isSelected()) {
				definitionOfDone.setStatus(false);
			}
			
			if (definitionofdoneService.updateDefinitionOfDone(definitionOfDone)) {
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} else {
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			}
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		definitionofdoneService = StartwindowController.getDefinitionofdoneService();
		data = DefinitionOfDoneController.rowData;
		kriterium.setText(data.getKriterium());
		if (data.isStatus()) {
			erfuellt.setSelected(true);
			nichtErfuellt.setSelected(false);
		} else {
			erfuellt.setSelected(false);
			nichtErfuellt.setSelected(true);
		}
		
		erfuellt.setToggleGroup(group);
		nichtErfuellt.setToggleGroup(group);
	}
	
}
