package edu.augustana.egret.Tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.augustana.egret.BoardEditor.EditorScreen;
import edu.augustana.egret.BoardEditorData.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;

public class RefreshCanvas {

	private static final double SELECT_RESIZE = .9;
	private static final double POINTY_RESIZE = .4;

	/**
	 * Refreshes the canvas so that all changes to the tiles in the passed map data
	 * will be shown and visible to the user.
	 * 
	 * @param mapData       - the data being changed and in need of being refreshed
	 * @param canvas        - the canvas which the refreshing will happen to
	 * @param gc            - the GraphicsContext being used to redraw the updated
	 *                      tiles from mapData
	 * @param selectedTiles - all tiles that have been selected by the user
	 */

	public static void use(MapData mapData, Canvas canvas, GraphicsContext gc, List<Tile> selectedTiles) {
		ArrayList<Integer> tileHeightList = new ArrayList<Integer>();
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		for (Tile tile : mapData.getTileList()) {
			if (!tileHeightList.contains((int) tile.getHeight())) {
				tileHeightList.add((int) tile.getHeight());
			}

			tile.setSize(CanvasData.getTileSize(mapData));
			gc.setFill(new Color(tile.getGrayscaleColor(), tile.getGrayscaleColor(), tile.getGrayscaleColor(), 1));
			gc.fillPolygon(tile.getXCoordinates(), tile.getYCoordinates(), tile.getYCoordinates().length);
			if (selectedTiles.contains(tile)) {
				gc.setFill(Color.CYAN);
				gc.fillPolygon(tile.getXCoordinates(), tile.getYCoordinates(), tile.getYCoordinates().length);
				gc.setFill(new Color(tile.getGrayscaleColor(), tile.getGrayscaleColor(), tile.getGrayscaleColor(), 1));
				gc.fillPolygon(tile.resizeXCoordinates(SELECT_RESIZE), tile.resizeYCoordinates(SELECT_RESIZE),
						tile.getYCoordinates().length);
			}
			if (tile.getIsTilePointy()) {
				gc.setFill(Color.RED);
				gc.fillPolygon(tile.resizeXCoordinates(POINTY_RESIZE), tile.resizeYCoordinates(POINTY_RESIZE),
						tile.getYCoordinates().length);
			}

		}
		Collections.sort(tileHeightList);
		EditorScreen.setTileHeightsString(tileHeightList.toString());
	}
}
