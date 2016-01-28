package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import scrumbo.de.entity.CurrentProject;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.Impediment;
import scrumbo.de.service.ImpedimentService;

public class ImpedimentBacklogController implements Initializable {
	
	Parent										root;
	Scene										scene;
	Stage										stage;
	ImpedimentService							impedimentService	= null;
	@FXML
	private ImageView							informationImage;
	private Tooltip								tooltipIB			= new Tooltip(
			"Ein Impediment Backlog besteht aus den Eintragungen von Hindernissen, die das\n"
					+ "Entwicklungsteam an effektiver Arbeit hindern inklusive Datum des Auftretens und Datum der Behebung.");
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
												
	public static ObservableList<Impediment>	data				= FXCollections.observableArrayList();
																	
	private Timer								timer;
												
	public static ObservableList<Impediment> getData() {
		return data;
	}
	
	@FXML
	private void handleButtonBack(ActionEvent event) throws Exception {
		data.clear();
		if (CurrentUser.isSM) {
			timer.cancel();
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/ScrumSM.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonLogout.getScene().getWindow();
			stage.setScene(scene);
		} else {
			timer.cancel();
			this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Scrum.fxml"));
			this.scene = new Scene(root);
			Stage stage = (Stage) buttonLogout.getScene().getWindow();
			stage.setScene(scene);
		}
	}
	
	@FXML
	private void handleButtonLogout(ActionEvent event) throws Exception {
		data.clear();
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
		
		timer.cancel();
		
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonLogout.getScene().getWindow();
		stage.setScene(scene);
		
	}
	
	@FXML
	private void handleButtonAddImpediment(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/scrumbo/de/gui/ImpedimentCreate.fxml"));
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		impedimentService = StartwindowController.getImpedimentService();
		vorname.setText(CurrentUser.prename);
		nachname.setText(CurrentUser.lastname);
		benutzerrolle.setText(CurrentUser.role);
		projektname.setText(CurrentProject.projectname);
		
		impedimentService.getImpedimentBacklog();
		
		data.clear();
		
		for (int i = 0; i < CurrentProject.impedimentbacklog.size(); i++) {
			data.add(CurrentProject.impedimentbacklog.get(i));
		}
		
		tableViewImpedimentBacklog.setRowFactory(new Callback<TableView<Impediment>, TableRow<Impediment>>() {
			@Override
			public TableRow<Impediment> call(TableView<Impediment> tableViewImpedimentBacklog) {
				final TableRow<Impediment> row = new TableRow<Impediment>() {
					@Override
					protected void updateItem(Impediment impediment, boolean empty) {
						super.updateItem(impediment, empty);
						if (impediment != null) {
							if (impediment.getDatumDerBehebung() == null) {
								setStyle("-fx-background-color: #FE6B6B;");
							} else {
								setStyle("-fx-background-color: #90FE74;");
							}
						} else {
							setStyle(null);
						}
					}
				};
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 2 && (!row.isEmpty())) {
						rowData = row.getItem();
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(
									getClass().getResource("/scrumbo/de/gui/ImpedimentEdit.fxml"));
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
			}
		});
		
		tableColumnPrioritaet.setCellValueFactory(new PropertyValueFactory<Impediment, Integer>("priorität"));
		tableColumnBeschreibung.setCellValueFactory(new PropertyValueFactory<Impediment, String>("beschreibung"));
		tableColumnErstelltVon.setCellValueFactory(new PropertyValueFactory<Impediment, String>("mitarbeiter"));
		tableColumnErstelltAm.setCellValueFactory(new PropertyValueFactory<Impediment, Date>("datumDesAuftretens"));
		tableColumnBehobenAm.setCellValueFactory(new PropertyValueFactory<Impediment, Date>("datumDerBehebung"));
		tableColumnKommentar.setCellValueFactory(new PropertyValueFactory<Impediment, String>("kommentar"));
		
		tableViewImpedimentBacklog.setItems(data);
		
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						
						try {
							reload();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
			}
		}, 0, 10000);
		
		Tooltip.install(informationImage, tooltipIB);
		
	}
	
	@FXML
	private void handleButtonReload(ActionEvent event) throws Exception {
		reload();
	}
	
	private void reload() throws Exception {
		tableViewImpedimentBacklog.getItems().clear();
		impedimentService.getImpedimentBacklog();
		
		data.clear();
		
		for (int i = 0; i < CurrentProject.impedimentbacklog.size(); i++) {
			data.add(CurrentProject.impedimentbacklog.get(i));
		}
	}
	
}
