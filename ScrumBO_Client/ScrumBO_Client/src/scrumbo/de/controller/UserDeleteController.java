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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.User;
import scrumbo.de.service.BenutzerService;

public class UserDeleteController implements Initializable {
	
	Parent					root;
	Scene					scene;
	BenutzerService			benutzerService		= null;
	@FXML
	private ComboBox<User>	comboBoxBenutzer	= new ComboBox<>();
	@FXML
	private Button			buttonAbbort;
	@FXML
	private Button			buttonDelete;
	private List<User>		benutzerList		= new LinkedList<User>();
	private User			currentBenutzer		= new User();
												
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		Stage stage = (Stage) buttonDelete.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleKeyPressed(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER))
			deleteUser();
	}
	
	private void deleteUser() {
		if (currentBenutzer.getId() != null) {
			try {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Benutzer löschen");
				alert.setHeaderText(null);
				alert.setContentText("Wollen Sie diesen Benutzer wirklich löschen?");
				
				ButtonType buttonTypeOne = new ButtonType("Ja");
				ButtonType buttonTypeTwo = new ButtonType("Nein");
				alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
				
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeOne) {
					alert.close();
					if (benutzerService.deleteBenutzer(currentBenutzer)) {
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("Benutzer löschen");
						alert2.setHeaderText(null);
						alert2.setContentText("Benutzer wurde erfolgreich gelöscht!");
						alert2.showAndWait();
						Stage stage = (Stage) buttonDelete.getScene().getWindow();
						stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
					} else {
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("Benutzer löschen");
						alert2.setHeaderText(null);
						alert2.setContentText("Fehler! Benutzer wurde nicht gelöscht!");
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
		}
	}
	
	@FXML
	private void handleButtonDelete(ActionEvent event) throws Exception {
		deleteUser();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		benutzerService = StartwindowController.getBenutzerService();
		benutzerList = benutzerService.ladeBenutzer();
		
		if (benutzerList.isEmpty()) {
			buttonDelete.setDisable(true);
		} else {
			buttonDelete.setDisable(false);
		}
		
		ObservableList<User> options = FXCollections.observableArrayList();
		for (int i = 0; i < benutzerList.size(); i++) {
			if (!benutzerList.get(i).getId().equals(CurrentUser.userId))
				options.add(benutzerList.get(i));
		}
		comboBoxBenutzer.getItems().addAll(options);
		comboBoxBenutzer.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				comboBoxBenutzer.requestFocus();
			}
		});
		
		comboBoxBenutzer.setConverter(new StringConverter<User>() {
			@Override
			public String toString(User u) {
				return u.getVorname() + " " + u.getNachname();
			}
			
			@Override
			public User fromString(String string) {
				throw new UnsupportedOperationException();
			}
		});
		
		comboBoxBenutzer.valueProperty().addListener((ChangeListener<User>) (ov, t, t1) -> currentBenutzer = t1);
		
	}
	
}
