package edu.augustana.egret.BoardEditorData;

public abstract class Tile {

	private int column;
	private int row;
	private double height;
	private double size;
	private boolean isTilePointy;

	public abstract boolean clickInTile(double xCoordinate, double yCoordinate);

	public abstract double[] getXCoordinates();

	public abstract double[] getYCoordinates();

	public abstract double[] resizeXCoordinates(double resize);

	public abstract double[] resizeYCoordinates(double resize);

	/**
	 * Creates a new tile with the properties passed in.
	 * 
	 * @param column - the column the tile will be in
	 * @param row    - the row the tile will be in
	 * @param height - the height the tile will be
	 * @param size   - the size the tile will be
	 * @param pointy - whether the tile is pointy or not
	 */

	public Tile(int column, int row, double height, double size, boolean pointy) {
		this.column = column;
		this.row = row;
		this.height = height;
		this.size = size;
		isTilePointy = pointy;
	}

	/**
	 * Returns the column a tile is in.
	 * 
	 * @return the column the tile is in
	 */

	public int getColumn() {
		return column;
	}

	/**
	 * Returns the row a tile is in.
	 * 
	 * @return the row the tile is in
	 */

	public int getRow() {
		return row;
	}

	/**
	 * Sets the row a tile is in.
	 * 
	 * @param row - the row the tile will be set in
	 */

	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * Sets the column a tile is in.
	 * 
	 * @param column - the column the tile will be set in
	 */

	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Returns the height of a tile.
	 * 
	 * @return the height of the tile
	 */

	public double getHeight() {
		return height;
	}

	/**
	 * Sets the height of a tile.
	 * 
	 * @param height - the height the tile will be
	 */

	public void setHeight(double height) {
		this.height = height;
	}

	/**
	 * Returns the size of a tile.
	 * 
	 * @return the size of the tile
	 */

	public double getSize() {
		return size;
	}

	/**
	 * Sets the size of a tile.
	 * 
	 * @param size - the size the tile will be
	 */

	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Returns the grayScale color value for a tile.
	 * 
	 * @return an rgb value from 0-1.0 which shows the color of the tile
	 */

	public double getGrayscaleColor() {
		if (getHeight() / 10.0 > 1) {
			return 1;
		}

		return getHeight() / 10.0;
	}

	/**
	 * Returns whether a tile is pointy or not.
	 * 
	 * @return whether the tile is pointy or not
	 */

	public boolean getIsTilePointy() {
		return isTilePointy;
	}

	/**
	 * Sets a tile to be pointy.
	 * 
	 * @param point - the point at which the tile containing it will be turned
	 *              pointy
	 */

	public void setTilePointy(boolean point) {
		isTilePointy = point;
	}

	/**
	 * Returns the tile's properties in the form of a String.
	 * 
	 * @return the tile's properties in the form of a String
	 */

	public String toString() {
		String string = "Column: " + getColumn() + ", Row " + getRow() + ", Size: " + getSize() + ", Height: "
				+ getHeight();
		return string;
	}

	/**
	 * Returns if the passed object is a tile or not.
	 * 
	 * @return if the passed object is a tile or not
	 */

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (!(object instanceof Tile)) {
			return false;
		} else {
			Tile other = (Tile) object;
			return other.getColumn() == this.getColumn() && other.getRow() == this.getRow();
		}
	}

}
