package edu.augustana.egret.Tools;

import java.util.List;

import edu.augustana.egret.BoardEditorData.MapData;
import edu.augustana.egret.BoardEditorData.Tile;

public class HeightChanger {

	/**
	 * Changes the height of all selected tiles to the passed height value.
	 * 
	 * @param heightValue   - the height all selected tiles will be set to
	 * @param mapData       - the data from which tiles are selected
	 * @param selectedTiles - all tiles selected from mapData
	 */

	public static void use(double heightValue, MapData mapData, List<Tile> selectedTiles) {
		for (Tile tile : selectedTiles) {
			tile.setHeight(heightValue);
		}
	}
}
