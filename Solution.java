import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) {
        int playerID;
        int player1Mancala;
        int[] player1Marbles = new int[6];
        int player2Mancala;
        int[] player2Marbles = new int[6];
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
      }
    }
