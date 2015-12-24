package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.UserStory;
import scrumbo.de.service.ProductbacklogService;

public class FXMLProductBacklogController implements Initializable {
	
	Parent									root;
	Scene									scene;
	Stage									stage;
	ProductbacklogService					productbacklogService	= null;
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
	private TableColumn<UserStory, Integer>	tableColumnStatus;
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
											
	public static ObservableList<UserStory>	data					= FXCollections.observableArrayList();
																	
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
		productbacklogService = FXMLStartController.getProductbacklogService();
		vorname.setText(CurrentBenutzer.vorname);
		nachname.setText(CurrentBenutzer.nachname);
		benutzerrolle.setText(CurrentBenutzer.benutzerrolle);
		projektname.setText(CurrentScrumprojekt.projektname);
		
		if (!CurrentBenutzer.isPO)
			buttonCreateUserStory.setDisable(true);
			
		initTableView();
		
	}
	
	public void initTableView() {
		tableViewProductBacklog.setRowFactory(tv -> {
			TableRow<UserStory> row = new TableRow<>();
			if (CurrentBenutzer.isPO) {
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
			}
			return row;
		});
		
		productbacklogService.loadProductBacklog();
		
		for (int i = 0; i < CurrentScrumprojekt.productbacklog.getUserstory().size(); i++) {
			data.add(CurrentScrumprojekt.productbacklog.getUserstory().get(i));
		}
		
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<UserStory, Integer>("status"));
		tableColumnStatus
				.setCellFactory(new Callback<TableColumn<UserStory, Integer>, TableCell<UserStory, Integer>>() {
					@Override
					public TableCell<UserStory, Integer> call(TableColumn<UserStory, Integer> param) {
						final TableCell<UserStory, Integer> cell = new TableCell<UserStory, Integer>() {
							private Integer status;
							
							@Override
							public void updateItem(Integer item, boolean empty) {
								super.updateItem(item, empty);
								Circle circle = null;
								if (!isEmpty()) {
									
									status = item;
									
									if (item == 0) {
										circle = new Circle(10, Color.BLUE);
									}
									if (item == 1) {
										circle = new Circle(10, Color.YELLOW);
									}
									if (item == 2) {
										circle = new Circle(10, Color.GREEN);
									}
									
									setGraphic(circle);
								} else {
									setGraphic(null);
								}
							}
						};
						cell.setAlignment(Pos.CENTER);
						return cell;
					}
				});
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
	
	public void reload() throws IOException {
		tableViewProductBacklog.getItems().clear();
		productbacklogService.loadProductBacklog();
		for (int i = 0; i < CurrentScrumprojekt.productbacklog.getUserstory().size(); i++) {
			data.add(CurrentScrumprojekt.productbacklog.getUserstory().get(i));
		}
	}
	
}
