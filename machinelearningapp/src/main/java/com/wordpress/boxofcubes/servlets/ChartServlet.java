package com.wordpress.boxofcubes.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wordpress.boxofcubes.machinelearningapp.models.Data;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

@WebServlet(name = "chartServlet")
public class ChartServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("Entered chart...");

        // THIS SEEMS TO WORK TOO, BUT MIGHT AS WELL REQUEST ONLY ONCE no, got confused a few times, ugh
        /*if(request.getSession().getAttribute("data") != null){
            Data data = (Data)request.getSession().getAttribute("data");
            System.out.println("there was a data attribute");

            response.setContentType("image/png");
            OutputStream outputStream = response.getOutputStream();

            // Make the chart
            JFreeChart chart = getChart(data);
            ChartUtils.writeChartAsPNG(outputStream, chart, 700, 400); 
        }*/

        // THIS SEEMS TO BE WORKING!!!!!! NO - shows wrong set sometimes
        // IF I HIT TAB TOO MANY TIMES WHEN ENTERING DATA, WILL SWITCH TO SAMPLE LIFE SET!!!!!!!
        /*Data data = (Data)request.getSession().getAttribute("data");
        System.out.println("data name: "+data.getName());
        if(data != null){
            System.out.println("data object for servlet");

            response.setContentType("image/png");
            OutputStream outputStream = response.getOutputStream();

            // Make the chart
            JFreeChart chart = getChart(data);
            ChartUtils.writeChartAsPNG(outputStream, chart, 700, 400); 
        }else{
            System.out.println("NO data object for servlet");
        }*/
        // can't do this because I'll have to use it later!!! still doesn't work reliably anyway
        // actully, might - just not if I accidentally do more tabs on enter
        //request.getSession().removeAttribute("data");
        //System.out.println("Removed attribute from session "+data);

        // THIS SEEMS TO WORK, BUT IS CLUNKY - WOULD PREFER TO NOT DEAL WITH UUID
        // BUT SEEMS ONLY ONE THAT WORKS CONSISTENTLY!!!!!!!!!!!!!!
        // Get the data UUID from the URL
        String dataUUID = request.getParameter("dataUUID");
        if(dataUUID != null){
            System.out.println("There's a data object for the chart");
        
            // Reconstruct the data object associated with that UUID
            Data data = (Data)request.getSession().getAttribute(dataUUID);
            if(data != null){
                response.setContentType("image/png");
                OutputStream outputStream = response.getOutputStream();

                // Make the chart
                JFreeChart chart = getChart(data);
                ChartUtils.writeChartAsPNG(outputStream, chart, 700, 400); 
            }else{
                System.out.println("uuid was there but Data object not found");
            }   
        }else{
            System.out.println("There's NO data object for the chart!!!!!!!!!");
        }

        request.getSession().removeAttribute(dataUUID);
        System.out.println("Removed UUID from session "+dataUUID);
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