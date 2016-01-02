package scrumbo.de.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.CurrentSprint;
import scrumbo.de.entity.UserStory;
import scrumbo.de.entity.UserStoryTask;

public class SprintBacklogAddUserStoryController implements Initializable {
	
	Parent								root;
	Scene								scene;
	@FXML
	private TextField					prioritaet;
	@FXML
	private TextField					thema;
	@FXML
	private TextArea					beschreibung;
	@FXML
	private TextArea					akzeptanzkriterien;
	@FXML
	private TextField					aufwandintagen;
	@FXML
	private Button						buttonAbbort;
	@FXML
	private Button						buttonAddTask;
	@FXML
	private Button						buttonSave;
	@FXML
	private Button						buttonRemoveTask;
	@FXML
	private ComboBox<UserStory>			comboBoxUserStory		= new ComboBox<>();
																
	private List<UserStory>				userstoryList			= new LinkedList<UserStory>();
	protected UserStory					currentUserStory		= null;
	@FXML
	protected ListView<UserStoryTask>	listViewUserStoryTask	= new ListView<>();
	protected static UserStoryTask		selectedUserStoryTask	= new UserStoryTask();
	protected static UserStoryTask		addedUserStoryTask		= new UserStoryTask();
																
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		if (!prioritaet.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Userstory übertragen");
			alert.setHeaderText(null);
			alert.setContentText("Wollen Sie fortfahren ohne zu speichern?");
			
			ButtonType buttonTypeOne = new ButtonType("Ja");
			ButtonType buttonTypeTwo = new ButtonType("Nein");
			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeOne) {
				alert.close();
				comboBoxUserStory = null;
				userstoryList = null;
				currentUserStory = null;
				listViewUserStoryTask = null;
				selectedUserStoryTask = null;
				addedUserStoryTask = null;
				
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} else {
				alert.close();
			}
		} else {
			comboBoxUserStory = null;
			userstoryList = null;
			currentUserStory = null;
			listViewUserStoryTask = null;
			selectedUserStoryTask = null;
			addedUserStoryTask = null;
			Stage stage = (Stage) buttonAbbort.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
		
	}
	
	@FXML
	private void handleButtonRemoveTask(ActionEvent event) throws Exception {
		listViewUserStoryTask.getItems().remove(selectedUserStoryTask);
		currentUserStory.getUserstorytask().remove(selectedUserStoryTask);
		buttonRemoveTask.setDisable(true);
		
		if (currentUserStory.getUserstorytask().isEmpty()) {
			buttonSave.setDisable(true);
		}
	}
	
	@FXML
	private void handleButtonAddTask(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/TaskCreate.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						if (addedUserStoryTask != null) {
							listViewUserStoryTask.getItems().add(addedUserStoryTask);
							currentUserStory.getUserstorytask().add(addedUserStoryTask);
							buttonSave.setDisable(false);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleButtonSave(ActionEvent event) throws Exception {
		Gson gson = new Gson();
		String output = gson.toJson(currentUserStory);
		
		JSONObject jsonObject = new JSONObject(output);
		
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/userstory/updateTasks/" + CurrentSprint.id
					+ "/" + ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setConnectTimeout(50000);
			conn.setReadTimeout(50000);
			conn.setRequestMethod("POST");
			
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			out.write(jsonObject.toString());
			out.close();
			
			if (conn.getResponseMessage().equals("Tasks erfolgreich geupdated"))
				System.out.println("\nRest Service Invoked Successfully..");
			conn.disconnect();
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Userstory übertragen");
			alert.setHeaderText(null);
			alert.setContentText("Userstory wurde erfolgreich ins Sprint Backlog übertragen!");
			alert.showAndWait();
			Stage stage = (Stage) buttonAbbort.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		} catch (Exception e) {
			System.out.println("\nError while calling Rest service");
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Userstory übertragen");
			alert.setHeaderText(null);
			alert.setContentText("Fehler! Userstory wurde nicht ins Sprint Backlog übertragen!");
			alert.showAndWait();
			Stage stage = (Stage) buttonAbbort.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		buttonAddTask.setDisable(true);
		buttonRemoveTask.setDisable(true);
		buttonSave.setDisable(true);
		ladeUserStory();
		initComboBox();
		initListView();
	}
	
	protected void initListView() {
		ObservableList<UserStoryTask> options = FXCollections.observableArrayList();
		
		if (currentUserStory != null) {
			for (int i = 0; i < currentUserStory.getUserstorytask().size(); i++) {
				listViewUserStoryTask.getItems().add(currentUserStory.getUserstorytask().get(i));
			}
		}
		
		listViewUserStoryTask.setCellFactory(new Callback<ListView<UserStoryTask>, ListCell<UserStoryTask>>() {
			
			@Override
			public ListCell<UserStoryTask> call(ListView<UserStoryTask> p) {
				
				ListCell<UserStoryTask> cell = new ListCell<UserStoryTask>() {
					
					@Override
					protected void updateItem(UserStoryTask t, boolean bln) {
						super.updateItem(t, bln);
						if (t != null) {
							setText(t.getBeschreibung());
						}
					}
					
				};
				
				return cell;
			}
		});
		
		listViewUserStoryTask.setOnMouseClicked(new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent click) {
				
				if (click.getClickCount() == 1) {
					selectedUserStoryTask = listViewUserStoryTask.getSelectionModel().getSelectedItem();
					if (selectedUserStoryTask != null) {
						buttonRemoveTask.setDisable(false);
					}
					listViewUserStoryTask.setOnKeyPressed(new EventHandler<KeyEvent>() {
						@Override
						public void handle(KeyEvent event) {
							if (event.getCode().equals(KeyCode.DELETE)) {
								listViewUserStoryTask.getItems().remove(selectedUserStoryTask);
								currentUserStory.getUserstorytask().remove(selectedUserStoryTask);
							}
						}
					});
				}
			}
		});
		
	}
	
	private void initComboBox() {
		ObservableList<UserStory> options = FXCollections.observableArrayList();
		for (int i = 0; i < userstoryList.size(); i++) {
			options.add(userstoryList.get(i));
		}
		comboBoxUserStory.getItems().addAll(options);
		comboBoxUserStory.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				comboBoxUserStory.requestFocus();
			}
		});
		
		comboBoxUserStory.setConverter(new StringConverter<UserStory>() {
			@Override
			public String toString(UserStory u) {
				return u.getBeschreibung();
			}
			
			@Override
			public UserStory fromString(String string) {
				throw new UnsupportedOperationException();
			}
		});
		
		comboBoxUserStory.valueProperty().addListener(new ChangeListener<UserStory>() {
			@Override
			public void changed(ObservableValue ov, UserStory t, UserStory t1) {
				prioritaet.setText(t1.getPrioritaet().toString());
				beschreibung.setText(t1.getBeschreibung());
				aufwandintagen.setText(t1.getAufwandintagen().toString());
				akzeptanzkriterien.setText(t1.getAkzeptanzkriterien());
				thema.setText(t1.getThema());
				currentUserStory = t1;
				buttonAddTask.setDisable(false);
			}
		});
	}
	
	private void ladeUserStory() {
		String output = "";
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/userstory/sucheNULL/productbacklogid/"
					+ CurrentScrumprojekt.productbacklog.getId() + "/" + ScrumBOClient.getDatabaseconfigfile());
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
		Type listType = new TypeToken<LinkedList<UserStory>>() {
		}.getType();
		userstoryList = gson.fromJson(output, listType);
		
	}
	
}
