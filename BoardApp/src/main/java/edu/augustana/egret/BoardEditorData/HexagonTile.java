package edu.augustana.egret.BoardEditorData;

import java.awt.Point;
import java.awt.Polygon;

public class HexagonTile extends Tile {

	private double[] xCoordinates;
	private double[] yCoordinates;
	private double TILE_WIDTH;
	private double TILE_HEIGHT;
	private double sideRadius;
	private int yStartOffset;
	private double cornerRadius;

	/**
	 * Creates a hexagonal tile located in the specified column and row with the
	 * specified size height and state as pointy or not.
	 * 
	 * @param column - the column the tile is located in
	 * @param row    - the row the tile is located in
	 * @param size   - the length of each side of the tile
	 * @param height - how tall the tile is
	 * @param pointy - whether or not the tile is a pointy tile
	 */

	public HexagonTile(int column, int row, double size, double height, boolean pointy) {
		super(column, row, height, size, pointy);
		helper(column, row);
	}

	/**
	 * Returns the x-coordinates of each vertex of a particular 2D hexagonal tile.
	 * 
	 * @return the x-coordinates of each vertex of a particular 2D hexagonal tile
	 */

	@Override
	public double[] getXCoordinates() {
		helper(getColumn(), getRow());
		return xCoordinates;
	}

	/**
	 * Returns the y-coordinates of each vertex of a particular 2D hexagonal tile.
	 * 
	 * @return the y-coordinates of each vertex of a particular 2D hexagonal tile
	 */

	@Override
	public double[] getYCoordinates() {
		helper(getColumn(), getRow());
		return yCoordinates;
	}

	/**
	 * Returns whether a given hexagonal tile contains the point clicked on.
	 * 
	 * @return whether a given hexagonal tile contains the point clicked on
	 */

	@Override
	public boolean clickInTile(double xCoordinate, double yCoordinate) {

		Polygon poly = new Polygon();
		for (int i = 0; i < 6; i++) {
			poly.addPoint((int) xCoordinates[i], (int) yCoordinates[i]);
		}
		return poly.contains(new Point((int) xCoordinate, (int) yCoordinate));
	}

	// code credit to Dennis
	// https://stackoverflow.com/questions/54165602/create-hexagonal-field-with-javafx

	private void helper(int column, int row) {
		cornerRadius = getSize() / Math.sqrt(3.0); // the inner radius from the hexagon center to outer corner
		sideRadius = Math.sqrt(cornerRadius * cornerRadius * 0.75); // the inner radius from the hexagon center to
																	// middle of the axis
		yStartOffset = (int) cornerRadius / 2;
		TILE_HEIGHT = 2 * cornerRadius;
		TILE_WIDTH = 2 * sideRadius;

		double xCoord = column * TILE_WIDTH + (row % 2) * sideRadius;
		double yCoord = row * TILE_HEIGHT * 0.75 + yStartOffset;
		xCoordinates = new double[] { xCoord + getColumn() * 2, xCoord + getColumn() * 2,
				xCoord + sideRadius + getColumn() * 2, xCoord + TILE_WIDTH + getColumn() * 2,
				xCoord + TILE_WIDTH + getColumn() * 2, xCoord + sideRadius + getColumn() * 2 };
		yCoordinates = new double[] { yCoord + getRow() * 2, yCoord + cornerRadius + getRow() * 2,
				yCoord + cornerRadius * 1.5 + getRow() * 2, yCoord + cornerRadius + getRow() * 2, yCoord + getRow() * 2,
				yCoord - cornerRadius * 0.5 + getRow() * 2 };
	}

	/**
	 * Returns the width of a particular hexagonal tile.
	 * 
	 * @return the width of a particular hexagonal tile
	 */

	public double getTileWidth() {
		return TILE_WIDTH;
	}

	/**
	 * Returns the height of a particular hexagonal tile.
	 * 
	 * @return the height of a particular hexagonal tile
	 */

	public double getTileHeight() {
		return TILE_HEIGHT;
	}

	/**
	 * Returns the side radius of a particular hexagonal tile.
	 * 
	 * @return the side radius of a particular hexagonal tile
	 */

	public double getSideRadius() {
		return sideRadius;
	}

	/**
	 * returns the initial y offset of a particular hexagonal tile.
	 * 
	 * @return the initial y offset of a particular hexagonal tile
	 */

	public int getYStartOffset() {
		return yStartOffset;
	}

	/**
	 * Resizes all the x-coordinates of a particular hexagonal tile.
	 * 
	 * @param resize - the percentage of the original tile size that the tile's
	 *               x-coordinates should be resized to
	 * @return a list of the resized tile x-coordinates
	 */

	@Override
	public double[] resizeXCoordinates(double resize) {
		double[] selectCoords = getXCoordinates();
		double cornerRadius = getSize() / Math.sqrt(3.0) * resize;
		double tempSideRadius = Math.sqrt(cornerRadius * cornerRadius * 0.75);
		double tileWidth = tempSideRadius * 2;
		double xCoord = getColumn() * TILE_WIDTH + (getRow() % 2) * sideRadius + sideRadius * (1 - resize);

		selectCoords[0] = xCoord + getColumn() * 2;
		selectCoords[1] = xCoord + getColumn() * 2;
		selectCoords[3] = xCoord + tileWidth + getColumn() * 2;
		selectCoords[4] = xCoord + tileWidth + getColumn() * 2;
		return selectCoords;
	}

	/**
	 * Resizes all the y-coordinates of a particular hexagonal tile.
	 * 
	 * @param resize - the percentage of the original tile size that the tile's
	 *               y-coordinates should be resized to
	 * @return a list of the resized tile y-coordinates
	 */

	@Override
	public double[] resizeYCoordinates(double resize) {
		double[] selectCoords = getYCoordinates();
		double cornerRadius = getSize() / Math.sqrt(3.0) * resize;
		double yCoord = getRow() * TILE_HEIGHT * 0.75 + yStartOffset + cornerRadius * (1 - resize);

		selectCoords[0] = yCoord + getRow() * 2;
		selectCoords[1] = yCoord + cornerRadius * .95 + getRow() * 2;
		selectCoords[2] = yCoord + cornerRadius * 1.5 * .95 + getRow() * 2;
		selectCoords[3] = yCoord + cornerRadius * .95 + getRow() * 2;
		selectCoords[4] = yCoord + getRow() * 2;
		selectCoords[5] = yCoord - cornerRadius * 0.5 + getRow() * 2;

		return selectCoords;
	}
}
