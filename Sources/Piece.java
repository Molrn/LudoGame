// Class representing pieces
public class Piece {
	private int axisX, axisY, status, originX, originY;
	
//	Constructor
	public Piece(int x, int y, int s) {
		axisX=x;
		axisY=y;
		status=s;
		originX=x;
		originY=y;
	}
	
//	Getters
	public int getX() { return axisX; }
	public int getY() { return axisY; }
	public int getOriginX () { return originX; }
	public int getOriginY () { return originY; }
	public int getStatus() { return status; }
	
//	Setters
	public void setX(int x) { axisX=x; }
	public void setY(int y) { axisY=y; }
	public void setStatus(int s) { status=s; }
	public void movePiece(int x, int y) {
		axisX = x;
		axisY = y;
	}
	
//	Sets the coordinates of the piece to its origin
	public void reinitializePosition() {
		axisX=originX;
		axisY=originY;
		status = -1;
	}
}
