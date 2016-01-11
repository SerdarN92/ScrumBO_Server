package scrumbo.de.controller;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

public class ImpedimentEditController implements Initializable {
	
	Parent				root;
	Scene				scene;
	ImpedimentService	impedimentService	= null;
	private Integer		id					= -1;
	@FXML
	private Button		buttonAbbort;
	@FXML
	private Button		buttonSave;
	@FXML
	private Button		buttonDelete;
	@FXML
	private TextField	prioritaet;
	@FXML
	private TextField	angelegtVon;
	@FXML
	private TextArea	beschreibung;
	@FXML
	private TextArea	kommentar;
	@FXML
	private DatePicker	datumAufgetretenAm;
	@FXML
	private DatePicker	datumBehobenAm;
	@FXML
	private Text		txtError;
	private Impediment	data				= null;
											
	@FXML
	private void handleButtonAbbort(ActionEvent event) throws Exception {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Impediment bearbeiten");
		alert.setHeaderText(null);
		alert.setContentText("Wollen Sie fortfahren ohne zu Speichern?");
		
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
	
	@FXML
	private void handleButtonDelete(ActionEvent event) throws Exception {
		Impediment impediment = data;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Impediment löschen");
		alert.setHeaderText(null);
		alert.setContentText("Wollen Sie dieses Impediment wirklich löschen?");
		
		ButtonType buttonTypeOne = new ButtonType("Ja");
		ButtonType buttonTypeTwo = new ButtonType("Nein");
		alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == buttonTypeOne) {
			alert.close();
			if (impedimentService.deleteImpediment(impediment)) {
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("Impediment löschen");
				alert2.setHeaderText(null);
				alert2.setContentText("Impediment wurde erfolgreich gelöscht.");
				alert2.showAndWait();
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} else {
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("Impediment löschen");
				alert2.setHeaderText(null);
				alert2.setContentText("Fehler! Impediment wurde nicht gelöscht.");
				alert2.showAndWait();
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.close();
			}
		} else {
			alert.close();
		}
		
	}
	
	@FXML
	private void handleButtonSave(ActionEvent event) throws Exception {
		if (checkPrioritaet() && checkAngelegtVon() && checkBeschreibung() && checkDatumAngelegtAm()) {
			Impediment impediment = data;
			impediment.setPriorität(Integer.parseInt(prioritaet.getText()));
			impediment.setMitarbeiter(angelegtVon.getText());
			impediment.setBeschreibung(beschreibung.getText());
			Date date = Date.from(datumAufgetretenAm.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String b = sdf.format(date);
			impediment.setDatumDesAuftretens(b);
			if (datumBehobenAm.getValue() != null) {
				Date date2 = Date.from(datumBehobenAm.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
				SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
				String b2 = sdf.format(date2);
				impediment.setDatumDerBehebung(b2);
			} else {
				impediment.setDatumDerBehebung(null);
			}
			impediment.setKommentar(kommentar.getText());
			List<Impediment> liste = CurrentProject.impedimentbacklog;
			liste.add(impediment);
			
			if (impedimentService.updateImpediment(impediment)) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Impediment bearbeiten");
				alert.setHeaderText(null);
				alert.setContentText("Impediment wurde erfolgreich bearbeitet!");
				alert.showAndWait();
				Stage stage = (Stage) buttonAbbort.getScene().getWindow();
				stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Impediment bearbeiten");
				alert.setHeaderText(null);
				alert.setContentText("Fehler! Impediment wurde nicht erfolgreich bearbeitet!");
				alert.showAndWait();
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
		data = ImpedimentBacklogController.rowData;
		prioritaet.setText(data.getPriorität().toString());
		angelegtVon.setText(data.getMitarbeiter());
		beschreibung.setText(data.getBeschreibung());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date datumAuftreten = null;
		if (data.getDatumDesAuftretens() != null) {
			try {
				datumAuftreten = sdf.parse(data.getDatumDesAuftretens());
				LocalDate dateA = datumAuftreten.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				datumAufgetretenAm.setValue(dateA);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			datumAufgetretenAm.setValue(null);
		}
		Date datumBehoben = null;
		if (data.getDatumDerBehebung() != null) {
			try {
				datumBehoben = sdf.parse(data.getDatumDerBehebung());
				LocalDate dateB = datumBehoben.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				datumBehobenAm.setValue(dateB);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			datumBehobenAm.setValue(null);
		}
		kommentar.setText(data.getKommentar());
	}
	
}
