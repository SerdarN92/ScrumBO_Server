package scrumbo.de.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.Benutzer;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.UserStoryTask;

public class FXMLTaskEditController implements Initializable {
	
	Parent						root;
	Scene						scene;
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
		task.setBeschreibung(taskbeschreibung.getText());
		task.setAufwandinstunden(Integer.parseInt(aufwandinstunden.getText()));
		task.setBenutzer(currentBenutzer);
		FXMLSprintBacklogEditUserStoryController.addedUserStoryTask = task;
		Stage stage = (Stage) buttonAdd.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ladeBenutzer();
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
		
		comboBoxBenutzer.valueProperty().addListener(new ChangeListener<Benutzer>() {
			@Override
			public void changed(ObservableValue ov, Benutzer t, Benutzer t1) {
				currentBenutzer = t1;
			}
		});
		
	}
	
	private void ladeBenutzer() {
		String output = "";
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/benutzer/alle/scrumprojekt/"
					+ CurrentScrumprojekt.scrumprojektID + "/" + ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json" + ";charset=utf-8");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
			output = br.readLine();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		Type listType = new TypeToken<LinkedList<Benutzer>>() {
		}.getType();
		benutzerList = gson.fromJson(output, listType);
		
	}
	
}
