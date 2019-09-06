package lifegame09B17054;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class MyActions implements ActionListener{
	BoardModel model;
	JButton next,undo,start,stop,redo,newgame;
	BoardView view;
	Timer timer=new Timer(500,this);
	
	static int boardnumber=0;
	public MyActions(BoardModel model2,JButton next2,JButton undo2,JButton redo2,JButton start2,JButton stop2,JButton newgame2,BoardView view2) {
	model=model2;
	next=next2;
	undo=undo2;
	redo=redo2;
	start=start2;
	stop=stop2;
	newgame=newgame2;
	view=view2;
	}
	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		
		
		if(e.getSource()==next||e.getSource()==timer) {
			model.next();
			view.repaint();
			if(model.isRedoable()) {redo.setEnabled(true);}
			else{redo.setEnabled(false);}
		}
		else if(e.getSource()==undo) {
			if(!model.isUndoable()) {
				undo.setEnabled(false);}
			else{model.undo();
			view.repaint();}
			if(!model.isUndoable()) {undo.setEnabled(false);}
			else{undo.setEnabled(true);}
			if(model.isRedoable()) {redo.setEnabled(true);}
			else{redo.setEnabled(false);}
		}
		else if(e.getSource()==redo) {
			if(model.isRedoable()) {model.redo();
			view.repaint();}
			else{redo.setEnabled(false);}
		}
		else if(e.getSource()==start) {
			timer.start();
			stop.setEnabled(true);start.setEnabled(false);
		}
		else if(e.getSource()==stop) {
			timer.stop();
			stop.setEnabled(false);start.setEnabled(true);
		}
		else if(e.getSource()==newgame) {
			Main a=new Main();
			boardnumber++;
			a.run();
		}
		
		if(model.isUndoable()) {undo.setEnabled(true);}
		else{undo.setEnabled(false);}
	}
	
	public synchronized void allowUndo() {
		undo.setEnabled(true);
	}
	
	public synchronized void closeThread() {
		timer.stop();
	}
}
