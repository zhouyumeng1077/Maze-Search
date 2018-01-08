// Name:Yuanda Tang
// USC loginid:yuandata
// CS 455 PA3
// Fall 2016

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.util.LinkedList;

import javax.swing.JComponent;

/**
   MazeComponent class
   
   A component that displays the maze and path through it if one has been found.
*/
public class MazeComponent extends JComponent
{
   private Maze maze;
   private static final int START_X = 10; // where to start drawing maze in frame
   private static final int START_Y = 10;
   private static final int BOX_WIDTH = 20;  // width and height of one maze unit
   private static final int BOX_HEIGHT = 20;
   private static final int INSET = 2;   // how much smaller on each side to make entry/exit inner box
   
   
   /**
      Constructs the component.
      @param maze   the maze to display
   */
   public MazeComponent(Maze maze) 
   {   
       this.maze=maze;
   }

   
   /**
     Draws the current state of maze including the path through it if one has
     been found.
     @param g the graphics context
   */
   public void paintComponent(Graphics g)
   {   
	   int startCol;
	   int startRow;
	   int endCol;
	   int endRow;
	   int sideLen;
	   Graphics2D g2=(Graphics2D) g;
	   for(int i=0;i<maze.numRows();i++){
		   for(int j=0;j<maze.numCols();j++){
			   if(maze.hasWallAt(new MazeCoord(i,j))){
				   g2.setColor(Color.BLACK);
			   }
			   else{
				   g2.setColor(Color.WHITE);
			   }
			   Rectangle box=new Rectangle(START_X+j*BOX_WIDTH,START_X+i*BOX_HEIGHT,BOX_WIDTH,BOX_HEIGHT);
		       g2.fill(box);
		   }
	   }
	   startCol=START_X+maze.getEntryLoc().getCol()*BOX_WIDTH+INSET;
	   startRow=START_X+maze.getEntryLoc().getRow()*BOX_HEIGHT+INSET;
	   endCol=START_X+maze.getExitLoc().getCol()*BOX_WIDTH+INSET;
	   endRow=START_X+maze.getExitLoc().getRow()*BOX_HEIGHT+INSET;
	   sideLen=BOX_WIDTH-2*INSET;
	   Rectangle startPoint=new Rectangle(startCol,startRow,sideLen,sideLen);
	   g2.setColor(Color.YELLOW);
	   g2.fill(startPoint);
	   Rectangle endPoint=new Rectangle(endCol,endRow,sideLen,sideLen);
	   g2.setColor(Color.GREEN);
	   g2.fill(endPoint);
	   Rectangle frame=new Rectangle(START_X,START_X,BOX_WIDTH*maze.numCols(),BOX_HEIGHT*maze.numRows());
	   g2.setColor(Color.BLACK);
	   g2.draw(frame);
	   if(maze.getPath()!=null){
		   drawPath(maze.getPath(),g2);  
	   }
   }
   
   
   /**
    * draw the path from start point to destination.
    */
   private void drawPath(LinkedList<MazeCoord> path,Graphics2D g2){
	   for(int i=1;i<path.size();i++){
		   int firstX=(path.get(i-1).getCol()+1)*BOX_WIDTH;
		   int firstY=(path.get(i-1).getRow()+1)*BOX_WIDTH;
		   int secondX=(path.get(i).getCol()+1)*BOX_WIDTH;
		   int secondY=(path.get(i).getRow()+1)*BOX_WIDTH;
	       Line2D.Double part= new Line2D.Double(firstX,firstY,secondX,secondY);
	       g2.setColor(Color.BLUE);
	       g2.draw(part);
	   }
   }
}


