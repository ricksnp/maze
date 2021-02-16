package Test;

import java.util.*;
import java.io.*;

public class Maze
 {static final String inputFile = "maze.txt";                                   // This is the input file for the maze
  static char[][] maze;
  static Integer steps    = 0,
       rows = null,  cols = null,
       row  = null,  col  = null,
      sRow  = null, sCol  = null,
      eRow  = null, eCol  = null;

  public static void main(String[] args)
   {getMaze();
    getStartLocationAndDirection();
    doMaze(sRow, sCol);
    say("Failed");
   }

  public static void getMaze()                                                  // Load the maze
   {try
     {final ArrayList<String> m = new ArrayList<String>();
      String l = "";
      final Scanner input = new Scanner(new File(inputFile));

      while(input.hasNextLine())
       {l = input.nextLine();
        m.add(l);
        if      (cols == null) cols = l.length();
        else if (cols != l.length())
         {say("Line should be "+cols+" characters long:\n"+l);
          System.exit(0);
         }
       }

      rows = m.size();
      maze = new char[rows][cols];
      for  (int r = 0; r < rows; r++)
       {for(int c = 0; c < cols; c++) maze[r][c] = m.get(r).charAt(c);
       }
     }
    catch (FileNotFoundException e)
     {say("Error opening file:\n"+inputFile+"\n" + e);
      System.exit(0);
     }
   }

  public static void getStartLocationAndDirection()                             // Locate start and exit
   {for   (int r = 0; r < rows; r++)
     {for (int c = 0; c < cols; c++)
       {if (maze[r][c] == 'S')
         {sRow   = r;
          sCol   = c;
         }
        if (maze[r][c] == 'E')
         {eRow     = r;
          eCol     = c;
         }
       }
     }
   }

  public static boolean doMaze(int row, int col)                                // Find path through the maze
   {++steps;

    if (maze[row][col] == 'E')
     {  maze[row][col]  = '*';
      say("Success!");
      displayMaze();
      System.exit(0);
     }

    if (maze[row][col] == ' ' || maze[row][col] == 'S')
     {maze[row][col] = '*';
      boolean r = false;
      if      (doMaze(row, col + 1)) r = true;
      else if (doMaze(row, col - 1)) r = true;
      else if (doMaze(row + 1, col)) r = true;
      else if (doMaze(row - 1, col)) r = true;
      if (!r) maze[row][col] = '-';
      return r;
     }

    return false;
   }

  public static void displayMaze()                                              // Display the maze
   {say("Step: " + steps);
    for  (int r = 0; r < rows; r++)
     {for(int c = 0; c < cols; c++) System.err.print(maze[r][c]);
      say();
     }
    say();
   }

  public static void say(Object...O)                                           // Say something
   {final StringBuilder b = new StringBuilder();
    for(Object o: O) b.append(o);
    System.err.print(b.toString()+"\n");
   }
 }
