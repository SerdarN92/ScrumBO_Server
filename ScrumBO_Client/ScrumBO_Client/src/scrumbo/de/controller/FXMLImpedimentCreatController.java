package scrumbo.de.controller;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.Impediment;

public class FXMLImpedimentCreatController implements Initializable {
	
	Parent				root;
	Scene				scene;
	@FXML
	private Button		buttonAbbort;
	@FXML
	private Button		buttonAdd;
	@FXML
	private TextField	prioritaet;
	@FXML
	private TextField	angelegtVon;
	@FXML
	private TextArea	beschreibung;
	@FXML
	private DatePicker	datumAufgetretenAm;
	@FXML
	private Text		textprioritaet;
	@FXML
	private Text		textbeschreibung;
	@FXML
	private Text		textAngelegtVon;
	@FXML
	private Text		textDatumAufgetretenAm;
	
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		Stage stage = (Stage) buttonAbbort.getScene().getWindow();
		stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
	}
	
	@FXML
	private void handleButtonAdd(ActionEvent event) throws Exception {
		if (checkPrioritaet() && checkAngelegtVon() && checkBeschreibung() && checkDatumAngelegtAm()) {
			Impediment impediment = new Impediment();
			impediment.setPriorität(Integer.parseInt(prioritaet.getText()));
			impediment.setMitarbeiter(angelegtVon.getText());
			impediment.setBeschreibung(beschreibung.getText());
			Date date = Date.from(datumAufgetretenAm.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String b = sdf.format(date);
			impediment.setDatumDesAuftretens(b);
			List<Impediment> liste = CurrentScrumprojekt.impedimentbacklog;
			liste.add(impediment);
			
			Gson gson = new Gson();
			String output = gson.toJson(impediment);
			
			JSONObject jsonObject = new JSONObject(output);
			try {
				URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/impedimentbacklog/create/"
						+ CurrentScrumprojekt.scrumprojektID + "/" + ScrumBOClient.getDatabaseconfigfile());
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type", "application/json");
				conn.setConnectTimeout(5000);
				conn.setReadTimeout(5000);
				conn.setRequestMethod("POST");
				
				OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
				
				if (conn.getResponseMessage().equals("Impediment erfolgreich erstellt"))
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
	
	private Boolean checkBeschreibung() {
		if (beschreibung.getText().isEmpty()) {
			textbeschreibung.setText("Bitte eine Beschreibung eingeben");
			return false;
		} else {
			textbeschreibung.setVisible(false);
			return true;
		}
	}
	
	private Boolean checkAngelegtVon() {
		if (angelegtVon.getText().isEmpty()) {
			textAngelegtVon.setText("Bitte eine Benutzer eingeben");
			return false;
		} else {
			textAngelegtVon.setVisible(false);
			return true;
		}
	}
	
	private Boolean checkDatumAngelegtAm() {
		if (datumAufgetretenAm.getValue() == null) {
			textDatumAufgetretenAm.setText("Bitte ein Datum auswählen");
			return false;
		} else {
			textDatumAufgetretenAm.setVisible(false);
			return true;
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
	
}
