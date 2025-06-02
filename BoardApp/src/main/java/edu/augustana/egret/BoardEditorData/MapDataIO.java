package edu.augustana.egret.BoardEditorData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import edu.augustana.egret.BoardEditor.App;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class MapDataIO {

	/**
	 * Uses the GSON library to write the passed MapData parameter into the passed
	 * file using JSON. This method is mostly from MovieTrackerExample.
	 * 
	 * @param data       - the MapData object being written to the file
	 * @param outputFile - the file data will be written into
	 * @throws IOException - thrown if the file or directory that the person is
	 *                     attempting to save to doesn't exist
	 */

	public static void saveMapDataToJSON(MapData data, File outputFile) throws IOException {
		GsonBuilder gsonBilder = new GsonBuilder();
		gsonBilder.registerTypeAdapter(Tile.class, new InterfaceAdapter<Tile>());
		gsonBilder.setPrettyPrinting();
		Gson gson = gsonBilder.create();
		FileWriter writer = new FileWriter(outputFile);
		gson.toJson(data, writer);
		writer.close();
	}

	/**
	 * Uses the GSON library to pull a MapData object from the passed file using
	 * JSON. This method is mostly from MovieTrackerExample.
	 * 
	 * @param inputFile - the file from which the MapData will be pulled
	 * @return the MapData object pulled from the inputFile
	 * @throws JsonSyntaxException - thrown if the JSON element GSON tries to read
	 *                             is malformed
	 * @throws JsonIOException     - thrown if IOException occurs while JSON API is
	 *                             operating
	 * @throws IOException         - thrown if the file or directory that the person
	 *                             is attempting to save to doesn't exist
	 */

	public static MapData getMapDataFromJSON(File inputFile) throws JsonSyntaxException, JsonIOException, IOException {
		GsonBuilder gsonBilder = new GsonBuilder();
		gsonBilder.registerTypeAdapter(Tile.class, new InterfaceAdapter<Tile>());
		Gson gson = gsonBilder.create();
		FileReader reader = new FileReader(inputFile);
		MapData data = gson.fromJson(reader, MapData.class);
		reader.close();
		return data;
	}

	/**
	 * Allows the user to choose a file from their directory, which is then opened
	 * in the editor.
	 * 
	 * @throws IOException - thrown if the file or directory that the person is
	 *                     attempting to save to doesn't exist
	 */

	public static void chooseFile() throws IOException {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Map Data (*.mapdata)", "*.mapdata");
		fileChooser.getExtensionFilters().add(filter);
		File selectedFile = fileChooser.showOpenDialog(App.getMainWindow());
		App.fileCheck = false;
		openFile(selectedFile);
	}

	/**
	 * Takes in and opens a file of the user's choice for editing, assuming it is a
	 * valid file. This method is partly from MovieTrackerExample.
	 * 
	 * @param selectedFile - the file the user wants to open from their file
	 *                     directory
	 * @throws IOException - thrown if the file or directory that the person is
	 *                     attempting to save to doesn't exist
	 */

	public static void openFile(File selectedFile) throws FileNotFoundException, IOException {
		if (selectedFile != null) {
			App.setCurrentFile(selectedFile);
			try {
				App.setMapData(MapDataIO.getMapDataFromJSON(App.getCurrentFile()));
			} catch (FileNotFoundException ex) {
				new Alert(AlertType.ERROR, "File not found.").showAndWait();
			} catch (IOException ex) {
				new Alert(AlertType.ERROR, "File or directory not found! Please select valid file or directory.")
						.showAndWait();
			}
			App.setOriginalMapData(UndoRedoManager.copy(App.getMapData()));
		}
	}

	// From MovieTrackerExample

	/**
	 * Opens the user's file directory and allows a created file storing a MapData
	 * object to be saved onto the computer. This method is partly from
	 * MovieTrackerExample.
	 * 
	 * @throws IOException - thrown if the file or directory that the person is
	 *                     attempting to save to doesn't exist
	 */

	public static void saveAsFile() throws IOException {
		FileChooser saveChooser = new FileChooser();
		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Map Data (*.mapdata)",
				"*.mapdata");
		saveChooser.getExtensionFilters().add(extensionFilter);
		File outputFile = saveChooser.showSaveDialog(App.getMainWindow());
		if (outputFile != null) {
			App.setCurrentFile(outputFile);
			MapData mapData = App.getMapData();
			try {
				MapDataIO.saveMapDataToJSON(mapData, App.getCurrentFile());
			} catch (IOException ex) {
				new Alert(AlertType.ERROR, "File or directory not found! Please select valid file or directory.")
						.showAndWait();
			}
		}
	}

	/**
	 * Either calls saveAsFile if the file hasn't been saved yet or saves changes to
	 * the file without opening the file dialogue box since it's already been saved
	 * as something.
	 * 
	 * @throws IOException - thrown if the file or directory that the person is
	 *                     attempting to save to doesn't exist
	 */

	public static void saveFile() throws IOException {
		if (App.getCurrentFile() == null) {
			saveAsFile();
		} else {
			MapData mapData = App.getMapData();
			try {
				MapDataIO.saveMapDataToJSON(mapData, App.getCurrentFile());
			} catch (IOException ex) {
				new Alert(AlertType.ERROR, "File or directory not found! Please select valid file or directory.")
						.showAndWait();
			}
		}
	}

}
