/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumbo.de.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.common.Encryptor;
import scrumbo.de.common.LetterTextField;
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.Role;
import scrumbo.de.entity.User;
import scrumbo.de.service.UserService;

/**
 * FXML Controller Klasse für die Erstellung eines Benutzers
 *
 * @author Serdar
 */
public class UserCreateForProjectController implements Initializable {
	
	Parent					root;
	Scene					scene;
	List<Role>				liste	= null;
	@FXML
	private LetterTextField	txtFieldPrename;
	@FXML
	private LetterTextField	txtFieldLastname;
	@FXML
	private TextField		txtFieldEmail;
	@FXML
	private Button			buttonBackCreateUser;
	@FXML
	private Button			buttonCreateUser;
	@FXML
	private RadioButton		radiobuttonScrumMaster;
	@FXML
	private RadioButton		radiobuttonProductOwner;
	@FXML
	private RadioButton		radiobuttonDeveloper;
	@FXML
	private Text			emailValidFail;
	@FXML
	private Text			prenameValidFail;
	@FXML
	private Text			lastnameValidFail;
	@FXML
	private Text			roleValidFail;
							
	@FXML
	private void handleKeyPressed(KeyEvent event) throws JSONException, IOException, Exception {
		if (event.getCode().equals(KeyCode.ENTER))
			createUserForProject();
	}
	
	@FXML
	private void handleBackButtonCreateUser(ActionEvent event) throws IOException {
		Stage stage = (Stage) buttonCreateUser.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleButtonCreateUser(ActionEvent event) throws Exception {
		createUserForProject();
	}
	
	private void createUserForProject() throws JSONException, IOException {
		Integer benutzerrollenId = 0;
		UserService benutzerService = new UserService();
		if (checkPreName() && checkLastName() && checkEmail() && checkRole()) {
			if (benutzerService.checkEmail(txtFieldEmail.getText())) {
				emailValidFail.setText("E-Mail Adresse bereits vorhanden.");
				emailValidFail.setVisible(true);
				txtFieldEmail.setStyle("-fx-border-color:#FF0000;");
			} else {
				txtFieldEmail.setStyle(null);
				emailValidFail.setVisible(false);
				
				User benutzer = new User();
				benutzer.setVorname(txtFieldPrename.getText());
				benutzer.setNachname(txtFieldLastname.getText());
				benutzer.setEmail(txtFieldEmail.getText());
				benutzer.setPasswort(Encryptor.encrypt("12345678"));
				
				Role benutzerrolle = null;
				
				String benutzerrolleSelection = "";
				
				if (radiobuttonProductOwner.isSelected()) {
					benutzerrolleSelection = radiobuttonProductOwner.getText();
					benutzerrollenId = 2;
				} else if (radiobuttonDeveloper.isSelected()) {
					benutzerrolleSelection = radiobuttonDeveloper.getText();
					benutzerrollenId = 3;
				} else if (radiobuttonScrumMaster.isSelected()) {
					benutzerrolleSelection = radiobuttonScrumMaster.getText();
					benutzerrollenId = 1;
				}
				
				for (int i = 0; i < liste.size(); i++) {
					if (benutzerrolleSelection.equals(liste.get(i).getBezeichnung())) {
						benutzerrolle = new Role(liste.get(i).getId(), liste.get(i).getBezeichnung());
					}
				}
				
				Gson gson = new Gson();
				String output = gson.toJson(benutzer);
				
				JSONObject jsonObject = new JSONObject(output);
				
				try {
					URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
							+ "/ScrumBO_Server/rest/benutzer/create/" + benutzerrollenId + "/"
							+ CurrentProject.projectId + "/" + ScrumBOClient.getDatabaseconfigfile());
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setRequestProperty("Content-Type", "application/json");
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(5000);
					conn.setRequestMethod("POST");
					
					OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
					out.write(jsonObject.toString());
					out.close();
					
					if (conn.getResponseMessage().equals("User erfolgreich erstellt"))
						System.out.println("\nRest Service Invoked Successfully..");
					conn.disconnect();
					
					this.root = FXMLLoader
							.load(getClass().getResource("/scrumbo/de/gui/UserCreateScrumMasterSuccess.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonCreateUser.getScene().getWindow();
					stage.setScene(scene);
				} catch (Exception e) {
					System.out.println("\nError while calling Rest service");
					e.printStackTrace();
					this.root = FXMLLoader
							.load(getClass().getResource("/scrumbo/de/gui/UserCreateScrumMasterFail.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonCreateUser.getScene().getWindow();
					stage.setScene(scene);
				}
				
			}
		}
	}
	
	private Boolean checkEmail() {
		if (txtFieldEmail.getText().contains("@")) {
			emailValidFail.setText(null);
			txtFieldEmail.setStyle(null);
			return true;
		} else {
			emailValidFail.setText("Ungültige E-Mail");
			txtFieldEmail.setStyle("-fx-border-color:#FF0000;");
			return false;
		}
	}
	
	private Boolean checkPreName() {
		if (!(txtFieldPrename.getText().isEmpty())) {
			prenameValidFail.setText(null);
			txtFieldPrename.setStyle(null);
			return true;
		} else {
			prenameValidFail.setText("Bitte einen Vornamen eingeben");
			txtFieldPrename.setStyle("-fx-border-color:#FF0000;");
			return false;
		}
	}
	
	private Boolean checkLastName() {
		if (!(txtFieldLastname.getText().isEmpty())) {
			lastnameValidFail.setText(null);
			txtFieldLastname.setStyle(null);
			return true;
		} else {
			lastnameValidFail.setText("Bitte einen Nachnamen eingeben");
			txtFieldLastname.setStyle("-fx-border-color:#FF0000;");
			return false;
		}
	}
	
	private Boolean checkRole() {
		if ((radiobuttonProductOwner.isSelected()) || (radiobuttonDeveloper.isSelected())
				|| (radiobuttonScrumMaster.isSelected())) {
			roleValidFail.setText(null);
			radiobuttonScrumMaster.setStyle(null);
			radiobuttonProductOwner.setStyle(null);
			radiobuttonDeveloper.setStyle(null);
			return true;
		} else {
			roleValidFail.setText("Bitte eine Rolle auswählen");
			radiobuttonScrumMaster.setStyle("-fx-border-color:#FF0000;");
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
		ladeRollen();
	}
	
	private Boolean checkIfEmailExists(String email) throws JSONException {
		try {
			URL url = new URL("http://" + ScrumBOClient.getHost() + ":" + ScrumBOClient.getPort()
					+ "/ScrumBO_Server/rest/benutzer/suche/" + email + "/" + ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = br.readLine();
			conn.disconnect();
			
			Gson gson = new Gson();
			User a = null;
			if (output.equals("User ist nicht vorhanden")) {
				return false;
				
			} else {
				a = gson.fromJson(output, User.class);
				if (email.equals(a.getEmail())) {
					return true;
				} else {
					return false;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
