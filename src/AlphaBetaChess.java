import javax.swing.*;
public class AlphaBetaChess {
	static String chessBoard[][] = {
			{"r","k","b","q","a","b","k","r"},
			{"p","p","p","p","p","p","p","p"},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},//dong 5 cot 4
			{"P","P","P","P","P","P","P","P"},
			{"R","K","B","Q","A","B","K","R"},
	};
	static int kingPositionU,kingPositionL; //uppercase,lowercase
	public static void main(String[] args) {
//		JFrame f = new JFrame("Co vua");
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		UserInterface ui = new UserInterface();
//		f.add(ui);
//		f.setSize(500,500);
//		f.setVisible(true);
//		System.out.println("hello");
		System.out.println(possibleMoves());
	}
	public static String possibleMoves() {
		String list = "";
		for (int i = 0; i < 64; i++) {
			switch(chessBoard[i/8][i%8]) {
				case "P":
					list+=possibleP(i);
					break;
				case "R":
					list+=possibleR(i);
					break;
				case "K":
					list+=possibleK(i);
					break;
				case "B":
					list+=possibleB(i);
					break;
				case "Q":
					list+=possibleQ(i);
					break;
				case "A":
					list+=possibleA(i);
					break;
			}	
				
			
		}
		return list; //x1,y1,x2,y2 capture piece(ăn quân) , list này sẽ trả về các nước đi có thể đi được của từng quân
	}
public static String possibleP(int i) {
	String list = "";
	return list;
}
public static String possibleR(int i) {
	String list = "";
	return list;
}
public static String possibleK(int i) {
	String list = "";
	return list;
}
public static String possibleB(int i) {
	String list = "";
	return list;
}
public static String possibleQ(int i) {
	String list = "";
	return list;
}
public static String possibleA(int i) {
	String list = "",oldPiece;
	int r = i/8, c = i%8;
	for (int j = 0; j < 9; j++) {
		if(j != 4) { // vị trí vua đang đứng
			try {
				if(Character.isLowerCase(chessBoard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(chessBoard[r-1+j/3][c-1+j%3])) { // nghĩa là kiểm tra xem 8 ô xung quanh vua có quân nào hay không(00,01,02,10,12,20,21,22)
					// Nếu không có
					oldPiece =chessBoard[r-1+j/3][c-1+j%3];
					chessBoard[r][c] = " "; //Sau khi vua đi qua ô khác thì ô của vua đang đứng sẽ rỗng
					chessBoard[r-1+j/3][c-1+j%3] = "A"; // Gán lại vị trí của vua ở 1 trong 8 vị trí trống đó
					int kingTemp = kingPositionU;
					kingPositionU = i+(j/3)*8 +j%3-9; 
//					{"r","k","b","q","a","b","k","r"},
//					{"p","p","p","p","p","p","p","p"},
//					{" "," "," "," "," "," "," "," "},
//					{" "," "," "," "," "," "," "," "},
//					{" "," "," "," "," "," "," "," "},
//					{" "," "," "," ","A"," "," "," "},// dòng 5 cột 4
//					{"P","P","P","P","P","P","P","P"},
//					{"R","K","B","Q","A","B","K","R"},
//					ví dụ vua đang ở dòng 5 cột 4 thì kingPositionU lúc đầu i = 44 => vua có 5 trường hợp để đi được và i lần lượt là:kingPositionU = (35,36,37,43,45) ( vị trí mới)
					if(kingSafe()) {
						list = list+r+c+(r-1+j/3)+(c-1+j%3)+oldPiece;
						// r: fromRow, c: fromCol, (r-1+j/3): toRow, (c-1+j%3): toCol
						//oldPiece(là chuỗi để check xem có : (1) là " ", (2) là có 1 quân nào đó nếu có quân thì ghi tên của quân đó vd: có quân q,k,r của đối thủ bao quây vua)
//						{"r","k","b","q","a","b","k","r"},
//						{"p","p","p","p","p","p","p","p"},
//						{" "," "," "," "," "," "," "," "},
//						{" "," "," "," "," "," "," "," "},
//						{"q","k"," "," "," "," "," "," "},
//						{"A","r"," "," "," "," "," "," "},//dong 5 cot 4
//						{"P","P","P","P","P","P","P","P"},
//						{"R","K","B","Q","A","B","K","R"},
						//=> Kết quả sẽ là: 5040q5041k5051r
					}
					// Trường hợp vua quay trở về vị trí cũ
					chessBoard[r][c] = "A";
					chessBoard[r-1+j/3][c-1+j%3] = oldPiece;
					kingPositionU = kingTemp;
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	} // < 9 nghĩa là vua có thể đi được 9 ô bao gồm ô của mình
	return list;
}
public static boolean kingSafe() {
	return true;
}
}
