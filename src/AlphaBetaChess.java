import javax.swing.*;
public class AlphaBetaChess {
	static String chessBoard[][] = {
			{"r","k","b","q","a","b","k","r"},
			{"p","p","p","p","p","p","p","p"},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," ","R"," "," "," "},//dong 3 cot 4
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
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
	public static String possibleA(int i) {
		String list = "",oldPiece;
		int r = i/8, c = i%8; // Vị trí vua đang đứng
		for (int j = 0; j < 9; j++) {
			if(j != 4) { 
				try {
					if(Character.isLowerCase(chessBoard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(chessBoard[r-1+j/3][c-1+j%3])) { // nghĩa là kiểm tra xem 8 ô xung quanh vua có quân nào hay không(00,01,02,10,12,20,21,22)
						// Nếu không có
						oldPiece =chessBoard[r-1+j/3][c-1+j%3];
						chessBoard[r][c] = " "; //Sau khi vua đi qua ô khác thì ô của vua đang đứng sẽ rỗng
						chessBoard[r-1+j/3][c-1+j%3] = "A"; // Gán lại vị trí của vua ở 1 trong 8 vị trí trống đó
						int kingTemp = kingPositionU;
						kingPositionU = i+(j/3)*8 +j%3-9; 
//						{"r","k","b","q","a","b","k","r"},
//						{"p","p","p","p","p","p","p","p"},
//						{" "," "," "," "," "," "," "," "},
//						{" "," "," "," "," "," "," "," "},
//						{" "," "," ","*","*","*"," "," "},
//						{" "," "," ","*","A","*"," "," "},// dòng 5 cột 4, "*": là các ô mà vua có thể đi
//						{"P","P","P","P","P","P","P","P"},
//						{"R","K","B","Q","A","B","K","R"},
//						ví dụ vua đang ở dòng 5 cột 4 thì kingPositionU lúc đầu i = 44 => vua có 5 trường hợp để đi được và i lần lượt là:kingPositionU = (35,36,37,43,45) ( vị trí mới)
						if(kingSafe()) {
							list = list+r+c+(r-1+j/3)+(c-1+j%3)+oldPiece; // Này là trả về các nước mà vua có thể đi được bao gồm (ăn quân)
							// r: fromRow, c: fromCol, (r-1+j/3): toRow, (c-1+j%3): toCol
							//oldPiece(là chuỗi để check xem có : (1) là " ", (2) là có 1 quân nào đó nếu có quân thì ghi tên của quân đó vd: có quân q,k,r của đối thủ bao quây vua)
//							{"r","k","b","q","a","b","k","r"},
//							{"p","p","p","p","p","p","p","p"},
//							{" "," "," "," "," "," "," "," "},
//							{" "," "," "," "," "," "," "," "},
//							{"q","k"," "," "," "," "," "," "},
//							{"A","r"," "," "," "," "," "," "},//dong 5 cot 4
//							{"P","P","P","P","P","P","P","P"},
//							{"R","K","B","Q","A","B","K","R"},
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
	public static String possibleQ(int i) {
		String list = "",oldPiece;
		//Lấy vị trí Hậu
		int r = i/8, c = i%8;
		int temp = 1;
		//Duyệt 8 hướng quanh Hậu
		// (-1,-1): chéo trái trên, (-1,0): trên,(-1,0): chéo phải trên
		// (0,-1): trái,(0,1): phải
		// (1,-1): chéo trái dưới, (1,0): dưới,(1,1): chéo phải dưới
		for (int j = -1; j <=1; j++) {
			for (int k = -1; k <=1; k++) {
				try {
//					{"r","k","b","q","a","b","k","r"},
//					{"p","p*","p","p","p*","p","p*","p"},
//					{" "," ","*"," ","*"," ","*"," "},
//					{" "," "," ","*","*","*"," "," "},
//					{"*","*","*","*","Q","*","*","*"},//dong 4 cot 4(r:4,c:4),"*":là các nước Hậu có thể đi
//					{" "," "," ","*","*","*"," "," "},
//					{"P","P","P","P","P","P","P","P"},
//					{"R","K","B","Q","A","B","K","R"},
					//kiểm tra xem ô tiếp theo trên đường đi của Hậu có (trống) không.
					//nếu trống thì tiếp tục đi thêm 1 ô nữa theo cùng hướng.
					while(" ".equalsIgnoreCase(chessBoard[r+temp*j][c+temp*k])) { 
						oldPiece = chessBoard[r+temp*j][c+temp*k]; // gán oldPiece là các ô xung quanh có thể đi
						chessBoard[r][c] = " "; // gán vị trí mà Hậu đang ở đó trước khi đi là rỗng
						chessBoard[r+temp*j][c+temp*k] = "Q"; // sau khi đi thì gán vị trí mới cho Hậu
						if(kingSafe()) {
							list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece; // Này là trả về các nước mà Hậu có thể đi được (bao gồm cả ăn quân)
						}
						//nếu đặt quân hậu về vị trí cũ
						chessBoard[r][c] = "Q";
						chessBoard[r+temp*j][c+temp*k] = oldPiece;
						temp++;
					}
//					{"r","k","b","q","a","b","k","r"},
//					{"p","p*","p","p","p*","p","p*","p"},
//					{" "," ","*"," ","*"," ","*"," "},
//					{" "," "," ","*","*","*"," "," "},
//					{"*","*","*","*","Q","*","*","*"},//dong 4 cot 4(r:4,c:4),"*":là các nước Hậu có thể đi
//					{" "," "," ","*","*","*"," "," "},
//					{"P","P","P","P","P","P","P","P"},
//					{"R","K","B","Q","A","B","K","R"},
					// Kiểm tra khi quân Hậu ăn quân của đối thủ vd(p*)
					if(Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))) {
						oldPiece = chessBoard[r+temp*j][c+temp*k];
						chessBoard[r][c] = " ";
						chessBoard[r+temp*j][c+temp*k] = "Q";
						if(kingSafe()) {
							list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece;
						}
						//Đặt quân hậu về vị trí cũ
						chessBoard[r][c] = "Q";
						chessBoard[r+temp*j][c+temp*k] = oldPiece;
						
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				temp=1;
			}
		}
		return list;
	}
	public static String possibleB(int i) {
		String list = "",oldPiece;
		//Lấy vị trí Tượng
		int r = i/8, c = i%8;
		int temp = 1;
		//Duyệt 4 hướng chéo quanh Tượng
		// (-1,-1): chéo trái trên,(-1,1): chéo phải trên
		// (1,-1): chéo trái dưới,(1,1): chéo phải dưới
		for (int j = -1; j <=1; j+=2) {
			for (int k = -1; k <=1; k+=2) {
				try {
//					{"r","k","b","q","a","b","k","r"},
//					{"p","p","p","p","p*","p","p","p"},
//					{" "," "," ","*"," "," "," "," "},
//					{" "," ","*"," "," "," "," "," "},
//					{" ","*"," "," "," "," "," "," "},
//					{"B"," "," "," "," "," "," "," "},//dong 5 cot 0,"*" là các nước tượng có thể đi
//					{"P","P","P","P","P","P","P","P"},
//					{"R","K","B","Q","A","B","K","R"},
					//kiểm tra xem ô tiếp theo trên đường đi của Tượng có (trống) không.
					//nếu trống thì tiếp tục đi thêm 1 ô nữa theo cùng hướng.
					while(" ".equalsIgnoreCase(chessBoard[r+temp*j][c+temp*k])) { 
						oldPiece = chessBoard[r+temp*j][c+temp*k]; // gán oldPiece là các ô xung quanh có thể đi
						chessBoard[r][c] = " "; // gán vị trí mà Tượng đang ở đó trước khi đi là rỗng
						chessBoard[r+temp*j][c+temp*k] = "B"; // sau khi đi thì gán vị trí mới cho Tượng
						if(kingSafe()) {
							list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece; // Này là trả về các nước mà Tượng có thể đi được (bao gồm cả ăn quân)
						}
						//nếu đặt quân tượng về vị trí cũ
						chessBoard[r][c] = "B";
						chessBoard[r+temp*j][c+temp*k] = oldPiece;
						temp++;
					}
//					{"r","k","b","q","a","b","k","r"},
//					{"p","p","p","p","p*","p","p","p"},
//					{" "," "," ","*"," "," "," "," "},
//					{" "," ","*"," "," "," "," "," "},
//					{" ","*"," "," "," "," "," "," "},
//					{"B"," "," "," "," "," "," "," "},//dong 5 cot 4,"*" là các nước tượng có thể đi
//					{"P","P","P","P","P","P","P","P"},
//					{"R","K","B","Q","A","B","K","R"},
					// Kiểm tra khi quân Tượng ăn quân của đối thủ vd(p*)
					if(Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))) {
							oldPiece = chessBoard[r+temp*j][c+temp*k]; // gán oldPiece là các ô xung quanh có thể đi
							chessBoard[r][c] = " "; // gán vị trí mà Tượng đang ở đó trước khi đi là rỗng
							chessBoard[r+temp*j][c+temp*k] = "B"; // sau khi đi thì gán vị trí mới cho Tượng
							if(kingSafe()) {
								list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece; // Này là trả về các nước mà Tượng có thể đi được (bao gồm cả ăn quân)
							}
							//nếu đặt quân tượng về vị trí cũ
							chessBoard[r][c] = "B";
							chessBoard[r+temp*j][c+temp*k] = oldPiece;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				temp=1;
			
			}
		}
		return list;
	}
public static String possibleR(int i) {
	String list = "", oldPiece;
    int r = i / 8, c = i % 8;
    int temp =1;

    // duyệt 4 hướng thẳng(trên,xuống,trái,phải)
 // (-1,0): thẳng trên,(1,0): xuống dưới
    // (0,-1): trái ngang,(0,1): phải ngang
    for (int j = -1; j <= 1; j++) {
        for (int k = -1; k <= 1; k++) {
            // loại bỏ hướng (0,0) và các hướng chéo
            if ((j == 0 && k == 0) || (j != 0 && k != 0)) continue; //kiểm tra (j = 0 hoac k = 0) rồi mới đi tiếp 
            try {
//            	{"r","k","b","q","a","b","k","r"},
//    			{"p","p","p","p","p*","p","p","p"},
//    			{" "," "," "," ","*"," "," "," "},
//    			{"*","*","*","*","R","*","*","*"},//dong 3 cot 4(r:3,c:4) ,"*" là các nước xe có thể đi
//    			{" "," "," "," ","*"," "," "," "},
//    			{" "," "," "," ","*"," "," "," "},
//    			{"P","P","P","P","P","P","P","P"},
//    			{"R","K","B","Q","A","B","K","R"},
                while (" ".equalsIgnoreCase(chessBoard[r+temp*j][c+temp*k])) {
                    oldPiece = chessBoard[r+temp*j][c+temp*k]; // gán oldPiece là các ô xung quanh có thể đi
                    chessBoard[r][c] = " "; // gán vị trí mà Xe đang ở đó trước khi đi là rỗng
                    chessBoard[r+temp*j][c+temp*k] = "R"; // sau khi đi thì gán vị trí mới cho Xe

                    if (kingSafe()) {
                    	list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece; // Này là trả về các nước mà Xe có thể đi được (bao gồm cả ăn quân)
                    }

                  //nếu đặt quân xe về vị trí cũ
                    chessBoard[r][c] = "R";
                    chessBoard[r+temp*j][c+temp*k] = oldPiece; 

                    temp++;
                }

//            	{"r","k","b","q","a","b","k","r"},
//    			{"p","p","p","p","p*","p","p","p"},
//    			{" "," "," "," ","*"," "," "," "},
//    			{"*","*","*","*","R","*","*","*"},//dong 3 cot 4(r:3,c:4)
//    			{" "," "," "," ","*"," "," "," "},
//    			{" "," "," "," ","*"," "," "," "},
//    			{"P","P","P","P","P","P","P","P"},
//    			{"R","K","B","Q","A","B","K","R"},
// 				Kiểm tra khi quân Xe ăn quân của đối thủ vd(p*)
                if (Character.isLowerCase(chessBoard[r+temp*j][c+temp*k].charAt(0))) {

                	 oldPiece = chessBoard[r+temp*j][c+ temp*k]; // gán oldPiece là các ô xung quanh có thể đi
                     chessBoard[r][c] = " "; // gán vị trí mà Xe đang ở đó trước khi đi là rỗng
                     chessBoard[r+temp*j][c+temp*k] = "R"; // sau khi đi thì gán vị trí mới cho Xe

                     if (kingSafe()) {
                     	list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece; // Này là trả về các nước mà Xe có thể đi được (bao gồm cả ăn quân)
                     }

                   //nếu đặt quân xe về vị trí cũ
                     chessBoard[r][c] = "R";
                     chessBoard[r+temp*j][c+temp*k] = oldPiece; 
                }

            } catch (Exception e) {
                
            }
            temp = 1;
        }
    }
    return list;
}
public static String possibleK(int i) {
	String list = "";
	return list;
}
public static String possibleP(int i) {
	String list = "";
	return list;
}
public static boolean kingSafe() {
	return true;
}
}
