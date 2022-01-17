import java.awt.*;

// Class representing players of the game
public class Player {
	
	private String nickName;
	private Color color;
	private boolean human;
	private Piece[] pieces=new Piece[4];
	
//	Constructor
	public Player(Color c, String n, boolean h, int p) {
		color = c;
		nickName=n;
		human = h;
		int compteur=0;
//		Initializes pieces according to the position of the player
//		Their origin is the 2 by 2 square in the middle of each player's square
		switch(p) { 
			case 0 : 
				for(int i = 0; i<2; i++) {
					for(int j = 0; j<2; j++) {
						pieces[compteur] = new Piece(2+i, 2+j, -1);
						compteur++;
					}
				}
			break;
			case 1 : 
				for(int i = 0; i<2; i++) {
					for(int j = 0; j<2; j++) {
						pieces[compteur] = new Piece(2+i, 11+j, -1);
						compteur++;
					}
				}
			break;
			case 2 : 
				for(int i = 0; i<2; i++) {
					for(int j = 0; j<2; j++) {
						pieces[compteur] = new Piece(11+i, 11+j, -1);
						compteur++;
					}
				}
			break;
			case 3 : 
				for(int i = 0; i<2; i++) {
					for(int j = 0; j<2; j++) {
						pieces[compteur] = new Piece(11+i, 2+j, -1);
						compteur++;
					}
				}
			break;
		}
	}
	
//	Getters
	public String getName () { return nickName; }
	public Color getColor() { return color; }
	public boolean isHuman() { return human; }
	public Piece[] getPieces() { return pieces; }	
}