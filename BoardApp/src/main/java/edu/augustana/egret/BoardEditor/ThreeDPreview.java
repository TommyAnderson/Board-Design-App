package edu.augustana.egret.BoardEditor;

import java.util.List;

import edu.augustana.egret.BoardEditorData.HexagonTile;
import edu.augustana.egret.BoardEditorData.Tile;
import edu.augustana.egret.BoardEditorData.MapData.Shape;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.scene.shape.Box;

public class ThreeDPreview {
	private static int cubeSize;
	private static int width = 1500;
	private static int length = 800;
	private static int heightVar = 20;
	private static double boardWidth;
	private static double boardHeight;

	private static double anchorX, anchorY;
	private static double anchorAngleX = 0;
	private static double anchorAngleY = 0;
	private static DoubleProperty angleX;
	private static DoubleProperty angleY;
	
	//Credit given to Genuine Coder for methods indicated by comments above methods
	//https://www.youtube.com/channel/UCCXbhmjID-T2I0KfuDPbi6A

	/**
	 * Creates a new stage which will render the 3D preview inside a new scene.
	 */
	public static void start() {
		List<Tile> tileList = App.getMapData().getTileList();
		boardWidth = App.getMapData().getNumColumns();
		boardHeight = App.getMapData().getNumRows();
		calculateSize();

		SmartGroup group = createGroup(tileList);

		PerspectiveCamera camera = new PerspectiveCamera();
		Stage stage = new Stage();
		Scene scene = new Scene(group, width, length, true);

		scene.setFill(Color.SILVER);
		scene.setCamera(camera);

		group.translateXProperty().set(width / 2);
		group.translateYProperty().set(length / 2);
		group.rotateByY(25);

		initMouseCntrol(group, scene, stage);

		stage.setTitle("3D Example");
		stage.setScene(scene);
		stage.show();
	}

	// Used from YouTube video by Genuine Coder, but tweaked to fix some bugs that
	// come with previewing multiple designs during one session.
	private static void initMouseCntrol(SmartGroup group, Scene scene, Stage stage) {
		angleX = new SimpleDoubleProperty(0);
		angleY = new SimpleDoubleProperty(0);

		Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
		Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
		group.getTransforms().addAll(xRotate, yRotate);

		xRotate.angleProperty().bind(angleX);
		yRotate.angleProperty().bind(angleY);

		scene.setOnMousePressed(event -> {
			anchorX = event.getSceneX();
			anchorY = event.getSceneY();
			anchorAngleX = angleX.get();
			anchorAngleY = angleY.get();

		});

		scene.setOnMouseDragged(event -> {
			angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
			angleY.set(anchorAngleY + (anchorX - event.getSceneX()));
		});

		stage.addEventHandler(ScrollEvent.SCROLL, event -> {
			double movement = event.getDeltaY();
			group.translateZProperty().set(group.getTranslateZ() + movement);
		});
	}

	/**
	 * Creates a SmartGroup made up of the 3D renditions of every tile contained in
	 * the passed tile list and returns it.
	 * 
	 * @param tileList - the list of tiles that the 3D preview will be created from
	 * @return a SmartGroup comprising 3D renditions of every tile in the passed
	 *         tile list
	 */

	public static SmartGroup createGroup(List<Tile> tileList) {
		SmartGroup root = new SmartGroup();

		if (App.getMapData().getShape().equals(Shape.Hexagon)) {
			for (int i = 0; i < tileList.size(); i++) {
				HexagonTile tile = (HexagonTile) tileList.get(i);
				int height = (int) tile.getHeight() * heightVar;
				double xCoord = tile.getColumn() * cubeSize * .866 + (tile.getRow() % 2) * cubeSize * .866 / 2
						- boardWidth * cubeSize * .866 / 2;
				double zCoord = -(tile.getRow() * cubeSize * 0.75 + tile.getYStartOffset())
						+ boardHeight * cubeSize * .866 / 2;

				Box box1 = createCube(height, xCoord, zCoord, cubeSize * .866, cubeSize / 2, -height / 2);
				Box box2 = createCube(height, xCoord, zCoord, cubeSize * .866, cubeSize / 2, -height / 2);
				Box box3 = createCube(height, xCoord, zCoord, cubeSize * .866, cubeSize / 2, -height / 2);
				box1.rotationAxisProperty().setValue(Rotate.Y_AXIS);
				box1.rotateProperty().set(60);
				box2.rotationAxisProperty().setValue(Rotate.Y_AXIS);
				box2.rotateProperty().set(120);

				root.getChildren().addAll(box1, box2, box3);

				if (tile.getIsTilePointy()) {
					double numLayers = cubeSize * 10;
					double layerHeight = (cubeSize / numLayers) / 2;
					double sideLengthAdj = cubeSize / numLayers;
					for (int j = 1; j < numLayers + 1; j++) {
						Box layer1 = createCube(layerHeight, xCoord, zCoord, cubeSize * .866 - sideLengthAdj * j,
								cubeSize / 2 - sideLengthAdj * j, -height - (layerHeight * (j - 1)));
						Box layer2 = createCube(layerHeight, xCoord, zCoord, cubeSize * .866 - sideLengthAdj * j,
								cubeSize / 2 - sideLengthAdj * j, -height - (layerHeight * (j - 1)));
						Box layer3 = createCube(layerHeight, xCoord, zCoord, cubeSize * .866 - sideLengthAdj * j,
								cubeSize / 2 - sideLengthAdj * j, -height - (layerHeight * (j - 1)));
						layer1.rotationAxisProperty().setValue(Rotate.Y_AXIS);
						layer1.rotateProperty().set(60);
						layer2.rotationAxisProperty().setValue(Rotate.Y_AXIS);
						layer2.rotateProperty().set(120);

						root.getChildren().addAll(layer1, layer2, layer3);
					}
				}
			}

		} else {
			for (int i = 0; i < tileList.size(); i++) {
				Tile tile = tileList.get(i);
				int height = (int) tile.getHeight() * heightVar;
				double xCoord = createSquareXCoordinate(tile.getColumn());
				double zCoord = createDepthCoordinate(tile.getRow());

				Box box = createCube(height, xCoord, zCoord, cubeSize, cubeSize, -height / 2);

				root.getChildren().addAll(box);

				if (tile.getIsTilePointy()) {
					double numLayers = cubeSize * 10;
					double layerHeight = (cubeSize / numLayers) / 2;
					double sideLengthAdj = cubeSize / numLayers;
					for (int j = 1; j < numLayers + 1; j++) {
						Box layer = createCube(layerHeight, xCoord, zCoord, cubeSize - sideLengthAdj * j,
								cubeSize - sideLengthAdj * j, -height - (layerHeight * (j - 1)));
						root.getChildren().addAll(layer);
					}
				}
			}
		}

		return root;
	}

	/**
	 * Creates a 3D box from the properties of a specific tile.
	 * 
	 * @param width       - the extent in the x-direction of this specific tile
	 * @param height      - the extent in the z-direction of this specific tile
	 * @param length      - the extent in the y-direction of this specific tile
	 * @param xAdjustment - the adjustment value in the x-direction necessitated of
	 *                    this specific tile
	 * @param yAdjustment - the adjustment value in the y-direction necessitated of
	 *                    this specific tile
	 * @param zAdjustment - the adjustment value in the z-direction necessitated of
	 *                    this specific tile
	 * @return the 3D box created from a specific tile's properties
	 */

	public static Box createCube(double height, double xAdjustment, double zAdjustment, double width, double length,
			double yAdjustment) {
		Box box = new Box();
		box.setWidth(width); // x size
		box.setHeight(height); // y size
		box.setDepth(length); // z size

		box.translateXProperty().set(xAdjustment);
		box.translateYProperty().set(yAdjustment);
		box.setTranslateZ(zAdjustment);

		PhongMaterial mat = new PhongMaterial();
		mat.setDiffuseColor(Color.BURLYWOOD);

		box.setMaterial(mat);

		return box;
	}

	/**
	 * Calculates the cube size for each tile based on the board's width and height.
	 */

	public static void calculateSize() {
		if (boardWidth >= boardHeight) {
			cubeSize = (int) (600 / boardWidth);
		} else {
			cubeSize = (int) (600 / boardHeight);
		}
	}

	/**
	 * Calculates the x-coordinate for the 3D rendition of a specific square tile.
	 * 
	 * @param tileXCoordinate - the x-coordinate of this specific square tile
	 * @return the calculated x-coordinate of the 3D rendition of this specific
	 *         square tile
	 */

	public static double createSquareXCoordinate(int tileXCoordinate) {
		if (boardWidth % 2 == 0) {
			if (boardWidth / 2 > tileXCoordinate) {
				return -(boardWidth / 2 - tileXCoordinate) * cubeSize + cubeSize / 2;
			} else {
				return (tileXCoordinate - boardWidth / 2) * cubeSize + cubeSize / 2;
			}
		} else {
			if (boardWidth / 2 > tileXCoordinate) {
				return -(boardWidth / 2 - tileXCoordinate - 1) * cubeSize - cubeSize / 2;
			} else if (boardWidth / 2 + 1 == tileXCoordinate) {
				return 0;
			} else {
				return (tileXCoordinate - boardWidth / 2 + 1) * cubeSize - cubeSize / 2;
			}

		}
	}

	/**
	 * Calculates the x-coordinate for the 3D rendition of a specific hexagonal
	 * tile.
	 * 
	 * @param tileXCoordinate - the x-coordinate of this specific hexagonal tile
	 * @param column          - the column this specific hexagonal tile is located
	 *                        in
	 * @param TileWidth       - the width of this specific hexagonal tile
	 * @param row             - the row this specific hexagonal tile is located in
	 * @param sideRadius      - the radius of each side of this specific hexagonal
	 *                        tile
	 * @return the calculated x-coordinate for the 3D rendition of this specific
	 *         hexagonal tile
	 */

	public static double createHexagonalXCoordinate(int tileXCoordinate, int column, double TileWidth, int row,
			double sideRadius) {
		double xCoord = column * TileWidth + (row % 2) * sideRadius;
		return xCoord;
	}

	/**
	 * Calculates the depth coordinate for the 3D rendition of a specific tile
	 * 
	 * @param tileYCoordinate - the y-coordinate of this specific tile
	 * @return the calculated y-coordinate for the 3D rendition of this specific
	 *         tile
	 */

	public static double createDepthCoordinate(int tileYCoordinate) {
		if (boardHeight % 2 == 0) {
			if (boardHeight / 2 > tileYCoordinate) {
				return (boardHeight / 2 - tileYCoordinate) * cubeSize - cubeSize / 2;
			} else {
				return -(tileYCoordinate - boardHeight / 2) * cubeSize - cubeSize / 2;
			}
		} else {
			if (boardHeight / 2 > tileYCoordinate) {
				return (boardHeight / 2 - tileYCoordinate) * cubeSize - cubeSize / 2;
			} else if (boardHeight / 2 + 1 == tileYCoordinate) {
				return 0;
			} else {
				return -(tileYCoordinate - boardHeight / 2) * cubeSize - cubeSize / 2;
			}

		}
	}

	// Copied from YouTube video by GenuineCoder
	static class SmartGroup extends Group {
		Transform transform;

		void rotateByX(int angle) {
			transform = new Rotate(angle, new Point3D(0, 1, 0));
			this.getTransforms().add(transform);
		}

		void rotateByY(int angle) {
			transform = new Rotate(angle, new Point3D(1, 0, 0));
			this.getTransforms().add(transform);
		}
	}
}