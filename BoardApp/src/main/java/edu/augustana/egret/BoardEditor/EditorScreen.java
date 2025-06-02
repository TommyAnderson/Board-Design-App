package edu.augustana.egret.BoardEditor;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseButton;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;

import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.augustana.egret.BoardEditorData.*;
import edu.augustana.egret.Tools.*;

public class EditorScreen {
	@FXML
	private Canvas mainEditor;
	@FXML
	private GraphicsContext gc;
	@FXML
	private Slider heightSelector;
	@FXML
	private TextField tileHeightToSelect;
	@FXML
	private Label tileHeightLabel;
	@FXML
	private ToggleButton pointyTileBtn;

	private static List<Tile> selectedTiles = new ArrayList<Tile>();
	private static String tileHeights;
	private static boolean isDragging = false;

	/**
	 * takes in a String listing the heights of all the tiles in the MapData tile
	 * list and sets the tileHeights instance variable equal to that String.
	 * 
	 * @param tileHeights - the String listing the heights of all tiles in the
	 *                    MapData tile list
	 */

	public static void setTileHeightsString(String tileHeights) {
		EditorScreen.tileHeights = tileHeights;
	}

	/**
	 * returns a list of all Tiles that have been selected
	 * 
	 * @return - a list of all Tiles that are selected
	 */
	
	public static List<Tile> getSelectedTiles() {
		return selectedTiles;
	}

	@FXML
	private void switchToMainMenu() throws IOException {
		App.editorScreenUsed = false;
		App.setRoot("mainMenu");
	}

	@FXML
	private void switchToNewScreen() throws IOException {
		App.setRoot("setGridSizeScreen");
		App.createNewScreenUsed = false;
	}

	@FXML
	private void switchToPreviewScreen() throws IOException {
		ThreeDPreview.start();
	}

	@FXML
	private void showAboutScreen() throws IOException {
		showScreen("aboutScreen", "About Us", 625, 460);
	}

	@FXML
	private void showGettingStartedScreen() throws IOException {
		showScreen("gettingStartedScreen", "Getting Started", 625, 525);
	}

	@FXML
	private void showBoardToolsInfo() throws IOException {
		showScreen("boardToolsInfo", "Board Tools Info", 625, 220);
	}

	@FXML
	private void showBoardFeaturesInfo() throws IOException {
		showScreen("boardFeaturesInfo", "Board Features Info", 625, 470);
	}

	@FXML
	private void showExtraFeaturesInfo() throws IOException {
		showScreen("extraFeaturesInfo", "Extra Features Info", 630, 520);
	}

	@FXML
	private void initialize() throws IOException {
		gc = mainEditor.getGraphicsContext2D();

		selectedTiles.clear();

		if (PredesignScreenController.predesignedFile != null) {
			MapDataIO.openFile(PredesignScreenController.predesignedFile);
			PredesignScreenController.predesignedFile = null;
		} else if (App.fileCheck) {
			chooseFile();
		}

		if (App.editorScreenUsed) {
			App.setOriginalMapData(UndoRedoManager.copy(App.getMapData()));
		}

		setUpMouseClickHandling();
		dragSelectFeature();
		refreshCanvas();
		UndoRedoManager.saveState();
	}

	private void showScreen(String fxmlFile, String title, int screenWidth, int screenHeight) throws IOException {
		Stage stage = new Stage();
		Scene aboutScene = new Scene(App.loadFXML(fxmlFile), screenWidth, screenHeight);
		stage.setTitle(title);
		stage.setScene(aboutScene);
		stage.show();
	}

	@FXML
	private void randomizeTiles() {
		Random random = new Random();
		for (Tile tile : App.getMapData().getTileList()) {
			double tileHeight = random.nextInt(9) + 1;
			tile.setHeight(tileHeight);
		}
		refreshCanvas();
		UndoRedoManager.saveState();

	}

	@FXML
	private void randomizeSelectedTiles() {
		Random random = new Random();
		for (Tile tile : selectedTiles) {
			double tileHeight = random.nextInt(9) + 1;
			tile.setHeight(tileHeight);
		}
		refreshCanvas();
		UndoRedoManager.saveState();
	}

	@FXML
	private void heightChanger() {
		double heightValue = heightSelector.getValue();
		HeightChanger.use(heightValue, App.getMapData(), selectedTiles);
		setUpMouseClickHandling();
		refreshCanvas();
		UndoRedoManager.saveState();
	}

	@FXML
	private void addRow() {
		MapData mapData = App.getMapData();
		mapData.addRow(mapData.getNumRows());
		refreshCanvas();
		UndoRedoManager.saveState();
	}

	@FXML
	private void addColumn() {
		MapData mapData = App.getMapData();
		mapData.addColumn(mapData.getNumColumns());
		refreshCanvas();
		UndoRedoManager.saveState();
	}

	@FXML
	private void deleteRow() {
		MapData mapData = App.getMapData();
		mapData.deleteRow(mapData.getNumRows() - 1);
		refreshCanvas();
		UndoRedoManager.saveState();
	}

	@FXML
	private void deleteColumn() {
		MapData mapData = App.getMapData();
		mapData.deleteColumn(mapData.getNumColumns() - 1);
		refreshCanvas();
		UndoRedoManager.saveState();
	}

	@FXML
	private void deselectAllTiles() {
		selectedTiles.removeAll(selectedTiles);
		refreshCanvas();
	}

	@FXML
	private void selectAllTiles() {
		selectedTiles.addAll(App.getMapData().getTileList());
		refreshCanvas();
	}

	@FXML
	private void selectTilesByHeight() {
		selectedTiles = new ArrayList<Tile>();
		String tileHeightString = tileHeightToSelect.getText();
		SelectTilesByHeight.use(tileHeightString, selectedTiles);
		refreshCanvas();
	}

	@FXML
	private void changeUnselectedTilesHeight() {
		MapData mapData = App.getMapData();
		double heightValue = heightSelector.getValue();
		mapData.getTileList().removeAll(selectedTiles);
		for (Tile tile : mapData.getTileList()) {
			tile.setHeight(heightValue);
		}
		mapData.getTileList().addAll(selectedTiles);
		refreshCanvas();
		UndoRedoManager.saveState();
	}

	@FXML
	private void resetArea() {
		if (!selectedTiles.isEmpty()) {
			for (Tile tile : selectedTiles) {
				tile.setHeight(1);
			}
		}
		// mouseClick();
		refreshCanvas();
		UndoRedoManager.saveState();
	}

	@FXML
	private void resetAll() {
		selectedTiles.clear();
		App.setMapData(UndoRedoManager.copy(App.getOriginalMapData()));
		refreshCanvas();
		UndoRedoManager.saveState();
	}

	/**
	 * Deselects all tiles, undoes the previous action done to the tiles, and
	 * refreshes the canvas to show the undo worked.
	 */

	@FXML
	public void undo() {
		selectedTiles.clear();
		UndoRedoManager.undo();
		refreshCanvas();
	}

	/**
	 * Deselects all tiles, does the most recent undone action again, and refreshes
	 * the canvas to show the redo worked.
	 */

	@FXML
	public void redo() {
		selectedTiles.clear();
		UndoRedoManager.redo();
		refreshCanvas();
	}

	private void refreshCanvas() {
		MapData mapData = App.getMapData();
		RefreshCanvas.use(mapData, mainEditor, gc, selectedTiles);
		tileHeightLabel.setText(tileHeights);
	}

	private void setUpMouseClickHandling() {
		ContextMenu menu = new ContextMenu();
		MenuItem addRow = new MenuItem("Add row");
		MenuItem deleteRow = new MenuItem("Delete Row");
		MenuItem addColumn = new MenuItem("Add Column");
		MenuItem deleteColumn = new MenuItem("Delete Column");
		MenuItem makeMountain = new MenuItem("Make Mountain");
		MenuItem makeValley = new MenuItem("Make Valley");
		MenuItem togglePointy = new MenuItem("Toggle Pointy");
		menu.getItems().addAll(addRow, addColumn, deleteRow, deleteColumn, makeMountain, makeValley, togglePointy);
		mainEditor.setOnMouseClicked(click -> {
			try {
				Tile tileClicked = App.getMapData().getTileAtClick(click.getX(), click.getY());
				addRow.setOnAction(event -> {
					App.getMapData().addRow(tileClicked.getRow() + 1);
					refreshCanvas();
					UndoRedoManager.saveState();
				});
				deleteRow.setOnAction(event -> {
					App.getMapData().deleteRow(tileClicked.getRow());
					refreshCanvas();
					UndoRedoManager.saveState();
				});
				addColumn.setOnAction(event -> {
					App.getMapData().addColumn(tileClicked.getColumn() + 1);
					refreshCanvas();
					UndoRedoManager.saveState();
				});
				deleteColumn.setOnAction(event -> {
					App.getMapData().deleteColumn(tileClicked.getColumn());
					refreshCanvas();
					UndoRedoManager.saveState();
				});
				makeMountain.setOnAction(event -> {
					MakeMountain.makeMountain(App.getMapData(), 10, tileClicked);
					refreshCanvas();
					UndoRedoManager.saveState();
				});
				makeValley.setOnAction(event -> {
					MakeValley.makeValley(App.getMapData(), 2, tileClicked);
					refreshCanvas();
					UndoRedoManager.saveState();
				});
				togglePointy.setOnAction(event -> {
					tileClicked.setTilePointy(!tileClicked.getIsTilePointy());
					refreshCanvas();
					UndoRedoManager.saveState();
				});

				if (click.getButton() == MouseButton.SECONDARY) {
					menu.show(mainEditor, Side.TOP, click.getX(), click.getY());
				} else {
					menu.hide();
					if (!isDragging) {
						SelectTile.click(click.getX(), click.getY(), App.getMapData(), selectedTiles);
					}
				}

				refreshCanvas();
				isDragging = false;

			} catch (InterruptedException e) {
				// does nothing
			}
		});
	}

	private void dragSelectFeature() {
		mainEditor.setOnMouseDragged(drag -> {
			SelectTile.drag(drag.getX(), drag.getY(), App.getMapData(), selectedTiles);
			isDragging = true;
			refreshCanvas();
		});
	}

	@FXML
	private void makeSelectedTilesPointy() {
		boolean makePointy = true;
		for (Tile tile : selectedTiles) {
			if (!tile.getIsTilePointy()) {
				makePointy = false;
			}
		}
		if (makePointy) {
			for (Tile tile : selectedTiles) {
				tile.setTilePointy(false);
			}
		} else {
			for (Tile tile : selectedTiles) {
				tile.setTilePointy(true);
			}
		}

		refreshCanvas();
		UndoRedoManager.saveState();
	}

	@FXML
	private void chooseFile() throws IOException {
		File temp = App.getCurrentFile();
		MapDataIO.chooseFile();
		if (App.getCurrentFile() != temp) {
			selectedTiles.clear();
		}
		refreshCanvas();
	}

	@FXML
	private void saveAsFile() throws IOException {
		MapDataIO.saveAsFile();
	}

	@FXML
	private void saveFile() throws IOException {
		MapDataIO.saveFile();
	}

	@FXML
	private void closeCurrentFile() throws IOException {
		App.getMapData().getTileList().clear();
		selectedTiles.clear();
		refreshCanvas();
		App.setRoot("mainMenu");
	}

	@FXML
	private void exportToOBJ() {
		BoardExporter.exportToOBJ(App.getMapData(), App.getMainWindow());
	}

	@FXML
	private void exportToPLY() {
		BoardExporter.exportToPLY(App.getMapData(), App.getMainWindow());
	}
}