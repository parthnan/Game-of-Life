package lifegame09B17054;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main implements Runnable{
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Main());
	}
	
	public void run() {
		int cols=0,rows=0,lc=23,bc=3;
		do {
			String test1= JOptionPane.showInputDialog("Input number of Columns(x)0 to 130: ");
			try{cols = Integer.parseInt(test1);}
			catch(NumberFormatException e){cols=1;}
			String test2= JOptionPane.showInputDialog("Input number of Rows(y)0 to 130: ");
			try{rows = Integer.parseInt(test2);}
			catch(NumberFormatException e){rows=1;}
			String test3= JOptionPane.showInputDialog("Living Condition:eg.23=live if 2/3 neighbors.Noninteger=Standard");
			try{lc = Integer.parseInt(test3);}
			catch(NumberFormatException e){lc=23;}
			String test4= JOptionPane.showInputDialog("Birth Condition:eg.345.Noninteger=Standard");
			try{bc = Integer.parseInt(test4);}
			catch(NumberFormatException e){bc=3;}
		}
		while(!(cols<130&&rows<130&&cols>0&&rows>0&&lc>=0&&bc>=0&&lc<87654321&&bc<87654321));
		
		BoardModel model = new BoardModel(cols, rows,lc,bc);
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel base = new JPanel();
		frame.setContentPane(base);
		base.setPreferredSize(new Dimension(350+500*cols/20, 100+500*rows/10)); 
		frame.setMinimumSize(new Dimension(520, 300));  
		base.setLayout(new BorderLayout());
		JButton next = new JButton("Next");
		JButton undo = new JButton("Undo");
		JButton redo = new JButton("Redo");
		JButton start = new JButton("Animate");
		JButton stop = new JButton("Stop");
		JButton newgame = new JButton("New Game");
		BoardView view = new BoardView(model,undo);
		base.add(view, BorderLayout.CENTER);    
		JPanel buttonPanel = new JPanel();
		base.add(buttonPanel, BorderLayout.SOUTH);  
		buttonPanel.setLayout(new FlowLayout());    
		MyActions al = new MyActions(model,next,undo,redo,start,stop,newgame,view);
		frame.setTitle("Game of Life - Board No."+(MyActions.boardnumber+1));
		next.addActionListener(al);
		undo.addActionListener(al);
		redo.addActionListener(al);
		start.addActionListener(al);
		stop.addActionListener(al);
		newgame.addActionListener(al);
		buttonPanel.add(next);
		buttonPanel.add(undo);
		buttonPanel.add(redo);
		buttonPanel.add(start);
		buttonPanel.add(stop);
		buttonPanel.add(newgame);
		redo.setEnabled(false);stop.setEnabled(false);
		if(model.editcount()==0) {undo.setEnabled(false);}
		frame.pack();
		frame.addWindowListener(new WindowAdapter() {
		  @Override
		  public void windowClosing(WindowEvent e) {
		   al.closeThread();
		 }
		});
		frame.setVisible(true); 
	}
	
}
