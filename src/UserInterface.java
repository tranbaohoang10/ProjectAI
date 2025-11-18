import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserInterface extends JPanel implements MouseListener,MouseMotionListener{
	static int x=0,y=0;
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
//		g.setColor(new Color());
		addMouseListener(this);
		addMouseMotionListener(this);
		g.setColor(Color.RED);
		g.fillRect(x, y, 20, 20);
		
		Image chessPiecesImage;
		chessPiecesImage = new ImageIcon("pieces.png").getImage();
		g.drawImage(chessPiecesImage, x, y,x+64,y+64,0,0,64,64, this);
	}

	

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		x=e.getX();
		y=e.getY();
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

	

	

}
