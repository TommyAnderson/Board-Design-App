package edu.augustana.egret.BoardEditorData;

import java.util.Stack;

import edu.augustana.egret.BoardEditor.App;
import edu.augustana.egret.BoardEditorData.MapData.Shape;

public class UndoRedoManager {

	//credit to  Dale Skrien for overall structure of the undo/redo.

	private static Stack<MapData> undoStack = new Stack<>();
	private static Stack<MapData> redoStack = new Stack<>();

	/**
	 * Empties the undoStack and redoStack of all stored data.
	 */

	public static void clearStacks() {
		undoStack.clear();
		redoStack.clear();
	}

	/**
	 * Undoes the previous action performed on the map data.
	 */

	public static void undo() {
		MapData mapData = App.getMapData();
		if (undoStack.size() > 1) {
			redoStack.push(copy(undoStack.pop()));
			mapData = copy(undoStack.peek());
		}
		App.setMapData(mapData);
	}

	/**
	 * Redoes the previous action undone on the map data.
	 */

	public static void redo() {
		MapData mapData = App.getMapData();
		if (redoStack.size() != 0) {
			mapData = redoStack.pop();
			undoStack.push(copy(mapData));
		}
		App.setMapData(mapData);
	}

	/**
	 * Copies the current mapData object and returns it for use in other methods.
	 * 
	 * @param mapData - the map data being copied
	 * @return a copy of the passed map data
	 */

	public static MapData copy(MapData mapData) {
		MapData tempData = new MapData(mapData.getNumRows(), mapData.getNumColumns(), mapData.getShape());
		if (tempData.getShape().equals(Shape.Square)) {
			for (Tile tile : mapData.getTileList()) {
				Tile tempTile = new SquareTile(tile.getColumn(), tile.getRow(), tile.getSize(), tile.getHeight(),
						tile.getIsTilePointy());
				tempData.getTileList().add(tempTile);
			}
		} else if (tempData.getShape().equals(Shape.Hexagon)) {
			for (Tile tile : mapData.getTileList()) {
				Tile tempTile = new HexagonTile(tile.getColumn(), tile.getRow(), tile.getSize(), tile.getHeight(),
						tile.getIsTilePointy());
				tempData.getTileList().add(tempTile);
			}
		}

		return tempData;
	}

	/**
	 * Saves the current state of the map data.
	 */

	public static void saveState() {
		MapData mapData = App.getMapData();
		MapData prevData = copy(mapData);
		undoStack.push(prevData);
		redoStack.clear();
	}
}
