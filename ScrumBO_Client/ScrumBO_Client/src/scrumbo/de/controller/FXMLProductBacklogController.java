package scrumbo.de.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

import com.google.gson.Gson;

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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.ProductBacklog;
import scrumbo.de.entity.UserStory;

public class FXMLProductBacklogController implements Initializable {
	
	Parent									root;
	Scene									scene;
	Stage									stage;
	public static UserStory					rowData;
	@FXML
	private Text							vorname;
	@FXML
	private Text							nachname;
	@FXML
	private Text							benutzerrolle;
	@FXML
	private Text							projektname;
	@FXML
	private Button							buttonLogout;
	@FXML
	private Button							buttonBack;
	@FXML
	private Button							buttonCreateUserStory;
	@FXML
	private Button							buttonReload;
	@FXML
	private TableView<UserStory>			tableViewProductBacklog;
	@FXML
	private TableColumn<UserStory, Integer>	tableColumnPrioritaet;
	@FXML
	private TableColumn<UserStory, String>	tableColumnThema;
	@FXML
	private TableColumn<UserStory, String>	tableColumnBeschreibung;
	@FXML
	private TableColumn<UserStory, String>	tableColumnAkzeptanzkriterien;
	@FXML
	private TableColumn<UserStory, Integer>	tableColumnAufwand;
											
	public static ObservableList<UserStory>	data	= FXCollections.observableArrayList();
													
	public static ObservableList<UserStory> getData() {
		return data;
	}
	
	@FXML
	private void handleButtonReload(ActionEvent event) throws Exception {
		reload();
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
	private void handleCreatUserStory(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/FXMLUserStoryCreate.fxml"));
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
					}
				}
			});
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleButtonLogout(ActionEvent event) throws Exception {
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
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		vorname.setText(CurrentBenutzer.vorname);
		nachname.setText(CurrentBenutzer.nachname);
		benutzerrolle.setText(CurrentBenutzer.benutzerrolle);
		projektname.setText(CurrentScrumprojekt.projektname);
		
		tableViewProductBacklog.setRowFactory(tv -> {
			TableRow<UserStory> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					rowData = row.getItem();
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(
								getClass().getResource("/scrumbo/de/gui/FXMLUserStoryEdit.fxml"));
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
								} catch (IOException e) {
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
		
		ladeProductBacklog(CurrentScrumprojekt.productbacklog.getId());
		
		tableColumnPrioritaet.setCellValueFactory(new PropertyValueFactory<UserStory, Integer>("prioritaet"));
		tableColumnThema.setCellValueFactory(new PropertyValueFactory<UserStory, String>("thema"));
		tableColumnBeschreibung.setCellValueFactory(new PropertyValueFactory<UserStory, String>("beschreibung"));
		tableColumnBeschreibung
				.setCellFactory(new Callback<TableColumn<UserStory, String>, TableCell<UserStory, String>>() {
					@Override
					public TableCell<UserStory, String> call(TableColumn<UserStory, String> param) {
						final TableCell<UserStory, String> cell = new TableCell<UserStory, String>() {
							private Text text;
							
							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (!isEmpty()) {
									text = new Text(item.toString());
									text.setWrappingWidth(280);
									
									setGraphic(text);
								} else {
									text = null;
									setGraphic(null);
								}
							}
						};
						return cell;
					}
				});
		tableColumnAkzeptanzkriterien
				.setCellValueFactory(new PropertyValueFactory<UserStory, String>("akzeptanzkriterien"));
		tableColumnAkzeptanzkriterien
				.setCellFactory(new Callback<TableColumn<UserStory, String>, TableCell<UserStory, String>>() {
					@Override
					public TableCell<UserStory, String> call(TableColumn<UserStory, String> param) {
						final TableCell<UserStory, String> cell = new TableCell<UserStory, String>() {
							private Text text;
							
							@Override
							public void updateItem(String item, boolean empty) {
								super.updateItem(item, empty);
								if (!isEmpty()) {
									text = new Text(item.toString());
									text.setWrappingWidth(270);
									
									setGraphic(text);
								} else {
									text = null;
									setGraphic(null);
								}
							}
						};
						return cell;
					}
				});
		tableColumnAufwand.setCellValueFactory(new PropertyValueFactory<UserStory, Integer>("aufwandintagen"));
		
		tableViewProductBacklog.setItems(data);
		
	}
	
	public void ladeProductBacklog(Integer id) {
		String output = "";
		Integer platz = -1;
		try {
			
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/productbacklog/suche/"
					+ CurrentScrumprojekt.productbacklog.getId() + "/" + ScrumBOClient.getDatabaseconfigfile());
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
		ProductBacklog a = gson.fromJson(output, ProductBacklog.class);
		CurrentScrumprojekt.productbacklog = a;
		
		for (int i = 0; i < CurrentScrumprojekt.productbacklog.getUserstory().size(); i++) {
			data.add(CurrentScrumprojekt.productbacklog.getUserstory().get(i));
		}
		
	}
	
	public void reload() throws IOException {
		tableViewProductBacklog.getItems().clear();
		ladeProductBacklog(CurrentScrumprojekt.productbacklog.getId());
	}
	
}
