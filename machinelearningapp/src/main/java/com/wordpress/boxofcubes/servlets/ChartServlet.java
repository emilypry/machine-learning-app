package com.wordpress.boxofcubes.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.awt.Color;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

@WebServlet(name = "chartServlet")
public class ChartServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        // Get Data UUID from the URL
        String dataUUID = request.getParameter("dataUUID");
        if(dataUUID != null){
            // Get the Data object identified by the UUID from the session
            Data data = (Data)request.getSession().getAttribute(dataUUID);
            if(data != null){
                // Get the predictions from the session
                double[] predictions = (double[])request.getSession().getAttribute("predictions");

                // If there are predictions, show the chart with predictions
                if(predictions != null){
                    response.setContentType("image/png");
                    OutputStream outputStream = response.getOutputStream();
        
                    // Make the chart
                    JFreeChart chart = getChartWithPredictions(data, predictions);
                    ChartUtils.writeChartAsPNG(outputStream, chart, 700, 400); 
    
                    // Remove the predictions from the session
                    request.getSession().removeAttribute("predictions");
                }
                // If there aren't predictions, show the chart with just the data points
                else{
                    response.setContentType("image/png");
                    OutputStream outputStream = response.getOutputStream();
    
                    // Make the chart
                    JFreeChart chart = getChart(data);
                    ChartUtils.writeChartAsPNG(outputStream, chart, 700, 400); 
                }
            }

            // Remove dataUUID from session
            request.getSession().removeAttribute(dataUUID);
        }
    }

    private XYDataset getDataset(Data data){
        XYSeriesCollection dataset = new XYSeriesCollection();

        // Get the data values from the Data object
        XYSeries pairs = new XYSeries(data.getItemLabel());
        for(int i=0; i<data.getX().length; i++){
          pairs.add(data.getX()[i], data.getY()[i]);
        }
        dataset.addSeries(pairs);

        return (XYDataset)dataset;
    }

    private JFreeChart getChart(Data data){
        // Get the data
        XYDataset dataset = getDataset(data);

        // Make the chart
        JFreeChart chart = ChartFactory.createScatterPlot(data.getName(), data.getXLabel(),
        data.getYLabel(), dataset);

        // Set the background color
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(240,240,240));
        plot.setChart(chart);

        return chart;   
    }

    private JFreeChart getChartWithPredictions(Data data, double[] predictions){
        // Get the data
        XYDataset dataset = getDataset(data);
        
        // Make the predictions data
        XYSeriesCollection preds = new XYSeriesCollection();
        XYSeries pairs = new XYSeries("Model Predictions");
        pairs.add(predictions[0], predictions[1]);
        pairs.add(predictions[2], predictions[3]);
        preds.addSeries(pairs);
        XYDataset modelPreds = (XYDataset)preds;

        // Set the renderers
        XYItemRenderer renderer1 = new XYLineAndShapeRenderer(false, true); // points
        XYItemRenderer renderer2 = new XYLineAndShapeRenderer(true, false); // line

        // Create chart
        JFreeChart chart = ChartFactory.createScatterPlot(data.getName(), data.getXLabel(),
        data.getYLabel(), dataset);
        
        // Create the plot, add the datasets to it, set the background color
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setDataset(0, dataset); 
        plot.setDataset(1, modelPreds); 
        plot.setRenderer(0, renderer1);
        plot.setRenderer(1, renderer2);
        plot.setBackgroundPaint(new Color(240,240,240));
        plot.setChart(chart);

        return chart;
    }
}