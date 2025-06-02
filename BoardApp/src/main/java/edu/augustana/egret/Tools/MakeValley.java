package edu.augustana.egret.Tools;

import edu.augustana.egret.BoardEditorData.MapData;
import edu.augustana.egret.BoardEditorData.Tile;

public abstract class MakeValley {

	/**
	 * Creates a valley with the center being located at the tile passed in.
	 * 
	 * @param mapData   - the map data which the valley will be inserted into
	 * @param height    - the height the selected tile will be set to
	 * @param clickTile - the tile the valley will be centered at
	 */

	public static void makeValley(MapData mapData, int height, Tile clickTile) {
		clickTile.setHeight(height);
		for (Tile tile : mapData.getTileList()) {
			if (tile.getRow() <= clickTile.getRow() + 1 && tile.getColumn() <= clickTile.getColumn() + 1
					&& tile.getRow() >= clickTile.getRow() - 1 && tile.getColumn() >= clickTile.getColumn() - 1) {
				tile.setHeight(height + 2);
			}
		}
		clickTile.setHeight(height);
	}

}
