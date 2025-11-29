import javax.swing.*;
public class ChessPieces {
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
	static int kingPositionU,kingPositionL; //uppercase,lowercase
	public static void main(String[] args) {
//		JFrame f = new JFrame("Co vua");
//		f.setDefaultCloseOperation(JFrame.EXIT_On_CLOSE);
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
		return list;
	}
	public static String possibleA(int i) {
		String list = "",oldPiece;
		int r = i/8, c = i%8; // Vị trí vua đang đứng
		for (int j = 0; j < 9; j++) {
			if(j != 4) { 
				try {
					if(Character.isLowerCase(chessBoard[r-1+j/3][c-1+j%3].charAt(0)) || " ".equals(chessBoard[r-1+j/3][c-1+j%3])) { // nghĩa là kiểm tra xem 8 ô xung quanh vua có quân nào hay không(00,01,02,10,12,20,21,22)
						// nếu không có
						int kingTemp = kingPositionU;
						oldPiece =chessBoard[r-1+j/3][c-1+j%3];
						chessBoard[r][c] = " "; //Sau khi vua đi qua ô khác thì ô của vua đang đứng sẽ rỗng
						chessBoard[r-1+j/3][c-1+j%3] = "A"; // gán lại vị trí của vua ở 1 trong 8 vị trí trống đó
						kingPositionU = i+(j/3)*8 +j%3-9; 
//						{"r","k","b","q","a","b","k","r"},
//						{"p","p","p","p","p","p","p","p"},
//						{" "," "," "," "," "," "," "," "},
//						{" "," "," "," "," "," "," "," "},
//						{" "," "," ","*","*","*"," "," "},
//						{" "," "," ","*","A","*"," "," "},// dòng 5 cột 4, "*": là các ô mà vua có thể đi(r:5,c:4)
//						{"P","P","P","P","P","P","P","P"},
//						{"R","K","B","Q","A","B","K","R"},
//						ví dụ vua đang ở dòng 5 cột 4 thì kingPositionU lúc đầu i = 44 => vua có 5 trường hợp để đi được và i lần lượt là:kingPositionU = (35,36,37,43,45) ( vị trí mới)
						if(kingSafe()) {
							list = list+r+c+(r-1+j/3)+(c-1+j%3)+oldPiece; // này là trả về các nước mà vua có thể đi được bao gồm (ăn quân)
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
							list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece; // này là trả về các nước mà Hậu có thể đi được (bao gồm cả ăn quân)
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
							list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece; // này là trả về các nước mà Tượng có thể đi được (bao gồm cả ăn quân)
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
								list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece; // này là trả về các nước mà Tượng có thể đi được (bao gồm cả ăn quân)
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
            if ((j == 0 && k == 0) || (j != 0 && k != 0)) continue; //kiểm tra (j=0 hoac k=0) rồi mới đi tiếp 
            // phải có 1 thằng = 0 mới duyệt try catch
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
                    	list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece; // này là trả về các nước mà Xe có thể đi được (bao gồm cả ăn quân)
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
                     	list = list+r+c+(r+temp*j)+(c+temp*k)+oldPiece; // này là trả về các nước mà Xe có thể đi được (bao gồm cả ăn quân)
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
	String list = "",oldPiece;
	//Lấy vị trí ngựa
	int r = i/8, c = i%8;
	for (int j = -1; j <=1; j+=2) {	
		for (int k = -1; k <=1; k+=2) {
			//Trường hợp: đi 1 hàng  2 cột
			try {
//				{"r","k","b","q","a","b","k","r"},
//				{"p","p","p*","p","p","p","p","p"},
//				{"K"," "," "," "," "," "," "," "},//dong 2 cot 0(r:2,c:0) ,"*" là các nước ngựa có thể đi
//				{" "," ","*"," "," "," "," "," "},
//				{" ",""," "," "," "," "," "," "},
//				{" "," "," "," "," ", " "," "," "},
//				{"P","P","P","P","P","P","P","P"},
//				{"R","K","B","Q","A","B","K","R"},
				if(Character.isLowerCase(chessBoard[r+j][c+k*2].charAt(0)) ||" ".equalsIgnoreCase(chessBoard[r+j][c+k*2])) { 
					oldPiece = chessBoard[r+j][c+k*2]; // gán oldPiece là các ô xung quanh có thể đi
					chessBoard[r][c] = " "; // gán vị trí mà ngựa đang ở đó trước khi đi là rỗng
					chessBoard[r+j][c+k*2] = "K"; // sau khi đi thì gán vị trí mới cho ngựa
					 if (kingSafe()) {
	                     	list = list+r+c+(r+j)+(c+k*2)+oldPiece; // này là trả về các nước mà Xe có thể đi được (bao gồm cả ăn quân)
	                     }
					//nếu đặt quân ngựa về vị trí cũ
					 chessBoard[r][c] = "K";
					 chessBoard[r+j][c+k*2] = oldPiece;
				}
			}
			catch (Exception e) {
                
            }
//			Trường hợp: đi 2 hàng 1 cột
			try {
//				{"r","k*","b","q","a","b","k","r"},
//				{"p","p","p","p","p","p","p","p"},
//				{"K"," "," "," "," "," "," "," "},//dong 2 cot 0(r:2,c:0) ,"*" là các nước ngựa có thể đi
//				{" "," ",""," "," "," "," "," "},
//				{" ","*"," "," "," "," "," "," "},
//				{"*"," ","*"," "," ", "*"," ","*"},
//				{"P","P","P","P","P","P","P","P"},
//				{"R","K","B","Q","A","B","K","R"},
                if (Character.isLowerCase(chessBoard[r + 2*j][c + k].charAt(0)) ||
                    " ".equalsIgnoreCase(chessBoard[r + 2*j][c + k])) {

                    oldPiece = chessBoard[r + 2*j][c + k];
                    chessBoard[r][c] = " ";
                    chessBoard[r+2*j][c + k] = "K";

                    if (kingSafe()) {
                        list = list+r+c+(r+ 2*j) + (c + k) + oldPiece;
                    }

                    chessBoard[r][c] = "K";
                    chessBoard[r + 2*j][c + k] = oldPiece;
                }
            } catch (Exception e) {}
		}
			
	}
	return list;
}
public static String possibleP(int i) {
	String list = "",oldPiece;
	//Lấy vị trí Tốt
	int r = i/8, c = i%8;

	for (int j = -1; j <=1; j+=2) {
			try {
				if(Character.isLowerCase(chessBoard[r-1][c+j].charAt(0)) && i >= 16) {
					oldPiece = chessBoard[r-1][c+j]; // gán oldPiece là các ô xung quanh có thể đi
					chessBoard[r][c] = " "; // gán vị trí mà tốt đang ở đó trước khi đi là rỗng
					chessBoard[r-1][c+j] = "P"; // sau khi đi thì gán vị trí mới cho tốt
					 if (kingSafe()) {
	                     	list = list+r+c+(r-1)+(c+j)+oldPiece; 
	                     }
					//nếu đặt quân tốt về vị trí cũ
					 chessBoard[r][c] = "P";
					 chessBoard[r-1][c+j] = oldPiece;
				}
			}
			catch (Exception e) {
				// TODO: handle exception
			}
			try {
				if(Character.isLowerCase(chessBoard[r-1][c+j].charAt(0)) && i < 16) {
					String[] temp = {"Q","R","B","K"};
					for (int k = 0; k <4; k++) {
						oldPiece = chessBoard[r-1][c+j]; // gán oldPiece là các ô xung quanh có thể đi
						chessBoard[r][c] = " "; // gán vị trí mà tốt đang ở đó trước khi đi là rỗng
						chessBoard[r-1][c+j] = temp[k]; // sau khi đi thì gán vị trí mới cho tốt
						 if (kingSafe()) {
							 //column1,column2,quân bị ăn,quân mới,P
		                     	list = list+c+(c+j)+oldPiece+temp[k]+"P"; 
		                     }
						//nếu đặt quân tốt về vị trí cũ
						 chessBoard[r][c] = "P";
						 chessBoard[r-1][c+j] = oldPiece;
					}
					
				}
			}
			catch (Exception e) {
				// TODO: handle exception
			}
	}
	// đi 1 nước
	try {
		if(" ".equals(chessBoard[r-1][c]) && i >= 16) {
			
			oldPiece = chessBoard[r-1][c]; // gán oldPiece là các ô xung quanh có thể đi
			chessBoard[r][c] = " "; // gán vị trí mà tốt đang ở đó trước khi đi là rỗng
			chessBoard[r-1][c] = "P"; // sau khi đi thì gán vị trí mới cho tốt
			 if (kingSafe()) {
                 	list = list+r+c+(r-1)+c+oldPiece; 
                 }
			//nếu đặt quân tốt về vị trí cũ
			 chessBoard[r][c] = "P";
			 chessBoard[r-1][c] = oldPiece;
		}
	}
	catch (Exception e) {
		// TODO: handle exception
	}
	try {
		if(" ".equals(chessBoard[r-1][c]) && i < 16) {
			String[] temp = {"Q","R","B","K"};
			for (int k = 0; k <4; k++) {
				oldPiece = chessBoard[r-1][c]; // gán oldPiece là các ô xung quanh có thể đi
				chessBoard[r][c] = " "; // gán vị trí mà tốt đang ở đó trước khi đi là rỗng
				chessBoard[r-1][c] = temp[k]; // sau khi đi thì gán vị trí mới cho tốt
				 if (kingSafe()) {
					//column1,column2,quân bị ăn,quân mới,P
					 list = list+c+c+oldPiece+temp[k]+"P"; 
	                 }
				//nếu đặt quân tốt về vị trí cũ
				 chessBoard[r][c] = "P";
				 chessBoard[r-1][c] = oldPiece;
			}
			
		}
	}
	catch (Exception e) {
		// TODO: handle exception
	}
	
	// đi 2 nước
		try {
			if(" ".equals(chessBoard[r-1][c]) && " ".equals(chessBoard[r-2][c]) && i >= 48) {
 
					oldPiece = chessBoard[r-2][c]; // gán oldPiece là các ô xung quanh có thể đi
					chessBoard[r][c] = " "; // gán vị trí mà tốt đang ở đó trước khi đi là rỗng
					chessBoard[r-2][c] = "P"; // sau khi đi thì gán vị trí mới cho tốt
					 if (kingSafe()) {
						
						 list = list+r+c+(r-2)+c+oldPiece;
		                 }
					//nếu đặt quân tốt về vị trí cũ
					 chessBoard[r][c] = "P";
					 chessBoard[r-2][c] = oldPiece;
			}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	return list;
}
public static boolean kingSafe() {
	// chú thích: mấy cái ! là khi vua đi thì xét theo dòng và cột mới
	//bishop/queen(duong cheo)
	//(-1,-1), (-1,1), (1,-1), (1,1) 4 hướng chéo
		//	{"r","k","b","q","a","b","k","r"},
		//	{"p","p","p","p","p","p","p","p"},
		//	{" "," "," "," "," ","b"," "," "},
		//	{" "," ","*","*","!"," "," "," "}, // dong 3 cot 4 có(!)
		//	{" "," ","*","A","*"," "," "," "},//dong 4 cot 3(kingpositionU = 35)
		//	{" "," ","!","*","*"," "," "," "},
		//	{"P","P","P","P","P","P","P","P"},
		//	{"R","K","B","Q","A","B","K","R"},
			//4332 4333 4342 4344 4353 4354 đây là vị trí mà khi đi vua sẽ an toàn mà không bị chiếu
			//(chưa xử lí quân tốt trắng nên mới có nước đi này)	
			// * là vị trí vua có thể đi, ! là vị trí vua đi thì sẽ bị chiếu
	int temp = 1;
	for (int i = -1; i <=1; i+=2) {
		for (int j = -1; j <=1; j+=2) {
			try {
				// này là nó sẽ tìm đường chéo đến vua xem có đang là ô trống không
				while(" ".equalsIgnoreCase(chessBoard[kingPositionU/8+temp*i][kingPositionU%8+temp*j])) {
					temp++;
					}
					
					if("b".equalsIgnoreCase(chessBoard[kingPositionU/8+temp*i][kingPositionU%8+temp*j]) || 
						"q".equalsIgnoreCase(chessBoard[kingPositionU/8+temp*i][kingPositionU%8+temp*j])
							) {
						return false;
					}
				}	
			catch (Exception e) {
				// TODO: handle exception
			}
			temp=1;
		}
	}
	//rook/queen(duong thang)
	//TH1: thẳng ngang
		//	{"r","k","b","q","a","b","k","r"},
		//	{"p","p","p","p","p","p","p","p"},
		//	{" "," "," "," "," "," "," "," "},
		//	{" "," ","*","*","*"," "," "," "},
		//	{" "," ","!","A","!","r"," "," "},//dong 4 cot 3(kingpositionU=35) , // dong 4 cot 2 hoac 3 có(!)
		//	{" "," "," ","*","*"," "," "," "},
		//	{"P","P","P","P","P","P","P","P"},
		//	{"R","K","B","Q","A","B","K","R"},
		//4332 4333 4334 4353 4354 đây là vị trí mà khi đi vua sẽ an toàn mà không bị chiếu (4352 không đến 52 được vì K trắng đã ở chỗ đó)
		//(chưa xử lí quân tốt trắng nên mới có nước đi này)
		// * là vị trí vua có thể đi, ! là vị trí vua đi thì sẽ bị chiếu
		for (int i = -1; i <=1; i+=2) {
				try {
					// này là nó sẽ tìm đường thẳng ngang đến vua xem có đang là ô trống không
					while(" ".equalsIgnoreCase(chessBoard[kingPositionU/8][kingPositionU%8+temp*i])) {
						temp++;
						}
						
						if("r".equalsIgnoreCase(chessBoard[kingPositionU/8][kingPositionU%8+temp*i]) || 
							"q".equalsIgnoreCase(chessBoard[kingPositionU/8][kingPositionU%8+temp*i])) {
							return false;
						}
					}	
				catch (Exception e) {
					// TODO: handle exception
				}
				temp=1;
	//TH2: thẳng dọc
//				{"r","k","b","q","a","b","k","r"},
//				{"p","p","p","p","p","p","p","p"},
//				{" "," "," ","r"," "," "," "," "},
//				{" "," ","*","!","*"," "," "," "}, // dong 3 cot 3 có(!)
//				{" "," ","*","A","*"," "," "," "},//dong 4 cot 3
//				{" "," "," ","!"," "," "," "," "},// dong 5 cot 3 có(!)
//				{"P","P","P","P","P","P","P","P"},
//				{"R","K","B","Q","A","B","K","R"},
				//4332 4334 4342 4344 4354 đây là vị trí mà khi đi vua sẽ an toàn mà không bị chiếu (4352 không đến 52 được vì K trắng đã ở chỗ đó)
				//(chưa xử lí quân tốt trắng nên mới có nước đi này)
				// * là vị trí vua có thể đi, ! là vị trí vua đi thì sẽ bị chiếu
				
				try {
					// này là nó sẽ tìm đường thẳng dọc đến vua xem có đang là ô trống không
					while(" ".equalsIgnoreCase(chessBoard[kingPositionU/8+temp*i][kingPositionU%8])) {
						temp++;
						}
						
						if("r".equalsIgnoreCase(chessBoard[kingPositionU/8+temp*i][kingPositionU%8]) || 
							"q".equalsIgnoreCase(chessBoard[kingPositionU/8+temp*i][kingPositionU%8])
								) {
							return false;
						}
					}	
				catch (Exception e) {
					// TODO: handle exception
				}
				temp=1;
		}
		//knight
//		TH1: ngựa đi theo 2 cột 1 hàng
//		{"r","k","b","q","a","b","k","r"},
//		{"p","p","p","p","p","p","p","p"},
//		{" "," "," "," "," ","k"," "," "},
//		{" "," ","*","!","*"," "," "," "}, // dòng 3 cột 3 có (!)
//		{" "," ","*","A","*"," "," "," "},//dong 4 cot 3
//		{" "," "," ","*","*"," "," "," "},
//		{"P","P","P","P","P","P","P","P"},
//		{"R","K","B","Q","A","B","K","R"},
		//4332 4334 4342 4344 4353 4354  đây là vị trí mà khi đi vua sẽ an toàn mà không bị chiếu theo TH1 của ngựa (4352 không đến 52 được vì K trắng đã ở chỗ đó)
		//(chưa xử lí quân tốt trắng nên mới có nước đi này)
		// * là vị trí vua có thể đi, ! là vị trí vua đi thì sẽ bị chiếu
		for (int i = -1; i <=1; i+=2) {
			for (int j = -1; j <=1; j+=2) {
				try {
					// này là nó sẽ tìm đường theo 2 cột 1 hàng đến vua xem có đang là ô trống không
					//(2,1),(2,5)
						if("k".equalsIgnoreCase(chessBoard[kingPositionU/8+i][kingPositionU%8+j*2])) {
							return false;
						}
					}	
				catch (Exception e) {
					// TODO: handle exception
				}
//				TH2: ngựa đi theo 2 hàng 1 cột
//				{"r","k","b","q","a","b","k","r"},
//				{"p","p","p","p","p","p","p","p"},
//				{" "," "," "," "," ","k"," "," "},
//				{" "," ","*","*","*"," "," "," "},
//				{" "," ","*","A","!"," "," "," "},//dong 4 cot 3 , dong 4 cot 4 co (!)
//				{" "," "," ","*","*"," "," "," "},
//				{"P","P","P","P","P","P","P","P"},
//				{"R","K","B","Q","A","B","K","R"},
				//4332 4333 4334 4342 4353 4354 đây là vị trí mà khi đi vua sẽ an toàn mà không bị chiếu theo TH2 của ngựa (4352 không đến 52 được vì K trắng đã ở chỗ đó)
				//(chưa xử lí quân tốt trắng nên mới có nước đi này)
				// * là vị trí vua có thể đi, ! là vị trí vua đi thì sẽ bị chiếu
				try {		
					if("k".equalsIgnoreCase(chessBoard[kingPositionU/8+i*2][kingPositionU%8+j])) {
						return false;
					}
				}
				//Kết hợp TH1 và TH2:
//				{"r","k","b","q","a","b","k","r"},
//				{"p","p","p","p","p","p","p","p"},
//				{" "," "," "," "," ","k"," "," "},
//				{" "," ","*","!","*"," "," "," "}, // dong 3 cot 3 co (!)
//				{" "," ","*","A","!"," "," "," "},//dong 4 cot 3 , // dong 4 cot 4 co (!)
//				{" "," "," ","*","*"," "," "," "},
//				{"P","P","P","P","P","P","P","P"},
//				{"R","K","B","Q","A","B","K","R"},
				//4332 4334 4342 4353 4354 đây là vị trí mà khi đi vua sẽ an toàn mà không bị chiếu (4352 không đến 52 được vì K trắng đã ở chỗ đó)
				//(chưa xử lí quân tốt trắng nên mới có nước đi này)
				// * là vị trí vua có thể đi, ! là vị trí vua đi thì sẽ bị chiếu
			catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
		//pawn
		if(kingPositionU >= 16) {
			try {		
				if("p".equalsIgnoreCase(chessBoard[kingPositionU/8-1][kingPositionU%8-1])) {
					return false;
				}
			}	
		catch (Exception e) {
			// TODO: handle exception
		}
			try {		
				if("p".equalsIgnoreCase(chessBoard[kingPositionU/8-1][kingPositionU%8+1])) {
					return false;
				}
			}	
		catch (Exception e) {
			//king
			for (int i = -1; i <=1; i++) {
				for (int j = -1; j <=1; j++) {
					if(i!=0 || j!=0) {
					try {		
						if("a".equalsIgnoreCase(chessBoard[kingPositionU/8+i][kingPositionU%8+j])) {
							return false;
						}
					}
					catch (Exception e1) {
							// TODO: handle exception
					}
						}
					}	
				}
			}
		}
	return true;
}
}
