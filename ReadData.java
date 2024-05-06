import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.CategorySeries.CategorySeriesRenderStyle;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries.XYSeriesRenderStyle;
import org.knowm.xchart.style.Styler.LegendPosition;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class ReadData {
      ArrayList<Housing> houses = new ArrayList<Housing>();
      public ReadData(){
          try{
              File f = new File("Housing_Price_Data.csv");
              Scanner s = new Scanner(f);
              s.nextLine();
              while(s.hasNextLine()){
                  String row = s.nextLine();
                  System.out.println(row);
                  String[] rowArr = row.split(",");
                  double price = Integer.parseInt(rowArr[0]);
                  double area = Double.parseDouble(rowArr[1]);
                  houses.add(new Housing(area, price));
              }
              s.close();
              System.out.println(houses);
          }catch(Exception e){
              System.out.println("error reading file");
          }
  
      }
    


  public void plotLines(){
      double[] xData = getAreaData();
      double[] yData = getPriceData();

    
      // Create Chart
      XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
    
      // Show it
      new SwingWrapper(chart).displayChart();
  }
  
  public void scatter(ArrayList<double[]> data) {
 
    // Create Chart
    XYChart chart = new XYChartBuilder().width(800).height(600).build();
 
    // Customize Chart
    chart.getStyler().setDefaultSeriesRenderStyle(XYSeriesRenderStyle.Scatter);
    chart.getStyler().setChartTitleVisible(false);
    chart.getStyler().setLegendPosition(LegendPosition.InsideSW);
    chart.getStyler().setMarkerSize(16);
 
    // Series
    double[] xData = getAreaData();
    double[] yData = getPriceData();
    
    System.out.println(getAreaData().length);
    System.out.println(getPriceData().length);
    chart.addSeries("House Units", xData, yData);
    chart.addSeries("lsrl", data.get(0), data.get(1));

    new SwingWrapper(chart).displayChart();
 
  }

 public CategoryChart stickChart(){
    CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Stick").build();
 
    // Customize Chart
    chart.getStyler().setChartTitleVisible(true);
    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
    chart.getStyler().setDefaultSeriesRenderStyle(CategorySeriesRenderStyle.Stick);
 
    // Series
    double[] xData = getAreaData();
    double[] yData = getPriceData();
    chart.addSeries("Area to Price", xData, yData);

    return chart;
 }

 

  

  public double[] getAreaData(){
    double[] data = new double[houses.size()];
    for (int i = 0; i < 50; i++){
      data[i] = houses.get(i).getArea();
    }
    return data;
  }

  public double[] getPriceData(){
    double[] data = new double[houses.size()];
    for (int i = 0; i < 50; i++){
      data[i] = houses.get(i).getPrice();
    }
    return data;
  }


  public double deviation(double[] data){
    double deviation = 0.0;
    for (int i = 0; i < data.length; i++){
      deviation += Math.pow(data[i]-avgData(data), 2);
    }
    return Math.sqrt(deviation/data.length);
  }

  public double avgData(double[] data){
    double total = 0;
    for (int i = 0; i < data.length; i++){
      total += data[i];
    }
    return total/data.length;
  }
  // + value -> direct
  // - value -> indirect
  // 0 -> no relationship
  // summation((x-xMean)(y-yMean))

  public double getMin(double[] data){
        double min = data[0];
    for (int i = 0; i < data.length; i++) {
          if (data[i]<min){
            min = data[i];
          }
        }
    
    return min;
    }

  public double getMax(double[] data){

      double max = data[0];
      for (int i = 0; i < data.length; i++) {
            if (data[i]>max){
              max = data[i];
            }
          }
      
      return max;
      }
  
  public double covarieance(double[] xData, double[] yData){
    double summation = 0;
    for (int i=0; i < xData.length; i++){
      summation += (xData[i] - avgData(xData))*(yData[i] - avgData(yData));
    }
    return summation*1.0/(xData.length - 1);

  }

  public double corrCo(double[] xData, double[] yData){
    return covarieance(xData, yData)/(deviation(xData)*deviation(yData));
  }

  public double lsrlSlope(double[] xData, double[] yData){
    return (corrCo(xData, yData)*((deviation(yData))/(deviation(xData))));
  }

  public double lsrlIntercept(double[] xData, double[] yData){
    return (avgData(yData))-(avgData(xData)*lsrlSlope(xData, yData));
  }

  public ArrayList<double[]> generateLineData(double slope, double yint) {
    double[] xData = new double[16200];
    double[] yData = new double[16200];
    for(int x = 0; x < 16200; x++) {
        xData[x] = x;
        yData[x] = slope * x + yint;
    }
    ArrayList<double[]> data = new ArrayList<double[]>();
    data.add(xData);
    data.add(yData);

    return data;
}

  public void plotLsrl(ArrayList<double[]> data){
    double[] xData = data.get(0);
    double[] yData = data.get(1);
    
    XYChart chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
    
      // Show it
    new SwingWrapper(chart).displayChart();
  }

    public static void main(String[] args) {
        ReadData r = new ReadData();
        //r.scatter(r.generateLineData(r.lsrlSlope(r.getAreaData(), r.getPriceData()), r.lsrlIntercept(r.getAreaData(), r.getPriceData())));
        //r.stick();
        //r.plotLines();
        new SwingWrapper<CategoryChart>(r.stickChart()).displayChart();
        // r.plotLsrl(r.generateLineData(r.lsrlSlope(r.getSugarData(), r.getRatingData()), r.lsrlIntercept(r.getSugarData(), r.getRatingData())));
        // System.out.println(r.covarieance(r.getSugarData(), r.getRatingData()));
        // System.out.println(r.deviation(r.getSugarData()));
        // System.out.println(r.deviation(r.getRatingData()));
        // System.out.println(r.corrCo(r.getSugarData(), r.getRatingData()));
        // System.out.println(r.lsrlIntercept(r.getSugarData(), r.getRatingData()));
        // System.out.println(r.lsrlSlope(r.getSugarData(), r.getRatingData()));
    }
}
