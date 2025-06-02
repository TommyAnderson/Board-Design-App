package edu.augustana.egret.BoardEditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

import edu.augustana.egret.BoardEditorData.MapData;

public class App extends Application {

	static Scene scene;
	private static Stage mainWindow;
	private static MapData currentMapData = new MapData();
	private static MapData originalMapData = new MapData();
	private static File currentFile = null;

	public static boolean editorScreenUsed = true;
	public static boolean createNewScreenUsed = true;
	public static boolean randomCheck = false;
	public static boolean fileCheck = false;

	/**
	 * Overrides the start method in the Application superclass and prepares the
	 * mainMenu window to be displayed upon launching the application.
	 * 
	 * @param stage - the feature which displays all scenes in the application
	 */

	@Override
	public void start(Stage stage) throws IOException {
		scene = new Scene(loadFXML("mainMenu"), 1300, 700);
		mainWindow = stage;
		stage.setScene(scene);
		stage.setTitle("Board Editor App");
		stage.show();
	}

	/**
	 * Sets the current scene to be whatever the name of the FXML file passed in is.
	 * 
	 * @param fxml - the name of the FXML file which the scene will be changed to
	 * @throws IOException - thrown if the FXML passed in doesn't exist
	 */

	public static void setRoot(String fxml) throws IOException {
		scene.setRoot(loadFXML(fxml));
	}

	/**
	 * returns the scene created by whichever FXML file is currently loaded; the
	 * accessor method for scene.
	 * 
	 * @return the scene created by whichever FXML file is currently loaded
	 */

	public Scene getScene() {
		return scene;
	}

	/**
	 * Takes in the name of an FXML file and opens it if it exists, otherwise
	 * throwing an IOException.
	 * 
	 * @param fxml - the name of the FXML file that will be loaded upon calling this
	 *             method
	 * @return the loaded FXML file
	 * @throws IOException - thrown when the FXML file passed doesn't exist
	 */

	public static Parent loadFXML(String fxml) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
		return fxmlLoader.load();
	}

	/**
	 * Launches the entire application.
	 * 
	 * @param args - the collection of Strings that can be typed into the program
	 *             terminal directly
	 */

	public static void main(String[] args) {
		launch();
	}

	/**
	 * returns the main window of the program.
	 * 
	 * @return the main window of the program
	 */

	public static Stage getMainWindow() {
		return mainWindow;
	}

	/**
	 * sets the current map data equal to the passed map data, along with its rows
	 * and columns being set equal to the passed map data's rows and columns.
	 * 
	 * @param data - the map data that will be set equal to the current map data as
	 *             well as it's rows and columns
	 */

	public static void setMapData(MapData data) {
		currentMapData = data;
		currentMapData.setNumColumns(data.getNumColumns());
		currentMapData.setNumRows(data.getNumRows());
	}

	/**
	 * returns the current map data.
	 * 
	 * @return the current map data
	 */

	public static MapData getMapData() {
		return currentMapData;
	}

	/**
	 * returns the original map data.
	 * 
	 * @return the original map data
	 */

	public static MapData getOriginalMapData() {
		return originalMapData;
	}

	/**
	 * sets the original map data equal to the passed map data.
	 * 
	 * @param data - the MapData that originalMapData will be set equal to
	 */

	public static void setOriginalMapData(MapData data) {
		originalMapData = data;
	}

	/**
	 * returns the file that data is currently being saved to or loaded from.
	 * 
	 * @return - the file data is currently being saved to or loaded from
	 */

	public static File getCurrentFile() {
		return currentFile;
	}

	/**
	 * sets the current file that the map data is being saved to or loaded from
	 * equal to the passed file.
	 * 
	 * @param currentFile - the file data is currently being saved to or loaded from
	 */

	public static void setCurrentFile(File currentFile) {
		App.currentFile = currentFile;
	}

}