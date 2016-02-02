/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumbo.de.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.Role;
import scrumbo.de.entity.User;
import scrumbo.de.service.BenutzerService;

/**
 *
 * @author Serdar
 */
public class UserAssignForProjectController implements Initializable {
	
	Parent					root;
	Scene					scene;
	List<Role>				liste			= null;
	BenutzerService			benutzerService	= null;
	private List<User>		benutzerList	= new LinkedList<User>();
	private User			currentBenutzer	= null;
	@FXML
	private Button			buttonBackAssignUser;
	@FXML
	private Button			buttonAssignUser;
	@FXML
	private RadioButton		radiobuttonProductOwner;
	@FXML
	private RadioButton		radiobuttonDeveloper;
	@FXML
	private Text			validFail;
	@FXML
	private ComboBox<User>	comboBoxUser	= new ComboBox<>();
											
	@FXML
	private void handleBackButtonAssignUser(ActionEvent event) throws IOException {
		Stage stage = (Stage) buttonAssignUser.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleButtonAssignUser(ActionEvent event) throws Exception {
		boolean status = false;
		Integer roleId = 0;
		Integer userId = -1;
		
		if (currentBenutzer == null) {
			validFail.setText("Bitte einen Benutzer auswählen");
			validFail.setVisible(true);
			validFail.setStyle("-fx-border-color:#FF0000;");
		} else {
			if (checkRole()) {
				validFail.setStyle(null);
				validFail.setVisible(false);
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Benutzer zuweisen");
				alert.setHeaderText(null);
				alert.setContentText("Wollen Sie den ausgewählten User Ihrem Projekt zuweisen?");
				
				ButtonType buttonTypeOne = new ButtonType("Ja");
				ButtonType buttonTypeTwo = new ButtonType("Nein");
				alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
				
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == buttonTypeOne) {
					alert.close();
					userId = currentBenutzer.getId();
					
					if (radiobuttonProductOwner.isSelected()) {
						roleId = 2;
					} else if (radiobuttonDeveloper.isSelected()) {
						roleId = 3;
					}
					
					URL url = null;
					HttpURLConnection conn = null;
					try {
						url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
								+ "/ScrumBO_Server/rest/benutzer/assign/" + userId + "/" + roleId + "/"
								+ CurrentProject.projectId + "/" + ScrumBOClient.getDatabaseconfigfile());
						conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("GET");
						conn.setRequestProperty("Accept", "application/json");
						
						if (conn.getResponseCode() != 200) {
							throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
						}
						
						BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
						String output = br.readLine();
						conn.disconnect();
						
						if (output.equals("Ok")) {
							status = true;
							
						}
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					if (status) {
						Stage stage = (Stage) buttonAssignUser.getScene().getWindow();
						stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
					} else {
						Stage stage = (Stage) buttonAssignUser.getScene().getWindow();
						stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
					}
				} else {
					alert.close();
				}
				
			}
		}
		
	}
	
	private Boolean checkRole() {
		if ((radiobuttonProductOwner.isSelected()) || (radiobuttonDeveloper.isSelected())) {
			validFail.setText(null);
			radiobuttonProductOwner.setStyle(null);
			radiobuttonDeveloper.setStyle(null);
			return true;
		} else {
			validFail.setText("Bitte eine Rolle auswählen");
			radiobuttonProductOwner.setStyle("-fx-border-color:#FF0000;");
			radiobuttonDeveloper.setStyle("-fx-border-color:#FF0000;");
			return false;
		}
	}
	
	public void ladeRollen() {
		URL url2 = null;
		HttpURLConnection conn = null;
		
		try {
			url2 = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/role/all" + "/" + ScrumBOClient.getDatabaseconfigfile());
			conn = (HttpURLConnection) url2.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = "";
			JSONArray o = null;
			while ((output = br.readLine()) != null) {
				o = new JSONArray(output);
			}
			conn.disconnect();
			
			Gson gson = new Gson();
			Type listType = new TypeToken<LinkedList<Role>>() {
			}.getType();
			this.liste = gson.fromJson(o.toString(), listType);
			
		} catch (
		
		Exception e)
		
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		buttonAssignUser.setDisable(true);
		ladeRollen();
		
		benutzerService = StartwindowController.getBenutzerService();
		benutzerList = benutzerService.ladeBenutzerOhneProjektzugriff();
		ObservableList<User> options = FXCollections.observableArrayList();
		for (int i = 0; i < benutzerList.size(); i++) {
			if (benutzerList.get(i).getId() != CurrentUser.userId)
				options.add(benutzerList.get(i));
		}
		comboBoxUser.getItems().addAll(options);
		comboBoxUser.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				buttonAssignUser.setDisable(false);
				comboBoxUser.requestFocus();
			}
		});
		
		comboBoxUser.setConverter(new StringConverter<User>() {
			@Override
			public String toString(User u) {
				return u.getVorname() + " " + u.getNachname();
			}
			
			@Override
			public User fromString(String string) {
				throw new UnsupportedOperationException();
			}
		});
		
		comboBoxUser.valueProperty().addListener((ChangeListener<User>) (ov, t, t1) -> currentBenutzer = t1);
	}
	
}
