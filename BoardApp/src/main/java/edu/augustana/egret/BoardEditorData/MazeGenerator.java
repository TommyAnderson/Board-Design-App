package edu.augustana.egret.BoardEditorData;

import java.util.Random;

public class MazeGenerator {
	private static final int ALIVE_HEIGHT = 1;
	private static final int DEAD_HEIGHT = 4;
	private Tile[][] tileList;

	/**
	 * Creates a MazeGenerator object from a passed MapData object.
	 * 
	 * @param mapData - the map data that will be used to create a random maze
	 */

	public MazeGenerator(MapData mapData) {
		tileList = mapData.makeTileList2D();
		createMaze(mapData);
	}

	/**
	 * Creates a random maze through the use of a passed MapData object and by
	 * checking each tile's alive neighbors throughout the MapData's tile list.
	 * 
	 * @param mapData - the data used to generate the random maze from
	 */

	public void createMaze(MapData mapData) {
		Random random = new Random();
		for (int i = 0; i < (mapData.getNumRows() * mapData.getNumColumns()) / 4; i++) {
			tileList[random.nextInt(mapData.getNumRows())][random.nextInt(mapData.getNumColumns())]
					.setHeight(DEAD_HEIGHT);
		}

		for (int k = 0; k < 200; k++) {
			for (int i = 0; i < mapData.getNumRows(); i++) {
				for (int j = 0; j < mapData.getNumColumns(); j++) {
					if (tileList[i][j].getHeight() == DEAD_HEIGHT) {
						if (checkAliveNeighbors(i, j) == 3) {
							tileList[i][j].setHeight(ALIVE_HEIGHT);
						}
					} else {
						if (checkAliveNeighbors(i, j) == 0 || checkAliveNeighbors(i, j) > 5) {
							tileList[i][j].setHeight(DEAD_HEIGHT);
						}
					}
				}
			}
		}
	}

	/**
	 * Checks the neighbors of every tile in a MapData object's tile list to see
	 * which are alive and dead.
	 * 
	 * @param row    - the row of a specific tile
	 * @param column - the column of a specific tile
	 * @return the number of alive neighbors of a specific tile
	 */

	public int checkAliveNeighbors(int row, int column) {
		int alive = 0;

		for (int i = row - 1; i < row + 2; i++) {
			for (int j = column - 1; j < column + 2; j++) {
				try {
					if (tileList[i][j].getHeight() == ALIVE_HEIGHT) {
						alive++;
					}
				} catch (ArrayIndexOutOfBoundsException e) {
					// nothing
				}
			}
		}
		return alive;
	}
}
