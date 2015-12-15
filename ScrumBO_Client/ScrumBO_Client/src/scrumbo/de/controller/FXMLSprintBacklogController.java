package scrumbo.de.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import com.google.gson.Gson;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.common.MyHBox;
import scrumbo.de.entity.CurrentBenutzer;
import scrumbo.de.entity.CurrentScrumprojekt;
import scrumbo.de.entity.ProductBacklog;
import scrumbo.de.entity.UserStory;

public class FXMLSprintBacklogController implements Initializable {
	
	Parent									root;
	Scene									scene;
	Stage									stage;
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
	private Button							buttonAddUserStory;
	@FXML
	private VBox							VBOXUserStories;
	public static ObservableList<UserStory>	dataProductBacklog	= FXCollections.observableArrayList();
	
	public static ObservableList<UserStory> getData() {
		return dataProductBacklog;
	}
	
	@FXML
	private void handleButtonAddUserStory(ActionEvent event) throws Exception {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(
					getClass().getResource("/scrumbo/de/gui/FXMLSprintBacklogUserStory.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(new Scene(root1));
			// stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			// @Override
			// public void handle(WindowEvent event) {
			// try {
			// reload();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			// }
			// });
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleButtonBack(ActionEvent event) throws Exception {
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
	public void initialize(URL location, ResourceBundle resources) {
		vorname.setText(CurrentBenutzer.vorname);
		nachname.setText(CurrentBenutzer.nachname);
		benutzerrolle.setText(CurrentBenutzer.benutzerrolle);
		projektname.setText(CurrentScrumprojekt.projektname);
		
		ScrollPane sp = new ScrollPane();
		sp.setFitToHeight(true);
		sp.setContent(VBOXUserStories);
		sp.setVisible(true);
		
		ladeProductBacklog(CurrentScrumprojekt.productbacklog.get(0).getId());
		initSprintBacklog();
		
		for (int i = 0; i < dataProductBacklog.size(); i++) {
			UserStory userstory = dataProductBacklog.get(i);
			MyHBox hb = new MyHBox(userstory);
			VBOXUserStories.getChildren().add(hb);
		}
		
	}
	
	void initSprintBacklog() {
		
		HBox hbox = new HBox();
		hbox.setMinHeight(30);
		hbox.setMinWidth(883);
		hbox.setStyle(" -fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: black");
		VBox vboxprio = new VBox();
		vboxprio.setMinWidth(100);
		vboxprio.setAlignment(Pos.CENTER);
		vboxprio.setStyle(" -fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: black");
		Text text1 = new Text("Priorität");
		vboxprio.getChildren().add(text1);
		VBox vboxuserstory = new VBox();
		vboxuserstory.setMinWidth(183);
		vboxuserstory.setAlignment(Pos.CENTER);
		vboxuserstory.setStyle(" -fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: black");
		Text text2 = new Text("User Story");
		vboxuserstory.getChildren().add(text2);
		VBox vboxopentask = new VBox();
		vboxopentask.setMinWidth(200);
		vboxopentask.setAlignment(Pos.CENTER);
		vboxopentask.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: black");
		Text text3 = new Text("Offene Tasks");
		vboxopentask.getChildren().add(text3);
		VBox vboxinworktask = new VBox();
		vboxinworktask.setMinWidth(200);
		vboxinworktask.setAlignment(Pos.CENTER);
		vboxinworktask.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: black");
		Text text4 = new Text("Tasks in Arbeit");
		vboxinworktask.getChildren().add(text4);
		VBox vboxdonetask = new VBox();
		vboxdonetask.setMinWidth(199);
		vboxdonetask.setAlignment(Pos.CENTER);
		vboxdonetask.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: black");
		Text text5 = new Text("Erledigte Tasks");
		vboxdonetask.getChildren().add(text5);
		
		hbox.getChildren().add(vboxprio);
		hbox.getChildren().add(vboxuserstory);
		hbox.getChildren().add(vboxopentask);
		hbox.getChildren().add(vboxinworktask);
		hbox.getChildren().add(vboxdonetask);
		VBOXUserStories.getChildren().add(hbox);
		
	}
	
	public void ladeProductBacklog(Integer id) {
		String output = "";
		Integer platz = -1;
		try {
			for (int i = 0; i < CurrentScrumprojekt.productbacklog.size(); i++) {
				if (CurrentScrumprojekt.productbacklog.get(i).getId().equals(id))
					platz = i;
			}
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/productbacklog/sucheSB/"
					+ CurrentScrumprojekt.productbacklog.get(platz).getId() + "/"
					+ ScrumBOClient.getDatabaseconfigfile());
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
		List<ProductBacklog> list = new LinkedList<ProductBacklog>();
		list.add(a);
		CurrentScrumprojekt.productbacklog = list;
		dataProductBacklog.clear();
		for (int i = 0; i < CurrentScrumprojekt.productbacklog.get(0).getUserstory().size(); i++) {
			dataProductBacklog.add(CurrentScrumprojekt.productbacklog.get(0).getUserstory().get(i));
		}
		
	}
	
	public void reloadProductBacklog() throws IOException {
		dataProductBacklog.clear();
		ladeProductBacklog(CurrentScrumprojekt.productbacklog.get(0).getId());
	}
	
}
