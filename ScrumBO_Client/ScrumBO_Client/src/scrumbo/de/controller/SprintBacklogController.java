package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.common.MyHBox;
import scrumbo.de.common.MyToolBox;
import scrumbo.de.entity.CurrentBurndownChart;
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.CurrentSprint;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.Sprint;
import scrumbo.de.entity.UserStory;
import scrumbo.de.service.SprintBacklogService;

public class SprintBacklogController implements Initializable {
	
	Parent									root;
	Scene									scene;
	Stage									stage					= null;
	SprintBacklogService					sprintbacklogService	= null;
	@FXML
	private ImageView						informationImage;
	@FXML
	private Button							buttonLogout;
	@FXML
	private Button							buttonBack;
	@FXML
	private Button							buttonAddUserStory;
	@FXML
	private Button							buttonLoadSprint;
	@FXML
	private Button							buttonCreateNewSprint;
	@FXML
	private Button							buttonStartSprint;
	@FXML
	private Button							buttonEndDay;
	@FXML
	private VBox							VBOXUserStories;
	@FXML
	private Text							sprintNumber;
	@FXML
	private Text							tagNumber;
											
	public ObservableList<UserStory>		dataSprintBacklog		= FXCollections.observableArrayList();
	public static Integer					anzahlSprints			= 0;
																	
	public static SprintBacklogController	controller				= null;
																	
	private Integer							count					= 0;
																	
	public static boolean					editable				= true;
																	
	private Timer							timer;
											
	public ObservableList<UserStory> getData() {
		return dataSprintBacklog;
	}
	
	public static Integer getAnzahlSprints() {
		return anzahlSprints;
	}
	
	public void pause() {
		this.timer.cancel();
	}
	
	public void resume() {
		this.timer = new Timer();
		this.timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						
						try {
							reloadSprintBacklog();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		}, 0, 10000);
	}
	
	public void initLoadOldSprint() {
		if (CurrentSprint.sprintnumber < sprintbacklogService.ladeAnzahlSprints()) {
			pause();
			editable = false;
		} else {
			resume();
			editable = true;
		}
		sprintbacklogService.ladeAltenSprint(CurrentSprint.sprintnumber);
		dataSprintBacklog.clear();
		VBOXUserStories.getChildren().remove(0, VBOXUserStories.getChildren().size());
		initSprintBacklog();
		
		ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true);
		sp.setContent(VBOXUserStories);
		sp.setVisible(true);
		
		ladeSprintBacklog(CurrentProject.productbacklog.getId());
		
		for (int i = 0; i < dataSprintBacklog.size(); i++) {
			UserStory userstory = dataSprintBacklog.get(i);
			MyHBox hb = new MyHBox(userstory);
			VBOXUserStories.getChildren().add(hb);
		}
		
		sprintNumber.setText(CurrentSprint.sprintnumber.toString());
		if (CurrentBurndownChart.days != null) {
			tagNumber.setText((CurrentBurndownChart.days.toString()));
		} else {
			tagNumber.setText(null);
		}
		if (CurrentSprint.sprintnumber < anzahlSprints) {
			System.out.println("Ja");
			buttonStartSprint.setDisable(true);
			buttonCreateNewSprint.setDisable(true);
			buttonEndDay.setDisable(true);
		} else {
			if (!CurrentSprint.status) {
				buttonStartSprint.setDisable(false);
				buttonCreateNewSprint.setDisable(true);
				buttonEndDay.setDisable(true);
			} else {
				buttonStartSprint.setDisable(true);
				buttonCreateNewSprint.setDisable(false);
				buttonEndDay.setDisable(false);
			}
		}
	}
	
	@FXML
	private void handleButtonStartSprint(ActionEvent event) throws Exception {
		try {
			if (sprintbacklogService.createBurndownChart()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Sprint starten");
				alert.setHeaderText(null);
				alert.setContentText("Sprint Nr." + CurrentSprint.sprintnumber + " wurde gestartet!");
				alert.showAndWait();
				buttonEndDay.setDisable(false);
				buttonCreateNewSprint.setDisable(false);
				buttonStartSprint.setDisable(true);
				buttonAddUserStory.setDisable(true);
				reloadSprintBacklog();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
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
					getClass().getResource("/scrumbo/de/gui/SprintBacklogLoadOldSprint.fxml"));
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
					getClass().getResource("/scrumbo/de/gui/SprintBacklogAddUserStory.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						reloadSprintBacklog();
						if (!dataSprintBacklog.isEmpty())
							buttonStartSprint.setDisable(false);
							
						if (CurrentUser.isPO || CurrentUser.isDev) {
							buttonStartSprint.setDisable(true);
							buttonEndDay.setDisable(true);
							buttonCreateNewSprint.setDisable(true);
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
	private void handleButtonEndDay(ActionEvent event) throws Exception {
		if (count == 0) {
			if (sprintbacklogService.endDay()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Tag beenden");
				alert.setHeaderText(null);
				alert.setContentText("Tag " + tagNumber.getText() + " wurde beendet.");
				alert.showAndWait();
				count++;
				Integer number = Integer.parseInt(tagNumber.getText());
				number++;
				tagNumber.setText(number.toString());
			}
		}
		count = 0;
	}
	
	@FXML
	private void handleButtonBack(ActionEvent event) throws Exception {
		if (CurrentUser.isSM) {
			pause();
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/ScrumSM.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonLogout.getScene().getWindow();
			stage.setScene(scene);
		} else {
			pause();
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Scrum.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonLogout.getScene().getWindow();
			stage.setScene(scene);
		}
	}
	
	@FXML
	private void handleButtonLogout(ActionEvent event) throws Exception {
		StartwindowController.logout();
		
		pause();
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sprintbacklogService = StartwindowController.getSprintbacklogService();
		anzahlSprints = sprintbacklogService.ladeAnzahlSprints();
		initSprintBacklog();
		
		ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true);
		sp.setContent(VBOXUserStories);
		sp.setVisible(true);
		
		sprintbacklogService.ladeSprint();
		ladeSprintBacklog(CurrentProject.productbacklog.getId());
		
		for (int i = 0; i < dataSprintBacklog.size(); i++) {
			UserStory userstory = dataSprintBacklog.get(i);
			MyHBox hb = new MyHBox(userstory);
			VBOXUserStories.getChildren().add(hb);
		}
		
		sprintNumber.setText(CurrentSprint.sprintnumber.toString());
		if (CurrentBurndownChart.days != null) {
			tagNumber.setText(CurrentBurndownChart.days.toString());
		}
		
		controller = this;
		
		if (!CurrentSprint.status) {
			buttonEndDay.setDisable(true);
			buttonCreateNewSprint.setDisable(true);
		} else {
			buttonStartSprint.setDisable(true);
		}
		
		if (dataSprintBacklog.isEmpty()) {
			buttonStartSprint.setDisable(true);
		}
		
		if (sprintbacklogService.ladeAnzahlSprints() <= 1) {
			buttonLoadSprint.setDisable(true);
		}
		
		if (CurrentUser.isPO || CurrentUser.isDev) {
			buttonStartSprint.setDisable(true);
			buttonEndDay.setDisable(true);
			buttonCreateNewSprint.setDisable(true);
			buttonAddUserStory.setDisable(true);
		}
		
		if (CurrentUser.isPO) {
			buttonAddUserStory.setDisable(true);
		}
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						
						try {
							reloadSprintBacklog();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		}, 0, 10000);
		
		MyToolBox toolbox = new MyToolBox();
		Tooltip.install(informationImage, toolbox.getTooltipSprintBacklog());
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
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sprint beenden");
		alert.setHeaderText(null);
		alert.setContentText("Wollen Sie diese Sprint wirklich beenden?");
		
		ButtonType buttonTypeOne = new ButtonType("Ja");
		ButtonType buttonTypeTwo = new ButtonType("Nein");
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne && sprintbacklogService.removeIncompleteUserStories()) {
			alert.close();
			Sprint sprint = sprintbacklogService.addNewSprintToSprintBacklog();
			CurrentSprint.id = sprint.getId();
			CurrentSprint.sprintnumber = sprint.getSprintnummer();
			CurrentSprint.status = sprint.getStatus();
			
			reloadSprintBacklog();
			
			buttonStartSprint.setDisable(false);
			buttonEndDay.setDisable(true);
			buttonCreateNewSprint.setDisable(true);
			buttonAddUserStory.setDisable(false);
		} else {
			alert.close();
		}
		
		tagNumber.setText(null);
		
	}
	
	public void reloadSprintBacklog() throws IOException {
		VBOXUserStories.getChildren().clear();
		
		initSprintBacklog();
		
		ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true);
		sp.setContent(VBOXUserStories);
		sp.setVisible(true);
		
		sprintbacklogService.ladeSprint();
		ladeSprintBacklog(CurrentProject.productbacklog.getId());
		
		for (int i = 0; i < dataSprintBacklog.size(); i++) {
			UserStory userstory = dataSprintBacklog.get(i);
			MyHBox hb = new MyHBox(userstory);
			VBOXUserStories.getChildren().add(hb);
		}
		
		sprintNumber.setText(CurrentSprint.sprintnumber.toString());
		if (CurrentBurndownChart.days != null) {
			tagNumber.setText(CurrentBurndownChart.days.toString());
		}
		
		if (dataSprintBacklog.isEmpty()) {
			buttonStartSprint.setDisable(true);
		}
	}
	
}
