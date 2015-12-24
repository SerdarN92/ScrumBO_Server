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

public class FXMLTaskEditController implements Initializable {
	
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
	private void handleButtonAdd(ActionEvent event) throws Exception {
		FXMLSprintBacklogEditUserStoryController.selectedUserStoryTask.setBeschreibung(taskbeschreibung.getText());
		FXMLSprintBacklogEditUserStoryController.selectedUserStoryTask
				.setAufwandinstunden(Integer.parseInt(aufwandinstunden.getText()));
		FXMLSprintBacklogEditUserStoryController.selectedUserStoryTask.setBenutzer(currentBenutzer);
		Stage stage = (Stage) buttonAdd.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		benutzerService = FXMLStartController.getBenutzerService();
		benutzerList = benutzerService.ladeBenutzerVomAktuellenProjekt();
		initComboBox();
		
		taskbeschreibung.setText(FXMLSprintBacklogEditUserStoryController.selectedUserStoryTask.getBeschreibung());
		aufwandinstunden.setText(
				FXMLSprintBacklogEditUserStoryController.selectedUserStoryTask.getAufwandinstunden().toString());
		Integer platz = 0;
		for (int i = 0; i < benutzerList.size(); i++) {
			if (benutzerList.get(i).getEmail()
					.equals(FXMLSprintBacklogEditUserStoryController.selectedUserStoryTask.getBenutzer().getEmail()))
				platz = i;
		}
		
		benutzerList.set(platz, benutzerList.get(platz));
	}
	
	public void initComboBox() {
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
