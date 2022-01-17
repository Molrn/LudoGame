import java.awt.*;

// Class representing cases containing a piece
public class PieceCase extends Case {
	
	private static final long serialVersionUID = -2227304568344809199L;
	private Color pieceColor;
	
//	Constructor
	public PieceCase(boolean s, boolean b, int p, Color c, Color piece) {
		super(s, b, p, c);
		pieceColor=piece;
	}

//	Draws a disc into the case
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(pieceColor);
		g.fillOval(0, 0, SIZE-3, SIZE-3);
		g.setColor(Color.black);
		g.drawOval(0,0,SIZE-3,SIZE-3);
	}
}
