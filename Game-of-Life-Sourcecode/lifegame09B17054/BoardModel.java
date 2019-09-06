package lifegame09B17054;
import java.util.ArrayList;

public class BoardModel {
	private int rows,cols,lc,bc;
	private boolean[][][] edits;
	private int timesincelastundo;
	private int editcount,cyclecount,undocount,changecount;
	private boolean[][] cells;
	private boolean lastmoveundo;
	private int livcon[],bircon[];
	private ArrayList<BoardListener> listeners;
	
	public BoardModel(int c, int r, int lc2, int bc2) {
		editcount=0;cyclecount=0;undocount=0;changecount=0;
		timesincelastundo=0;
		lastmoveundo=false;
		cols = c;
		rows = r;
		lc=lc2; bc=bc2;
		cells = new boolean[rows][cols];
		edits=new boolean[33][rows][cols];
		livcon=new int[8];
		bircon=new int[8];
		int i=0;
		for(i=0;i<8;i++) {livcon[i]=-1;
		bircon[i]=-1;}
		i=0;
		do {livcon[i++]=lc%10;
		lc=lc/10;}while(lc>0);
		i=0;
		do {bircon[i++]=bc%10;
		bc=bc/10;}while(bc>0);
		listeners = new ArrayList<BoardListener>();
	}
	public int getCols() {return cols;}
	public int getRows() {return rows;}

	public void addListener(BoardListener listener) {
			listeners.add(listener);
		}
	
	private void fireUpdate() {
		for (BoardListener listener: listeners) {
			listener.updated(this);
		}
    }
	
	private boolean linSearch(int[] arr,int d) {
		for (int i=0;i<8;i++) {
			if(arr[i]==d) {return true;}
		}
		return false;
    }
	
	public synchronized void changeCellState(int x, int y) {
		for (int j=0; j<cols; j++) {
			for (int i=0; i<rows; i++) {
				edits[editcount][i][j]=cells[i][j];
			}
		}
		cells[x][y]=!cells[x][y];
		if(editcount<32) {editcount++;}
		else{editcount=0;}
		for (int j=0; j<cols; j++) {
			for (int i=0; i<rows; i++) {
				edits[editcount][i][j]=cells[i][j];
			}
		}
		if(changecount<32) {changecount++;}
		else{changecount=0;cyclecount++;}
		undocount=0;
		this.fireUpdate();
		timesincelastundo++;
		if(lastmoveundo) {timesincelastundo=1;}
		lastmoveundo=false;
	}
	
	public synchronized void next() {
		int count=0;
		boolean[][] d = new boolean[rows][cols];
		for (int y=0; y<cols; y++) {
			for (int x=0; x<rows; x++) {
				d[x][y]=cells[x][y];
			}
		}
		for (int y=0; y<cols; y++) {
			for (int x=0; x<rows; x++) {
				edits[editcount][x][y]=cells[x][y];
			}
		}
		for (int y=0; y<cols; y++) {
			for (int x=0; x<rows; x++) {
				count=0;
				for ( int i =-1; i <=1; i++ ){
					if(x+i<0) {i++;}if(x+i>=rows) {break;}
					for ( int j =-1; j <=1; j++ ){
						if(y+j<0) {j++;}if(y+j>=cols) {break;}
						if(d[x+i][y+j]) {count++;}
						}
				}
				if(d[x][y]) {count--;}
				if(!d[x][y]&&linSearch(bircon,count)) {
					cells[x][y]=!cells[x][y];
					}
				else if(d[x][y]&&!linSearch(livcon,count)) {
					cells[x][y]=!cells[x][y];
				}
			}
		}
		if(editcount<32) {editcount++;}
		else{editcount=0;}
		for (int y=0; y<cols; y++) {
			for (int x=0; x<rows; x++) {
				edits[editcount][x][y]=cells[x][y];
			}
		}
		if(changecount<32) {changecount++;}
		else{changecount=0;cyclecount++;}
		undocount=0;
		timesincelastundo++;
		if(lastmoveundo) {timesincelastundo=1;}
		lastmoveundo=false;
		this.fireUpdate();
	}
	
	public synchronized void undo() {
		if(editcount>0&&undocount<32) {
			for (int y=0; y<cols; y++) {
				for (int x=0; x<rows; x++) {
					cells[x][y]=edits[editcount-1][x][y];
				}
			}
			editcount-=1;
			undocount++;
			}
		else if(editcount==0&&undocount<32) {
			for (int y=0; y<cols; y++) {
				for (int x=0; x<rows; x++) {
					cells[x][y]=edits[32][x][y];
				}
			}
			editcount=32;
			undocount++;
		}
		lastmoveundo=true;
		this.fireUpdate();
	}
	
	public synchronized void redo() {
		if(undocount>0&&editcount>=0&&editcount!=32) {
			for (int y=0; y<cols; y++) {
				for (int x=0; x<rows; x++) {
					cells[x][y]=edits[editcount+1][x][y];
				}
			}
			editcount+=1;
			undocount--;
			}
		else if(undocount>0&&editcount==32) {
			for (int y=0; y<cols; y++) {
				for (int x=0; x<rows; x++) {
					cells[x][y]=edits[0][x][y];
				}
			}
			editcount=0;
			undocount--;
		}
		this.fireUpdate();
	}
	
	public boolean isUndoable() {
		if(editcount>=0&&editcount<33&&undocount>=0&&undocount<32&&undocount<timesincelastundo&&undocount<(cyclecount*33+changecount)&&0<(cyclecount*33+changecount)) {
			return true;
		}
	    return false;
	}
	
	public boolean isRedoable() {
		if(editcount>=0&&editcount<33&&undocount>0) {
			return true;
		}
	    return false;
	}
	
	public boolean isAlive(int x,int y) {
		if(cells[x][y]) {
			return true;
		}
	    return false;
	}
	
	public int population() {
		int count=0;
		if(undocount>=0&&editcount>=0&&editcount<33) {
			for (int y=0; y<cols; y++) {
				for (int x=0; x<rows; x++) {
					if(cells[x][y]) {count++;}
					}
				}
		}
		return count;
	}
	
	public int countParent(int x,int y) {
		int count=0;
	for ( int i =-1; i <=1; i++ ){
		if(x+i<0) {i++;}if(x+i>=rows) {break;}
		for ( int j =-1; j <=1; j++ ){
			if(y+j<0) {j++;}if(y+j>=cols) {break;}
			if(cells[x+i][y+j]) {count++;}
			}
		}
		if(cells[x][y]) {count--;}
		return count;
    }
	
	public int rows() {return rows;}
	public int cols() {return cols;}
	public int edits() {return (cyclecount*33+changecount);}
	public int editcount() {return editcount;}
	public int undos() {return undocount;}
}
