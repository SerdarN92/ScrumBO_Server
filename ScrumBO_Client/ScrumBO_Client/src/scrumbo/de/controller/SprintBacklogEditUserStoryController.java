package scrumbo.de.controller;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

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
import scrumbo.de.common.MyHBox;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.User;
import scrumbo.de.entity.UserStory;
import scrumbo.de.entity.UserStoryTask;
import scrumbo.de.service.BenutzerService;
import scrumbo.de.service.UserstoryService;

public class SprintBacklogEditUserStoryController implements Initializable {
	
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
	private Button						buttonDelete;
	@FXML
	private Button						buttonAbbort;
	@FXML
	private Button						buttonAddTask;
	@FXML
	private Button						buttonSave;
	@FXML
	private Button						buttonRemoveTask;
	@FXML
	private Button						buttonGetTask;
										
	private List<UserStory>				userstoryList			= new LinkedList<UserStory>();
	protected UserStory					currentUserStory		= null;
	@FXML
	protected ListView<UserStoryTask>	listViewUserStoryTask	= new ListView<>();
	protected static UserStoryTask		selectedUserStoryTask	= new UserStoryTask();
	protected static UserStoryTask		addedUserStoryTask		= new UserStoryTask();
																
	@FXML
	private void handleButtonGetTask(ActionEvent event) throws Exception {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Userstory zuweisen");
			alert.setHeaderText(null);
			alert.setContentText("Wollen Sie diese Userstory sich selbst zuweisen?");
			
			ButtonType buttonTypeOne = new ButtonType("Ja");
			ButtonType buttonTypeTwo = new ButtonType("Nein");
			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeOne) {
				boolean status = false;
				try {
					BenutzerService benutzerService = new BenutzerService();
					User user = benutzerService.getUserByEmail(CurrentUser.email);
					selectedUserStoryTask.setBenutzer(user);
					status = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (status) {
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Userstory zuweisen");
					alert2.setHeaderText(null);
					alert2.setContentText("Userstory wurde Ihrem Benutzer erfolgreich zugewiesen!");
					alert2.showAndWait();
				} else {
					Alert alert3 = new Alert(AlertType.INFORMATION);
					alert3.setTitle("Userstory zuweisen");
					alert3.setHeaderText(null);
					alert3.setContentText("Fehler! Userstory wurde Ihrem Benutzer nicht zugewiesen!");
					alert3.showAndWait();
				}
			} else {
				alert.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleButtonDelete(ActionEvent event) throws Exception {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Userstory entfernen");
			alert.setHeaderText(null);
			alert.setContentText("Wollen Sie diese Userstory wirklich aus dem Sprint Backlog entfernen?");
			
			ButtonType buttonTypeOne = new ButtonType("Ja");
			ButtonType buttonTypeTwo = new ButtonType("Nein");
			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeOne) {
				if (userstoryService.setSprintNull(currentUserStory.getId())) {
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Userstory entfernen");
					alert2.setHeaderText(null);
					alert2.setContentText("Userstory wurde erfolgreich aus dem Sprint Backlog entfernt!");
					alert2.showAndWait();
					Stage stage = (Stage) buttonDelete.getScene().getWindow();
					stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
				} else {
					Alert alert3 = new Alert(AlertType.INFORMATION);
					alert3.setTitle("Userstory entfernen");
					alert3.setHeaderText(null);
					alert3.setContentText("Fehler! Userstory wurde nicht aus dem Sprint Backlog entfernt!");
					alert3.showAndWait();
				}
			} else {
				alert.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
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
	private void handleButtonRemoveTask(ActionEvent event) throws Exception {
		ObservableList<UserStoryTask> options = FXCollections.observableArrayList();
		for (int i = 0; i < listViewUserStoryTask.getItems().size(); i++) {
			if (!(listViewUserStoryTask.getItems().get(i) == selectedUserStoryTask))
				options.add(listViewUserStoryTask.getItems().get(i));
		}
		for (int i = 0; i < currentUserStory.getUserstorytask().size(); i++) {
			if (currentUserStory.getUserstorytask().get(i).getBeschreibung()
					.equals(selectedUserStoryTask.getBeschreibung())) {
				currentUserStory.getUserstorytask().remove(i);
			}
		}
		listViewUserStoryTask.getItems().clear();
		
		listViewUserStoryTask.getItems().addAll(options);
		
		buttonRemoveTask.setDisable(true);
		
		if (currentUserStory.getUserstorytask().isEmpty()) {
			buttonSave.setDisable(true);
		}
	}
	
	@FXML
	private void handleButtonAddTask(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/TaskCreate2.fxml"));
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
		if (userstoryService.updateTask(currentUserStory) && !(currentUserStory.getUserstorytask().isEmpty())) {
			Stage stage = (Stage) buttonAbbort.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		} else {
			Stage stage = (Stage) buttonAbbort.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		buttonGetTask.setDisable(true);
		buttonRemoveTask.setDisable(true);
		userstoryService = StartwindowController.getUserstoryService();
		currentUserStory = MyHBox.userstoryStatic;
		prioritaet.setText(currentUserStory.getPriority().toString());
		thema.setText(currentUserStory.getTheme());
		beschreibung.setText(currentUserStory.getDescription());
		akzeptanzkriterien.setText(currentUserStory.getAcceptanceCriteria());
		aufwandintagen.setText(currentUserStory.getEffortInDays().toString());
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
						} else {
							setText(null);
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
					buttonRemoveTask.setDisable(false);
					buttonGetTask.setDisable(false);
					listViewUserStoryTask.setOnKeyPressed(new EventHandler<KeyEvent>() {
						@Override
						public void handle(KeyEvent event) {
							if (event.getCode().equals(KeyCode.DELETE)) {
								listViewUserStoryTask.getItems().remove(selectedUserStoryTask);
								currentUserStory.getUserstorytask().remove(addedUserStoryTask);
							}
						}
					});
				}
			}
		});
		
	}
	
}
