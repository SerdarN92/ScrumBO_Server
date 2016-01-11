package scrumbo.de.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import scrumbo.de.entity.Project;
import scrumbo.de.service.ScrumprojektService;

public class ProjektLoeschenController implements Initializable {
	
	Parent							root;
	Scene							scene;
	ScrumprojektService				scrumprojektService	= null;
	@FXML
	private ComboBox<Project>	comboBoxProjekte	= new ComboBox<>();
	@FXML
	private Button					buttonAbbort;
	@FXML
	private Button					buttonDelete;
	private List<Project>		scrumprojectList	= new LinkedList<Project>();
	private Project			currentScrumproject	= new Project();
														
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		Stage stage = (Stage) buttonDelete.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleButtonDelete(ActionEvent event) throws Exception {
		if (currentScrumproject.getId() != null) {
			try {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Projekt löschen");
				alert.setHeaderText(null);
				alert.setContentText("Wollen Sie dieses Projekt wirklich löschen?");
				
				ButtonType buttonTypeOne = new ButtonType("Ja");
				ButtonType buttonTypeTwo = new ButtonType("Nein");
				alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
				
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeOne) {
					alert.close();
					if (scrumprojektService.deleteProject(currentScrumproject)) {
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("Projekt löschen");
						alert2.setHeaderText(null);
						alert2.setContentText("Projekt wurde erfolgreich gelöscht!");
						alert2.showAndWait();
						Stage stage = (Stage) buttonDelete.getScene().getWindow();
						stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
					} else {
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("Projekt löschen");
						alert2.setHeaderText(null);
						alert2.setContentText("Fehler! Projekt wurde nicht gelöscht!");
						alert2.showAndWait();
						Stage stage = (Stage) buttonAbbort.getScene().getWindow();
						stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
					}
				} else {
					alert.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			Stage stage = (Stage) buttonDelete.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		scrumprojektService = StartwindowController.getScrumprojektService();
		scrumprojectList = scrumprojektService.ladeProjekte();
		
		if (scrumprojectList.isEmpty()) {
			buttonDelete.setDisable(true);
		} else {
			buttonDelete.setDisable(false);
		}
		
		ObservableList<Project> options = FXCollections.observableArrayList();
		for (int i = 0; i < scrumprojectList.size(); i++) {
			options.add(scrumprojectList.get(i));
		}
		comboBoxProjekte.getItems().addAll(options);
		comboBoxProjekte.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				comboBoxProjekte.requestFocus();
			}
		});
		
		comboBoxProjekte.setConverter(new StringConverter<Project>() {
			@Override
			public String toString(Project u) {
				return u.getProjektname();
			}
			
			@Override
			public Project fromString(String string) {
				throw new UnsupportedOperationException();
			}
		});
		
		comboBoxProjekte.valueProperty()
				.addListener((ChangeListener<Project>) (ov, t, t1) -> currentScrumproject = t1);
				
	}
	
}
