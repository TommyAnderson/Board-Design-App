package edu.augustana.egret.BoardEditorData;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import edu.augustana.egret.BoardEditorData.MapData.Shape;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BoardExporter {

	// Source for OBJ: http://paulbourke.net/dataformats/obj/
	// Source for PLY: http://paulbourke.net/dataformats/ply/

	/**
	 * Writes all the data in the passed MapData object to the passed file in OBJ
	 * format in preparation of being exported.
	 * 
	 * @param currentData - the current map data that will be exported
	 * @param outputFile  - the file the data will be written to to be exported
	 * @throws IOException - thrown if the file the data being written to doesn't
	 *                     exist
	 */

	public static void createDataForOBJ(MapData currentData, File outputFile) throws IOException {
		PrintWriter objFileWriter = new PrintWriter(new FileWriter(outputFile));

		int numVertices = 1;
		for (Tile tile : currentData.getTileList()) {
			double xCoords[] = new double[tile.getXCoordinates().length];
			double yCoords[] = new double[tile.getYCoordinates().length];
			for (int i = 0; i < tile.getXCoordinates().length; i++) {
				xCoords[i] = tile.getXCoordinates()[i] - (tile.getColumn() * 2);
			}
			for (int i = 0; i < tile.getYCoordinates().length; i++) {
				yCoords[i] = tile.getYCoordinates()[i] - (tile.getRow() * 2);
			}

			for (int i = 0; i < xCoords.length; i++) {
				objFileWriter.println("v " + xCoords[i] + " " + yCoords[i] + " 0");
				objFileWriter.println("v " + xCoords[i] + " " + yCoords[i] + " " + (tile.getHeight() * 10));
			}
			if (tile.getIsTilePointy()) {
				objFileWriter.println("v " + (xCoords[0] + (tile.getSize() / 2)) + " "
						+ (yCoords[0] + (tile.getSize() / 2)) + " " + ((tile.getHeight() + 5) * 10));
			}

			if (currentData.getShape().equals(Shape.Square)) {
				objFileWriter.println("f " + (numVertices) + " " + (numVertices + 2) + " " + (numVertices + 3) + " "
						+ (numVertices + 1));
				objFileWriter.println("f " + (numVertices) + " " + (numVertices + 1) + " " + (numVertices + 7) + " "
						+ (numVertices + 6));
				objFileWriter.println("f " + (numVertices) + " " + (numVertices + 2) + " " + (numVertices + 4) + " "
						+ (numVertices + 6));
				objFileWriter.println("f " + (numVertices + 1) + " " + (numVertices + 3) + " " + (numVertices + 5) + " "
						+ (numVertices + 7));
				objFileWriter.println("f " + (numVertices + 2) + " " + (numVertices + 3) + " " + (numVertices + 5) + " "
						+ (numVertices + 4));
				objFileWriter.println("f " + (numVertices + 7) + " " + (numVertices + 6) + " " + (numVertices + 4) + " "
						+ (numVertices + 5));
				if (tile.getIsTilePointy()) {
					objFileWriter.println("f " + (numVertices + 1) + " " + (numVertices + 3) + " " + (numVertices + 8));
					objFileWriter.println("f " + (numVertices + 3) + " " + (numVertices + 5) + " " + (numVertices + 8));
					objFileWriter.println("f " + (numVertices + 5) + " " + (numVertices + 7) + " " + (numVertices + 8));
					objFileWriter.println("f " + (numVertices + 7) + " " + (numVertices + 1) + " " + (numVertices + 8));
					numVertices += 1;
				}
				numVertices += tile.getXCoordinates().length * 2;
			} else if (currentData.getShape().equals(Shape.Hexagon)) {
				objFileWriter.println("f " + (numVertices) + " " + (numVertices + 1) + " " + (numVertices + 3) + " "
						+ (numVertices + 2));
				objFileWriter.println("f " + (numVertices + 2) + " " + (numVertices + 3) + " " + (numVertices + 5) + " "
						+ (numVertices + 4));
				objFileWriter.println("f " + (numVertices + 4) + " " + (numVertices + 5) + " " + (numVertices + 7) + " "
						+ (numVertices + 6));
				objFileWriter.println("f " + (numVertices + 6) + " " + (numVertices + 7) + " " + (numVertices + 9) + " "
						+ (numVertices + 8));
				objFileWriter.println("f " + (numVertices + 8) + " " + (numVertices + 9) + " " + (numVertices + 11)
						+ " " + (numVertices + 10));
				objFileWriter.println("f " + (numVertices + 10) + " " + (numVertices + 11) + " " + (numVertices + 1)
						+ " " + (numVertices));
				objFileWriter.println("f " + (numVertices) + " " + (numVertices + 2) + " " + (numVertices + 4) + " "
						+ (numVertices + 6) + " " + (numVertices + 8) + " " + (numVertices + 10));
				objFileWriter.println("f " + (numVertices + 1) + " " + (numVertices + 3) + " " + (numVertices + 5) + " "
						+ (numVertices + 7) + " " + (numVertices + 9) + " " + (numVertices + 11));
				if (tile.getIsTilePointy()) {
					objFileWriter
							.println("f " + (numVertices + 1) + " " + (numVertices + 3) + " " + (numVertices + 12));
					objFileWriter
							.println("f " + (numVertices + 3) + " " + (numVertices + 5) + " " + (numVertices + 12));
					objFileWriter
							.println("f " + (numVertices + 5) + " " + (numVertices + 7) + " " + (numVertices + 12));
					objFileWriter
							.println("f " + (numVertices + 7) + " " + (numVertices + 9) + " " + (numVertices + 12));
					objFileWriter
							.println("f " + (numVertices + 9) + " " + (numVertices + 11) + " " + (numVertices + 12));
					objFileWriter
							.println("f " + (numVertices + 11) + " " + (numVertices + 1) + " " + (numVertices + 12));
					numVertices += 1;
				}
				numVertices += tile.getXCoordinates().length * 2;
			}
		}

		objFileWriter.close();
	}

	/**
	 * Opens the file directory on the passed stage and allows the passed map data
	 * to be saved as an OBJ file.
	 * 
	 * @param mapData - the map data that will be exported in OBJ format
	 * @param stage   - the stage on which the OBJ saving dialogue will appear on
	 */

	public static void exportToOBJ(MapData mapData, Stage stage) {
		try {
			FileChooser saveChooser = new FileChooser();
			FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("OBJ (*.obj)", "*.obj");
			saveChooser.getExtensionFilters().add(extensionFilter);
			File outputOBJFile = saveChooser.showSaveDialog(stage);
			if (outputOBJFile != null) {
				createDataForOBJ(mapData, outputOBJFile);
			}
		} catch (FileNotFoundException e) {
			new Alert(AlertType.ERROR, "This file cannot be found. Please try a different one.").showAndWait();
		} catch (IOException e) {
			new Alert(AlertType.ERROR, "An I/o error occurred while trying to save this file.").showAndWait();
		}
	}

	/**
	 * Writes all the data in the passed MapData object to the passed file in PLY
	 * format in preparation of being exported.
	 * 
	 * @param currentData - the current map data that will be exported
	 * @param outputFile  - the file the data will be written to to be exported
	 * @throws IOException - thrown if the file the data being written to doesn't
	 *                     exist
	 */

	public static void createDataForPLY(MapData currentData, File outputFile) throws IOException {
		PrintWriter plyFileWriter = new PrintWriter(new FileWriter(outputFile));
		int numVertices = 1;
		int vertexes = 0;
		int faces = 0;

		for (Tile tile : currentData.getTileList()) {
			vertexes += tile.getXCoordinates().length * 2;
			faces += tile.getXCoordinates().length + 2;
			if (tile.getIsTilePointy()) {
				vertexes += 1;
				faces += tile.getXCoordinates().length;
			}
		}

		plyFileWriter.println("ply");
		plyFileWriter.println("format ascii 1.0");
		plyFileWriter.println("element vertex " + vertexes);
		plyFileWriter.println("property int x");
		plyFileWriter.println("property int y");
		plyFileWriter.println("property int z");
		plyFileWriter.println("element face " + faces);
		plyFileWriter.println("property list uchar int vertex_index");
		plyFileWriter.println("end_header");

		for (Tile tile : currentData.getTileList()) {
			int xCoords[] = new int[tile.getXCoordinates().length];
			int yCoords[] = new int[tile.getYCoordinates().length];
			for (int i = 0; i < tile.getXCoordinates().length; i++) {
				xCoords[i] = (int) (tile.getXCoordinates()[i] - (tile.getColumn() * 2));
			}
			for (int i = 0; i < tile.getYCoordinates().length; i++) {
				yCoords[i] = (int) (tile.getYCoordinates()[i] - (tile.getRow() * 2));
			}

			for (int i = 0; i < xCoords.length; i++) {
				plyFileWriter.println(xCoords[i] + " " + yCoords[i] + " 0");
				plyFileWriter.println(xCoords[i] + " " + yCoords[i] + " " + ((int) (tile.getHeight() * 10)));
			}
			if (tile.getIsTilePointy()) {
				plyFileWriter.println(((int) (xCoords[0] + (tile.getSize() / 2))) + " "
						+ ((int) (yCoords[0] + (tile.getSize() / 2))) + " " + ((int) ((tile.getHeight() + 5) * 10)));
			}
		}

		numVertices = 0;
		for (Tile tile : currentData.getTileList()) {
			if (currentData.getShape().equals(Shape.Square)) {
				plyFileWriter.println("4 " + (numVertices) + " " + (numVertices + 2) + " " + (numVertices + 3) + " "
						+ (numVertices + 1));
				plyFileWriter.println("4 " + (numVertices) + " " + (numVertices + 1) + " " + (numVertices + 7) + " "
						+ (numVertices + 6));
				plyFileWriter.println("4 " + (numVertices) + " " + (numVertices + 2) + " " + (numVertices + 4) + " "
						+ (numVertices + 6));
				plyFileWriter.println("4 " + (numVertices + 1) + " " + (numVertices + 3) + " " + (numVertices + 5) + " "
						+ (numVertices + 7));
				plyFileWriter.println("4 " + (numVertices + 2) + " " + (numVertices + 3) + " " + (numVertices + 5) + " "
						+ (numVertices + 4));
				plyFileWriter.println("4 " + (numVertices + 7) + " " + (numVertices + 6) + " " + (numVertices + 4) + " "
						+ (numVertices + 5));
				if (tile.getIsTilePointy()) {
					plyFileWriter.println("3 " + (numVertices + 1) + " " + (numVertices + 3) + " " + (numVertices + 8));
					plyFileWriter.println("3 " + (numVertices + 3) + " " + (numVertices + 5) + " " + (numVertices + 8));
					plyFileWriter.println("3 " + (numVertices + 5) + " " + (numVertices + 7) + " " + (numVertices + 8));
					plyFileWriter.println("3 " + (numVertices + 7) + " " + (numVertices + 1) + " " + (numVertices + 8));
					numVertices += 1;
				}
				numVertices += tile.getXCoordinates().length * 2;
			} else if (currentData.getShape().equals(Shape.Hexagon)) {
				plyFileWriter.println("4 " + (numVertices) + " " + (numVertices + 1) + " " + (numVertices + 3) + " "
						+ (numVertices + 2));
				plyFileWriter.println("4 " + (numVertices + 2) + " " + (numVertices + 3) + " " + (numVertices + 5) + " "
						+ (numVertices + 4));
				plyFileWriter.println("4 " + (numVertices + 4) + " " + (numVertices + 5) + " " + (numVertices + 7) + " "
						+ (numVertices + 6));
				plyFileWriter.println("4 " + (numVertices + 6) + " " + (numVertices + 7) + " " + (numVertices + 9) + " "
						+ (numVertices + 8));
				plyFileWriter.println("4 " + (numVertices + 8) + " " + (numVertices + 9) + " " + (numVertices + 11)
						+ " " + (numVertices + 10));
				plyFileWriter.println("4 " + (numVertices + 10) + " " + (numVertices + 11) + " " + (numVertices + 1)
						+ " " + (numVertices));
				plyFileWriter.println("8 " + (numVertices) + " " + (numVertices + 2) + " " + (numVertices + 4) + " "
						+ (numVertices + 6) + " " + (numVertices + 8) + " " + (numVertices + 10));
				plyFileWriter.println("8 " + (numVertices + 1) + " " + (numVertices + 3) + " " + (numVertices + 5) + " "
						+ (numVertices + 7) + " " + (numVertices + 9) + " " + (numVertices + 11));
				if (tile.getIsTilePointy()) {
					plyFileWriter
							.println("3 " + (numVertices + 1) + " " + (numVertices + 3) + " " + (numVertices + 12));
					plyFileWriter
							.println("3 " + (numVertices + 3) + " " + (numVertices + 5) + " " + (numVertices + 12));
					plyFileWriter
							.println("3 " + (numVertices + 5) + " " + (numVertices + 7) + " " + (numVertices + 12));
					plyFileWriter
							.println("3 " + (numVertices + 7) + " " + (numVertices + 9) + " " + (numVertices + 12));
					plyFileWriter
							.println("3 " + (numVertices + 9) + " " + (numVertices + 11) + " " + (numVertices + 12));
					plyFileWriter
							.println("3 " + (numVertices + 11) + " " + (numVertices + 1) + " " + (numVertices + 12));
					numVertices += 1;
				}
				numVertices += tile.getXCoordinates().length * 2;
			}
		}
		plyFileWriter.close();
	}

	/**
	 * Opens the file directory on the passed stage and allows the passed map data
	 * to be saved as a PLY file.
	 * 
	 * @param mapData - the map data that will be exported in PLY format
	 * @param stage   - the stage on which the PLY saving dialogue will appear on
	 */

	public static void exportToPLY(MapData mapData, Stage stage) {
		try {
			FileChooser saveChooser = new FileChooser();
			FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PLY (*.ply)", "*.ply");
			saveChooser.getExtensionFilters().add(extensionFilter);
			File outputPLYFile = saveChooser.showSaveDialog(stage);
			if (outputPLYFile != null) {
				createDataForPLY(mapData, outputPLYFile);
			}
		} catch (FileNotFoundException e) {
			new Alert(AlertType.ERROR, "This file cannot be found. Please try a different one.").showAndWait();
		} catch (IOException e) {
			new Alert(AlertType.ERROR, "An I/o error occurred whil trying to save this file.").showAndWait();
		}
	}		
}
