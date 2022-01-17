import static javax.swing.JOptionPane.showMessageDialog;

// Class containing the course of the game
public class Game {
	private Board board;
	private Dice dice;
	
//	Constructor
	public Game() {
		dice = new Dice();
		board = new Board();
	}
	
//	Course of the game
//	plays a turn, then switches player
//	Does so until a player wins
	public void playGame() {
		dice.print();
		do{
			dice.setColor(board.getPlayers()[board.getPlaying()].getColor());
			turn();
			board.switchPlayer();
			updateStatus();
		}while (winner(board.getPlayers()) == -1); 		
		showMessageDialog(null, board.getPlayers()[winner(board.getPlayers())].getName()+" won ! \nThanks for playing");
		System.exit(0);
	}
	
//	Changes the status of the pieces which arrived to their final cases
	public void updateStatus() {
		Piece p;
		int x, y;
		for (int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				p=board.getPlayers()[i].getPieces()[j];
				x = p.getX();
				y = p.getY();
				if(board.getBM().getMap().get(x+" "+y)%100 == 56) {
					if(board.getGrid()[x][y].getIndex()%100 == 56) {p.setStatus(1); }
				}
			}
		}
	}
	
//	Course of a turn
//	Rolls the dice, prints the board and waits for a button to be pressed
	public void turn() {
		dice.roll();
		board.setDiceRoll(dice.getValue());
		board.initBoard();
		board.printBoard();
		if (isPlayable(board.getPlayers()[board.getPlaying()])) {
			while(board.isPlayed() == false) { System.out.print(""); }
		}else {
			try { Thread.sleep(1000) ;
			}  catch (InterruptedException e) { }
			dice.errorMessage();
			while(dice.isReady() == false) { System.out.print(""); }
		}
	}
	
//	Returns the index of the winning player, and -1 if there are no winners
	public int winner(Player[] p) {
		for (int i = 0; i < 4; i++) {
			if(isWinner(p[i])) {
				return i;
			}
		}
		return -1;
	}
	
//	Returns true if the player did win the game
	public boolean isWinner(Player p) {
		for (int i = 0; i < 4; i++) {
			if (p.getPieces()[i].getStatus() != 1) {
				return false;
			}
		}
		return true;
	}
	
//	Returns true if the player can move one of its pieces
	public boolean isPlayable(Player p) {
		int x, y;
		if(isWinner(p)) {
			return false;
		}else {
			for (int i = 0; i < 4; i++) {
				x = p.getPieces()[i].getX();
				y = p.getPieces()[i].getY();
				if (p.getPieces()[i].getStatus() == 0 && (board.getBM().getMap().get(x+" "+y)+dice.getValue())%100 < 57) {
					if(board.isPieceBlocked(x, y) == false) { return true; }
				}
				if (p.getPieces()[i].getStatus() == -1 && dice.getValue() == 6) { return true; }
			}
			return false;
		}
	}

	public static void main(String[] args) {
    	Game g = new Game();
    	g.playGame();
	}
}
