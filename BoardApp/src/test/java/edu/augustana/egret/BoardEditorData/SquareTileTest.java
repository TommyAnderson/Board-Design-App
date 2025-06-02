package edu.augustana.egret.BoardEditorData;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class SquareTileTest {

	private MapData createData() {
		MapData mapData1 = new MapData(2, 2, MapData.Shape.Square);
		List<Tile> tileList = new ArrayList<>();
		tileList.add(new SquareTile(0, 0, 300.0, 1.0, false));
		tileList.add(new SquareTile(1, 0, 300.0, 2.0, false));
		tileList.add(new SquareTile(0, 1, 300.0, 3.0, false));
		tileList.add(new SquareTile(1, 1, 300.0, 4.0, false));
		mapData1.setTileList(tileList);
		return mapData1;
	}

	@Test
	void testResizeXCoordinates() {
		MapData data = createData();
		data.addRow(0);
		data.addColumn(0);
		for (Tile tile : data.getTileList()) {
			tile.resizeXCoordinates(2 / 3);
		}
		assertEquals(198, data.getTileList().get(7).getSize());
	}

	@Test
	void testResizeYCoordinates() {
		MapData data = createData();
		data.addRow(1);
		data.addRow(1);
		data.addColumn(1);
		data.addColumn(1);
		for (Tile tile : data.getTileList()) {
			tile.resizeYCoordinates(0.5);
		}
		assertEquals(148, data.getTileList().get(15).getSize());
	}

	@Test
	void testToString() {
		MapData data = createData();
		Tile tile = data.getTileList().get(3);
		assertEquals("Column: 1, Row 1, Size: 300.0, Height: 4.0", tile.toString());
	}
}
