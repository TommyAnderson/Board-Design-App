package edu.augustana.egret.Tools;

import java.util.List;

import edu.augustana.egret.BoardEditor.App;
import edu.augustana.egret.BoardEditorData.Tile;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SelectTilesByHeight {

	/**
	 * Identifies every tile at a specified height and adds them to the list of
	 * selected tiles.
	 * 
	 * @param tileHeightString - the String stating every height that is reached by
	 *                         at least one tile
	 * @param selectedTiles    - the list of all tiles selected by the user
	 */

	public static void use(String tileHeightString, List<Tile> selectedTiles) {
		try {
			int tileHeight = Integer.parseInt(tileHeightString);
			if (tileHeight >= 1 && tileHeight <= 10) {
				for (Tile tile : App.getMapData().getTileList()) {
					if (tile.getHeight() == tileHeight) {
						SelectTile.use(tile, selectedTiles);
					}
				}
			} else {
				new Alert(AlertType.WARNING, "Input valid height").show();
			}
		} catch (NumberFormatException e) {
			new Alert(AlertType.WARNING, "Input valid height").show();
		}
	}
}
