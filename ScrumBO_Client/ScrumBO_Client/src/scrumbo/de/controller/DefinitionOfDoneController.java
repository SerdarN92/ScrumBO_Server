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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
		definitionofdoneService.ladeDefinitionOfDone();
		
		data.clear();
		
		for (int i = 0; i < CurrentUserStory.dod.size(); i++) {
			data.add(CurrentUserStory.dod.get(i));
		}
		
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		definitionofdoneService = StartwindowController.getDefinitionofdoneService();
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
		
		ladeDefinitionOfDone();
		
		tableColumnKriterien.setCellValueFactory(new PropertyValueFactory<DefinitionOfDone, String>("kriterium"));
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<DefinitionOfDone, Boolean>("status"));
		
		tableViewDefinitionOfDone.setItems(data);
	}
}
