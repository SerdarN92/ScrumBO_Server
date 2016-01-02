package scrumbo.de.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.entity.BurndownChartPoint;
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.entity.CurrentBurndownChart;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.CurrentSprint;
import scrumbo.de.service.SprintbacklogService;

public class BurndownChartController implements Initializable {
	
	private Parent						root;
	private Scene						scene;
	private SprintbacklogService		sprintbacklogService;
	@FXML
	private Button						buttonLogout;
	@FXML
	private Button						buttonBack;
	@FXML
	private Pane						mainPane;
	@FXML
	private Button						buttonLoadBurndownChart;
	private Integer						burndownChartId;
	private Integer						tage;
	private List<BurndownChartPoint>	points;
										
	private CategoryAxis				xAxis		= new CategoryAxis();
	private NumberAxis					yAxis		= new NumberAxis();
	private LineChart<String, Number>	lineChart	= new LineChart<String, Number>(xAxis, yAxis);
	private XYChart.Series				series;
	private XYChart.Series				series2;
	private Timer						timer;
										
	@FXML
	private void handleButtonBack(ActionEvent event) throws Exception {
		if (CurrentBenutzer.isSM) {
			timer.cancel();
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/ScrumSM.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonLogout.getScene().getWindow();
			stage.setScene(scene);
		} else {
			timer.cancel();
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Scrum.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonLogout.getScene().getWindow();
			stage.setScene(scene);
		}
	}
	
	@FXML
	private void handleButtonLoadBurndownChart(ActionEvent event) throws Exception {
		SprintBacklogController.anzahlSprints = sprintbacklogService.ladeAnzahlSprints();
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
						burndownChartId = CurrentBurndownChart.id;
						tage = CurrentBurndownChart.tage;
						points = CurrentBurndownChart.points;
						
						lineChart.getData().remove(series);
						lineChart.getData().remove(series2);
						
						series.getData().clear();
						series2.getData().clear();
						
						if (points != null && points.size() > 0) {
							
							for (int i = 0; i < points.size(); i++) {
								series.getData().add(new Data("Tag " + i, points.get(i).getY()));
							}
							
							series2.getData().add(new Data("Tag 0", points.get(0).getY()));
							if (points.size() > 0) {
								series2.getData().add(
										new Data("Tag " + (points.size() - 1), points.get(points.size() - 1).getY()));
							}
							
							lineChart.getData().add(series);
							lineChart.getData().add(series2);
						}
						
						lineChart.setTitle("zu Sprint " + CurrentSprint.sprintnummer);
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
	
	public void initLoadOldSprint() {
		sprintbacklogService.ladeAltenSprint(CurrentSprint.sprintnummer);
		
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
		
		timer.cancel();
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		sprintbacklogService = new SprintbacklogService();
		sprintbacklogService.ladeSprint();
		
		burndownChartId = CurrentBurndownChart.id;
		tage = CurrentBurndownChart.tage;
		points = CurrentBurndownChart.points;
		
		mainPane.getChildren().removeAll();
		initBurndownChart();
		
		if (sprintbacklogService.ladeAnzahlSprints() <= 1) {
			buttonLoadBurndownChart.setDisable(true);
		}
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						
						try {
							reloadBurndownChart();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		}, 0, 10000);
	}
	
	private void initBurndownChart() {
		xAxis.setLabel("Anzahl der Tage");
		yAxis.setLabel("Offener Entwicklungsaufwand in Stunden");
		
		lineChart.setTitle("zu Sprint " + CurrentSprint.sprintnummer);
		
		lineChart.setMinWidth(886);
		lineChart.setMinHeight(422);
		lineChart.setAnimated(false);
		
		series = new XYChart.Series<>();
		
		series.setName("Real");
		
		series2 = new XYChart.Series<>();
		series2.setName("Geplant");
		
		if (points != null && points.size() > 0) {
			
			for (int i = 0; i < points.size(); i++) {
				series.getData().add(new Data("Tag " + i, points.get(i).getY()));
			}
			
			series2.getData().add(new Data("Tag 0", points.get(0).getY()));
			if (points.size() > 0) {
				series2.getData().add(new Data("Tag " + (points.size() - 1), points.get(points.size() - 1).getY()));
			}
			
			lineChart.getData().add(series);
			lineChart.getData().add(series2);
		}
		
		mainPane.getChildren().add(lineChart);
	}
	
	public void reloadBurndownChart() {
		sprintbacklogService = new SprintbacklogService();
		sprintbacklogService.ladeSprint();
		burndownChartId = CurrentBurndownChart.id;
		tage = CurrentBurndownChart.tage;
		points = CurrentBurndownChart.points;
		
		lineChart.getData().remove(series);
		lineChart.getData().remove(series2);
		
		series.getData().clear();
		series2.getData().clear();
		
		if (points != null && points.size() > 0) {
			
			for (int i = 0; i < points.size(); i++) {
				series.getData().add(new Data("Tag " + i, points.get(i).getY()));
			}
			
			series2.getData().add(new Data("Tag 0", points.get(0).getY()));
			if (points.size() > 0) {
				series2.getData().add(new Data("Tag " + (points.size() - 1), points.get(points.size() - 1).getY()));
			}
			
			lineChart.getData().add(series);
			lineChart.getData().add(series2);
		}
		
		lineChart.setTitle("zu Sprint " + CurrentSprint.sprintnummer);
	}
	
}
