import javax.swing.*;
public class AlphaBetaChess {
	static String chessBoard[][] = {
			{"r","k","b","q","a","b","k","r"},
			{"p","p","p","p","p","p","p","p"},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{"P","P","P","P","P","P","P","P"},
			{"R","K","B","Q","A","B","K","R"},
	};
	public static void main(String[] args) {
		JFrame f = new JFrame("Co vua");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UserInterface ui = new UserInterface();
		f.add(ui);
		f.setSize(500,500);
		f.setVisible(true);
	}
	public static String possibleMoves() {
		return ""; //x1,y1,x2,y2 capture piece(ăn quân)
	}
}
