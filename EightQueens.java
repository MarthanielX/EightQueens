import java.util.Arrays;
import java.io.*;

public class EightQueens {

   final static int SIZE = 8; 
   static PrintWriter writer;
   static int solutionCount;
   
   // initializes variables and runs completeBoard on a blank board to find all solutions
   public static void main (String[] args) throws FileNotFoundException, UnsupportedEncodingException{      
      writer = new PrintWriter("QueensSolutions.txt", "UTF-8");
      solutionCount = 0;
      // initialize blank board
      char[][] board = new char[SIZE][SIZE];
      for(int i = 0; i < board.length; i++){
         for (int j = 0; j < board[i].length; j++){
            board[i][j] = '_';
         }
      }      
      completeBoard(board);
      writer.close();  
   }
   
   /* Finds all solutions from given position recursively
   Base Case: if can't add a queen, return null
   Base Case: if found solution, write to file and return null
   Else: copy array, add next queen, recursive call on that
      board, then do a recursive call on the original board
      with an 'X' in the spot the method just filled */  
   public static char[][] completeBoard(char[][] board){
      char[][] original = copyArray(board);
      // Count queens, return if complete
      int numQueens = countQueens(original);
      if (numQueens == SIZE){
         solutionCount++;
         writer.println("Solution #" + solutionCount + " :");   
         writer.println(getString(original));
         return null;
      }
      // return null if no available squares
      Integer col = getNextSpot(numQueens, original);
      if (col == null) return null;
      // completeBoard on a copy with next square marked
      char[][] copy = copyArray(original);
      copy = markOff(original, numQueens, col);
      copy = completeBoard(copy);
      // then mark that spot with 'X', complete on remaining
      original[numQueens][col] = 'X';
      completeBoard(original);
      return null;
   }
      
   // for loop end conditions adapted from code by Dave Musciant
   // takes board and returns copy with Xs and Q added
   // param row and col are location of Q to be added
   private static char[][] markOff(char[][] inputBoard, int row, int col){
      char[][] board = copyArray(inputBoard);
      int i,j = 0;
   
        // Mark column
      for (i=0; i<SIZE; i++)
         board[i][col] = 'X';
            
        // Mark row
      for (j=0; j<SIZE; j++)
         board[row][j] = 'X'; 
            
        // Mark diagonal down and right
      for (i=row, j=col; i < SIZE && j < SIZE; i++, j++)
         board[i][j] = 'X';
   
        // Mark diagonal up and left
      for (i=row, j=col; i>=0 && j>=0;  i--, j--)
         board[i][j] = 'X';
   
        // Mark diagonal up and right
      for (i=row, j=col; i>=0 && j < SIZE  ; i--, j++)
         board[i][j] = 'X';
   
        // Mark diagonal down and left
      for (i=row, j=col; i < SIZE && j >=0 ; i++, j--)
         board[i][j] = 'X';
            
      board[row][col] = 'Q';
      return board; 
   }
   
   // returns copy of 2D array param
   private static char[][] copyArray (char[][] original){
      char[][] copy = new char[original.length][original[0].length];
      for (int i = 0; i < copy.length; i++){
         copy[i] = Arrays.copyOf(original[i], original[i].length);
      }
      return copy;
   }
   
   // counts and returns # of queens in param
   private static int countQueens (char[][] board){
      int count = 0; 
      for (char[] col : board)
         for (char square : col)
            if (square == 'Q')
               count ++;
      return count;
   }
   
   // finds next available square in row corresponing to numQueens
   private static Integer getNextSpot(int numQueens, char[][] board){
      int col = 0;
      while (col < SIZE && board[numQueens][col] != '_'){
         col ++;
      }
      if (col == SIZE) 
         return null;
      return col;
   }
   
   // returns string representation of param
   private static String getString(char[][] board){
      String s = "";
      for (char[] array : board){
         for (char square : array){
            if (square == 'Q'){
               s += square + " ";
            } else {
               s += "- ";
            }
         }
         s += "\n";
      }
      return s;
   }
}