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
    @Override
    public int compareTo(Stock s2) {
      return new Double(this.fullSlope()).compareTo(s2.fullSlope());
    }
}

  public static void main(String[] args) {
      ArrayList<Stock> stocks = new ArrayList<Stock>();
      ArrayList<String> transactions = new ArrayList<String>();
      Scanner in = new Scanner(System.in);
      String temp = "";
      int lowestStock = 0;
      double m = in.nextDouble();
      int k = in.nextInt();
      int d = in.nextInt();
      in.nextLine(); // consume unused \n by int d. was causing errors in the next line.
      for (int i = 0; i < k; i++) {
        temp = in.nextLine();
        stocks.add(new Stock(temp));
      }
      // GET THE LINEAR REGRESSION (USING OLS) OF EVERY STOCK OF THE ENTIRE LIST OF DAYS
      // buy low, sell high approach:
      // whatever stocks has the highest increase in price, sell it.
      // whatever stock has the highest decrease in price, buy a lot of them.
      /*
      Sell all stocks we have for the one with the highest spike.
Buy all stocks we can for the one with the hardest fall – and hope its price increases in the next days.
*/
      Collections.sort(stocks); // get a list of the lowest slopes to highest slopes.
      Collections.reverse(stocks); // reverse. now we have a list of highest slopes to lowest slopes.
      for (int i = 0; i < stocks.size(); i++) {
      //  System.out.println(stocks.get(i).name + " " + Arrays.toString(stocks.get(i).prices) + ": " + stocks.get(i).fullSlope());
      }
      if (d == 1) { //one day left, sell everything
        for (int i = 0; i < stocks.size(); i++) {
          if (stocks.get(i).owned > 0) {
            transactions.add(stocks.get(i).name + " SELL " + stocks.get(i).owned);
          }
        }
      } else {
        for (int i = 0; i < 3; i++) { // only get the top 3 stocks
          if (stocks.get(i).owned > 0) {
            // if our stock starts to fall in value, panic sell all of them
            transactions.add(stocks.get(i).name + " SELL " + stocks.get(i).owned);
          }
        }
        for (int i = 3; i < stocks.size(); i++) {
          if (stocks.get(i).prices[4] - stocks.get(i).prices[3] >= 15 && stocks.get(i).owned > 0) {
            // if any other owned stocks show a significant price spike, sell all of them as well
            transactions.add(stocks.get(i).name + " SELL " + stocks.get(i).owned);
          }
        }
          Collections.reverse(stocks); // reverse again. now we have regular list of lowest to high.
          if (stocks.get(0).owned <= 0 && m >= stocks.get(0).prices[4]) {
            for (double i = m; i > stocks.get(0).prices[4]; i -= stocks.get(0).prices[4]) { // continue to add lowest stocks to buy until we can't buy anymore
              lowestStock++;
            }
            transactions.add(stocks.get(0).name + " BUY " + lowestStock);
          }
        //  if (stocks.get(i).owned == 0 && !(stocks.get(i).risingStock()) && (stocks.get(i).prices[4] * 2 < m)) {
          // if there's a stock we don't have and it's nosediving in value, capitalize on that and buy 2 of them if possible
        //    transactions.add(stocks.get(i).name + " BUY " + 2);
          //  m -= stocks.get(i).prices[4] * 2;
        //  }
        //}
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
