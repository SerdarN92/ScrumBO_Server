package scrumbo.de.controller;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.Benutzer;
import scrumbo.de.entity.CurrentBenutzer;

public class FXMLUserChangePasswordController implements Initializable {
	
	Parent					root;
	Scene					scene;
	@FXML
	private Button			buttonSave;
	@FXML
	private PasswordField	txtPassword;
	@FXML
	private PasswordField	txtPassword2;
	@FXML
	private Text			txtName;
	@FXML
	private Text			txtPasswortValid;
							
	@FXML
	private void handleButtonSave(ActionEvent event) throws Exception {
		if (passwordCheck()) {
			Benutzer benutzer = new Benutzer();
			benutzer.setId(CurrentBenutzer.benutzerID);
			benutzer.setVorname(CurrentBenutzer.vorname);
			benutzer.setNachname(CurrentBenutzer.nachname);
			benutzer.setEmail(CurrentBenutzer.email);
			benutzer.setPasswort(txtPassword.getText());
			
			Gson gson = new Gson();
			String output = gson.toJson(benutzer);
			
			JSONObject jsonObject = new JSONObject(output);
			try {
				URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/benutzer/updatePassword/"
						+ CurrentBenutzer.email + "/" + ScrumBOClient.getDatabaseconfigfile());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setConnectTimeout(5000);
				conn.setReadTimeout(5000);
				conn.setRequestMethod("POST");
				
				OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
				
				if (conn.getResponseMessage().equals("Passwort vom Benutzer geupdated"))
					System.out.println("\nRest Service Invoked Successfully..");
				conn.disconnect();
				
				if (CurrentBenutzer.isSM) {
					this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLProject.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonSave.getScene().getWindow();
					stage.setScene(scene);
				} else {
					this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLProjectLogin.fxml"));
					this.scene = new Scene(root);
					Stage stage = (Stage) buttonSave.getScene().getWindow();
					stage.setScene(scene);
				}
			} catch (Exception e) {
				System.out.println("\nError while calling Rest service");
				e.printStackTrace();
				// this.root =
				// FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLUserCreateMasterFail.fxml"));
				// this.scene = new Scene(root);
				// Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				// stage.setScene(scene);
			}
			
		}
	}
	
	private Boolean passwordCheck() {
		Boolean check = false;
		if (txtPassword.getText().isEmpty() || txtPassword2.getText().isEmpty()) {
			txtPasswortValid.setText("Bitte füllen Sie beide Felder mit einem identischem neuem Passwort.");
			if (!(txtPasswortValid.isVisible()))
				txtPasswortValid.setVisible(true);
		} else if (!(txtPassword.getText().equals(txtPassword2.getText()))) {
			txtPasswortValid.setText("Die von Ihnen eingegebenen Passwörter sind nicht identisch");
			if (!(txtPasswortValid.isVisible()))
				txtPasswortValid.setVisible(true);
		} else if (txtPassword.getText().equals("12345678")) {
			txtPasswortValid.setText(
					"Das von Ihnen eingegebene Passwort ist identisch mit dem Default-Kennwort, welches unzulässig ist.");
			if (!(txtPasswortValid.isVisible()))
				txtPasswortValid.setVisible(true);
		} else if (txtPassword.getText().length() < 8) {
			txtPasswortValid
					.setText("Das von Ihnen eingegebene Passwort ist kleiner als die Länge 8, welches unzulässig ist.");
			if (!(txtPasswortValid.isVisible()))
				txtPasswortValid.setVisible(true);
				
		} else {
			if ((txtPasswortValid.isVisible()))
				txtPasswortValid.setVisible(false);
			check = true;
		}
		return check;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txtName.setText(CurrentBenutzer.vorname + " " + CurrentBenutzer.nachname + ",");
		
	}
	
}
