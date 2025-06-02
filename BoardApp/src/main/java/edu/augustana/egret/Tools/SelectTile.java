package edu.augustana.egret.Tools;

import java.util.List;

import edu.augustana.egret.BoardEditorData.MapData;
import edu.augustana.egret.BoardEditorData.Tile;

public class SelectTile {

	/**
	 * Adds the tile containing the point clicked on to the list of selected tiles
	 * or removes the tile clicked on from the list of selected tiles if it's
	 * already selected.
	 * 
	 * @param xCoord        - the x-coordinate of the mouse click
	 * @param yCoord        - the y-coordinate of the mouse click
	 * @param mapData       - the map data used to determine which tile was clicked
	 *                      on
	 * @param selectedTiles - the list of all tiles selected by the user
	 */

	public static void click(double xCoord, double yCoord, MapData mapData, List<Tile> selectedTiles) {
		try {
			Tile tile = mapData.getTileAtClick(xCoord, yCoord);
			if (selectedTiles.contains(tile)) {
				selectedTiles.remove(tile);
			} else {
				selectedTiles.add(tile);
			}
		} catch (InterruptedException e) {
			// does nothing
		}
	}

	/**
	 * Adds every tile not already selected that the mouse drags over after clicking
	 * to the list of selected tiles.
	 * 
	 * @param xCoord        - the x-coordinate of the sustained mouse click
	 * @param yCoord        - the y-coordinate of the sustained mouse click
	 * @param mapData       - the map data used to determine which tiles have been
	 *                      passed through by the sustained mouse click
	 * @param selectedTiles - the list of all tiles selected by the user
	 */

	public static void drag(double xCoord, double yCoord, MapData mapData, List<Tile> selectedTiles) {
		try {
			Tile tileClicked = mapData.getTileAtClick(xCoord, yCoord);
			if (!selectedTiles.contains(tileClicked)) {
				selectedTiles.add(tileClicked);
			}
		} catch (InterruptedException e) {
			// does nothing
		}
	}

	/**
	 * Adds the passed tile to the list of selected tiles or removes it from the
	 * list of selected tiles if already selected.
	 * 
	 * @param tile          - the tile being added or removed from the list of
	 *                      selected tiles
	 * @param selectedTiles - the list of all tiles selected by the user
	 */

	public static void use(Tile tile, List<Tile> selectedTiles) {
		Tile tileToAdd = tile;
		if (selectedTiles.contains(tileToAdd)) {
			selectedTiles.remove(tileToAdd);
		} else {
			selectedTiles.add(tileToAdd);
		}
	}

}
