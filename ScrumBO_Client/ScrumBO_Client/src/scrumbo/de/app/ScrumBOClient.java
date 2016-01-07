/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumbo.de.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Serdar
 */
public class ScrumBOClient extends Application {
	
	public static String	databaseconfigfile	= "";
	public static String	host				= "";
	public static String	port				= "";
												
	@Override
	public void start(Stage stage) throws Exception {
		
		try {
			File file = new File("tmp.txt");
			if (!file.exists()) {
				databaseconfigfile = "hibernate.cfg.xml";
				host = "localhost";
				port = "8080";
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(databaseconfigfile);
				bw.write("\n" + host);
				bw.write("\n" + port);
				bw.close();
			} else {
				try {
					InputStream fis = new FileInputStream("tmp.txt");
					InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
					BufferedReader br = new BufferedReader(isr);
					databaseconfigfile = br.readLine();
					host = br.readLine();
					port = br.readLine();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Startfenster wird geladen
		Parent root;
		root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
		
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.exit(0);
			}
		});
		stage.show();
		stage.setResizable(false);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static String getDatabaseconfigfile() {
		return databaseconfigfile;
	}
	
	public static String getHost() {
		return host;
	}
	
	public static String getPort() {
		return port;
	}
	
	public static void setDatabaseconfigfile(String databaseconfigfile, String host, String port) {
		ScrumBOClient.databaseconfigfile = databaseconfigfile;
		ScrumBOClient.host = host;
		ScrumBOClient.port = port;
		
		try {
			File file = new File("tmp.txt");
			if (file.exists()) {
				file.delete();
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(databaseconfigfile);
				bw.write("\n" + host);
				bw.write("\n" + port);
				bw.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
