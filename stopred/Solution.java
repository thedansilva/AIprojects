import java.io.*;
import java.util.*;
import java.lang.*;
// idea: half window approach where slope of stocks are evaluated at time positions 3 and time positions 5
// and if the slope is lower in 2nd eval, sell. if higher, buy.
public class Solution {
  public static class Stock implements Comparable<Stock> {
    public String name = " ";
    public int owned = 0;
    public double[] prices = new double [5];
    public Stock(String n) {
      String[] assign = n.split(" "); // delimit stock data by spaces
      name = assign[0]; // first value is the name of the stock
      owned = Integer.parseInt(assign[1]); // second value is how many are owned
      for (int i = 0; i < 5; i++) {
        prices[i] = Double.parseDouble(assign[i + 2]); // assign prices
      }
    }
    public double halfSlope() { // uses OLS method to get a slope of half the data
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
    public double fullSlope() { // uses OLS method to get a full slope
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
    public double slopeChange() {
      return this.fullSlope() - this.halfSlope();
    }
    @Override
    public int compareTo(Stock s2) {
      return new Double(this.slopeChange()).compareTo(s2.slopeChange());
    }
    public boolean risingStock() {
      if (this.fullSlope() > this.halfSlope()) {
        return true;
      }
      return false;
    }
}

  public static void main(String[] args) {
      ArrayList<Stock> stocks = new ArrayList<Stock>();
      ArrayList<String> transactions = new ArrayList<String>();
      Scanner in = new Scanner(System.in);
      String temp = "";
      double m = in.nextDouble();
      int k = in.nextInt();
      int d = in.nextInt();
      in.nextLine(); // consume unused \n by int d. was causing errors in the next line.
      for (int i = 0; i < k; i++) {
        temp = in.nextLine();
        stocks.add(new Stock(temp));
      }
      // GET THE LINEAR REGRESSION (USING OLS) OF EVERY STOCK UP TO THE HALFWAY POINT OF DAYS
      // GET THE LINEAR REGRESSION (USING OLS) OF EVERY STOCK OF THE ENTIRE LIST OF DAYS
      // GET A LIST OF MOST RAPIDLY RISING STOCKS BASED ON SLOPE
      Collections.sort(stocks);
      for (int i = 0; i < stocks.size(); i++) {
      //  System.out.println(stocks.get(i).name + " " + Arrays.toString(stocks.get(i).prices) + ": " + stocks.get(i).slopeChange() + " " + stocks.get(i).risingStock());
      }
      if (d == 1) { //one day left, sell everything
        for (int i = 0; i < stocks.size(); i++) {
          if (stocks.get(i).owned > 0) {
            transactions.add(stocks.get(i).name + " SELL " + stocks.get(i).owned);
          }
        }
      } else {
        for (int i = 0; i < stocks.size(); i++) {
          if (stocks.get(i).owned > 0 && !(stocks.get(i).risingStock())) {
            // if our stock starts to fall in value, panic sell all of them
            transactions.add(stocks.get(i).name + " SELL " + stocks.get(i).owned);
          }
          if (stocks.get(i).owned == 0 && !(stocks.get(i).risingStock()) && (stocks.get(i).prices[4] * 2 < m)) {
          // if there's a stock we don't have and it's nosediving in value, capitalize on that and buy 2 of them if possible
            transactions.add(stocks.get(i).name + " BUY " + 2);
            m -= stocks.get(i).prices[4] * 2;
          }
        }
      }
      System.out.println(transactions.size());
      for (int i = 0; i < transactions.size(); i++) {
          System.out.println(transactions.get(i));
    }
      // GET A LIST OF MOST RAPIDLY DECLINING STOCKS BASED ON SLOPE
      // SELL STOCKS THAT ARE DECLINING
      // maybe only go every other turn (odd or even days) to better observe stocks?
      // IF WE CAN BUY THE MOST RAPIDLY DECLINING STOCKS, BUY THE TOP 4 STOCKS
      // IF WE CAN SELL THE MOST RAPIDLY RISING STOCKS, SELL THE TOP 4 STOCKS
    }
}
