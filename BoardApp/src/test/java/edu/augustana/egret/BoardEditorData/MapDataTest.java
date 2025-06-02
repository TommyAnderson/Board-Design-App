package edu.augustana.egret.BoardEditorData;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class MapDataTest {

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
	void testAddRow() {
		MapData data = createData();
		assertEquals(2, data.getNumRows());
		assertEquals(1.0, data.getTileList().get(0).getHeight());

		data.addRow(1);
		assertEquals(3, data.getNumRows());
		assertEquals(1.0, data.getTileList().get(4).getHeight());
	}

	@Test
	void testDeleteRow() {
		MapData data = createData();
		data.deleteRow(1);
		assertEquals(1, data.getNumRows());

		data.addRow(0);
		data.addRow(0);
		data.addRow(0);
		assertEquals(4, data.getNumRows());

		data.deleteRow(3);
		assertEquals(3, data.getNumRows());
	}

	@Test
	void testAddColumn() {
		MapData data = createData();
		assertEquals(2, data.getNumColumns());

		data.addColumn(0);
		data.addColumn(0);
		data.addColumn(0);
		assertEquals(5, data.getNumColumns());
	}

	@Test
	void testDeleteColumn() {
		MapData data = createData();
		data.deleteColumn(1);
		assertEquals(1, data.getNumColumns());
	}

	@Test
	void testGetTileAtClick() throws InterruptedException {
		MapData data = createData();
		Tile tileClicked = data.getTileList().get(3);
		assertEquals(tileClicked, data.getTileAtClick(450.0, 450.0));

		data.addRow(1);
		tileClicked = data.getTileList().get(5);
		assertEquals(tileClicked, data.getTileAtClick(500.0, 500.0));
	}
}
