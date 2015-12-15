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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.DefinitionOfDone;

public class FXMLDefinitionOfDoneEditController implements Initializable {
	
	Parent						root;
	Scene						scene;
	private static Integer		id		= -1;
	@FXML
	private TextField			kriterium;
	@FXML
	private RadioButton			erfuellt;
	@FXML
	private RadioButton			nichtErfuellt;
	@FXML
	private Button				buttonDelete;
	@FXML
	private Button				buttonAbbort;
	@FXML
	private Button				buttonSave;
	@FXML
	private Text				txtKriterium;
	private DefinitionOfDone	data	= null;
	private final ToggleGroup	group	= new ToggleGroup();
	
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
	private void handleButtonDelete(ActionEvent event) throws Exception {
		DefinitionOfDone definitionOfDone = data;
		Gson gson = new Gson();
		String output = gson.toJson(definitionOfDone);
		
		JSONObject jsonObject = new JSONObject(output);
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/definitionofdone/delete/"
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
			
			if (conn.getResponseMessage().equals("Definition Of Done erfolgreich gel�scht"))
				System.out.println("\nRest Service Invoked Successfully..");
			conn.disconnect();
			
			Stage stage = (Stage) buttonDelete.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		} catch (Exception e) {
			System.out.println("\nError while calling Rest service");
			e.printStackTrace();
			Stage stage = (Stage) buttonAbbort.getScene().getWindow();
			stage.close();
		}
	}
	
	@FXML
	private void handleButtonSave(ActionEvent event) throws Exception {
		if (checkKriterium()) {
			DefinitionOfDone definitionOfDone = data;
			definitionOfDone.setKriterium(kriterium.getText());
			if (erfuellt.isSelected()) {
				definitionOfDone.setStatus(true);
			}
			if (nichtErfuellt.isSelected()) {
				definitionOfDone.setStatus(false);
			}
			
			Gson gson = new Gson();
			String output = gson.toJson(definitionOfDone);
			
			JSONObject jsonObject = new JSONObject(output);
			try {
				URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/definitionofdone/update/"
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
				
				if (conn.getResponseMessage().equals("Definition Of Done erfolgreich geupdated"))
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
		data = FXMLDefinitionOfDoneController.rowData;
		System.out.println(data.getKriterium());
		System.out.println("Hallo");
		kriterium.setText(data.getKriterium());
		if (data.isStatus()) {
			erfuellt.setSelected(true);
			nichtErfuellt.setSelected(false);
		} else {
			erfuellt.setSelected(false);
			nichtErfuellt.setSelected(true);
		}
		
		erfuellt.setToggleGroup(group);
		nichtErfuellt.setToggleGroup(group);
	}
	
}
