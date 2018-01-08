// Name:Yuanda Tang
// USC loginid:yuandata
// CS 455 PA3
// Fall 2016

import java.util.LinkedList;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).
   
   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls
 */

/**
Representation invariant:
--Two-dimensional boolean array mazeData stands for the maze. Pad it with
virtual outer walls. False stands for free way and true stands for wall.
--Two-dimensional boolean array arriveBefore stands for the maze. Every time 
walk through the specific point, change its value from false to true.  
--LinkedList route stands for the path from start point to destination. The order 
of points in the linked list is in the same order of the path.
*/

public class Maze {
   
   public static final boolean FREE = false;
   public static final boolean WALL = true;
   private MazeCoord startPoint;
   private MazeCoord endPoint;
   private boolean haveSearch;
   private boolean havePath;
   private boolean[][] mazeData;  //save the maze data
   private boolean[][] arriveBefore;  //save the information whether the path been here before
   private LinkedList<MazeCoord> route;  //save the way from startPoint to endPoint
   
  

   /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param endLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= endLoc.getRow() < mazeData.length and 0 <= endLoc.getCol() < mazeData[0].length

    */
   public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord endLoc)
   {
      this.mazeData=new boolean[mazeData.length+1][mazeData[0].length+1];  //bigger to store the outside wall
      for(int i=0;i<mazeData.length;i++){
    	  for(int j=0;j<mazeData[0].length;j++){
    		  this.mazeData[i][j]=mazeData[i][j];
    	  }
      }
      for(int i=0;i<mazeData[0].length+1;i++){
    	  this.mazeData[mazeData.length][i]=WALL;
      }
      for(int i=0;i<mazeData.length+1;i++){
    	  this.mazeData[i][mazeData[0].length]=WALL;
      }
      this.arriveBefore=new boolean[mazeData.length][mazeData[0].length];
      for(int i=0;i<mazeData.length;i++){  //initialize the matrix to record been here before
    	  for(int j=0;j<mazeData[0].length;j++){
    		  this.arriveBefore[i][j]=false;
    	  }
      }
      startPoint=startLoc;
      endPoint=endLoc;
      route=new LinkedList<MazeCoord>();
      haveSearch=false;
      havePath=false;
   }


   /**
   Returns the number of rows in the maze
   @return number of rows
   */
   public int numRows() {
      return mazeData.length-1;
   }

   
   /**
   Returns the number of columns in the maze
   @return number of columns
   */   
   public int numCols() {
      return mazeData[0].length-1;
   } 
 
   
   /**
      Returns true iff there is a wall at this location
      @param loc the location in maze coordinates
      @return whether there is a wall here
      PRE: 0 <= loc.getRow() < numRows() and 0 <= loc.getCol() < numCols()
   */
   public boolean hasWallAt(MazeCoord loc) {
      if(mazeData[loc.getRow()][loc.getCol()]==true){  
    	  return true;
      }
      else return false;
   }
   

   /**
      Returns the entry location of this maze.
    */
   public MazeCoord getEntryLoc() {
      return startPoint; 
   }
   
   
   /**
   Returns the exit location of this maze.
   */
   public MazeCoord getExitLoc() {
      return endPoint;
   }

   
   /**
      Returns the path through the maze. First element is starting location, and
      last element is exit location.  If there was not path, or if this is called
      before search, returns empty list.

      @return the maze path
    */
   public LinkedList<MazeCoord> getPath() {
       return route;
   }


   /**
      Find a path through the maze if there is one.  Client can access the
      path found via getPath method.
      @return whether path was found.
    */
   public boolean search()  {  
	   if(haveSearch==true){
		   return havePath;
	   }
	   haveSearch=true;
	   havePath=routine(startPoint,route);
       return havePath;
   }
   
   
   /**
    * find the path and save it in a LinkedList
    */
   private boolean routine(MazeCoord curPoint,LinkedList<MazeCoord> result){
	   if(this.hasWallAt(curPoint)){
		   return false;
	   }
	   if(this.arriveBefore[curPoint.getRow()][curPoint.getCol()]==true){
		   return false;
	   }
	   result.add(curPoint);
	   if(curPoint.equals(endPoint)){
		   return true;
	   }
	   result.removeLast();
	   arriveBefore[curPoint.getRow()][curPoint.getCol()]=true;
	   if(curPoint.getCol()>=1){
		   result.add(curPoint);
		   if(routine(new MazeCoord(curPoint.getRow(),curPoint.getCol()-1),result)){
			   return true;
		   }
		   result.removeLast();
	   }
	   if(curPoint.getRow()>=1){
		   result.add(curPoint);
		   if(routine(new MazeCoord(curPoint.getRow()-1,curPoint.getCol()),result)){
			   return true;
		   }
		   result.removeLast();
	   }
	   result.add(curPoint);
	   if(routine(new MazeCoord(curPoint.getRow()+1,curPoint.getCol()),result)){
		   return true;
	   }
	   result.removeLast();
	   result.add(curPoint);
	   if(routine(new MazeCoord(curPoint.getRow(),curPoint.getCol()+1),result)){
		   return true;
	   }
	   result.removeLast();
	   return false;
   }
}