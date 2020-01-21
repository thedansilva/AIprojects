import java.io.*;
import java.util.*;

public class Solution {
    public static int checkZero(int[] p1Marbles, int x) {
      if (p1Marbles[x] == 0) {
        return -99999;
      }
      return 0;
    }
    public static int checkFreeTurn(int[] p1Marbles, int x) {
        if (x + p1Marbles[x] == 6) {
          return 100;
        }
      return 0;
    }

    public static int checkSteal(int[] p1Marbles, int[] p2Marbles, int x) {
        try {
          if (p1Marbles[p1Marbles[x] + x] == 0 && p2Marbles[p1Marbles[x] + x] != 0) {
            return 10;
          }
        } catch (Exception e) {}
      return 0;
    }

    public static int checkPoints(int[] p1Marbles, int x) {
        if (x + p1Marbles[x] >= 6) {
          return 1;
        }
      return 0;
    }

    public static int checkBlockOptimal(int[] p1Marbles, int[] p2Marbles, int i) {
      int[] p2Score = new int[6];
      for (int x = 0; x < 6; x++) {
        p2Score[x] = checkZero(p2Marbles, x) + checkFreeTurn(p2Marbles, x) + checkSteal(p2Marbles, p1Marbles, x);
      }
      int bestMove = 0;
      int position = 0;
      for (int x = 0; x < 6; x++) {
        if (p2Score[x] > bestMove) {
          bestMove = p2Score[x];
          position = x;
        }
      }
      if (bestMove > 0) {
          if (p1Marbles[i] != 0 && (p1Marbles[i] + i) >= (position + 7)) {
            return 5;
          }
      }
      return 0;
    }
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int playerID = in.nextInt();
        int player1Mancala = in.nextInt();
        int[] player1Marbles = new int[6];
        int[] player2Marbles = new int[6];
        int[] score = new int[6];
        int[] playerMarbles = new int [6];
        int[] botMarbles = new int [6];
        int player2Mancala = 0;
        switch (playerID) {
          case 1:
            playerMarbles = player1Marbles;
            botMarbles = player2Marbles;
            for (int i = 0; i < 6; i++) {
              player1Marbles[i] = in.nextInt();
            }
            player2Mancala = in.nextInt();
            for (int i = 5; i >= 0; i--) {
              player2Marbles[i] = in.nextInt();
            }
            break;
          case 2:
            playerMarbles = player2Marbles;
            botMarbles = player1Marbles;
            for (int i = 5; i >= 0; i--) {
              player1Marbles[i] = in.nextInt();
            }
            player2Mancala = in.nextInt();
            for (int i = 0; i < 6; i++) {
              player2Marbles[i] = in.nextInt();
            }
            break;
          }


/*
          int playerID = 2;
          int player1Mancala = 0;
          int player2Mancala = 0;
          int[] player1Marbles = {0, 0, 6, 3, 0, 0};
          int[] player2Marbles = {0, 3, 0, 2, 0, 0};
          //THE ABOVE IS FOR DEBUG.
*/



          // ALGORITHM:
          for (int x = 0; x < 6; x++) {
          score[x] = checkZero(playerMarbles, x) + checkFreeTurn(playerMarbles, x) + checkSteal(playerMarbles, botMarbles, x) + checkBlockOptimal(playerMarbles, botMarbles, x) + checkPoints(playerMarbles, x);
          }

          for (int x = 0; x < 6; x++) {
            //System.out.print(score[x] + " ");
          }
          int bestScore = 0, bestMove = 0;
          for (int x = 0; x < 6; x++) {
              if (score[x] >= 0 && score[x] >= bestScore) {
                bestScore = score[x];
                bestMove = x;
              }
            }
          System.out.println((bestMove+1));
        }
    }
