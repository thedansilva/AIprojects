import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        double luminance = 0;
        double lumeAvg = 0;
        int count=0;
        Scanner scanner = new Scanner(System.in);
        String row = scanner.nextLine();
        scanner.close();
        String[] BGR = row.split(" ");
        int blue = 0;
        int green = 0;
        int red = 0;
        for(int x = 0; x < BGR.length; x++)
        {
                    String[] pixel = BGR[x].split(",");
                    blue = Integer.parseInt(pixel[0]);
                    green = Integer.parseInt(pixel[1]);
                    red = Integer.parseInt(pixel[2]);
                    luminance = ((0.33*red) + (0.66*green) + (0.33*blue));
                    lumeAvg += luminance / 255;
                    count++;
                    //System.out.println("Blue: " + blue + " Green: " + green + " Red: " + red);
        }
        // averaging per pixel
        lumeAvg /= count;      
        if (lumeAvg < 0.45)
            System.out.print("night");
        else
            System.out.print("day");
        
        //System.out.println("\nLum :" + luminance);
    }
}
