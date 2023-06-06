import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.io.ImageLoader;
import fr.unistra.pelican.util.Color;

public class Main {

	public static void main(String[] args) {
		Image img1 = ImageLoader.exec("img_Test/modifHisto.png");
		Image img2 = ImageLoader.exec("img_Test/sobRGB.png");
		
		//AnalyseImage.constructHistogramme(img);
		
		Histogramme histo1 = AnalyseImage.constructHistogramme(img1);
		Histogramme histo2 = AnalyseImage.constructHistogramme(img2);
		
		System.out.println(AnalyseImage.distanceEuclidienneHisto(histo1, histo2, 0));
	}
	
	
	public static void displayHistogram(double[] histogram, String name, int canal) {
		XYSeries series = new XYSeries("Nombre de pixels");
        for(int i = 0; i < histogram.length; i++) {
            series.add(new Double(i), new Double(histogram[i]));
        }
        XYSeriesCollection seriesColl =  new XYSeriesCollection(series);
		JFreeChart freeChart = ChartFactory.createXYBarChart(
	            "Histogramme de l'image",
	            "Canal N�" + canal,
	            false,
	            "Nombre de pixels",
	            seriesColl,
	            PlotOrientation.VERTICAL,
	            true,
	            false,
	            false);
	    freeChart.setBackgroundPaint(Color.white);
	    
        XYPlot xyplot = freeChart.getXYPlot();

        xyplot.setBackgroundPaint(Color.lightGray);
        xyplot.setRangeGridlinePaint(Color.white);
        NumberAxis axis = (NumberAxis) xyplot.getDomainAxis();

        axis.setLowerMargin(0);
        axis.setUpperMargin(0);

        // Display the frame
        String frameName = name != null ? "Histogram: " + name : "Histogram";
        ChartFrame frame = new ChartFrame(frameName, freeChart);
        frame.pack();
        frame.setVisible(true);
    }
	
	
    
    
}
