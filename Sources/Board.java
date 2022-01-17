import java.awt.*;
import java.awt.event.*;

import javax.swing.JButton;
import javax.swing.JFrame;

// class representing the frame on which the board is displayed
// Implements the interface ActionListener to be able to put buttons in the frame 
public class Board implements ActionListener {
	
	private JFrame f;
	private Case[][] grid;
	public final int SIZE=15;
	public final int GRAPHICAL_LENGTH=50;
	private Color backgroundColor, safeCaseColor, finalCaseColor;
	private Player[] players=new Player[4];
	private JButton[] playablePiece = new JButton[4];
	private int[] buttonToPiece = new int[4];
	private int[] whereTo = new int[4];
	private int playing, diceRoll,willChange;
	private BoardMapper b;
	private boolean played, isInit;
	private int[][] toChange = new int[7][2];
	
//	Constructor
 	public Board(){
		grid=new Case[SIZE][SIZE];
		backgroundColor = Color.white;
		safeCaseColor=Color.pink;
		finalCaseColor = Color.gray;
		players[0] = new Player(Color.yellow, "Lavabo", true, 0);
		players[1] = new Player(Color.red, "Crochet", true, 1);
		players[2] = new Player(Color.green, "Mistress", true, 2);
		players[3] = new Player(Color.cyan, "Molrn", true, 3);
		f = new JFrame("LudoKing");
		f.setLayout(new GridLayout(SIZE,SIZE));
		b = new BoardMapper();
		played = false;
		isInit=false;
	}

 	//Getters	
	public JFrame getFrame() { return f; }
	public BoardMapper getBM() { return b; }
	public JButton[] getButtons() { return playablePiece; }
	public Player[] getPlayers() { return players; }
	public int getPlaying() { return playing; }
	public Case[][] getGrid(){ return grid; }
	public boolean isPlayed() { return played; }
	
	//Setters
	public void switchPlayer() { playing=(playing + 1)%4; }
	public void setDiceRoll(int d) { diceRoll=d; }

//	Board initializer : initializes cases which need to be modified on the board
//	Gets the index of every case from the BoardMapper
//	sets a PieceCase object if it contains a piece, a Case object otherwise
	public void initBoard(){
		boolean safe, blocked;
		Color c;
		int index, player;
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				if(hasToChange(i ,j) || isInit == false) {
					safe = false;
					blocked = false;
					index=b.getMap().get(j+" "+i);
					c=caseColor(index);
					if(c != backgroundColor ) { safe = true; }
					blocked = isCaseBlocked(i, j);
					player = containsPiece(i, j);
					if(player != -1){
						grid[i][j]=new PieceCase(safe, blocked, index, c, players[player].getColor());
					}else {
						grid[i][j]=new Case(safe, blocked, index, c);
					}
				}
			}
		}
		isInit=true;
	}

//	Prints the content of the grid on the frame
//	Cases which contains a moveable piece from the current player are replaced by a button
	public void printBoard(){
		f.getContentPane().removeAll();
		int index, player, compteur=0, destination;
		willChange = 0;
		played = false;
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				
				if(containsPieceFromPlayer(i, j, players[playing])) {
					player = playing;
				}else { player = containsPiece(i, j); }
				if (player != -1) {
					index=grid[i][j].getIndex();
					destination = destinationIndex(player, i, j, index);
					if (destination != -1 && isPieceBlocked(i, j) == false) {
						playablePiece[compteur]=new JButton();
						playablePiece[compteur].addActionListener(this);
						playablePiece[compteur].setBackground(grid[i][j].getColor());
						playablePiece[compteur].add(new Case (players[playing].getColor()));
						f.add(playablePiece[compteur]);
						buttonToPiece[compteur] = whichPiece(playing, i, j);
						setToChange(i, j);
						whereTo[compteur]=destination;
						compteur++;
					}else {
						f.add(grid[i][j]);
					}
				}else {
					f.add(grid[i][j]);
				}
			}
		}
		f.setVisible(true);
		f.setSize(this.SIZE*this.GRAPHICAL_LENGTH, this.SIZE*this.GRAPHICAL_LENGTH);
	}

//	Returns -1 if the piece on the case can't move at this turn
//	Returns its destination's index otherwise
//	Takes as parameters the index of the considered player, the index of the case and its ccordinates
//	Even though coordinates can be determined using the index, using both as parameters is much more efficient
	public int destinationIndex(int player, int x, int y, int index) {
		if (player == playing) {
			int status = players[playing].getPieces()[whichPiece(playing, x, y)].getStatus();
			switch(status) {
				case -1 :
					if(diceRoll == 6) { return 13*playing; }
					break;
				case 0 :
					if ((index+13*((4-playing)%4))%51 + diceRoll < 51 && index < 100) {
						return (index + diceRoll)%52;
					}else {
						if (index < 100) {
							return index+13*((4-playing)%4)+diceRoll + 100*(playing+1);
						}else {
							if (index%100 + diceRoll < 57) {
								return index+diceRoll;
							}else {
								return -1;
							}
						}
					}
				case 1 : return -1;
			}
		}
		return -1;
	}

//	Returns the color of the case according to its index
	public Color caseColor (int index) {
		if(index%13==8 && index < 51) { return safeCaseColor; }
		if(index==-2 || index==0 || (index>150 && index<157)) { return players[0].getColor(); }
		if(index==-3 || index==13 || (index>250 && index<257)) { return players[1].getColor(); }
		if(index==-4 || index==26 || (index>350 && index<357)) { return players[2].getColor(); }
		if(index==-5 || index==39 || (index>450 && index<457)) { return players[3].getColor(); }
		if(index==56) { return finalCaseColor; }
		return backgroundColor;
	}

//	Returns the index of the first player which has a piece on the case, -1 if there isn't a piece
	public int containsPiece(int x, int y) {
		for(int i=0; i < 4; i++) {
			for(int j=0; j < 4; j++) {
				if(players[i].getPieces()[j].getX() == x && players[i].getPieces()[j].getY() == y ) {
					return i;
				}
			}
		}
		return -1;
	}

//	Saves the coordinates of the case which needs to be modified into the toChange array
	public void setToChange(int x, int y) {
		toChange[willChange][0] = x;
		toChange[willChange][1] = y;
		willChange++;
	}
	
//	Returns true if the case has to be modified
	public boolean hasToChange(int x, int y) {
		for (int i = 0; i < willChange; i++) {
			if(x == toChange[i][0] && y == toChange[i][1]) { return true; }
		}
		return false;
	}
	
//	Returns the index of the piece located on the case in parameters
	public int whichPiece(int p, int x, int y) {
		for(int i=0; i < 4; i++) {
			if(players[p].getPieces()[i].getX() == x && players[p].getPieces()[i].getY() == y ) {
				return i;
			}
		}
		return -1;
	}

//	Returns true if the player has a piece on the case in parameters 
	public boolean containsPieceFromPlayer(int x, int y, Player p) {
		for(int j=0; j < 4; j++) {
			if(p.getPieces()[j].getX() == x && p.getPieces()[j].getY() == y ) {
				return true;
			}
		}
		return false;
	}

//	Counts the number of piece each player has on the case
//	Returns true if on of them has more than one piece
	public boolean isCaseBlocked(int x, int y) {
		int compteur;
		for (int i = 0; i < 4; i++) {
			if(i != playing) {
				compteur = 0;
				for(int j=0; j < 4; j++) {
					if(players[i].getPieces()[j].getX() == x && players[i].getPieces()[j].getY() == y ) {
						compteur++;
					}
				}
				if (compteur > 1) { return true; }		
			}
		}
		return false;
	}

//	Checks if one of the cases whitin a dice roll ahead from the piece's location is blocked
//	returns true if so
	public boolean isPieceBlocked(int x, int y) {
		int index = grid[x][y].getIndex();
		for (int i = 1; i < diceRoll+1; i++) {
			if((index+13*((4-playing)%4))%51 + i < 51 && index < 100 && index >-1) {
				if(grid[getXFromIndex((index+i)%52)][getYFromIndex((index+i)%52)].isBlocked()) { 
					return true; 
				}
			}
		}
		return false;
	}

//	Moves the piece to its destination and updates the board in consequence to this move
	public void action (int pieceIndex, int destIndex) {
		int x = getXFromIndex(whereTo[destIndex]);
		int y = getYFromIndex(whereTo[destIndex]);
		Piece p;
		p=players[playing].getPieces()[pieceIndex];
		setToChange(p.getX(), p.getY());
		setToChange(x, y);
		boolean playAgain=false;
		p.movePiece(x, y);
		if (whereTo[pieceIndex]%100 == 56) {
			p.setStatus(1);
			playAgain=true;
		}else {
			p.setStatus(0);
		}
		if (grid[x][y].isSafe() == false) {
			if(takes(x, y)) {
				playAgain=true;
			}
		}
		if (diceRoll == 6) { 
			playAgain=true; 
		}

		if (playAgain) { playing=(playing+3)%4; }
		played = true;
		for (int i = 0; i < 4; i++) {
			playablePiece[i].setEnabled(false);
		}
	}

//	Sends pieces on the case which don't belong to the current player to their origin location
	public boolean takes(int x, int y) {
		boolean result = false;
		Piece p;
		for (int i = 0; i < 4; i++) {
			if (i != playing) {
				if (containsPieceFromPlayer(x, y, players[i])) {
					result = true;
					p=players[i].getPieces()[whichPiece(i, x, y)];
					p.reinitializePosition();
					setToChange(p.getOriginX(), p.getOriginY());
				}
			}
		}
		return result;
	}

//	Gets the X coordinate of the case from its index 
	public int getXFromIndex(int index) {
		String key= b.getKey(index);
		String[] coord=key.split(" ");
		return Integer.parseInt(coord[1]);
	}

//	Gets the X coordinate of the case from its index 
	public int getYFromIndex(int index) {
		String key= b.getKey(index);
		String[] coord=key.split(" ");
		return Integer.parseInt(coord[0]);
	}

//	Redirects the press of one of the buttons to the corresponding action 
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == playablePiece[0]) {
			action(buttonToPiece[0], 0);
		} else if (e.getSource() == playablePiece[1]) {
			action(buttonToPiece[1], 1);
		} else if (e.getSource() == playablePiece[2]) {
			action(buttonToPiece[2], 2);
		}else if (e.getSource() == playablePiece[3]) {
			action(buttonToPiece[3], 3);
		}
	}
}
