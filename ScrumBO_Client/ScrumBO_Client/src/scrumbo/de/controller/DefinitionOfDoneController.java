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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import scrumbo.de.common.MyToolBox;
import scrumbo.de.entity.CurrentUser;
import scrumbo.de.entity.CurrentUserStory;
import scrumbo.de.entity.DefinitionOfDone;
import scrumbo.de.service.DefinitionOfDoneService;

public class DefinitionOfDoneController implements Initializable {
	
	Parent											root;
	Scene											scene;
	Stage											stage;
	DefinitionOfDoneService							definitionofdoneService	= null;
	public static DefinitionOfDone					rowData;
	@FXML
	private ImageView								informationImage;
	@FXML
	private Button									buttonBack;
	@FXML
	private Button									buttonCreateDefinitionOfDone;
	@FXML
	private Button									buttonReload;
	@FXML
	private TableView<DefinitionOfDone>				tableViewDefinitionOfDone;
	@FXML
	private TableColumn<DefinitionOfDone, String>	tableColumnKriterien;
	@FXML
	private TableColumn<DefinitionOfDone, Boolean>	tableColumnStatus;
													
	public static ObservableList<DefinitionOfDone>	data					= FXCollections.observableArrayList();
																			
	public static ObservableList<DefinitionOfDone> getData() {
		return data;
	}
	
	@FXML
	private void handleButtonBack(ActionEvent event) throws Exception {
		data.clear();
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/UserStoryEdit.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonBack.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@FXML
	private void handleButtonCreateDefinitionOfDone(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/scrumbo/de/gui/DefinitionOfDoneCreate.fxml"));
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
	
	@FXML
	private void handleButtonReload(ActionEvent event) throws Exception {
		reload();
	}
	
	private void reload() throws Exception {
		tableViewDefinitionOfDone.getItems().clear();
		ladeDefinitionOfDone();
	}
	
	public void ladeDefinitionOfDone() {
		definitionofdoneService.loadDefinitionOfDone();
		
		data.clear();
		
		for (int i = 0; i < CurrentUserStory.dod.size(); i++) {
			data.add(CurrentUserStory.dod.get(i));
		}
		
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		definitionofdoneService = StartwindowController.getDefinitionofdoneService();
		if (CurrentUserStory.status != 2 && CurrentUser.isPO) {
			tableViewDefinitionOfDone.setRowFactory(tv -> {
				TableRow<DefinitionOfDone> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 1 && (!row.isEmpty())) {
						rowData = row.getItem();
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(
									getClass().getResource("/scrumbo/de/gui/DefinitionOfDoneEdit.fxml"));
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
		} else {
			buttonCreateDefinitionOfDone.setDisable(true);
		}
		
		ladeDefinitionOfDone();
		
		tableColumnKriterien.setCellValueFactory(new PropertyValueFactory<DefinitionOfDone, String>("kriterium"));
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<DefinitionOfDone, Boolean>("status"));
		tableColumnStatus.setCellFactory(
				new Callback<TableColumn<DefinitionOfDone, Boolean>, TableCell<DefinitionOfDone, Boolean>>() {
					@Override
					public TableCell<DefinitionOfDone, Boolean> call(TableColumn<DefinitionOfDone, Boolean> param) {
						final TableCell<DefinitionOfDone, Boolean> cell = new TableCell<DefinitionOfDone, Boolean>() {
							private boolean status;
							
							@Override
							public void updateItem(Boolean item, boolean empty) {
								super.updateItem(item, empty);
								Circle circle = null;
								if (!isEmpty()) {
									
									status = item;
									
									Text text = new Text();
									
									if (status) {
										text.setText("Erfüllt");
									} else {
										text.setText("Nicht erfüllt");
									}
									
									setGraphic(text);
								} else {
									setGraphic(null);
								}
							}
						};
						return cell;
					}
				});
				
		tableViewDefinitionOfDone.setItems(data);
		
		MyToolBox toolbox = new MyToolBox();
		Tooltip.install(informationImage, toolbox.getTooltipDoD());
		
		if (CurrentUser.isDev || CurrentUser.isSM) {
			buttonCreateDefinitionOfDone.setDisable(true);
		}
	}
}
