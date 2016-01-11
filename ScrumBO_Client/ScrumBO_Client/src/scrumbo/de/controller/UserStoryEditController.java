package scrumbo.de.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.CurrentUserStory;
import scrumbo.de.entity.UserStory;
import scrumbo.de.service.UserstoryService;

public class UserStoryEditController implements Initializable {
	
	Parent					root;
	Scene					scene;
	UserstoryService		userstoryService	= null;
	private static Integer	id					= -1;
	@FXML
	private TextField		prioritaet;
	@FXML
	private TextField		thema;
	@FXML
	private TextArea		beschreibung;
	@FXML
	private TextArea		akzeptanzkriterien;
	@FXML
	private TextField		aufwandintagen;
	@FXML
	private Button			buttonDelete;
	@FXML
	private Button			buttonAbbort;
	@FXML
	private Button			buttonUpdate;
	@FXML
	private Button			buttonDoD;
	@FXML
	private Text			txtError;
	private UserStory		data				= null;
												
	private Boolean checkPrioritaet() {
		if (prioritaet.getText().isEmpty()) {
			txtError.setText("Bitte eine Priorität eingeben");
			prioritaet.setStyle("-fx-border-color:#FF0000;");
			return false;
		} else {
			txtError.setVisible(false);
			if (isInteger(prioritaet.getText())) {
				txtError.setVisible(false);
				prioritaet.setStyle(null);
				return true;
			} else {
				txtError.setText("Bitte eine ganzzahlige Zahl eingeben.");
				txtError.setVisible(true);
				prioritaet.setStyle("-fx-border-color:#FF0000;");
				return false;
			}
			
		}
	}
	
	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
	
	private Boolean checkThema() {
		if (thema.getText().isEmpty()) {
			txtError.setText("Bitte ein Thema eingeben");
			thema.setStyle("-fx-border-color:#FF0000;");
			txtError.setVisible(true);
			return false;
		} else {
			txtError.setVisible(false);
			thema.setStyle(null);
			return true;
		}
	}
	
	private Boolean checkBeschreibung() {
		if (beschreibung.getText().isEmpty()) {
			txtError.setText("Bitte eine Beschreibung eingeben");
			beschreibung.setStyle("-fx-border-color:#FF0000;");
			txtError.setVisible(true);
			return false;
		} else {
			txtError.setVisible(false);
			beschreibung.setStyle(null);
			return true;
		}
	}
	
	private Boolean checkAkzeptanzkriterien() {
		if (akzeptanzkriterien.getText().isEmpty()) {
			txtError.setText("Bitte ein Akzeptanzkriterium eingeben");
			akzeptanzkriterien.setStyle("-fx-border-color:#FF0000;");
			txtError.setVisible(true);
			return false;
		} else {
			txtError.setVisible(false);
			akzeptanzkriterien.setStyle(null);
			return true;
		}
	}
	
	private Boolean checkAufwandInTagen() {
		if (aufwandintagen.getText().isEmpty()) {
			txtError.setText("Bitte einen Aufwand(in Tagen) eingeben");
			aufwandintagen.setStyle("-fx-border-color:#FF0000;");
			txtError.setVisible(true);
			return false;
		} else {
			txtError.setVisible(false);
			aufwandintagen.setStyle(null);
			if (isInteger(aufwandintagen.getText())) {
				aufwandintagen.setStyle(null);
				txtError.setVisible(false);
				return true;
			} else {
				txtError.setText("Bitte eine ganzzahlige Zahl eingeben.");
				aufwandintagen.setStyle("-fx-border-color:#FF0000;");
				txtError.setVisible(true);
				return false;
			}
			
		}
	}
	
	@FXML
	private void handleButtonDoD(ActionEvent event) throws Exception {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/DefinitionOfDone.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonDoD.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleButtonDelete(ActionEvent event) throws Exception {
		List<UserStory> userstoryList = CurrentProject.productbacklog.getUserstory();
		UserStory userstory = new UserStory();
		userstory.setId(data.getId());
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Userstory bearbeiten");
		alert.setHeaderText(null);
		alert.setContentText("Wollen Sie diese Userstory wirklich löschen?");
		
		ButtonType buttonTypeOne = new ButtonType("Ja");
		ButtonType buttonTypeTwo = new ButtonType("Nein");
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			alert.close();
			if (userstoryService.deleteUserStory(userstory)) {
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("Userstory löschen");
				alert2.setHeaderText(null);
				alert2.setContentText("Userstory wurde erfolgreich gelöscht!");
				alert2.showAndWait();
				Stage stage = (Stage) buttonDelete.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} else {
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("Userstory löschen");
				alert2.setHeaderText(null);
				alert2.setContentText("Fehler! Userstory wurde nicht gelöscht!");
				alert2.showAndWait();
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			}
		} else {
			alert.close();
		}
	}
	
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Userstory bearbeiten");
		alert.setHeaderText(null);
		alert.setContentText("Wollen Sie die Bearbeitung der Userstory wirklich abbrechen?");
		
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
	}
	
	@FXML
	private void handleButtonUpdate(ActionEvent event) throws Exception {
		if (checkPrioritaet() && checkThema() && checkBeschreibung() && checkAkzeptanzkriterien()
				&& checkAufwandInTagen()) {
			if (!(data.getPriority().equals(Integer.parseInt(prioritaet.getText())))
					|| !(data.getTheme().equals(thema.getText()))
					|| !(data.getDescription().equals(beschreibung.getText()))
					|| !(data.getAcceptanceCriteria().equals(akzeptanzkriterien.getText()))
					|| !(data.getEffortInDays().equals(Integer.parseInt(aufwandintagen.getText())))) {
				List<UserStory> userstoryList = CurrentProject.productbacklog.getUserstory();
				UserStory userstory = null;
				for (int i = 0; i < userstoryList.size(); i++) {
					if (userstoryList.get(i).getId().equals(data.getId())) {
						this.id = data.getId();
						userstoryList.get(i).setPriority(Integer.parseInt(prioritaet.getText()));
						userstoryList.get(i).setTheme(thema.getText());
						userstoryList.get(i).setDescription(beschreibung.getText());
						userstoryList.get(i).setAcceptanceCriteria(akzeptanzkriterien.getText());
						userstoryList.get(i).setEffortInDays(Integer.parseInt(aufwandintagen.getText()));
						userstory = userstoryList.get(i);
					}
				}
				
				if (userstoryService.updateUserStory(userstory)) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Userstory bearbeiten");
					alert.setHeaderText(null);
					alert.setContentText("Userstory wurde erfolgreich bearbeitet!");
					alert.showAndWait();
					Stage stage = (Stage) buttonAbbort.getScene().getWindow();
					stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Userstory bearbeiten");
					alert.setHeaderText(null);
					alert.setContentText("Fehler! Userstory wurde nicht erfolgreich bearbeitet!");
					alert.showAndWait();
					Stage stage = (Stage) buttonAbbort.getScene().getWindow();
					stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
				}
			} else {
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			}
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userstoryService = StartwindowController.getUserstoryService();
		data = ProductBacklogController.rowData;
		id = data.getId();
		CurrentUserStory.userstoryID = data.getId();
		prioritaet.setText(data.getPriority().toString());
		thema.setText(data.getTheme());
		beschreibung.setText(data.getDescription());
		akzeptanzkriterien.setText(data.getAcceptanceCriteria());
		aufwandintagen.setText(data.getEffortInDays().toString());
		CurrentUserStory.status = data.getStatus();
		
		if (data.getStatus() == 2) {
			buttonDelete.setDisable(true);
			buttonUpdate.setDisable(true);
			buttonAbbort.setText("Schließen");
			aufwandintagen.setEditable(false);
			beschreibung.setEditable(false);
			akzeptanzkriterien.setEditable(false);
			thema.setEditable(false);
			prioritaet.setEditable(false);
		}
	}
	
	public static Integer getId() {
		return id;
	}
	
	public static void setId(Integer id) {
		UserStoryEditController.id = id;
	}
	
}
