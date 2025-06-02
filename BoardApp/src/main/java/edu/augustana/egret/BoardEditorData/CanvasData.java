package edu.augustana.egret.BoardEditorData;

public class CanvasData {

	public static final int CANVAS_SIZE = 600;
	public static final int TILE_SPACING = 2;

	/**
	 * Calculates and returns the tile size for each tile in a mapData object.
	 * 
	 * @param the map data from which the tile size needs to be gotten
	 * @return a tile size based on the number of rows and columns in a map
	 */
	
	public static int getTileSize(MapData mapData) {
		int gridHeight = mapData.getNumRows();
		int gridWidth = mapData.getNumColumns();
		if (gridHeight >= gridWidth) {
			return (CANVAS_SIZE - ((gridHeight - 1) * TILE_SPACING)) / gridHeight;
		} else {
			return (CANVAS_SIZE - ((gridWidth - 1) * TILE_SPACING)) / gridWidth;
		}
	}
}
