import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MazeTester {

	public static void main(String[] args) {
		boolean[][] board = null;
		try {
			FileReader fr = new FileReader("test");
			BufferedReader br = new BufferedReader(fr);
			board = new boolean[20][20];
			for(int i=0;i<20;i++){
				String line = br.readLine();
				for (int j = 0; j < 20; j++) {
					if(line.charAt(j) == '0'){
						board[i][j] = false;
					}
					else{
						board[i][j] = true;
					}
				}	
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean[][] mazeData1 =
            {
                  {false,true,true,true,true,true,true,true,true,true},
                  {false,true,true,false,false,false,false,false,false,false},
                  {false,false,false,false,true,true,true,true,true,false},
                  {true,false,true,true,true,false,true,false,true,false},
                  {true,false,false,false,false,false,true,false,false,false},
                  {true,false,true,true,false,true,true,true,false,true},
                  {true,false,true,false,false,false,true,true,true,true},
                  {false,false,false,false,true,false,false,false,false,false},
                  {true,true,true,true,true,true,true,true,true,false}
            };
      MazeCoord entryLoc1 = new MazeCoord(5, 8);
      MazeCoord exitLoc1 = new MazeCoord(8, 9);
		
		Maze maze=new Maze(mazeData1,new MazeCoord(5,8),new MazeCoord(8,9));
        System.out.println(maze.search());
        System.out.println(maze.getPath());
	}
}
