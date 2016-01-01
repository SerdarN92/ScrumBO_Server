package scrumbo.de.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
	private void handleButtonDelete(ActionEvent event) throws Exception {
		DefinitionOfDone definitionOfDone = data;
		if (definitionofdoneService.deleteDefinitionOfDone(definitionOfDone)) {
			Stage stage = (Stage) buttonDelete.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		} else {
			Stage stage = (Stage) buttonAbbort.getScene().getWindow();
			stage.close();
		}
	}
	
	@FXML
	private void handleButtonSave(ActionEvent event) throws Exception {
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
				System.out.println("True");
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} else {
				System.out.println("False");
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
