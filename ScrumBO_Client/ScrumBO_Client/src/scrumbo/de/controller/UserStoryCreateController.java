package scrumbo.de.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.UserStory;
import scrumbo.de.service.UserstoryService;

public class UserStoryCreateController implements Initializable {
	
	Parent				root;
	Scene				scene;
	UserstoryService	userstoryService	= null;
	@FXML
	private TextField	prioritaet;
	@FXML
	private TextField	thema;
	@FXML
	private TextArea	beschreibung;
	@FXML
	private TextArea	akzeptanzkriterien;
	@FXML
	private TextField	aufwandintagen;
	@FXML
	private Button		buttonAbbort;
	@FXML
	private Button		buttonAdd;
	@FXML
	private Text		txtError;
						
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
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		if (!prioritaet.getText().isEmpty() || !thema.getText().isEmpty() || !beschreibung.getText().isEmpty()
				|| !akzeptanzkriterien.getText().isEmpty() || !aufwandintagen.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Userstory erstellen");
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
		if (checkPrioritaet() && checkThema() && checkBeschreibung() && checkAkzeptanzkriterien()
				&& checkAufwandInTagen()) {
			UserStory userstory = new UserStory();
			userstory.setPrioritaet(Integer.parseInt(prioritaet.getText()));
			userstory.setThema(thema.getText());
			userstory.setBeschreibung(beschreibung.getText());
			userstory.setAkzeptanzkriterien(akzeptanzkriterien.getText());
			userstory.setAufwandintagen(Integer.parseInt(aufwandintagen.getText()));
			List<UserStory> userstoryList = CurrentScrumprojekt.productbacklog.getUserstory();
			userstoryList.add(userstory);
			
			if (userstoryService.createUserStory(userstory)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Userstory erstellen");
				alert.setHeaderText(null);
				alert.setContentText("Userstory wurde erfolgreich erstellt!");
				alert.showAndWait();
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Userstory erstellen");
				alert.setHeaderText(null);
				alert.setContentText("Fehler! Userstory wurde nicht erfolgreich erstellt!");
				alert.showAndWait();
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.close();
			}
			
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userstoryService = StartwindowController.getUserstoryService();
		
	}
	
}
