package scrumbo.de.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import scrumbo.de.common.MyHBox;
import scrumbo.de.entity.UserStory;
import scrumbo.de.entity.UserStoryTask;
import scrumbo.de.service.UserstoryService;

public class FXMLSprintBacklogEditUserStoryController implements Initializable {
	
	Parent								root;
	Scene								scene;
	UserstoryService					userstoryService		= null;
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
										
	private List<UserStory>				userstoryList			= new LinkedList<UserStory>();
	protected static UserStory			currentUserStory		= null;
	@FXML
	protected ListView<UserStoryTask>	listViewUserStoryTask	= new ListView<>();
	private static UserStoryTask		selectedUserStoryTask	= new UserStoryTask();
	protected static UserStoryTask		addedUserStoryTask		= new UserStoryTask();
																
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		userstoryList = null;
		currentUserStory = null;
		listViewUserStoryTask = null;
		selectedUserStoryTask = null;
		addedUserStoryTask = null;
		
		Stage stage = (Stage) buttonAbbort.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleButtonAddTask(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/FXMLTaskEdit.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						listViewUserStoryTask.getItems().add(addedUserStoryTask);
						currentUserStory.getUserstorytask().add(addedUserStoryTask);
						
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
		if (userstoryService.updateTask(currentUserStory)) {
			Stage stage = (Stage) buttonAbbort.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		} else {
			Stage stage = (Stage) buttonAbbort.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userstoryService = FXMLStartController.getUserstoryService();
		currentUserStory = MyHBox.blabla;
		prioritaet.setText(currentUserStory.getPrioritaet().toString());
		thema.setText(currentUserStory.getThema());
		beschreibung.setText(currentUserStory.getBeschreibung());
		akzeptanzkriterien.setText(currentUserStory.getAkzeptanzkriterien());
		aufwandintagen.setText(currentUserStory.getAufwandintagen().toString());
		userstoryList = userstoryService.ladeUserStory();
		initListView();
	}
	
	protected void initListView() {
		
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
				
				if (click.getClickCount() == 2) {
					selectedUserStoryTask = listViewUserStoryTask.getSelectionModel().getSelectedItem();
				}
			}
		});
		
	}
	
}
