package scrumbo.de.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.CurrentUser;

public class ScrumSMController implements Initializable {
	
	Parent					root;
	Scene					scene;
	SprintBacklogController	controller	= null;
	@FXML
	private Text			name;
	@FXML
	private Text			projektname;
	@FXML
	private Button			buttonLogout;
	@FXML
	private Button			buttonAddProjectUser;
	@FXML
	private Button			buttonAssignProjectUser;
	@FXML
	private ImageView		imageProductBacklog;
	@FXML
	private ImageView		imageSprintBacklog;
	@FXML
	private ImageView		imageSprintBurndownChart;
	@FXML
	private ImageView		imageImpedimentBacklog;
	private Tooltip			tooltipPB	= new Tooltip(
			"Ein Product Backlog besteht aus User Stories, die vom Product Owner priorisiert werden.\n"
					+ "Die Aufwände der User Stories werden vom Entwicklungsteam geschätzt, beispielsweise in einer Schätzklausur.\n"
					+ "Eine User Story muss innerhalb eines Sprints realisierbar sein.\n"
					+ "Das Product Backlog ist nicht vollständig und verändert sich im Laufe des Projekts.\n"
					+ "Die Anforderungen können vom Kunden nach Bedarf verändert werden. Es wird üblich priorisiert.\n"
					+ "Es dürfen also mehr als eine User Story die Priorität 1 erhalten.\n"
					+ "Der Product Owner legt die Reihenfolge der User Stories fest. Die hoch priorisierten User Stories sollten, falls\n"
					+ "möglich zuerst abgearbeitet werden. Daher wird im Sprint Planning Meeting nicht über die Reihenfolge der Abarbeitung\n"
					+ "der User Stories diskutiert sondern nur über die Anzahl.");
	private Tooltip			tooltipSB	= new Tooltip(
			"Ein Sprint Backlog wird im Laufe des Sprints verändert und entsteht beim Sprint Planung.\n"
					+ "Es enthält die User Stories, die in dem aktuellen Sprint bearbeitet werden.\n"
					+ "User Stories werden bezüglich ihrer technischen Anforderungen untersucht und in Tasks aufgeteilt,\n"
					+ "deren Aufwand (jetzt genauer) geschätzt wird. In einem Sprint sollen die Entwickler die Tasks\n"
					+ "zu den hoch priorisierten User Stories des Sprints zuerst abarbeiten.\n"
					+ "Gibt es mehr als eine User Story mit derselben Priorisierung, wird keine weitere Angabe\n"
					+ "zur Reihenfolge gemacht. Sind Tasks einer User Story in Bearbeitung und noch weitere\n"
					+ "Tasks dieser User Story vorhanden, so soll ein Entwickler, der ein neues Task zweck\n"
					+ "Bearbeitung aussucht, erst ein Task der aktuellen bearbeiteten User Story aussuchen.");
	private Tooltip			tooltipSBC	= new Tooltip(
			"Die Fortschrittsanalyse eines Projektes erfolgt bei Scrum innerhalb der Sprints anhand\n"
					+ "eines Burndown Charts. Das Burndown Chart gibt Auskunft über die noch zu leistende Arbeit ab dem aktuellen Tag. Auf der\n"
					+ "y-Achse wird der geschätzte verbleibende Aufwand aller Aufgaben des Sprints in Tagen angezeigt und auf der\n"
					+ "x-Achse die Anzahl der Arbeitstage.");
	private Tooltip			tooltipIB	= new Tooltip(
			"Ein Impediment Backlog besteht aus den Eintragungen von Hindernissen, die das\n"
					+ "Entwicklungsteam an effektiver Arbeit hindern inklusive Datum des Auftretens und Datum der Behebung.");
					
	@FXML
	private void handleButtonAssignProjectUser(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/UserAssignForProject.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.show();
			stage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleButtonAddProjectUser(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/UserCreateForProject.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.show();
			stage.setResizable(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleButtonLogout(ActionEvent event) throws Exception {
		CurrentUser.userId = -1;
		CurrentUser.prename = null;
		CurrentUser.lastname = null;
		CurrentUser.email = null;
		CurrentUser.password = null;
		CurrentUser.roleId = -1;
		CurrentUser.role = null;
		CurrentUser.projects = null;
		CurrentProject.projectId = -1;
		CurrentProject.projectname = null;
		CurrentUser.isSM = false;
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		stage.setResizable(false);
	}
	
	@FXML
	private void handleImageProductbacklog(MouseEvent event) throws Exception {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/ProductBacklog.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		stage.setResizable(false);
	}
	
	@FXML
	private void handleImageSprintbacklog(MouseEvent event) throws Exception {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/SprintBacklog.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		stage.setResizable(false);
	}
	
	@FXML
	private void handleImageBurndownChart(MouseEvent event) throws Exception {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/BurndownChart.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		stage.setResizable(false);
	}
	
	@FXML
	private void handleImageImpedimentbacklog(MouseEvent event) throws Exception {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/ImpedimentBacklog.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		stage.setResizable(false);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		name.setText(CurrentUser.prename + " " + CurrentUser.lastname);
		projektname.setText(CurrentProject.projectname);
		
		if (!CurrentUser.isSM)
			buttonAddProjectUser.setVisible(false);
			
		Tooltip.install(imageProductBacklog, tooltipPB);
		Tooltip.install(imageSprintBacklog, tooltipSB);
		Tooltip.install(imageSprintBurndownChart, tooltipSBC);
		Tooltip.install(imageImpedimentBacklog, tooltipIB);
	}
	
}
