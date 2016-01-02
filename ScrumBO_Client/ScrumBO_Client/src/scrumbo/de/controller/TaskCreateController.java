package scrumbo.de.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import scrumbo.de.entity.Benutzer;
import scrumbo.de.entity.UserStoryTask;
import scrumbo.de.service.BenutzerService;

public class TaskCreateController implements Initializable {
	
	Parent						root;
	Scene						scene;
	BenutzerService				benutzerService		= null;
	@FXML
	private TextField			taskbeschreibung;
	@FXML
	private TextField			aufwandinstunden;
	@FXML
	private ComboBox<Benutzer>	comboBoxBenutzer	= new ComboBox<>();
	@FXML
	private Button				buttonAbbort;
	@FXML
	private Button				buttonAdd;
								
	private List<Benutzer>		benutzerList		= new LinkedList<Benutzer>();
	private Benutzer			currentBenutzer		= new Benutzer();
	private UserStoryTask		task				= new UserStoryTask();
													
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		SprintBacklogAddUserStoryController.addedUserStoryTask = null;
		Stage stage = (Stage) buttonAdd.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleButtonAdd(ActionEvent event) throws Exception {
		task.setBeschreibung(taskbeschreibung.getText());
		task.setAufwandinstunden(Integer.parseInt(aufwandinstunden.getText()));
		task.setBenutzer(currentBenutzer);
		SprintBacklogAddUserStoryController.addedUserStoryTask = task;
		Stage stage = (Stage) buttonAdd.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		benutzerService = StartwindowController.getBenutzerService();
		benutzerList = benutzerService.ladeBenutzerVomAktuellenProjekt();
		ObservableList<Benutzer> options = FXCollections.observableArrayList();
		for (int i = 0; i < benutzerList.size(); i++) {
			options.add(benutzerList.get(i));
		}
		comboBoxBenutzer.getItems().addAll(options);
		comboBoxBenutzer.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				comboBoxBenutzer.requestFocus();
			}
		});
		
		comboBoxBenutzer.setConverter(new StringConverter<Benutzer>() {
			@Override
			public String toString(Benutzer u) {
				return u.getVorname() + " " + u.getNachname();
			}
			
			@Override
			public Benutzer fromString(String string) {
				throw new UnsupportedOperationException();
			}
		});
		
		comboBoxBenutzer.valueProperty().addListener((ChangeListener<Benutzer>) (ov, t, t1) -> currentBenutzer = t1);
		
	}
	
}
