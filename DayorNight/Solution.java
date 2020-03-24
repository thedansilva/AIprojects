import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {

    static int split(String str){
      int total=0;
      int average =0;
      int count=0;
      String[] arrOfStr = str.split(" ");

          for (String a : arrOfStr){

            String[] pixels = a.split(",");

                for (String p : pixels){
                    int pnum =Integer.parseInt(p);
                    total=pnum+total;
                    count= count+1;
                    }

              //  System.out.println("count: "+ count);
          }
          average= total/count;
          //System.out.println(total);
        //  int average= total/pixels.length;

          return average;
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */


//just for testing with file, delete later
      //   try {
      // File myObj = new File("testcase3.txt");
      // Scanner myReader = new Scanner(myObj);
      //
      //   String row = myReader.nextLine();
      //
      //  System.out.println(row);
    //  }
      //myReader.close();
      int value = split(row);
  

      if (value>50 ){
        System.out.println("day");
      }
      else{
        System.out.println("night");
      }

    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }



//uncomment to work with STDIN
        Scanner scanner = new Scanner(System.in);
        String row = scanner.nextLine();
        scanner.close();
        System.out.println(row);

    }
}
