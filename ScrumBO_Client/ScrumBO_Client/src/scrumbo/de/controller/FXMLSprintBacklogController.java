package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.common.MyHBox;
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.CurrentSprint;
import scrumbo.de.entity.Sprint;
import scrumbo.de.entity.UserStory;
import scrumbo.de.service.SprintbacklogService;

public class FXMLSprintBacklogController implements Initializable {
	
	Parent										root;
	Scene										scene;
	Stage										stage					= null;
	SprintbacklogService						sprintbacklogService	= null;
	@FXML
	private Text								vorname;
	@FXML
	private Text								nachname;
	@FXML
	private Text								benutzerrolle;
	@FXML
	private Text								projektname;
	@FXML
	private Button								buttonLogout;
	@FXML
	private Button								buttonBack;
	@FXML
	private Button								buttonAddUserStory;
	@FXML
	private Button								buttonLoadSprint;
	@FXML
	private Button								buttonCreateNewSprint;
	@FXML
	private VBox								VBOXUserStories;
	@FXML
	private Text								sprintNumber;
												
	public ObservableList<UserStory>			dataSprintBacklog		= FXCollections.observableArrayList();
	private static Integer						anzahlSprints			= 0;
																		
	public static FXMLSprintBacklogController	controller				= null;
																		
	public ObservableList<UserStory> getData() {
		return dataSprintBacklog;
	}
	
	public static Integer getAnzahlSprints() {
		return anzahlSprints;
	}
	
	public void initLoadOldSprint() {
		sprintbacklogService.ladeAltenSprint(CurrentSprint.sprintnummer);
		dataSprintBacklog.clear();
		VBOXUserStories.getChildren().remove(0, VBOXUserStories.getChildren().size());
		initSprintBacklog();
		
		ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true);
		sp.setContent(VBOXUserStories);
		sp.setVisible(true);
		
		ladeSprintBacklog(CurrentScrumprojekt.productbacklog.getId());
		
		for (int i = 0; i < dataSprintBacklog.size(); i++) {
			UserStory userstory = dataSprintBacklog.get(i);
			MyHBox hb = new MyHBox(userstory);
			VBOXUserStories.getChildren().add(hb);
		}
		
		sprintNumber.setText(CurrentSprint.sprintnummer.toString());
	}
	
	@FXML
	private void handleButtonReload(ActionEvent event) throws Exception {
		try {
			reloadSprintBacklog();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleButtonLoadSprint(ActionEvent event) throws Exception {
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/scrumbo/de/gui/FXMLSprintBacklogLoadOldSprint.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						initLoadOldSprint();
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
	private void handleButtonAddUserStory(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/scrumbo/de/gui/FXMLSprintBacklogAddUserStory.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						reloadSprintBacklog();
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
	private void handleButtonBack(ActionEvent event) throws Exception {
		if (CurrentBenutzer.isSM) {
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLScrumSM.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonLogout.getScene().getWindow();
			stage.setScene(scene);
		} else {
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLScrum.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonLogout.getScene().getWindow();
			stage.setScene(scene);
		}
	}
	
	@FXML
	private void handleButtonLogout(ActionEvent event) throws Exception {
		CurrentBenutzer.benutzerID = -1;
		CurrentBenutzer.vorname = null;
		CurrentBenutzer.nachname = null;
		CurrentBenutzer.email = null;
		CurrentBenutzer.passwort = null;
		CurrentBenutzer.benutzerrollenID = -1;
		CurrentBenutzer.benutzerrolle = null;
		CurrentBenutzer.projekte = null;
		CurrentScrumprojekt.scrumprojektID = -1;
		CurrentScrumprojekt.projektname = null;
		CurrentBenutzer.isSM = false;
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLStartfenster.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sprintbacklogService = FXMLStartController.getSprintbacklogService();
		
		vorname.setText(CurrentBenutzer.vorname);
		nachname.setText(CurrentBenutzer.nachname);
		benutzerrolle.setText(CurrentBenutzer.benutzerrolle);
		projektname.setText(CurrentScrumprojekt.projektname);
		anzahlSprints = sprintbacklogService.ladeAnzahlSprints();
		initSprintBacklog();
		
		ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true);
		sp.setContent(VBOXUserStories);
		sp.setVisible(true);
		
		sprintbacklogService.ladeSprint();
		ladeSprintBacklog(CurrentScrumprojekt.productbacklog.getId());
		
		for (int i = 0; i < dataSprintBacklog.size(); i++) {
			UserStory userstory = dataSprintBacklog.get(i);
			MyHBox hb = new MyHBox(userstory);
			VBOXUserStories.getChildren().add(hb);
		}
		
		sprintNumber.setText(CurrentSprint.sprintnummer.toString());
		
		controller = this;
	}
	
	private void initSprintBacklog() {
		
		HBox hbox = new HBox();
		hbox.setMinHeight(30);
		hbox.setMinWidth(883);
		hbox.setStyle(" -fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: black");
		VBox vboxprio = new VBox();
		vboxprio.setMinWidth(100);
		vboxprio.setAlignment(Pos.CENTER);
		vboxprio.setStyle(" -fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: black");
		Text text1 = new Text("Priorität");
		vboxprio.getChildren().add(text1);
		VBox vboxuserstory = new VBox();
		vboxuserstory.setMinWidth(183);
		vboxuserstory.setAlignment(Pos.CENTER);
		vboxuserstory.setStyle(" -fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: black");
		Text text2 = new Text("User Story");
		vboxuserstory.getChildren().add(text2);
		VBox vboxopentask = new VBox();
		vboxopentask.setMinWidth(200);
		vboxopentask.setAlignment(Pos.CENTER);
		vboxopentask.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: black");
		Text text3 = new Text("Offene Tasks");
		vboxopentask.getChildren().add(text3);
		VBox vboxinworktask = new VBox();
		vboxinworktask.setMinWidth(200);
		vboxinworktask.setAlignment(Pos.CENTER);
		vboxinworktask.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: black");
		Text text4 = new Text("Tasks in Arbeit");
		vboxinworktask.getChildren().add(text4);
		VBox vboxdonetask = new VBox();
		vboxdonetask.setMinWidth(199);
		vboxdonetask.setAlignment(Pos.CENTER);
		vboxdonetask.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: black");
		Text text5 = new Text("Erledigte Tasks");
		vboxdonetask.getChildren().add(text5);
		
		hbox.getChildren().add(vboxprio);
		hbox.getChildren().add(vboxuserstory);
		hbox.getChildren().add(vboxopentask);
		hbox.getChildren().add(vboxinworktask);
		hbox.getChildren().add(vboxdonetask);
		VBOXUserStories.getChildren().add(hbox);
		
	}
	
	public void ladeSprintBacklog(Integer id) {
		List<UserStory> liste = sprintbacklogService.ladeSprintBacklog();
		dataSprintBacklog.clear();
		dataSprintBacklog.removeAll(getData());
		
		if (!liste.isEmpty()) {
			for (int i = 0; i < liste.size(); i++) {
				dataSprintBacklog.add(liste.get(i));
			}
		}
		
	}
	
	@FXML
	public void handleButtonCreateNewSprint(ActionEvent event) throws IOException {
		Sprint sprint = sprintbacklogService.addNewSprintToSprintBacklog();
		CurrentSprint.id = sprint.getId();
		CurrentSprint.sprintnummer = sprint.getSprintnummer();
		
		reloadSprintBacklog();
	}
	
	public void reloadSprintBacklog() throws IOException {
		VBOXUserStories.getChildren().clear();
		
		initSprintBacklog();
		
		ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true);
		sp.setContent(VBOXUserStories);
		sp.setVisible(true);
		
		sprintbacklogService.ladeSprint();
		ladeSprintBacklog(CurrentScrumprojekt.productbacklog.getId());
		
		for (int i = 0; i < dataSprintBacklog.size(); i++) {
			UserStory userstory = dataSprintBacklog.get(i);
			MyHBox hb = new MyHBox(userstory);
			VBOXUserStories.getChildren().add(hb);
		}
		
		sprintNumber.setText(CurrentSprint.sprintnummer.toString());
	}
	
}
