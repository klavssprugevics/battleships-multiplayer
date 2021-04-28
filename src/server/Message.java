package server;

import java.awt.Point;
import java.util.Vector;

@SuppressWarnings("serial")
public class Message implements java.io.Serializable{

	private boolean hit;
	private boolean sink;
	private boolean victory;
	private String nextTurn;
	private Vector<Point> shipCoords;
	
	public Message(boolean hit, boolean sink, boolean victory, String nextTurn) {
		this.hit = hit;
		this.sink = sink;
		this.victory = victory;
		this.nextTurn = nextTurn;
	}

	public Vector<Point> getShipCoords() {
		return shipCoords;
	}

	public void setShipCoords(Vector<Point> shipCoords) {
		this.shipCoords = shipCoords;
	}

	public boolean isHit() {
		return hit;
	}

	public boolean isSink() {
		return sink;
	}

	public boolean isVictory() {
		return victory;
	}

	public String getNextTurn() {
		return nextTurn;
	}
}
