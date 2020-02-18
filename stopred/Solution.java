import java.io.*;
import java.util.*;
import java.lang.*;
// idea: half window approach where slope of stocks are evaluated at time positions 3 and time positions 5
// and if the slope is lower in 2nd eval, sell. if higher, buy.
public class Solution {
  static class Stock {
    public String name = " ";
    public int owned = 0;
    public double[] prices = new double [5];
    public double halfSlope = 0;
    public double fullSlope = 0;
    public Stock(String n) {
      String[] assign = n.split(" "); // delimit stock data by spaces
      name = assign[0]; // first value is the name of the stock
      owned = Integer.parseInt(assign[1]); // second value is how many are owned
      for (int i = 0; i < 5; i++) {
        prices[i] = Double.parseDouble(assign[i + 2]); // assign prices
      }
    }
    public double halfSlope() { // uses OLS method to get a slope
      double[] xi = {0, 1, 2};
      double[] yi = prices;
      double xAvg = (xi[0] + xi[1] + xi[2]) / 3;
      double yAvg = (yi[0] + yi[1] + yi[2]) / 3;
      double Exix = 0;
      double Eyiy = 0;
      double ExixTyiy = 0;
      double ExixSq = 0;
      for (int i = 0; i < 3; i++) {
        Exix = xi[i] - xAvg;
        Eyiy = yi[i] - yAvg;
        ExixTyiy += Exix * Eyiy;
        ExixSq += Exix * Exix;
      }
      double m = ExixTyiy / ExixSq;
      return m;
    }
    public double fullSlope() {
      double[] xi = {0, 1, 2, 3, 4};
      double[] yi = prices;
      double xAvg = (xi[0] + xi[1] + xi[2] + xi[3] + xi[4]) / 5;
      double yAvg = (yi[0] + yi[1] + yi[2] + yi[3] + yi[4]) / 5;
      double Exix = 0;
      double Eyiy = 0;
      double ExixTyiy = 0;
      double ExixSq = 0;
      for (int i = 0; i < 5; i++) {
        Exix = xi[i] - xAvg;
        Eyiy = yi[i] - yAvg;
        ExixTyiy += Exix * Eyiy;
        ExixSq += Exix * Exix;
      }
      double m = ExixTyiy / ExixSq;
      return m;
    }
  }

  public static void main(String[] args) {
      ArrayList<Stock> stocks = new ArrayList<Stock>();
      Scanner in = new Scanner(System.in);
      String temp = "";
      double m = in.nextDouble();
      int k = in.nextInt();
      int d = in.nextInt();
      in.nextLine(); // consume unused \n by int d
      for (int i = 0; i < k; i++) {
        temp = in.nextLine();
        stocks.add(new Stock(temp));
      }
      // GET THE LINEAR REGRESSION (USING OLS) OF EVERY STOCK UP TO THE HALFWAY POINT OF DAYS
      // GET THE LINEAR REGRESSION (USING OLS) OF EVERY STOCK OF THE ENTIRE LIST OF DAYS
      for (int i = 0; i < stocks.size(); i++) {
  //      System.out.println("Half slope is " + stocks.get(i).halfSlope());
  //      System.out.println("Full slope is " + stocks.get(i).fullSlope());
        for (int j = 0; j < stocks.get(i).prices.length/2 + 1; j++) {
    //      System.out.println(stocks.get(i).prices[j]);
        }
      }
      // GET A LIST OF MOST RAPIDLY RISING STOCKS BASED ON SLOPE
      // GET A LIST OF MOST RAPIDLY DECLINING STOCKS BASED ON SLOPE
      // IF WE CAN BUY THE MOST RAPIDLY DECLINING STOCKS, BUY THE TOP 4 STOCKS
      // IF WE CAN SELL THE MOST RAPIDLY RISING STOCKS, SELL THE TOP 4 STOCKS
      // ON THE FINAL DAY, SELL EVERYTHING
    }
}
