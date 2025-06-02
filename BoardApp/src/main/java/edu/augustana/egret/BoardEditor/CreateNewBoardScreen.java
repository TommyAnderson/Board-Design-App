package edu.augustana.egret.BoardEditor;

import java.io.IOException;
import java.util.Random;

import edu.augustana.egret.BoardEditorData.MapData.Shape;
import edu.augustana.egret.BoardEditorData.CanvasData;
import edu.augustana.egret.BoardEditorData.HexagonTile;
import edu.augustana.egret.BoardEditorData.MapData;
import edu.augustana.egret.BoardEditorData.MazeGenerator;
import edu.augustana.egret.BoardEditorData.SquareTile;
import edu.augustana.egret.BoardEditorData.Tile;
import edu.augustana.egret.BoardEditorData.UndoRedoManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;

public class CreateNewBoardScreen {

	@FXML
	private TextField length;
	@FXML
	private TextField width;
	@FXML
	private ToggleButton square;
	@FXML
	private ToggleButton hexagon;
	private ToggleGroup group;

	@FXML
	private void initialize() {
		group = new ToggleGroup();
		group.getToggles().addAll(hexagon, square);
	}

	@FXML
	private void makeNewBlankBoard() throws IOException, InterruptedException {
		if (group.getSelectedToggle() != null) {
			setMapData();
			App.setRoot("editorScreen");
			App.setOriginalMapData(UndoRedoManager.copy(App.getMapData()));
			UndoRedoManager.clearStacks();
		} else {
			new Alert(AlertType.WARNING, "Select the Tile Type!").show();
		}
	}

	@FXML
	private void makeNewRandomBoard() throws IOException, InterruptedException {
		if (group.getSelectedToggle() != null) {
			setMapData();
			Random random = new Random();
			for (Tile tile : App.getMapData().getTileList()) {
				double tileHeight = random.nextInt(9) + 1;
				tile.setHeight(tileHeight);
			}
			App.setOriginalMapData(UndoRedoManager.copy(App.getMapData()));
			App.setRoot("editorScreen");
			UndoRedoManager.clearStacks();
		} else {
			new Alert(AlertType.WARNING, "Select the Tile Type!").show();
		}
	}

	@FXML
	private void makeNewMazeBoard() throws IOException, InterruptedException {
		if (group.getSelectedToggle() != null) {
			MapData mapData = App.getMapData();
			setMapData();
			MazeGenerator mazeGen = new MazeGenerator(mapData);
			mazeGen.createMaze(mapData);
			App.setRoot("editorScreen");
			App.setOriginalMapData(UndoRedoManager.copy(mapData));
			UndoRedoManager.clearStacks();
		} else {
			new Alert(AlertType.WARNING, "Select the Tile Type!").show();
		}
	}

	private void setMapData() throws IOException, InterruptedException {
		MapData mapData = App.getMapData();
		String lengthString = length.getText();
		String widthString = width.getText();
		if (hexagon.isSelected()) {
			mapData.setShape(Shape.Hexagon);
		} else if (square.isSelected()) {
			mapData.setShape(Shape.Square);
		}
		try {
			int lengthInt = Integer.parseInt(lengthString);
			int widthInt = Integer.parseInt(widthString);
			mapData.setNumColumns(lengthInt);
			mapData.setNumRows(widthInt);
			mapData.getTileList().clear();
			createBoard(mapData);
		} catch (NumberFormatException e) {
			new Alert(AlertType.WARNING, "Input valid Length and Width!").show();
		}
	}

	@FXML
	private void switchToCertainScreen() throws IOException {
		if (App.createNewScreenUsed) {
			App.setRoot("mainMenu");
		} else {
			App.setRoot("editorScreen");
		}
	}

	/**
	 * creates the tile list that will be stored in the passed MapData object that
	 * will become the 2D grid displayed in the editor eventually.
	 * 
	 * @param mapData - the MapData object which will be having its tile list
	 *                created
	 */

	public void createBoard(MapData mapData) {
		mapData.getTileList().removeAll(mapData.getTileList());
		int xCoordinate = 0;
		int yCoordinate = 0;
		for (int i = 0; i < mapData.getNumRows() * mapData.getNumColumns(); i++) {
			if (App.getMapData().getShape().equals(Shape.Hexagon)) {
				mapData.setShape(Shape.Hexagon);
				mapData.getTileList()
						.add(new HexagonTile(xCoordinate, yCoordinate, CanvasData.getTileSize(mapData), 1, false));
			} else {
				mapData.setShape(Shape.Square);
				mapData.getTileList()
						.add(new SquareTile(xCoordinate, yCoordinate, CanvasData.getTileSize(mapData), 1, false));
			}
			if (xCoordinate + 1 >= mapData.getNumColumns()) {
				xCoordinate = 0;
				yCoordinate++;
			} else {
				xCoordinate++;
			}
		}
	}

}