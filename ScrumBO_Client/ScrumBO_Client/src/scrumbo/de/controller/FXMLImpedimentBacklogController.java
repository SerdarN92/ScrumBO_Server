package scrumbo.de.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.Impediment;

public class FXMLImpedimentBacklogController implements Initializable {
	
	Parent										root;
	Scene										scene;
	Stage										stage;
	public static Impediment					rowData;
	@FXML
	private Text								vorname;
	@FXML
	private Text								nachname;
	@FXML
	private Text								benutzerrolle;
	@FXML
	private Text								projektname;
	@FXML
	private Button								buttonLogout;
	@FXML
	private Button								buttonBack;
	@FXML
	private Button								buttonAddImpediment;
	@FXML
	private TableView<Impediment>				tableViewImpedimentBacklog;
	@FXML
	private TableColumn<Impediment, Integer>	tableColumnPrioritaet;
	@FXML
	private TableColumn<Impediment, String>		tableColumnBeschreibung;
	@FXML
	private TableColumn<Impediment, String>		tableColumnErstelltVon;
	@FXML
	private TableColumn<Impediment, Date>		tableColumnErstelltAm;
	@FXML
	private TableColumn<Impediment, Date>		tableColumnBehobenAm;
	@FXML
	private TableColumn<Impediment, String>		tableColumnKommentar;
	
	public static ObservableList<Impediment> data = FXCollections.observableArrayList();
	
	public static ObservableList<Impediment> getData() {
		return data;
	}
	
	@FXML
	private void handleButtonBack(ActionEvent event) throws Exception {
		data.clear();
		if (CurrentBenutzer.isSM) {
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLScrumSM.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonLogout.getScene().getWindow();
			stage.setScene(scene);
		} else {
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLScrum.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonLogout.getScene().getWindow();
			stage.setScene(scene);
		}
	}
	
	@FXML
	private void handleButtonLogout(ActionEvent event) throws Exception {
		data.clear();
		CurrentBenutzer.benutzerID = -1;
		CurrentBenutzer.vorname = null;
		CurrentBenutzer.nachname = null;
		CurrentBenutzer.email = null;
		CurrentBenutzer.passwort = null;
		CurrentBenutzer.benutzerrollenID = -1;
		CurrentBenutzer.benutzerrolle = null;
		CurrentBenutzer.projekte = null;
		CurrentScrumprojekt.scrumprojektID = -1;
		CurrentScrumprojekt.projektname = null;
		CurrentBenutzer.isSM = false;
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/FXMLStartfenster.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		
	}
	
	@FXML
	private void handleButtonAddImpediment(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/FXMLImpedimentCreate.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					try {
						reload();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// public void ladeImpedimentBacklog() {
	// for (int i = 0; i < CurrentScrumprojekt.impedimentbacklog.size(); i++) {
	// data.add(CurrentScrumprojekt.impedimentbacklog.get(i));
	// }
	//
	// }
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vorname.setText(CurrentBenutzer.vorname);
		nachname.setText(CurrentBenutzer.nachname);
		benutzerrolle.setText(CurrentBenutzer.benutzerrolle);
		projektname.setText(CurrentScrumprojekt.projektname);
		ladeImpedimentBacklog(CurrentScrumprojekt.scrumprojektID);
		
		tableViewImpedimentBacklog.setRowFactory(tv -> {
			TableRow<Impediment> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					rowData = row.getItem();
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(
								getClass().getResource("/scrumbo/de/gui/FXMLImpedimentEdit.fxml"));
						Parent root1 = (Parent) fxmlLoader.load();
						Stage stage = new Stage();
						stage.initModality(Modality.APPLICATION_MODAL);
						stage.setScene(new Scene(root1));
						stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent event) {
								try {
									data.clear();
									reload();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						stage.show();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			return row;
		});
		
		tableColumnPrioritaet.setCellValueFactory(new PropertyValueFactory<Impediment, Integer>("priorität"));
		tableColumnBeschreibung.setCellValueFactory(new PropertyValueFactory<Impediment, String>("beschreibung"));
		tableColumnErstelltVon.setCellValueFactory(new PropertyValueFactory<Impediment, String>("mitarbeiter"));
		tableColumnErstelltAm.setCellValueFactory(new PropertyValueFactory<Impediment, Date>("datumDesAuftretens"));
		tableColumnBehobenAm.setCellValueFactory(new PropertyValueFactory<Impediment, Date>("datumDerBehebung"));
		tableColumnKommentar.setCellValueFactory(new PropertyValueFactory<Impediment, String>("kommentar"));
		
		tableViewImpedimentBacklog.setItems(data);
		
	}
	
	@FXML
	private void handleButtonReload(ActionEvent event) throws Exception {
		reload();
	}
	
	private void reload() throws Exception {
		tableViewImpedimentBacklog.getItems().clear();
		ladeImpedimentBacklog(CurrentScrumprojekt.scrumprojektID);
	}
	
	public void ladeImpedimentBacklog(Integer id) {
		String output = "";
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/impedimentbacklog/suche/"
					+ CurrentScrumprojekt.scrumprojektID + "/" + ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json" + ";charset=utf-8");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
			output = br.readLine();
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Gson gson = new Gson();
		Type listType = new TypeToken<LinkedList<Impediment>>() {
		}.getType();
		List<Impediment> liste = gson.fromJson(output, listType);
		CurrentScrumprojekt.impedimentbacklog = liste;
		
		data.clear();
		
		for (int i = 0; i < CurrentScrumprojekt.impedimentbacklog.size(); i++) {
			data.add(CurrentScrumprojekt.impedimentbacklog.get(i));
		}
	}
	
}
