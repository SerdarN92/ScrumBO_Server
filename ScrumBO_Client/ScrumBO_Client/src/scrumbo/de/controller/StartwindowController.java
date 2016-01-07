package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import scrumbo.de.service.BenutzerService;
import scrumbo.de.service.BenutzerrolleService;
import scrumbo.de.service.DefinitionOfDoneService;
import scrumbo.de.service.ImpedimentService;
import scrumbo.de.service.ProductbacklogService;
import scrumbo.de.service.ScrumprojektService;
import scrumbo.de.service.SprintbacklogService;
import scrumbo.de.service.TaskService;
import scrumbo.de.service.UserstoryService;

public class StartwindowController implements Initializable {
	
	Parent									root;
	Scene									scene;
											
	@FXML
	private ImageView						adminImage;
	@FXML
	private ImageView						loginUserImage;
	@FXML
	private ImageView						informationImage;
											
	private static BenutzerService			benutzerService			= null;
	private static BenutzerrolleService		benutzerrolleService	= null;
	private static ScrumprojektService		scrumprojektService		= null;
	private static ImpedimentService		impedimentService		= null;
	private static ProductbacklogService	productbacklogService	= null;
	private static UserstoryService			userstoryService		= null;
	private static TaskService				taskService				= null;
	private static SprintbacklogService		sprintbacklogService	= null;
	private static DefinitionOfDoneService	definitionofdoneService	= null;
																	
	@FXML
	private void handleClickAdminImage(MouseEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/AdminLogin.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) adminImage.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleClickLoginUserImage(MouseEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/UserLogin.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) loginUserImage.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		if (benutzerService == null)
			benutzerService = new BenutzerService();
		if (benutzerrolleService == null)
			benutzerrolleService = new BenutzerrolleService();
		if (scrumprojektService == null)
			scrumprojektService = new ScrumprojektService();
		if (impedimentService == null)
			impedimentService = new ImpedimentService();
		if (productbacklogService == null)
			productbacklogService = new ProductbacklogService();
		if (userstoryService == null)
			userstoryService = new UserstoryService();
		if (taskService == null)
			taskService = new TaskService();
		if (sprintbacklogService == null)
			sprintbacklogService = new SprintbacklogService();
		if (definitionofdoneService == null)
			definitionofdoneService = new DefinitionOfDoneService();
			
		Tooltip tooltip = new Tooltip(
				"Herzlich Willkommen zum ScrumBO! \nDiese Applikation ermöglicht es Ihnen Projektmanagement \nnach Scrum zu betreiben.");
		Tooltip.install(informationImage, tooltip);
	}
	
	public static BenutzerService getBenutzerService() {
		return benutzerService;
	}
	
	public static void setBenutzerService(BenutzerService benutzerService) {
		StartwindowController.benutzerService = benutzerService;
	}
	
	public static BenutzerrolleService getBenutzerrolleService() {
		return benutzerrolleService;
	}
	
	public static void setBenutzerrolleService(BenutzerrolleService benutzerrolleService) {
		StartwindowController.benutzerrolleService = benutzerrolleService;
	}
	
	public static ScrumprojektService getScrumprojektService() {
		return scrumprojektService;
	}
	
	public static void setScrumprojektService(ScrumprojektService scrumprojektService) {
		StartwindowController.scrumprojektService = scrumprojektService;
	}
	
	public static ImpedimentService getImpedimentService() {
		return impedimentService;
	}
	
	public static void setImpedimentService(ImpedimentService impedimentService) {
		StartwindowController.impedimentService = impedimentService;
	}
	
	public static ProductbacklogService getProductbacklogService() {
		return productbacklogService;
	}
	
	public static void setProductbacklogService(ProductbacklogService productbacklogService) {
		StartwindowController.productbacklogService = productbacklogService;
	}
	
	public static UserstoryService getUserstoryService() {
		return userstoryService;
	}
	
	public static void setUserstoryService(UserstoryService userstoryService) {
		StartwindowController.userstoryService = userstoryService;
	}
	
	public static TaskService getTaskService() {
		return taskService;
	}
	
	public static void setTaskService(TaskService taskService) {
		StartwindowController.taskService = taskService;
	}
	
	public static SprintbacklogService getSprintbacklogService() {
		return sprintbacklogService;
	}
	
	public static void setSprintbacklogService(SprintbacklogService sprintbacklogService) {
		StartwindowController.sprintbacklogService = sprintbacklogService;
	}
	
	public static DefinitionOfDoneService getDefinitionofdoneService() {
		return definitionofdoneService;
	}
	
	public static void setDefinitionofdoneService(DefinitionOfDoneService definitionofdoneService) {
		StartwindowController.definitionofdoneService = definitionofdoneService;
	}
	
}
