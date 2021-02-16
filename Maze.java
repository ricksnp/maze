package Test;

import java.util.*;
import java.io.*;

public class Maze
 {static String inputFile = "maze.txt";                                    //This is the input file for the maze
  static int steps = 0, row = -1, col = -1, startingRow = -1, startingCol = -1,
                                              endingRow = -1, endingCol   = -1;
  static char[][] theMaze;

  public static void main(String[] theArgs)
   {Scanner input = null;
    try
     {input = new Scanner(new File(inputFile));
     }
    catch (FileNotFoundException e)
     {System.out.println("Error opening file:\n"+inputFile+"\n" + e);
     }

    getMaze(input);
    getStartLocationAndDirection();
    doMaze(startingRow, startingCol);
    System.out.println("Failed");
  }

  public static void getMaze(Scanner input)
   {int rows = 0, columns = 0;
    String lineholder = "";
    ArrayList<String> mazeHolder = new ArrayList<String>();

    while (input.hasNextLine())
     {lineholder = input.nextLine();
      mazeHolder.add(lineholder);
     }

    columns = lineholder.length();
    rows = mazeHolder.size();

    theMaze = new char[rows][columns];

    for (int r = 0; r < rows; r++)
     {String holder = mazeHolder.get(r);
      for (int c = 0; c < columns; c++)
       {theMaze[r][c] = holder.charAt(c);
       }
     }
   }

  public static int getStartLocationAndDirection()
   {for   (int r = 0; r < theMaze.length;    r++)
     {for (int c = 0; c < theMaze[r].length; c++)
       {if (theMaze[r][c] == 'S')
         {startingRow      = r;
          startingCol      = c;
         }
        if (theMaze[r][c] == 'E')
         {endingRow        = r;
          endingCol        = c;
         }
       }
     }
    return startingCol;
  }

  public static boolean doMaze(int theRow, int theCol)
   {++steps;
    if (theMaze[theRow][theCol] == 'E')
     {System.out.println("Success!");
      displayMaze();
      System.exit(0);
     }
    if (theMaze[theRow][theCol] == ' ' || theMaze[theRow][theCol] == 'S')
     {theMaze[theRow][theCol] = '*';
      boolean r = false;
      if        (doMaze(theRow, theCol + 1)) r = true;
      else if   (doMaze(theRow, theCol - 1)) r = true;
      else if   (doMaze(theRow + 1, theCol)) r = true;
      else if   (doMaze(theRow - 1, theCol)) r = true;
      if (!r) theMaze[theRow][theCol] = '-';
      return r;
     }
    return false;
   }

  public static void displayMaze()
   {System.out.println("Step: " + steps);
    for (int r = 0; r < theMaze.length; r++)
     {for (int c = 0; c < theMaze[r].length; c++)
       {System.out.print(theMaze[r][c]);
       }
      System.out.println();
     }
    System.out.println();
   }
 }
