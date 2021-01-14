package com.wordpress.boxofcubes.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

@WebServlet(name = "chartServlet")
public class ChartServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<p>Testing servlet</p>");

        // request data object id
        // request model object id
    }

    private JFreeChart getChart(Data data){
        XYSeriesCollection dataset = new XYSeriesCollection();

        // Get the data from the Data object
        XYSeries pairs = new XYSeries(data.getItemLabel());
        for(int i=0; i<data.getX().length; i++){
          pairs.add(data.getX()[i], data.getY()[i]);
        }
        dataset.addSeries(pairs);

        // Make the chart
        JFreeChart chart = ChartFactory.createScatterPlot(data.getName(), data.getXLabel(),
        data.getYLabel(), (XYDataset)dataset);
        
        return chart;   
    }
}