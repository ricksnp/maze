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

  static Random rand = new Random();                                            // Random number generator

  public static void main(String[] args)
   {getMaze();
    getStartLocationAndDirection();
    dm(sRow, sCol, '+');
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

  public static boolean dm(int r, int c, char m)                                // Find path through the maze - try t step to position (r,c) and mark it with the specified character
   {++steps;
    if (r < 0 || r >= rows || c < 0 || c >= cols) return false;                 // Check the proposed location is valid

    if (maze[r][c] == 'E')                                                      // Check for exit
     {  maze[r][c]  = m;
      say("Success!");
      displayMaze();
      System.exit(0);
     }

    if (maze[r][c] == ' ' || maze[r][c] == 'S')                                 // Try to make a move
     {  maze[r][c] = m;
       final int  i = rand.nextInt(4);                                          // Randomize the directionality
       final char D = '⬆', U = '⬇', P = '⬅', N = '➜';
       final boolean R =
        i==0 ? dm(r, c+1, N) || dm(r, c-1, P) || dm(r+1, c, U) || dm(r-1, c, D):
        i==1 ? dm(r+1, c, U) || dm(r-1, c, D) || dm(r, c+1, N) || dm(r, c-1, P):
        i==2 ? dm(r, c-1, P) || dm(r+1, c, U) || dm(r-1, c, D) || dm(r, c+1, N):
               dm(r-1, c, D) || dm(r+1, c, U) || dm(r, c-1, P) || dm(r, c+1, N);
      if (!R) maze[r][c] = '-';                                                 // Failed to make a move so mark out maze
      return R;
     }

    return false;                                                               // Wall
   }

  public static void displayMaze()                                              // Display the maze
   {say("Step: " + steps);
    for  (int r = 0; r < rows; r++)
     {for(int c = 0; c < cols; c++)
       {final char p = maze[r][c], q = p == '-' ? ' ' : p;                      // Remove back tracks
        System.err.print(q);
       }
      say();
     }
    say();
   }

  public static void say(Object...O)                                            // Say something
   {final StringBuilder b = new StringBuilder();
    for(Object o: O) b.append(o);
    System.err.print(b.toString()+"\n");
   }
 }
