package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.json.JSONException;

import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.TextAreaSkin;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.UserStory;
import scrumbo.de.service.UserStoryService;

public class UserStoryCreateController implements Initializable {
	
	private UserStoryService	userstoryService	= null;
	private UserStory			userstory			= null;
													
	@FXML
	private TextField			prioritaet;
	@FXML
	private TextField			thema;
	@FXML
	private TextArea			beschreibung;
	@FXML
	private TextArea			akzeptanzkriterien;
	@FXML
	private TextField			aufwandintagen;
	@FXML
	private Button				buttonAbbort;
	@FXML
	private Button				buttonAdd;
	@FXML
	private Text				txtError;
								
	@FXML
	private void handleKeyPressed(KeyEvent event) throws JSONException, IOException, Exception {
		if (event.getCode().equals(KeyCode.ENTER))
			createUserStory();
	}
	
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
		createUserStory();
	}
	
	private void createUserStory() {
		if (checkPrioritaet() && checkThema() && checkBeschreibung() && checkAkzeptanzkriterien()
				&& checkAufwandInTagen()) {
			userstory = new UserStory();
			userstory.setPriority(Integer.parseInt(prioritaet.getText()));
			userstory.setTheme(thema.getText());
			userstory.setDescription(beschreibung.getText());
			userstory.setAcceptanceCriteria(akzeptanzkriterien.getText());
			userstory.setEffortInDays(Integer.parseInt(aufwandintagen.getText()));
			List<UserStory> userstoryList = CurrentProject.productbacklog.getUserstory();
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
		
		beschreibung.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.TAB) {
					TextAreaSkin skin = (TextAreaSkin) beschreibung.getSkin();
					if (skin.getBehavior() instanceof TextAreaBehavior) {
						TextAreaBehavior behavior = (TextAreaBehavior) skin.getBehavior();
						if (event.isControlDown()) {
							behavior.callAction("InsertTab");
						} else {
							behavior.callAction("TraverseNext");
						}
						event.consume();
					}
				}
			}
		});
		
		akzeptanzkriterien.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.TAB) {
					TextAreaSkin skin = (TextAreaSkin) akzeptanzkriterien.getSkin();
					if (skin.getBehavior() instanceof TextAreaBehavior) {
						TextAreaBehavior behavior = (TextAreaBehavior) skin.getBehavior();
						if (event.isControlDown()) {
							behavior.callAction("InsertTab");
						} else {
							behavior.callAction("TraverseNext");
						}
						event.consume();
					}
				}
			}
		});
	}
	
}
