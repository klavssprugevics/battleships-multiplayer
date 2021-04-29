package model;

@SuppressWarnings("serial")
public class Shot implements java.io.Serializable{

	private int x;
	private int y;
	
	public Shot(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
