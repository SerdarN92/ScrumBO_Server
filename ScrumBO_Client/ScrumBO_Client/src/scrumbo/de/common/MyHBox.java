package scrumbo.de.common;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import scrumbo.de.app.ScrumBOClient;
import scrumbo.de.entity.UserStory;
import scrumbo.de.entity.UserStoryTask;

public class MyHBox extends HBox {
	
	public UserStory		userstory		= new UserStory();
	private Text			prioritaet		= new Text();
	private Text			beschreibung	= new Text();
	private VBox			vboxprioritaet	= new VBox();
	private VBox			vboxuserstory	= new VBox();
	private VBox			vboxopentasks	= new VBox();
	private VBox			vboxtasksinwork	= new VBox();
	private VBox			vboxdonetasks	= new VBox();
	private final StackPane	taskPane2		= new StackPane();
	private final StackPane	taskPane3		= new StackPane();
	private Integer			bla				= 0;
	private Pane			currentPane		= new Pane();
											
	public static UserStory	blabla			= new UserStory();
											
	public static void setUserStory(UserStory userstory) {
		blabla = userstory;
	}
	
	private void initMyHBox() {
		prioritaet.setText(userstory.getPrioritaet().toString());
		vboxprioritaet.setMinWidth(100);
		vboxprioritaet.setAlignment(Pos.CENTER);
		vboxprioritaet.getChildren().add(prioritaet);
		vboxprioritaet.setStyle("-fx-border-style: solid;" + "-fx-border-width: 0.3;" + "-fx-border-color: black");
		StringBuilder sb = new StringBuilder(userstory.getBeschreibung());
		int i = 0;
		while (i + 20 < sb.length() && (i = sb.lastIndexOf(" ", i + 20)) != -1) {
			sb.replace(i, i + 1, "\n");
		}
		beschreibung.setText(sb.toString());
		vboxuserstory.setMinWidth(183);
		vboxuserstory.setMaxWidth(183);
		beschreibung.setWrappingWidth(vboxuserstory.getWidth());
		vboxuserstory.setAlignment(Pos.CENTER);
		vboxuserstory.getChildren().add(beschreibung);
		vboxuserstory.setStyle("-fx-border-style: solid;" + "-fx-border-width: 0.3;" + "-fx-border-color: black");
		vboxopentasks.setMinWidth(200);
		vboxopentasks.setAlignment(Pos.CENTER);
		vboxopentasks.setSpacing(4);
		vboxopentasks.setStyle(
				"-fx-padding:5px;" + "-fx-border-style: solid;" + "-fx-border-width: 0.3;" + "-fx-border-color: black");
		vboxtasksinwork.setMinWidth(200);
		vboxtasksinwork.setAlignment(Pos.CENTER);
		vboxtasksinwork.setSpacing(4);
		vboxtasksinwork.setStyle(
				"-fx-padding:5px;" + "-fx-border-style: solid;" + "-fx-border-width: 0.3;" + "-fx-border-color: black");
		vboxdonetasks.setMinWidth(198);
		vboxdonetasks.setAlignment(Pos.CENTER);
		vboxdonetasks.setSpacing(4);
		vboxdonetasks.setStyle(
				"-fx-padding:5px;" + "-fx-border-style: solid;" + "-fx-border-width: 0.3;" + "-fx-border-color: black");
				
		vboxtasksinwork.setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
				vboxtasksinwork.setBlendMode(BlendMode.DIFFERENCE);
			}
		});
		
		vboxtasksinwork.setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
				vboxtasksinwork.setBlendMode(null);
			}
		});
		
		vboxtasksinwork.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
				dragEvent.acceptTransferModes(TransferMode.MOVE);
				
				String pane = dragEvent.getDragboard().getString();
				int userstorytaskid = Integer.parseInt(pane);
				int platz = -1;
				if (bla == 1) {
					for (int i = 0; i < vboxopentasks.getChildren().size(); i++) {
						if (vboxopentasks.getChildren().get(i).equals(currentPane)) {
							platz = i;
						}
					}
					vboxtasksinwork.getChildren().add(vboxopentasks.getChildren().get(platz));
					vboxopentasks.getChildren().remove(currentPane);
					int taskstatusid = 2;
					try {
						updateTaskstatus(userstorytaskid, taskstatusid);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (bla == 3) {
					for (int i = 0; i < vboxdonetasks.getChildren().size(); i++) {
						if (vboxdonetasks.getChildren().get(i).equals(currentPane)) {
							platz = i;
						}
					}
					vboxtasksinwork.getChildren().add(vboxdonetasks.getChildren().get(platz));
					vboxdonetasks.getChildren().remove(currentPane);
					int taskstatusid = 2;
					try {
						updateTaskstatus(userstorytaskid, taskstatusid);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		
		vboxtasksinwork.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
			}
		});
		
		vboxopentasks.setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
				vboxopentasks.setBlendMode(BlendMode.DIFFERENCE);
			}
		});
		
		vboxopentasks.setOnDragExited(new EventHandler<DragEvent>() {
			
			@Override
			public void handle(DragEvent dragEvent) {
				vboxopentasks.setBlendMode(null);
				currentPane.setStyle(" -fx-border-radius: 10px; -fx-border-style: solid;" + "-fx-border-width: 0.3;"
						+ "-fx-border-color: black");
			}
		});
		
		vboxopentasks.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
				dragEvent.acceptTransferModes(TransferMode.MOVE);
				
				String pane = dragEvent.getDragboard().getString();
				int userstorytaskid = Integer.parseInt(pane);
				int platz = -1;
				if (bla == 2) {
					System.out.println("VBOXOPENTASK von 2");
					for (int i = 0; i < vboxtasksinwork.getChildren().size(); i++) {
						if (vboxtasksinwork.getChildren().get(i).equals(currentPane)) {
							platz = i;
						}
					}
					vboxopentasks.getChildren().add(vboxtasksinwork.getChildren().get(platz));
					vboxtasksinwork.getChildren().remove(currentPane);
					int taskstatusid = 1;
					try {
						updateTaskstatus(userstorytaskid, taskstatusid);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (bla == 3) {
					System.out.println("VBOXOPENTASK von 3");
					for (int i = 0; i < vboxdonetasks.getChildren().size(); i++) {
						if (vboxdonetasks.getChildren().get(i).equals(currentPane)) {
							platz = i;
						}
					}
					vboxopentasks.getChildren().add(vboxdonetasks.getChildren().get(platz));
					vboxdonetasks.getChildren().remove(currentPane);
					int taskstatusid = 1;
					try {
						updateTaskstatus(userstorytaskid, taskstatusid);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		
		vboxopentasks.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
			}
		});
		
		vboxdonetasks.setOnDragEntered(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
				vboxopentasks.setBlendMode(BlendMode.DIFFERENCE);
			}
		});
		
		vboxdonetasks.setOnDragExited(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
				vboxopentasks.setBlendMode(null);
			}
		});
		
		vboxdonetasks.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
				dragEvent.acceptTransferModes(TransferMode.MOVE);
				
				String pane = dragEvent.getDragboard().getString();
				int userstorytaskid = Integer.parseInt(pane);
				int platz = -1;
				if (bla == 1) {
					for (int i = 0; i < vboxopentasks.getChildren().size(); i++) {
						if (vboxopentasks.getChildren().get(i).equals(currentPane)) {
							platz = i;
						}
					}
					vboxdonetasks.getChildren().add(vboxopentasks.getChildren().get(platz));
					vboxopentasks.getChildren().remove(currentPane);
					int taskstatusid = 3;
					try {
						updateTaskstatus(userstorytaskid, taskstatusid);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (bla == 2) {
					for (int i = 0; i < vboxtasksinwork.getChildren().size(); i++) {
						if (vboxtasksinwork.getChildren().get(i).equals(currentPane)) {
							platz = i;
						}
					}
					vboxdonetasks.getChildren().add(vboxtasksinwork.getChildren().get(platz));
					vboxtasksinwork.getChildren().remove(currentPane);
					int taskstatusid = 3;
					try {
						updateTaskstatus(userstorytaskid, taskstatusid);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		vboxdonetasks.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
			}
		});
		
		this.setStyle("-fx-border-style: solid;" + "-fx-border-width: 1;" + "-fx-border-color: black");
		
		this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				setUserStory(userstory);
			}
		});
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getButton().equals(MouseButton.PRIMARY)) {
					if (event.getClickCount() == 2) {
						try {
							FXMLLoader fxmlLoader = new FXMLLoader(
									getClass().getResource("/scrumbo/de/gui/FXMLSprintBacklogEditUserStory.fxml"));
							Parent root1 = (Parent) fxmlLoader.load();
							Stage stage = new Stage();
							stage.initModality(Modality.APPLICATION_MODAL);
							stage.setScene(new Scene(root1));
							stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
								@Override
								public void handle(WindowEvent event) {
									try {
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
				}
			}
		});
		this.getChildren().add(vboxprioritaet);
		this.getChildren().add(vboxuserstory);
		this.getChildren().add(vboxopentasks);
		this.getChildren().add(vboxtasksinwork);
		this.getChildren().add(vboxdonetasks);
	}
	
	private void addTask(UserStoryTask userstorytask) {
		final GridPane pane = new GridPane();
		pane.setMinHeight(40);
		pane.setMaxWidth(150);
		pane.setMinWidth(150);
		pane.setStyle("-fx-border-radius: 10px; -fx-border-style: solid;" + "-fx-border-width: 0.3;"
				+ "-fx-border-color: black");
				
		Text taskInfo = new Text("Benutzer: " + String.valueOf(userstorytask.getBenutzer().getVorname().charAt(0))
				+ String.valueOf(userstorytask.getBenutzer().getNachname().charAt(0)) + ", Aufwand: "
				+ userstorytask.getAufwandinstunden() + "h");
		pane.setPadding(new Insets(1, 5, 1, 5));
		pane.setVgap(2);
		StringBuilder sb = new StringBuilder(userstorytask.getBeschreibung());
		int i = 0;
		while (i + 20 < sb.length() && (i = sb.lastIndexOf(" ", i + 20)) != -1) {
			sb.replace(i, i + 1, "\n");
		}
		String output = sb.toString();
		String[] a = output.split("\n");
		Integer zaehler = 0;
		for (int j = 0; j < a.length; j++) {
			Text text = new Text();
			text.setText(a[j]);
			pane.addRow(j, text);
			zaehler = j;
		}
		pane.addRow(zaehler + 1, taskInfo);
		// pane.getChildren().add(taskText);
		// pane.getChildren().add(taskInfo);
		// pane.setAlignment(Pos.CENTER);
		
		pane.setOnDragDetected(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				currentPane = pane;
				
				Dragboard dragBoard = pane.startDragAndDrop(TransferMode.MOVE);
				
				ClipboardContent content = new ClipboardContent();
				
				content.putString(userstorytask.getId().toString());
				
				dragBoard.setContent(content);
				
				if (pane.getParent().equals(vboxopentasks)) {
					bla = 1;
				}
				if (pane.getParent().equals(vboxtasksinwork)) {
					bla = 2;
				}
				if (pane.getParent().equals(vboxdonetasks)) {
					bla = 3;
				}
			}
		});
		pane.setOnDragDone(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent dragEvent) {
			}
		});
		
		if (userstorytask.getTaskstatus().getBeschreibung().equals("Offen"))
			vboxopentasks.getChildren().add(pane);
			
		if (userstorytask.getTaskstatus().getBeschreibung().equals("In Arbeit"))
			vboxtasksinwork.getChildren().add(pane);
			
		if (userstorytask.getTaskstatus().getBeschreibung().equals("Erledigt"))
			vboxdonetasks.getChildren().add(pane);
			
	}
	
	public MyHBox(UserStory userstory) {
		this.userstory = userstory;
		
		initMyHBox();
		
		if (userstory.getUserstorytask() != null) {
			for (int i = 0; i < userstory.getUserstorytask().size(); i++) {
				addTask(userstory.getUserstorytask().get(i));
			}
		}
		
	}
	
	private void updateTaskstatus(int userstorytaskid, int taskstatusid) throws JSONException {
		UserStoryTask userstorytask = new UserStoryTask();
		UserStory userstory = this.userstory;
		for (int i = 0; i < userstory.getUserstorytask().size(); i++) {
			
			if (userstory.getUserstorytask().get(i).getId() == userstorytaskid) {
				if (taskstatusid == 1) {
					userstory.getUserstorytask().get(i).getTaskstatus().setId(taskstatusid);
					userstory.getUserstorytask().get(i).getTaskstatus().setBeschreibung("Offen");
					userstorytask = userstory.getUserstorytask().get(i);
				}
				if (taskstatusid == 2) {
					userstory.getUserstorytask().get(i).getTaskstatus().setId(taskstatusid);
					userstory.getUserstorytask().get(i).getTaskstatus().setBeschreibung("In Arbeit");
					userstorytask = userstory.getUserstorytask().get(i);
				}
				if (taskstatusid == 3) {
					userstory.getUserstorytask().get(i).getTaskstatus().setId(taskstatusid);
					userstory.getUserstorytask().get(i).getTaskstatus().setBeschreibung("Erledigt");
					userstorytask = userstory.getUserstorytask().get(i);
				}
			}
			
		}
		
		Gson gson = new Gson();
		String output = gson.toJson(userstorytask);
		
		JSONObject jsonObject = new JSONObject(output);
		
		try {
			URL url = new URL("http://localhost:8080/ScrumBO_Server/rest/userstorytask/update" + "/"
					+ ScrumBOClient.getDatabaseconfigfile());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			conn.setRequestMethod("POST");
			
			OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
			out.write(jsonObject.toString());
			out.close();
			
			if (conn.getResponseMessage().equals("User Story Task erfolgreich geupdated"))
				System.out.println("\nRest Service Invoked Successfully..");
			conn.disconnect();
		} catch (Exception e) {
			System.out.println("\nError while calling Rest service");
			e.printStackTrace();
		}
		
	}
	
}
