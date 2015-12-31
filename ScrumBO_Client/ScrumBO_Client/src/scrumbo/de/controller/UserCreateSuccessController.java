/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumbo.de.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Serdar
 */
public class UserCreateSuccessController implements Initializable {
	
	Parent	root;
	Scene	scene;
	
	@FXML
	private Button buttonForward;
	
	@FXML
	private void handleButtonForward(ActionEvent event) throws IOException {
		this.root = FXMLLoader.load(getClass().getResource("/scrumbo/de/gui/Startwindow.fxml"));
		this.scene = new Scene(root);
		Stage stage = (Stage) buttonForward.getScene().getWindow();
		stage.setScene(scene);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
}
