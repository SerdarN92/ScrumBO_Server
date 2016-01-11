package scrumbo.de.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.Impediment;
import scrumbo.de.service.ImpedimentService;

public class ImpedimentCreatController implements Initializable {
	
	Parent				root;
	Scene				scene;
	ImpedimentService	impedimentService	= null;
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
	private Text		txtError;
						
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		if (!prioritaet.getText().isEmpty() || !angelegtVon.getText().isEmpty() || !beschreibung.getText().isEmpty()
				|| datumAufgetretenAm.getValue() != null) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Impediment erstellen");
			alert.setHeaderText(null);
			alert.setContentText("Wollen Sie ohne zu Speichern fortfahren?");
			
			ButtonType buttonTypeOne = new ButtonType("Ja");
			ButtonType buttonTypeTwo = new ButtonType("Nein");
			alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeOne) {
				alert.close();
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} else {
				alert.close();
			}
		}
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
			List<Impediment> liste = CurrentProject.impedimentbacklog;
			liste.add(impediment);
			
			if (impedimentService.createImpediment(impediment)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Impediment erstellen");
				alert.setHeaderText(null);
				alert.setContentText("Impediment wurde erfolgreich erstellt.");
				alert.showAndWait();
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} else {
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.close();
			}
			
		}
	}
	
	private Boolean checkPrioritaet() {
		if (prioritaet.getText().isEmpty()) {
			txtError.setText("Bitte eine Priorität eingeben");
			prioritaet.setStyle("-fx-border-color:#FF0000;");
			return false;
		} else {
			txtError.setVisible(false);
			if (isInteger(prioritaet.getText())) {
				prioritaet.setStyle(null);
				return true;
			} else {
				txtError.setText("Bitte eine ganzzahlige Zahl eingeben.");
				txtError.setVisible(true);
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
			txtError.setText("Bitte eine Beschreibung eingeben");
			txtError.setVisible(true);
			beschreibung.setStyle("-fx-border-color:#FF0000;");
			return false;
		} else {
			txtError.setVisible(false);
			beschreibung.setStyle(null);
			return true;
		}
	}
	
	private Boolean checkAngelegtVon() {
		if (angelegtVon.getText().isEmpty()) {
			txtError.setText("Bitte eine Benutzer eingeben");
			txtError.setVisible(true);
			angelegtVon.setStyle("-fx-border-color:#FF0000;");
			return false;
		} else {
			txtError.setVisible(false);
			angelegtVon.setStyle(null);
			return true;
		}
	}
	
	private Boolean checkDatumAngelegtAm() {
		if (datumAufgetretenAm.getValue() == null) {
			txtError.setText("Bitte ein Datum auswählen");
			txtError.setVisible(true);
			datumAufgetretenAm.setStyle("-fx-border-color:#FF0000;");
			return false;
		} else {
			txtError.setVisible(false);
			datumAufgetretenAm.setStyle(null);
			return true;
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		impedimentService = StartwindowController.getImpedimentService();
	}
	
}
