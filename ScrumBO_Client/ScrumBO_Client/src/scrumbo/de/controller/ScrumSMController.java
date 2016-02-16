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
import scrumbo.de.common.MyToolBox;
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
		StartwindowController.logout();
		
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
			
		MyToolBox toolbox = new MyToolBox();
		
		Tooltip.install(imageProductBacklog, toolbox.getTooltipProductBacklog());
		Tooltip.install(imageSprintBacklog, toolbox.getTooltipSprintBacklog());
		Tooltip.install(imageSprintBurndownChart, toolbox.getTooltipBurndownChart());
		Tooltip.install(imageImpedimentBacklog, toolbox.getTooltipImpedimentBacklog());
	}
	
}
