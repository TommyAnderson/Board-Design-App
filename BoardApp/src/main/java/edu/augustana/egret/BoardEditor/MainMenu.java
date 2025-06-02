package edu.augustana.egret.BoardEditor;

import java.io.File;
import java.io.IOException;

import edu.augustana.egret.BoardEditorData.MapData;
import edu.augustana.egret.BoardEditorData.MapDataIO;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class MainMenu {

	@FXML
	private void switchToPredesign() throws IOException {
		App.setRoot("predesignScreen");
		App.editorScreenUsed = false;
	}

	@FXML
	void switchToSetSizeScreen() throws IOException {
		App.setRoot("setGridSizeScreen");
		App.editorScreenUsed = true;
		App.createNewScreenUsed = true;
	}

	@FXML
	private void showAboutScreen() throws IOException {
		Stage stage = new Stage();
		Scene aboutScene = new Scene(App.loadFXML("aboutScreen"), 625, 460);
		stage.setTitle("About Us");
		stage.setScene(aboutScene);
		stage.show();
	}

	@FXML
	private void showGettingStartedScreen() throws IOException {
		Stage stage = new Stage();
		Scene gettingStartedScene = new Scene(App.loadFXML("gettingStartedScreen"), 625, 525);
		stage.setTitle("Getting Started");
		stage.setScene(gettingStartedScene);
		stage.show();
	}

	@FXML
	void loadSavedFile() throws IOException {
		App.fileCheck = true;
		File temp = App.getCurrentFile();
		MapDataIO.chooseFile();
		if (App.getCurrentFile() == null) {
			App.fileCheck = false;
		} else if (App.getCurrentFile() != temp) {
			App.setRoot("editorScreen");
			EditorScreen.getSelectedTiles().clear();
		}
	}

	@FXML
	private void switchToEditorScreenCurrent() throws IOException {
		if (App.getMapData().getTileList().isEmpty()) {
			new Alert(AlertType.WARNING, "No Current Board Open").show();
		} else {
			App.setRoot("editorScreen");
		}
	}
}
