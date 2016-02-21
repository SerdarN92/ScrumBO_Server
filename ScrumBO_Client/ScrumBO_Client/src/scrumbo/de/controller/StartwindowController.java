package scrumbo.de.controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
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
import javafx.util.Duration;
import scrumbo.de.common.MyToolBox;
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.service.UserService;
import scrumbo.de.service.RoleService;
import scrumbo.de.service.DefinitionOfDoneService;
import scrumbo.de.service.ImpedimentService;
import scrumbo.de.service.ProductBacklogService;
import scrumbo.de.service.ProjectService;
import scrumbo.de.service.SprintBacklogService;
import scrumbo.de.service.TaskService;
import scrumbo.de.service.UserStoryService;

public class StartwindowController implements Initializable {
	
	Parent									root;
	Scene									scene;
											
	@FXML
	private ImageView						adminImage;
	@FXML
	private ImageView						loginUserImage;
	@FXML
	private ImageView						informationImage;
											
	private static UserService			benutzerService			= null;
	private static RoleService		benutzerrolleService	= null;
	private static ProjectService		scrumprojektService		= null;
	private static ImpedimentService		impedimentService		= null;
	private static ProductBacklogService	productbacklogService	= null;
	private static UserStoryService			userstoryService		= null;
	private static TaskService				taskService				= null;
	private static SprintBacklogService		sprintbacklogService	= null;
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
			benutzerService = new UserService();
		if (benutzerrolleService == null)
			benutzerrolleService = new RoleService();
		if (scrumprojektService == null)
			scrumprojektService = new ProjectService();
		if (impedimentService == null)
			impedimentService = new ImpedimentService();
		if (productbacklogService == null)
			productbacklogService = new ProductBacklogService();
		if (userstoryService == null)
			userstoryService = new UserStoryService();
		if (taskService == null)
			taskService = new TaskService();
		if (sprintbacklogService == null)
			sprintbacklogService = new SprintBacklogService();
		if (definitionofdoneService == null)
			definitionofdoneService = new DefinitionOfDoneService();
			
		MyToolBox toolbox = new MyToolBox();
		setupCustomTooltipBehavior(650, 10000, 650);
		Tooltip.install(informationImage, toolbox.getTooltipWelcome());
	}
	
	public static UserService getBenutzerService() {
		return benutzerService;
	}
	
	public static void setBenutzerService(UserService benutzerService) {
		StartwindowController.benutzerService = benutzerService;
	}
	
	public static RoleService getBenutzerrolleService() {
		return benutzerrolleService;
	}
	
	public static void setBenutzerrolleService(RoleService benutzerrolleService) {
		StartwindowController.benutzerrolleService = benutzerrolleService;
	}
	
	public static ProjectService getScrumprojektService() {
		return scrumprojektService;
	}
	
	public static void setScrumprojektService(ProjectService scrumprojektService) {
		StartwindowController.scrumprojektService = scrumprojektService;
	}
	
	public static ImpedimentService getImpedimentService() {
		return impedimentService;
	}
	
	public static void setImpedimentService(ImpedimentService impedimentService) {
		StartwindowController.impedimentService = impedimentService;
	}
	
	public static ProductBacklogService getProductbacklogService() {
		return productbacklogService;
	}
	
	public static void setProductbacklogService(ProductBacklogService productbacklogService) {
		StartwindowController.productbacklogService = productbacklogService;
	}
	
	public static UserStoryService getUserstoryService() {
		return userstoryService;
	}
	
	public static void setUserstoryService(UserStoryService userstoryService) {
		StartwindowController.userstoryService = userstoryService;
	}
	
	public static TaskService getTaskService() {
		return taskService;
	}
	
	public static void setTaskService(TaskService taskService) {
		StartwindowController.taskService = taskService;
	}
	
	public static SprintBacklogService getSprintbacklogService() {
		return sprintbacklogService;
	}
	
	public static void setSprintbacklogService(SprintBacklogService sprintbacklogService) {
		StartwindowController.sprintbacklogService = sprintbacklogService;
	}
	
	public static DefinitionOfDoneService getDefinitionofdoneService() {
		return definitionofdoneService;
	}
	
	public static void setDefinitionofdoneService(DefinitionOfDoneService definitionofdoneService) {
		StartwindowController.definitionofdoneService = definitionofdoneService;
	}
	
	public static void setupCustomTooltipBehavior(int openDelayInMillis, int visibleDurationInMillis,
			int closeDelayInMillis) {
		try {
			
			Class TTBehaviourClass = null;
			Class<?>[] declaredClasses = Tooltip.class.getDeclaredClasses();
			for (Class c : declaredClasses) {
				if (c.getCanonicalName().equals("javafx.scene.control.Tooltip.TooltipBehavior")) {
					TTBehaviourClass = c;
					break;
				}
			}
			if (TTBehaviourClass == null) {
				// abort
				return;
			}
			Constructor constructor = TTBehaviourClass.getDeclaredConstructor(Duration.class, Duration.class,
					Duration.class, boolean.class);
			if (constructor == null) {
				// abort
				return;
			}
			constructor.setAccessible(true);
			Object newTTBehaviour = constructor.newInstance(new Duration(openDelayInMillis),
					new Duration(visibleDurationInMillis), new Duration(closeDelayInMillis), false);
			if (newTTBehaviour == null) {
				// abort
				return;
			}
			Field ttbehaviourField = Tooltip.class.getDeclaredField("BEHAVIOR");
			if (ttbehaviourField == null) {
				// abort
				return;
			}
			ttbehaviourField.setAccessible(true);
			
			// Cache the default behavior if needed.
			Object defaultTTBehavior = ttbehaviourField.get(Tooltip.class);
			ttbehaviourField.set(Tooltip.class, newTTBehaviour);
			
		} catch (Exception e) {
			System.out.println("Aborted setup due to error:" + e.getMessage());
		}
	}
	
	public static void logout() throws Exception {
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
		CurrentUser.isPO = false;
		CurrentUser.isDev = false;
	}
	
}
