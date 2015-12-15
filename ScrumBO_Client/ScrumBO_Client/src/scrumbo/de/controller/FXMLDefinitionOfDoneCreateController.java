package scrumbo.de.controller;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentUserStory;
import scrumbo.de.entity.DefinitionOfDone;
import scrumbo.de.entity.UserStory;

public class FXMLDefinitionOfDoneCreateController implements Initializable {
	
	Parent				root;
	Scene				scene;
	@FXML
	private TextField	kriterium;
	@FXML
	private Button		buttonAbbort;
	@FXML
	private Button		buttonAdd;
	@FXML
	private Text		txtKriterium;
	
	private Boolean checkKriterium() {
		if (kriterium.getText().isEmpty()) {
			txtKriterium.setText("Bitte ein Kriterium eingeben");
			return false;
		} else {
			txtKriterium.setVisible(false);
			return true;
		}
	}
	
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		Stage stage = (Stage) buttonAbbort.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleButtonAdd(ActionEvent event) throws Exception {
		if (checkKriterium()) {
			DefinitionOfDone definitionOfDone = new DefinitionOfDone();
			definitionOfDone.setKriterium(kriterium.getText());
			definitionOfDone.setStatus(false);
			UserStory userstory = new UserStory();
			userstory.setId(CurrentUserStory.userstoryID);
			definitionOfDone.setUserstory(userstory);
			
			Gson gson = new Gson();
			String output = gson.toJson(definitionOfDone);
			
			JSONObject jsonObject = new JSONObject(output);
			
			try {
				URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/definitionofdone/create/"
						+ ScrumBOClient.getDatabaseconfigfile());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setConnectTimeout(5000);
				conn.setReadTimeout(5000);
				conn.setRequestMethod("POST");
				
				OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
				
				if (conn.getResponseMessage().equals("Definition Of Done erfolgreich erstellt"))
					System.out.println("\nRest Service Invoked Successfully..");
				conn.disconnect();
				
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} catch (Exception e) {
				System.out.println("\nError while calling Rest service");
				e.printStackTrace();
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.close();
			}
			
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
