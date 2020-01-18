import java.io.*;
import java.util.*;

public class Solution {
    public static int checkFreeTurn(int[] p1Marbles) {
      for (int i = 0; i < 6; i++) {
        if (i + p1Marbles[i] == 6) {
          //System.out.println("Position: " + i + ". Marbles in this position: " + p1Marbles[i]);
          //System.out.println("optimal turn is at position " + i);
          return i + 1;
        }
      }
      return 0;
    }

    public static int checkSteal(int[] p1Marbles, int[] p2Marbles) {
      for (int i = 0; i < 6; i++) {
        try {
          if (p1Marbles[i] != 0 && p1Marbles[p1Marbles[i] + i] == 0 && p2Marbles[p1Marbles[i] + i] != 0) {
            //System.out.println("Steal opprtunity found at position " + i);
            return i + 1;
          }
        } catch (Exception e) {}
      }
      return 0;
    }

    public static int checkPoints(int[] p1Marbles) {
      for (int i = 0; i < 6; i++) {
        if (i + p1Marbles[i] >= 6) {
          //System.out.println("Position: " + i + ". Marbles in this position: " + p1Marbles[i]);
          //System.out.println("can score points at position " + i);
          return i + 1;
        }
      }
      return 0;
    }

    public static int checkBlockOptimal(int[] p1Marbles, int[] p2Marbles) {
      int freeTurn = checkFreeTurn(p2Marbles);
      int steal = checkSteal(p2Marbles, p1Marbles);
      int[] positions = new int [14]; // placeholder for the position of the marbles at any given point.
                                      // positions[0] and positions[7] are mancalas for players 1 and players 2 respectively.
      int blockCheck = 0;
      if (freeTurn != 0) {
        //System.out.println("Opponent can get a free turn at position " + freeTurn);
        blockCheck = freeTurn;
      }
      if (steal != 0) {
        //System.out.println("Opponent can get a steal at position " + steal);
        blockCheck = steal;
      }
      boolean blockable = false;
      if(blockCheck != 0) {
      for (int x = 0; x < 6; x++)
        positions[x] = p1Marbles[x];
      for (int x = 7; x < 13; x++)
        positions[x] = p2Marbles[x-7];
      for (int x = 0; x < positions.length; x++) {
        //System.out.print(positions[x] + " ");
      }
      //System.out.println();
      for (int x = 0; x < 6; x++) {
        if(x + positions[x] >= (blockCheck + 7) && blockable != true) {
          blockable = true;
          //System.out.println("Move at position " + x + " to block");
          return x + 1;
        }
      }
    }
      return 0;
    }
    public static int leastOptimal(int[] p1Marbles) {
      for (int x = 0; x < 6; x++){
        if (p1Marbles[x] != 0) {
          return x + 1;
        }
      }
      return 0;
    }
    public static void main(String[] args) {
        int playerID;
        int player1Mancala;
        int[] player1Marbles = new int[6];
        int player2Mancala;
        int[] player2Marbles = new int[6];
        int[] playerMarbles = new int [6];
        int[] botMarbles = new int [6];

        Scanner in = new Scanner(System.in);
        playerID = in.nextInt();
        player1Mancala = in.nextInt();
        for (int i = 0; i < 6; i++) {
          player1Marbles[i] = in.nextInt();
        }
        player2Mancala = in.nextInt();
        for (int i = 0; i < 6; i++) {
          player2Marbles[i] = in.nextInt();
        }
        /*
        playerID = 1;
        player1Mancala = 0;
        int[] player1Marbles = {4, 4, 4, 4, 4, 4};
        int[] player2Marbles = {4, 4, 4, 4, 4, 4};
        // THE ABOVE IS FOR DEBUG.
        */
        boolean moveMade = false;
        switch (playerID) {
          case 1:
            //System.out.println("DEBUG: you are player 1.");
            playerMarbles = player1Marbles;
            botMarbles = player2Marbles;
            break;
          case 2:
            //System.out.println("DEBUG: you are player 2.");
            playerMarbles = player2Marbles;
            botMarbles = player1Marbles;
            break;
          }

          // ALGORITHM:
          // first check to see if we can go again
          //System.out.println("checking for free turn");
          int freeCheck = checkFreeTurn(playerMarbles);
          if (freeCheck != 0 && moveMade == false) {
            System.out.print(freeCheck);
            //System.out.println(" freeCheck");
            moveMade = true;
          }

          // next check to see if we can get points moving the rightmost pit
          //System.out.println("checking for points");
          int pointsCheck = checkPoints(playerMarbles);
          if ((pointsCheck) != 0 && moveMade == false) {
            System.out.print(pointsCheck);
            //System.out.println(" checkPoints");
            moveMade = true;
          }

          // next check to see if we can steal
          //System.out.println("checking for steal");
          int stealCheck = checkSteal(playerMarbles, botMarbles);
          if (stealCheck != 0 && moveMade == false) {
            System.out.print(stealCheck);
            //System.out.println(" stealCheck");
            moveMade = true;
          }

          // next check to see if opponent can do either of these actions and block if possible)
          //System.out.println("checking for potential block");
          int blockCheck = checkBlockOptimal(playerMarbles, botMarbles);
          if ((blockCheck) != 0 && moveMade == false) {
            System.out.print(blockCheck);
            //System.out.println(" checkBlockOptimal");
            moveMade = true;
          }


          // if nothing is optimal, move the leftmost pit

          //System.out.println("Have to do least optimal move - moving first pit to not be empty.");
          if (moveMade == false) {
            System.out.print(leastOptimal(playerMarbles));
            //System.out.println(" leastOptimal");
            moveMade = true;
          }
        }
    }
