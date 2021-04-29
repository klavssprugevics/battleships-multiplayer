package model;
import java.awt.Point;
import java.util.Vector;

@SuppressWarnings("serial")
public class Ship implements java.io.Serializable{

	private int xPos;
	private int yPos;
	private int rotation;
	private int[] xCoordinates;
	private int[] yCoordinates;
	private Vector<Point> coordinates = new Vector<Point>();
	private int size;
	
	
	public Ship(int xPos, int yPos, int rotation, int size)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.rotation = rotation;
		this.size = size;
		
		xCoordinates = new int[size];
		yCoordinates = new int[size];
		
		for (int i = 0; i < size; i++) {
			if(rotation == 0)
			{
				xCoordinates[i] = xPos;
				yCoordinates[i] = yPos + i;
				coordinates.add(new Point(xPos, yPos + i));
			}
			else
			{
				xCoordinates[i] = xPos + i;
				yCoordinates[i] = yPos;
				coordinates.add(new Point(xPos + i, yPos));
			}
		}
	}
	
	public Vector<Point> getCoordinates() {
		return coordinates;
	}

	public int getxPos() {
		return xPos;
	}


	public void setxPos(int xPos) {
		this.xPos = xPos;
	}


	public int getyPos() {
		return yPos;
	}


	public void setyPos(int yPos) {
		this.yPos = yPos;
	}


	public int getRotation() {
		return rotation;
	}


	public void setRotation(int rotation) {
		this.rotation = rotation;
	}


	public int getSize() {
		return size;
	}


	public void setSize(int size) {
		this.size = size;
	}

}
