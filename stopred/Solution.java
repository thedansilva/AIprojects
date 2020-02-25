import java.io.*;
import java.util.*;
import java.lang.*;
import java.nio.file.*;

public class Solution {
  public static int days = 0;
  public static class Stock implements Comparable<Stock> {
    public String name = " ";
    public int owned = 0;
    public double[] prices = new double [5];
    public int stockNum;
    public double priceBought;
    public Stock(String n) {
      String[] assign = n.split(" "); // delimit stock data by spaces
      name = assign[0]; // first value is the name of the stock
      owned = Integer.parseInt(assign[1]); // second value is how many are owned
      for (int i = 0; i < 5; i++) {
        prices[i] = Double.parseDouble(assign[i + 2]); // assign prices
      }
      stockNum = Integer.parseInt(assign[7]); // stock Number for tracking purposes.
      priceBought = prices[4];
    }
    public double fullSlope() { // uses OLS method to get a full slope
      double[] xi = {1, 2, 3, 4, 5};
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
    public void setOwned(int newOwned) {
      this.owned = newOwned;
    }
    public int getStockNum() {
      return this.stockNum;
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
        temp = temp + " " + i;
        stocks.add(new Stock(temp));
      }
// use file archive to fill
      // COPYING TO FILES AND STUFF
      // /*
      File archive = new File("archive.txt");
      int justmade = 0;
      if (!archive.exists()) {
        try {
          archive.createNewFile();
        } catch ( IOException e ) {}
      } else {
        try {
          FileInputStream filestream = new FileInputStream(archive);
          BufferedReader br = new BufferedReader(new InputStreamReader(filestream));
      //    BufferedWriter bw = new BufferedWriter(new FileWriter(archive));
          //    bw.write(stocks.get(x).prices.get(y) + ""); // write initial values on day 1.
          //    bw.write(stocks.get(x).prices.get(y) + " "); // write initial values on day 1.
          String str = "";
          String name = "";
          int stockNum = 0;
          int owned = 0;
          double priceBought = 0.0;
          while ((str = br.readLine()) != null) {
            String[] assign = str.split(" "); // delimit stock data by spaces
            name = assign[0];
            stockNum = Integer.parseInt(assign[1]);
            owned = Integer.parseInt(assign[2]);
            priceBought = Double.parseDouble(assign[3]);
            stocks.get(stockNum).priceBought = priceBought;
          }
          br.close();
        } catch ( IOException e ) {}
      }
        /* COPYING TO FILES AND STUFF */

      Collections.sort(stocks); // get a list of the lowest slopes to highest slopes.
      Collections.reverse(stocks); // reverse. now we have a list of highest slopes to lowest slopes.

      for (int i = 0; i < stocks.size(); i++) {
        //System.out.println(stocks.get(i).name + " " + Arrays.toString(stocks.get(i).prices) + ": " + stocks.get(i).fullSlope());
        // System.out.println(stocks.get(i).name + " " + stocks.get(i).stockNum);
      }
      if (d == 1) { //one day left, sell everything
        for (int i = 0; i < stocks.size(); i++) {
          if (stocks.get(i).owned > 0) {
            transactions.add(stocks.get(i).name + " SELL " + stocks.get(i).owned);
            stocks.get(i).setOwned(0);
          }
        }
      } else {
        for (int i = 0; i < 4; i++) { // get the top 4 stocks
          if (stocks.get(i).owned > 0) {
            // if our stock starts to fall in value, panic sell all of them
            transactions.add(stocks.get(i).name + " SELL " + stocks.get(i).owned);
            stocks.get(i).setOwned(0);
          }
        }

        for (int i = 4; i < stocks.size() - 1; i++) {
          if (stocks.get(i).prices[4] - stocks.get(i).priceBought >= 25 && stocks.get(i).owned > 0) {
            // if any other owned stocks show a significant price spike, sell all of them as well
            transactions.add(stocks.get(i).name + " SELL " + stocks.get(i).owned);
            stocks.get(i).setOwned(0);
          }
        }

          Collections.reverse(stocks); // reverse again. now we have regular list of lowest to high.
          if (stocks.get(0).owned <= 0 && m >= stocks.get(0).prices[4]) {
            for (double i = m; i > stocks.get(0).prices[4]; i -= stocks.get(0).prices[4]) { // continue to add lowest stocks to buy until we can't buy anymore
              lowestStock++;
            }
            transactions.add(stocks.get(0).name + " BUY " + lowestStock);
            stocks.get(0).setOwned(lowestStock);
          }
      }
        System.out.println(transactions.size());
        for (int i = 0; i < transactions.size(); i++) {
          System.out.println(transactions.get(i));
        }

        try {
          FileInputStream filestream = new FileInputStream(archive);
          BufferedWriter bw = new BufferedWriter(new FileWriter(archive));
              //bw.write(stocks.get(x).prices.get(y) + ""); // write initial values on day 1.
              //bw.write(stocks.get(x).prices.get(y) + " "); // write initial values on day 1.
              for(int x = 0; x < stocks.size(); x++) {
                if (stocks.get(x).owned > 0) {
                bw.write(stocks.get(x).name + " " + stocks.get(x).stockNum + " " + stocks.get(x).owned + " " + stocks.get(x).priceBought);
                bw.newLine();
              }
              }
              bw.close();
        } catch (IOException e) { System.out.println("???");}
    }
}
