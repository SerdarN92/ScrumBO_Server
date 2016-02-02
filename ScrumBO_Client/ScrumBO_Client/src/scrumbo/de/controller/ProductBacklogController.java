package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.UserStory;
import scrumbo.de.service.ProductbacklogService;

public class ProductBacklogController implements Initializable {
	
	Parent									root;
	Scene									scene;
	public static Stage						stage;
	ProductbacklogService					productbacklogService	= null;
	public static UserStory					rowData;
	@FXML
	private ImageView						informationImage;
	private Tooltip							tooltipPB				= new Tooltip(
			"Ein Product Backlog besteht aus User Stories, die vom Product Owner priorisiert werden.\n"
					+ "Die Aufwände der User Stories werden vom Entwicklungsteam geschätzt, beispielsweise in einer Schätzklausur.\n"
					+ "Eine User Story muss innerhalb eines Sprints realisierbar sein.\n"
					+ "Das Product Backlog ist nicht vollständig und verändert sich im Laufe des Projekts.\n"
					+ "Die Anforderungen können vom Kunden nach Bedarf verändert werden. Es wird üblich priorisiert.\n"
					+ "Es dürfen also mehr als eine User Story die Priorität 1 erhalten.\n"
					+ "Der Product Owner legt die Reihenfolge der User Stories fest. Die hoch priorisierten User Stories sollten, falls\n"
					+ "möglich zuerst abgearbeitet werden. Daher wird im Sprint Planning Meeting nicht über die Reihenfolge der Abarbeitung\n"
					+ "der User Stories diskutiert sondern nur über die Anzahl.");
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
	@FXML
	private TableColumn<UserStory, Integer>	tableColumnSprintNummer;
											
	public static ObservableList<UserStory>	data					= FXCollections.observableArrayList();
																	
	private Timer							timer;
											
	private ObservableValue<Integer>		obsInt					= null;
																	
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
		if (CurrentUser.isSM) {
			// timer.cancel();
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/ScrumSM.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonLogout.getScene().getWindow();
			stage.setScene(scene);
		} else {
			// timer.cancel();
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Scrum.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonLogout.getScene().getWindow();
			stage.setScene(scene);
		}
	}
	
	@FXML
	private void handleCreatUserStory(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/UserStoryCreate.fxml"));
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
		CurrentUser.userId = -1;
		CurrentUser.prename = null;
		CurrentUser.lastname = null;
		CurrentUser.email = null;
		CurrentUser.password = null;
		CurrentUser.roleId = -1;
		CurrentUser.role = null;
		CurrentUser.projects = null;
		CurrentProject.projectId = -1;
		CurrentProject.projectname = null;
		CurrentUser.isSM = false;
		
		// timer.cancel();
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		productbacklogService = StartwindowController.getProductbacklogService();
		vorname.setText(CurrentUser.prename);
		nachname.setText(CurrentUser.lastname);
		benutzerrolle.setText(CurrentUser.role);
		projektname.setText(CurrentProject.projectname);
		
		if (!CurrentUser.isPO)
			buttonCreateUserStory.setDisable(true);
			
		initTableView();
		
		// timer = new Timer();
		// timer.schedule(new TimerTask() {
		//
		// @Override
		// public void run() {
		// try {
		// reload();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		// }, 0, 10000);
		
		Tooltip.install(informationImage, tooltipPB);
	}
	
	public void initTableView() {
		
		tableViewProductBacklog.setRowFactory(tv -> {
			TableRow<UserStory> row = new TableRow<>();
			// if (CurrentUser.isPO) {
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 1 && (!row.isEmpty())) {
					rowData = row.getItem();
					try {
						FXMLLoader fxmlLoader = new FXMLLoader(
								getClass().getResource("/scrumbo/de/gui/UserStoryEdit.fxml"));
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
			// }
			return row;
		});
		
		productbacklogService.loadProductBacklog();
		
		for (int i = 0; i < CurrentProject.productbacklog.getUserstory().size(); i++) {
			data.add(CurrentProject.productbacklog.getUserstory().get(i));
		}
		
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<UserStory, Integer>("status"));
		tableColumnStatus.setText(null);
		Label statusLabel = new Label("Status");
		statusLabel.setTooltip(new Tooltip(
				"Status einer User Story.\n" + "Blau = Offen\n" + "Gelb = In Arbeit\n" + "Grün = Erledigt"));
		tableColumnStatus.setGraphic(statusLabel);
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
		tableColumnPrioritaet.setCellValueFactory(new PropertyValueFactory<UserStory, Integer>("priority"));
		tableColumnPrioritaet.setText(null);
		Label prioritaetLabel = new Label("Priorität");
		prioritaetLabel.setTooltip(
				new Tooltip("Priorität einer User Story. Kann vom Entwickler nicht mehr verändert werden."));
		tableColumnPrioritaet.setGraphic(prioritaetLabel);
		tableColumnThema.setCellValueFactory(new PropertyValueFactory<UserStory, String>("theme"));
		tableColumnThema.setText(null);
		Label themaLabel = new Label("Thema");
		themaLabel.setTooltip(new Tooltip("'Grobes' Thema einer User Story."));
		tableColumnThema.setGraphic(themaLabel);
		tableColumnBeschreibung.setCellValueFactory(new PropertyValueFactory<UserStory, String>("description"));
		tableColumnBeschreibung.setText(null);
		Label beschreibungLabel = new Label("Thema");
		beschreibungLabel.setTooltip(new Tooltip("Ausführliche Formulierung der User Story"));
		tableColumnBeschreibung.setGraphic(beschreibungLabel);
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
				.setCellValueFactory(new PropertyValueFactory<UserStory, String>("acceptanceCriteria"));
		tableColumnAkzeptanzkriterien.setText(null);
		Label akLabel = new Label("Akzeptanzkriterien");
		akLabel.setTooltip(new Tooltip("Kriterien, die der Kunde an die User Story hat"));
		tableColumnAkzeptanzkriterien.setGraphic(akLabel);
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
		tableColumnAufwand.setCellValueFactory(new PropertyValueFactory<UserStory, Integer>("effortInDays"));
		tableColumnAufwand.setText(null);
		Label aufwandLabel = new Label("Aufwand in Tagen");
		aufwandLabel.setTooltip(new Tooltip("1 Tag = 8 Stunden"));
		tableColumnAufwand.setGraphic(aufwandLabel);
		tableColumnSprintNummer
				.setCellValueFactory(new Callback<CellDataFeatures<UserStory, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(CellDataFeatures<UserStory, Integer> data) {
						
						if (data.getValue().getSprint() != null) {
							obsInt = new ReadOnlyObjectWrapper<Integer>(data.getValue().getSprint().getSprintnummer());
						} else {
							obsInt = null;
						}
						
						return obsInt;
					}
				});
		tableColumnSprintNummer.setText(null);
		Label sprintnummberLabel = new Label("Sprint Nr.");
		sprintnummberLabel
				.setTooltip(new Tooltip("Die Nummer des Sprints, in welchem die User Story bearbeitet wird/wurde."));
		tableColumnSprintNummer.setGraphic(sprintnummberLabel);
		
		tableViewProductBacklog.setItems(data);
	}
	
	public void reload() throws IOException {
		// tableViewProductBacklog.getItems().clear();
		// productbacklogService.loadProductBacklog();
		// for (int i = 0; i <
		// CurrentScrumprojekt.productbacklog.getUserstory().size(); i++) {
		// data.add(CurrentScrumprojekt.productbacklog.getUserstory().get(i));
		// }
		
		data.clear();
		productbacklogService.loadProductBacklog();
		for (int i = 0; i < CurrentProject.productbacklog.getUserstory().size(); i++) {
			data.add(CurrentProject.productbacklog.getUserstory().get(i));
		}
	}
	
}
