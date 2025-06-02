package edu.augustana.egret.BoardEditor;

import java.io.File;
import java.io.IOException;

import edu.augustana.egret.BoardEditorData.UndoRedoManager;
import edu.augustana.egret.BoardEditorData.MapData.Shape;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PredesignScreenController {

	public static File predesignedFile;

	@FXML
	private void switchToEditorScreen() throws IOException {
		if (predesignedFile != null) {
			App.setRoot("editorScreen");
		} else {
			new Alert(AlertType.WARNING, "Please Select A Pre-Designed Template.").show();
		}
	}

	@FXML
	private void switchToMainMenu() throws IOException {
		predesignedFile = null;
		App.setRoot("mainMenu");
	}

	@FXML
	private void selectPredesignOneSquare() {
		predesignedFile = new File("squareTilePyramid.mapdata");
		App.getMapData().setShape(Shape.Square);
	}

	@FXML
	private void selectPredesignOneHex() {
		predesignedFile = new File("hexTilePyramid.mapdata");
		App.getMapData().setShape(Shape.Hexagon);
	}

	@FXML
	private void selectPredesignTwoSquare() {
		predesignedFile = new File("squareTileRaisedMiddle.mapdata");
		App.getMapData().setShape(Shape.Square);
	}

	@FXML
	private void selectPredesignTwoHex() {
		predesignedFile = new File("hexTileRaisedMiddle.mapdata");
		App.getMapData().setShape(Shape.Hexagon);
	}

	@FXML
	private void selectPredesignThreeSquare() {
		predesignedFile = new File("SquareTileCliff.mapdata");
		App.getMapData().setShape(Shape.Square);
	}

	@FXML
	private void selectPredesignThreeHex() {
		predesignedFile = new File("hexTileCliff.mapdata");
		App.getMapData().setShape(Shape.Hexagon);
	}
}
