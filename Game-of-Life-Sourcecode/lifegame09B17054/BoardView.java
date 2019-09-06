package lifegame09B17054;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.Graphics;
import java.lang.Math;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardView extends JPanel implements MouseListener,MouseMotionListener{
	
	JButton undo;
	BoardModel model;
	int lastevent = -1;
	
	public BoardView(BoardModel model2,JButton undo2) {
		model=model2;
		undo=undo2;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
		@Override
	public void paint(Graphics g) {
		int cols=model.cols();
		int rows=model.rows();
		super.paint(g); 
		int innerWidth=this.getWidth()-20;
		int innerHeight=this.getHeight()-20;
		for (int y=0; y<cols; y++) {
			for (int x=0; x<rows; x++) {
				g.setColor(new Color(0,0,0));
				drawBox(g,10+y*innerWidth/cols,10+x*innerHeight/rows,10+(y+1)*innerWidth/cols,10+(x+1)*innerHeight/rows);
				if(model.isAlive(x, y))
					{
					int parent=model.countParent(x,y);
					if(parent<4) {
						g.setColor(new Color(255*parent/13,255*parent/5,0));
					}
					else{
						g.setColor(new Color(255*(8-parent)/12,255*(8-parent)/30,0));
					}
					g.fillRect(10+y*innerWidth/cols,10+x*innerHeight/rows,(int)Math.ceil(innerWidth/cols+1),(int)Math.ceil(innerHeight/rows+1));}
			}
		}
		
	}
		private void drawBox(Graphics g,int x,int y, int z,int w) {
		g.drawLine(x, y, x, w);
		g.drawLine(x, y, z, y);
		g.drawLine(z, w, x, w);
		g.drawLine(z, w, z, y);
	}

		@Override
		public void mouseDragged(MouseEvent e) {
			int cols=model.cols(),cellX=cols*(e.getX()-10)/(this.getWidth()-20);
			int rows=model.rows(),cellY=rows*(e.getY()-10)/(this.getHeight()-20);
			if(lastevent==cellX+cols*cellY) {}
			else {
				if(cellX<cols&&cellY<rows&&cellX>=0&&cellY>=0) {
					model.changeCellState(cellY,cellX);
					repaint();
					if(model.isUndoable()) {undo.setEnabled(true);}
					}
			}
			lastevent=cellX+cols*cellY;
		}
		@Override
		public void mouseMoved(MouseEvent e) {
		}@Override
		public void mouseClicked(MouseEvent arg0) {
		}@Override
		public void mouseEntered(MouseEvent arg0) {
		}@Override
		public void mouseExited(MouseEvent arg0) {
		}@Override
		public void mouseReleased(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(e.getX()>10&&e.getY()>10&&e.getX()<(this.getWidth()-10)&&e.getY()<(this.getHeight()-10)) {
				int cols=model.cols(),cellX=cols*(e.getX()-10)/(this.getWidth()-20);
				int rows=model.rows(),cellY=rows*(e.getY()-10)/(this.getHeight()-20);
				model.changeCellState(cellY,cellX);
				repaint();
				lastevent=cellX+cols*cellY;
				if(model.isUndoable()) {undo.setEnabled(true);}
			}
		}
}