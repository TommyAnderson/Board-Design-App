package edu.augustana.egret.BoardEditorData;

public class SquareTile extends Tile {

	private double[] xCoordinates;
	private double[] yCoordinates;

	/**
	 * Creates a new square tile with the properties passed into it.
	 * 
	 * @param column - the column the tile is in
	 * @param row    - the row the tile is in
	 * @param size   - the size the tile will be
	 * @param height - the height the tile will be
	 * @param pointy - whether the tile is pointy or not
	 */

	public SquareTile(int column, int row, double size, double height, boolean pointy) {
		super(column, row, height, size, pointy);
		xCoordinates = new double[] { getTopLeftX(), getTopLeftX() + getSize(), getTopLeftX() + getSize(),
				getTopLeftX() };
		yCoordinates = new double[] { getTopLeftY(), getTopLeftY(), getTopLeftY() + getSize(),
				getTopLeftY() + getSize() };
	}

	/**
	 * Returns the x-coordinates of the tile.
	 * 
	 * @return the x-coordinates of the tile
	 */

	@Override
	public double[] getXCoordinates() {
		xCoordinates = new double[] { getTopLeftX(), getTopLeftX() + getSize(), getTopLeftX() + getSize(),
				getTopLeftX() };
		return xCoordinates;
	}

	/**
	 * Returns the y-coordinates of the tile.
	 *
	 * @return the y-coordinates of the tile
	 */

	@Override
	public double[] getYCoordinates() {
		yCoordinates = new double[] { getTopLeftY(), getTopLeftY(), getTopLeftY() + getSize(),
				getTopLeftY() + getSize() };
		return yCoordinates;
	}

	/**
	 * Returns the top left x-coordinate of the tile.
	 * 
	 * @return the top left x-coordinate of the tile
	 */

	public double getTopLeftX() {
		return getColumn() * getSize() + (getColumn() * 2);
	}

	/**
	 * Returns the top left y-coordinate of the tile.
	 * 
	 * @return the top left y-coordinate of the tile
	 */

	public double getTopLeftY() {
		return getRow() * getSize() + (getRow() * 2);
	}

	/**
	 * Determines and returns whether a mouse click is located within a tile.
	 * 
	 * @return whether a mouse click is located within a tile
	 */

	@Override
	public boolean clickInTile(double xCoordinate, double yCoordinate) {
		if (this.getTopLeftX() < xCoordinate && this.getTopLeftX() + this.getSize() > xCoordinate
				&& this.getTopLeftY() < yCoordinate && this.getTopLeftY() + this.getSize() > yCoordinate) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Resizes and returns the x-coordinates of a tile.
	 * 
	 * @return the resized x-coordinates of a tile
	 */

	@Override
	public double[] resizeXCoordinates(double resize) {
		double[] selectCoords = getXCoordinates();
		selectCoords[0] += getSize() * resize;
		selectCoords[1] -= getSize() * resize;
		selectCoords[2] -= getSize() * resize;
		selectCoords[3] += getSize() * resize;
		return selectCoords;
	}

	/**
	 * Resizes and returns the y-coordinates of a tile.
	 *
	 * @return the resized y-coordinates of a tile
	 */

	@Override
	public double[] resizeYCoordinates(double resize) {
		double[] selectCoords = getYCoordinates();
		selectCoords[0] += getSize() * resize;
		selectCoords[1] += getSize() * resize;
		selectCoords[2] -= getSize() * resize;
		selectCoords[3] -= getSize() * resize;
		return selectCoords;
	}

}