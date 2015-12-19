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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.UserStory;
import scrumbo.de.entity.UserStoryTask;

public class FXMLSprintBacklogAddUserStoryController implements Initializable {
	
	Parent										root;
	Scene										scene;
	@FXML
	private TextField							prioritaet;
	@FXML
	private TextField							thema;
	@FXML
	private TextArea							beschreibung;
	@FXML
	private TextArea							akzeptanzkriterien;
	@FXML
	private TextField							aufwandintagen;
	@FXML
	private Button								buttonAbbort;
	@FXML
	private Button								buttonAddTask;
	@FXML
	private ComboBox<UserStory>					comboBoxUserStory		= new ComboBox<>();
																		
	private List<UserStory>						userstoryList			= new LinkedList<UserStory>();
	protected static UserStory					currentUserStory		= null;
	@FXML
	protected static ListView<UserStoryTask>	listViewUserStoryTask	= new ListView<>();
	private static UserStoryTask				selectedUserStoryTask	= new UserStoryTask();
	protected static UserStoryTask				addedUserStoryTask		= new UserStoryTask();
																		
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		Stage stage = (Stage) buttonAbbort.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleButtonAddTask(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/FXMLTaskCreate.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						listViewUserStoryTask.getItems().add(addedUserStoryTask);
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
	private void handleButtonAdd(ActionEvent event) throws Exception {
	
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		buttonAddTask.setDisable(true);
		ladeUserStory();
		initComboBox();
		
	}
	
	protected static void initListView() {
		System.out.println("LISTVIEW");
		ObservableList<UserStoryTask> options = FXCollections.observableArrayList();
		
		if (currentUserStory != null) {
			for (int i = 0; i < currentUserStory.getUserstorytask().size(); i++) {
				listViewUserStoryTask.getItems().add(currentUserStory.getUserstorytask().get(i));
			}
		}
		// listViewUserStoryTask.setOnMousePressed(new
		// EventHandler<MouseEvent>() {
		// @Override
		// public void handle(MouseEvent event) {
		// listViewUserStoryTask.requestFocus();
		// }
		// });
		
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
				
				if (click.getClickCount() == 2) {
					selectedUserStoryTask = listViewUserStoryTask.getSelectionModel().getSelectedItem();
					System.out.println(selectedUserStoryTask.getBeschreibung());
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
					+ CurrentScrumprojekt.productbacklog.get(0).getId() + "/" + ScrumBOClient.getDatabaseconfigfile());
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
