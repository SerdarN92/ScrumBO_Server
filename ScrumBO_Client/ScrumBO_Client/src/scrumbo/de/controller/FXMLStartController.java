package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class FXMLStartController implements Initializable {
	
	Parent									root;
	Scene									scene;
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
	private ImageView						adminImage;
	@FXML
	private ImageView						loginUserImage;
											
	@FXML
	private void handleCreateUserAction(MouseEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLAdminLogin.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) adminImage.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleLoginUserAction(MouseEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLUserLogin.fxml"));
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
		if (definitionofdoneService == null) {
			definitionofdoneService = new DefinitionOfDoneService();
		}
	}
	
	public static BenutzerService getBenutzerService() {
		return benutzerService;
	}
	
	public static void setBenutzerService(BenutzerService benutzerService) {
		FXMLStartController.benutzerService = benutzerService;
	}
	
	public static BenutzerrolleService getBenutzerrolleService() {
		return benutzerrolleService;
	}
	
	public static void setBenutzerrolleService(BenutzerrolleService benutzerrolleService) {
		FXMLStartController.benutzerrolleService = benutzerrolleService;
	}
	
	public static ScrumprojektService getScrumprojektService() {
		return scrumprojektService;
	}
	
	public static void setScrumprojektService(ScrumprojektService scrumprojektService) {
		FXMLStartController.scrumprojektService = scrumprojektService;
	}
	
	public static ImpedimentService getImpedimentService() {
		return impedimentService;
	}
	
	public static void setImpedimentService(ImpedimentService impedimentService) {
		FXMLStartController.impedimentService = impedimentService;
	}
	
	public static ProductbacklogService getProductbacklogService() {
		return productbacklogService;
	}
	
	public static void setProductbacklogService(ProductbacklogService productbacklogService) {
		FXMLStartController.productbacklogService = productbacklogService;
	}
	
	public static UserstoryService getUserstoryService() {
		return userstoryService;
	}
	
	public static void setUserstoryService(UserstoryService userstoryService) {
		FXMLStartController.userstoryService = userstoryService;
	}
	
	public static TaskService getTaskService() {
		return taskService;
	}
	
	public static void setTaskService(TaskService taskService) {
		FXMLStartController.taskService = taskService;
	}
	
	public static SprintbacklogService getSprintbacklogService() {
		return sprintbacklogService;
	}
	
	public static void setSprintbacklogService(SprintbacklogService sprintbacklogService) {
		FXMLStartController.sprintbacklogService = sprintbacklogService;
	}
	
	public static DefinitionOfDoneService getDefinitionofdoneService() {
		return definitionofdoneService;
	}
	
	public static void setDefinitionofdoneService(DefinitionOfDoneService definitionofdoneService) {
		FXMLStartController.definitionofdoneService = definitionofdoneService;
	}
	
}
