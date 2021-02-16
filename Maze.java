package Test;

import java.util.*;
import java.io.*;

public class Maze
 {static final String inputFile = "maze.txt";                                   // This is the input file for the maze
  static char[][] maze, corners;
  static Integer steps    = 0,                                                  // Number of moves made
       rows = null,  cols = null,                                               // Size of the maze
       row  = null,  col  = null,                                               // Current position
      sRow  = null, sCol  = null,                                               // Start position
      eRow  = null, eCol  = null;                                               // End position

  static final char U = '⬆', D = '⬇', P = '⬅', N = '➜',                         // Arrows
     dp='┛', dn='┗', up='┓', un='┏', pu='┗', pd='┏', nu='┛',  nd='┓';           // Corners

  static Random rand = new Random();                                            // Random number generator

  public static void main(String[] args)
   {load();
    dm('+', sRow, sCol);
    say("Failed");
   }

  public static void load()                                                     // Load the maze
   {try
     {final ArrayList<String> m = new ArrayList<String>();
      String l = "";
      final Scanner in = new Scanner(new File(inputFile));

      while(in.hasNextLine())                                                   // Read maze
       {l = in.nextLine();
        m.add(l);
        if      (cols == null) cols = l.length();
        else if (cols != l.length())
         {say("Line should be "+cols+" characters long:\n"+l);
          System.exit(1);
         }
       }

      rows = m.size();                                                          // Parse maze
      maze = new char[rows][cols];
      corners = new char[rows][cols];
      for  (int r = 0; r < rows; r++)
       {for(int c = 0; c < cols; c++)
         {final char p = maze   [r][c] = m.get(r).charAt(c);
                         corners[r][c] = ' ';
          if (p == 'S') {sRow = r; sCol = c;}                                   // Entrance
          if (p == 'E') {eRow = r; eCol = c;}                                   // Exit
         }
       }
     }
    catch (FileNotFoundException e)                                             // Complain about missing input file
     {say("Error opening file:\n"+inputFile+"\n" + e);
      System.exit(2);
     }
   }

  public static void dm(char m, int pr, int pc)                                 // Find path through the maze - move in the indicated direction and mark the new position with the specified character in the context of the preceding character
   {++steps;
    int c = pc, r = pr;                                                         // New position
    switch(m)
     {case N: c = pc + 1; break;
      case P: c = pc - 1; break;
      case U: r = pr - 1; break;
      case D: r = pr + 1; break;
     }

    if (r < 0 || r >= rows || c < 0 || c >= cols) return;                       // Check the proposed location is valid

    char M = maze[pr][pc];                                                      // Fix previous arrow
    if      (false) {}
    else if (M == D && m == N) corners[pr][pc] = dn;
    else if (M == D && m == P) corners[pr][pc] = dp;
    else if (M == U && m == N) corners[pr][pc] = un;
    else if (M == U && m == P) corners[pr][pc] = up;
    else if (M == P && m == U) corners[pr][pc] = pu;
    else if (M == P && m == D) corners[pr][pc] = pd;
    else if (M == N && m == U) corners[pr][pc] = nu;
    else if (M == N && m == D) corners[pr][pc] = nd;

    if (maze[r][c] == 'E')                                                      // Check for exit
     {  maze[r][c]  = m;
      say("Success!");
      display();
      System.exit(0);
     }

    if (maze[r][c] == ' ' || maze[r][c] == 'S')                                 // Try to make a move
     {  maze[r][c] = m;
       switch(rand.nextInt(4))                                                  // Randomize the directionality
        {case  0: dm(N, r, c); dm(P, r, c); dm(D, r, c); dm(U, r, c); break;
         case  1: dm(D, r, c); dm(U, r, c); dm(N, r, c); dm(P, r, c); break;
         case  2: dm(P, r, c); dm(D, r, c); dm(U, r, c); dm(N, r, c); break;
         case  3: dm(U, r, c); dm(D, r, c); dm(P, r, c); dm(N, r, c); break;
        }
      maze    [r] [c] = '-';
     }
      corners[pr][pc] = ' ';
   }

  public static void display()                                                  // Display the maze
   {say("Step: " + steps);
    for  (int r = 0; r < rows; r++)
     {for(int c = 0; c < cols; c++)
       {final char m = maze[r][c], k = corners[r][c];
        char q = m;
             q = m == D || m == U ? '┃' : q;
             q = m == N || m == P ? '━' : q;
             q = m == '-'         ? ' ' : q;
             q = m == 'X'         ? '█' : q;
        System.err.print(k != ' ' ? k : q);                                     // Overlay corner if present
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
