package scrumbo.de.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.service.SprintBacklogService;

public class SprintBacklogSprintDaysController implements Initializable {
	
	@FXML
	TextField						txtFieldDays;
	@FXML
	Button							buttonStartSprint;
	@FXML
	Button							buttonAbbort;
	@FXML
	Text							errorTxt;
									
	private SprintBacklogService	sprintBacklogService;
									
	@FXML
	public void handleButtonAbbort() {
		try {
			Stage stage = (Stage) buttonAbbort.getScene().getWindow();
			stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleButtonStartSprint() {
		try {
			if (checkTxtFieldDays()) {
				int sprintDays = Integer.parseInt(txtFieldDays.getText());
				if (sprintBacklogService.startSprint(sprintDays)) {
					System.out.println("Sprint wurde erfolgreich gestartet.");
				} else {
					System.out.println("Fehler");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private boolean isInteger(String str) {
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
	
	private Boolean checkTxtFieldDays() throws Exception {
		if (!(txtFieldDays.getText().isEmpty())) {
			if (isInteger(txtFieldDays.getText())) {
				errorTxt.setText(null);
				txtFieldDays.setStyle(null);
				return true;
			} else {
				errorTxt.setText("Bitte geben Sie eine ganze Zahl ein.");
				txtFieldDays.setStyle("-fx-border-color:#FF0000;");
				return false;
			}
		} else {
			errorTxt.setText("Bitte geben Sie eine ganze Zahl ein.");
			txtFieldDays.setStyle("-fx-border-color:#FF0000;");
			return false;
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sprintBacklogService = new SprintBacklogService();
	}
	
}
