import java.awt.*;
import javax.swing.*;

// Class representing empty cases from the board
public class Case extends JComponent {
	
	private static final long serialVersionUID = -8946795539070238444L;
	public final int SIZE=50;
	private int index;
	private boolean safe, blocked;
	private Color color;
	
//	Constructors
	public Case(boolean s, boolean b, int i, Color c){
		safe=s;
		blocked = b;
		color=c;
		index=i;
	}
	public Case(){
		safe=true;
		blocked = false;
		color=Color.WHITE;
		index=-1;
	}
	public Case(Color c) { color = c; }

//	Getters
	public boolean isBlocked(){ return blocked; }
	public boolean isSafe(){ return safe; }
	public Color getColor(){ return color;	}
	public int getIndex(){ return index; }

//	Paints a JComponent 
//	Colored square surrounded by a black border 
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(color);
		g.fillRect(0,0,SIZE,SIZE);
		g.setColor(Color.black);
		g.drawRect(0,0,SIZE,SIZE);
	}
}
