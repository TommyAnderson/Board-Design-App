package edu.augustana.egret.BoardEditorData;

import java.util.ArrayList;
import java.util.List;

import edu.augustana.egret.BoardEditorData.MapData.Shape;

public class MapData {

	private Shape shape;
	private List<Tile> tileList;
	private int numRows;
	private int numColumns;

	public enum Shape {
		Hexagon, Square;
	}

	/**
	 * Creates a new MapData object with an empty ArrayList of tiles and no rows or
	 * columns.
	 */

	public MapData() {
		this.tileList = new ArrayList<>();
		this.numRows = 0;
		this.numColumns = 0;
	}

	/**
	 * Creates a new MapData object with an empty ArrayList of tiles and specified
	 * passed in values for the number of rows and columns and the shape of tiles
	 * the list will eventually have.
	 * 
	 * @param numRows    - the number of rows tileList will have
	 * @param numColumns - the number of columns tileList will have
	 * @param shape      - the shape all tiles in tileList will be
	 */

	public MapData(int numRows, int numColumns, Shape shape) {
		this.tileList = new ArrayList<>();
		this.numRows = numRows;
		this.numColumns = numColumns;
		this.shape = shape;
	}

	/**
	 * Returns the shape tiles in the MapData object have.
	 * 
	 * @return the shape tiles in the MapData object have
	 */

	public Shape getShape() {
		return shape;
	}

	/**
	 * Sets the shape of tiles in the MapData object to equal shape.
	 * 
	 * @param shape - the shape tiles in the MapData object will become
	 */

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	/**
	 * Returns the MapData object's list of tiles.
	 * 
	 * @return the MapData object's list of tiles
	 */

	public List<Tile> getTileList() {
		return tileList;
	}

	/**
	 * Sets the MapData object's tile list equal to tileList.
	 * 
	 * @param tileList - the list of tiles the MapData's tile list will be set equal
	 *                 to
	 */

	public void setTileList(List<Tile> tileList) {
		this.tileList = tileList;
	}

	/**
	 * Returns the number of rows in the MapData object.
	 * 
	 * @return the number of rows in the MapData object
	 */

	public int getNumRows() {
		return numRows;
	}

	/**
	 * Sets the number of rows in the MapData object.
	 * 
	 * @param numRows - the number of rows the MapData object will have
	 */

	public void setNumRows(int numRows) {
		if (numRows < 1) {
			throw new NumberFormatException();
		}
		this.numRows = numRows;
	}

	/**
	 * Adds a row to the MapData object at a specific location.
	 * 
	 * @param location - the location the new row will be added at
	 */

	public void addRow(int location) {
		for (Tile tile : getTileList()) {
			if (tile.getRow() >= location) {
				tile.setRow(tile.getRow() + 1);
			}
		}
		for (int i = 0; i < getNumColumns(); i++) {
			if (getShape().equals(Shape.Square)) {
				getTileList().add(new SquareTile(i, location, CanvasData.getTileSize(this), 1, false));
			} else if (getShape().equals(Shape.Hexagon)) {
				getTileList().add(new HexagonTile(i, location, CanvasData.getTileSize(this), 1, false));
			}

		}
		setNumRows(getNumRows() + 1);
	}

	/**
	 * Deletes a row from the MapData object at a specific location.
	 * 
	 * @param location - the location the row will be deleted from
	 */

	public void deleteRow(int location) {
		if (getNumRows() != 1) {
			List<Tile> rowToRemove = new ArrayList<Tile>();
			for (Tile tile : getTileList()) {
				if (tile.getRow() == location) {
					rowToRemove.add(tile);
				}
			}
			getTileList().removeAll(rowToRemove);
			for (Tile tile : getTileList()) {
				if (tile.getRow() > location) {
					tile.setRow(tile.getRow() - 1);
				}
			}
			setNumRows(getNumRows() - 1);
		}
	}

	/**
	 * Returns the number of columns in the MapData object.
	 * 
	 * @return the number of columns in the MapData object
	 */

	public int getNumColumns() {
		return numColumns;
	}

	/**
	 * Sets the number of columns in the MapData object equal to numColumns.
	 * 
	 * @param numColumns - the number of columns MapData will have
	 */

	public void setNumColumns(int numColumns) {
		if (numColumns < 1) {
			throw new NumberFormatException();
		}
		this.numColumns = numColumns;
	}

	/**
	 * Adds a column to the MapData object at a specific location.
	 * 
	 * @param location - the location the column will be added at
	 */

	public void addColumn(int location) {
		for (Tile tile : getTileList()) {
			if (tile.getColumn() >= location) {
				tile.setColumn(tile.getColumn() + 1);
			}
		}
		for (int i = 0; i < getNumRows(); i++) {
			if (getShape().equals(Shape.Square)) {
				getTileList().add(new SquareTile(location, i, CanvasData.getTileSize(this), 1, false));
			} else if (getShape().equals(Shape.Hexagon)) {
				getTileList().add(new HexagonTile(location, i, CanvasData.getTileSize(this), 1, false));
			}
		}
		setNumColumns(getNumColumns() + 1);
	}

	/**
	 * Deletes a column from the MapData object at a specific location.
	 * 
	 * @param location - the location the column will be deleted from
	 */

	public void deleteColumn(int location) {
		if (getNumColumns() != 1) {
			List<Tile> columnToRemove = new ArrayList<Tile>();
			for (Tile tile : getTileList()) {
				if (tile.getColumn() == location) {
					columnToRemove.add(tile);
				}
			}
			getTileList().removeAll(columnToRemove);
			for (Tile tile : getTileList()) {
				if (tile.getColumn() > location) {
					tile.setColumn(tile.getColumn() - 1);
				}
			}
			setNumColumns(getNumColumns() - 1);
		}
	}

	/**
	 * Returns the tile containing the point the mouse clicks on.
	 * 
	 * @param xCoord - the x-coordinate of the mouse click
	 * @param yCoord - the y-coordinate of the mouse click
	 * @return the tile containing the point the mouse clicks on
	 * @throws InterruptedException - thrown if no tile is found at the coordinates
	 *                              clicked on
	 */

	public Tile getTileAtClick(double xCoord, double yCoord) throws InterruptedException {
		for (Tile tile : getTileList()) {
			if (tile.clickInTile(xCoord, yCoord)) {
				return tile;
			}
		}
		throw new InterruptedException("No tile could be found at " + xCoord + ", " + yCoord);
	}

	/**
	 * Creates and returns a 2D Array of Tiles to be used in other methods.
	 * 
	 * @return a 2D Array of Tiles
	 */

	public Tile[][] makeTileList2D() {
		Tile[][] tileList = null;
		if (getShape().equals(Shape.Square)) {
			tileList = new SquareTile[getNumRows()][getNumColumns()];
		} else {
			tileList = new HexagonTile[getNumRows()][getNumColumns()];
		}
		int counter = 0;
		for (int i = 0; i < getNumRows(); i++) {
			for (int j = 0; j < getNumColumns(); j++) {
				tileList[i][j] = getTileList().get(counter);
				counter++;
			}
		}
		return tileList;
	}

	/**
	 * Returns the data of each tile as a String.
	 * 
	 * @return the data of each tile as a String
	 */

	@Override
	public String toString() {
		String string = "";
		for (Tile tile : tileList) {
			string += tile + "\n";
		}
		return string;
	}
}
