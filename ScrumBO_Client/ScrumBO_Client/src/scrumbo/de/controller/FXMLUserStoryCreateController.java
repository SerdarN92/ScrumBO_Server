package scrumbo.de.controller;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.json.JSONObject;

import com.google.gson.Gson;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.UserStory;

public class FXMLUserStoryCreateController implements Initializable {
	
	Parent				root;
	Scene				scene;
	@FXML
	private TextField	prioritaet;
	@FXML
	private TextField	thema;
	@FXML
	private TextArea	beschreibung;
	@FXML
	private TextArea	akzeptanzkriterien;
	@FXML
	private TextField	aufwandintagen;
	@FXML
	private Button		buttonAbbort;
	@FXML
	private Button		buttonAdd;
	@FXML
	private Text		textprioritaet;
	@FXML
	private Text		textthema;
	@FXML
	private Text		textbeschreibung;
	@FXML
	private Text		textakzeptanzkriterien;
	@FXML
	private Text		textaufwandintagen;
						
	private Boolean checkPrioritaet() {
		if (prioritaet.getText().isEmpty()) {
			textprioritaet.setText("Bitte eine Priorität eingeben");
			return false;
		} else {
			textprioritaet.setVisible(false);
			if (isInteger(prioritaet.getText())) {
				return true;
			} else {
				textprioritaet.setText("Bitte eine ganzzahlige Zahl eingeben.");
				textprioritaet.setVisible(true);
				return false;
			}
			
		}
	}
	
	public static boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		int length = str.length();
		if (length == 0) {
			return false;
		}
		int i = 0;
		if (str.charAt(0) == '-') {
			if (length == 1) {
				return false;
			}
			i = 1;
		}
		for (; i < length; i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}
	
	private Boolean checkThema() {
		if (thema.getText().isEmpty()) {
			textthema.setText("Bitte ein Thema eingeben");
			return false;
		} else {
			textthema.setVisible(false);
			return true;
		}
	}
	
	private Boolean checkBeschreibung() {
		if (beschreibung.getText().isEmpty()) {
			textbeschreibung.setText("Bitte eine Beschreibung eingeben");
			return false;
		} else {
			textbeschreibung.setVisible(false);
			return true;
		}
	}
	
	private Boolean checkAkzeptanzkriterien() {
		if (akzeptanzkriterien.getText().isEmpty()) {
			textakzeptanzkriterien.setText("Bitte ein Akzeptanzkriterium eingeben");
			return false;
		} else {
			textakzeptanzkriterien.setVisible(false);
			return true;
		}
	}
	
	private Boolean checkAufwandInTagen() {
		if (aufwandintagen.getText().isEmpty()) {
			textaufwandintagen.setText("Bitte einen Aufwand(in Tagen) eingeben");
			return false;
		} else {
			textaufwandintagen.setVisible(false);
			if (isInteger(aufwandintagen.getText())) {
				return true;
			} else {
				textaufwandintagen.setText("Bitte eine ganzzahlige Zahl eingeben.");
				textaufwandintagen.setVisible(true);
				return false;
			}
			
		}
	}
	
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		Stage stage = (Stage) buttonAbbort.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleButtonAdd(ActionEvent event) throws Exception {
		if (checkPrioritaet() && checkThema() && checkBeschreibung() && checkAkzeptanzkriterien()
				&& checkAufwandInTagen()) {
			UserStory userstory = new UserStory();
			userstory.setPrioritaet(Integer.parseInt(prioritaet.getText()));
			userstory.setThema(thema.getText());
			userstory.setBeschreibung(beschreibung.getText());
			userstory.setAkzeptanzkriterien(akzeptanzkriterien.getText());
			userstory.setAufwandintagen(Integer.parseInt(aufwandintagen.getText()));
			List<UserStory> userstoryList = CurrentScrumprojekt.productbacklog.getUserstory();
			userstoryList.add(userstory);
			
			Gson gson = new Gson();
			String output = gson.toJson(userstory);
			
			JSONObject jsonObject = new JSONObject(output);
			
			try {
				URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/userstory/create/"
						+ CurrentScrumprojekt.productbacklog.getId() + "/" + ScrumBOClient.getDatabaseconfigfile());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setConnectTimeout(5000);
				conn.setReadTimeout(5000);
				conn.setRequestMethod("POST");
				
				OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
				
				if (conn.getResponseMessage().equals("User Story erfolgreich erstellt"))
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
