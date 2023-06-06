import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import fr.unistra.pelican.Image;
import fr.unistra.pelican.util.Color;

public class Histogramme {
	double[][] histogramme;
	int nbCannaux;
	int nbPixel;

	public Histogramme(Image input) {
		super();
		int dimX = input.getXDim();
		int dimY = input.getYDim();
		int dimB = input.getBDim();
		
		this.nbPixel = dimX * dimY;
		
		double[][] result = new double[dimB][256];
		
	    for (int y = 0; y < dimY; y++) {
	        for (int x = 0; x < dimX; x++) {
	        	for (int b = 0; b < dimB; b++) {
		        	result[b][input.getPixelByte(x, y, 0, 0, b)]++;
		        }
	        }
	    }
	    this.nbCannaux = dimB;
		this.histogramme = result;
	}
	public Histogramme(double[][] histogrammeInput) {
		this.histogramme = histogrammeInput;
		this.nbCannaux = histogrammeInput.length;
		this.nbPixel = histogrammeInput.length * histogrammeInput[0].length; 
	}
	
	
	public void display() {
		for (int i = 0; i < this.histogramme.length; i++) {
			displayHistogram(this.histogramme[i], "histogramme", i);
		}
	}
	
	private void displayHistogram(double[] histogram, String name, int canal) {
		XYSeries series = new XYSeries("Nombre de pixels");
        for(int i = 0; i < histogram.length; i++) {
            series.add(new Double(i), new Double(histogram[i]));
        }
        XYSeriesCollection seriesColl =  new XYSeriesCollection(series);
		JFreeChart freeChart = ChartFactory.createXYBarChart(
	            "Histogramme de l'image",
	            "Canal N°" + canal,
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
	
	
	public void discrete() {
		int nbColumn = 10;
		int reduceTo = (this.histogramme[0].length-1)/nbColumn;
		double[][] histoDiscrete = new double[this.nbCannaux][nbColumn];
		
		for (int bHisto=0; bHisto < this.nbCannaux; bHisto++) {
			
			for(int column = 0; column < nbColumn; column++) {
				int start = reduceTo * column;
				int end = start + reduceTo;
				int moyenne = 0;
				for (int elem = start; elem <= end; elem++) {
					moyenne += this.histogramme[bHisto][elem];
				}
				moyenne /= reduceTo;
				histoDiscrete[bHisto][column] = moyenne;
			}
			
			
		}
		/*
		for (int bHisto=0; bHisto < histogramme.length; bHisto++) {
			int unit = this.histogramme[0].length/10;
			int moyenne = 0;
			
			for (int elem = 0; elem < histoDiscrete[0].length; elem++) {
				for(int id = elem * unit; id < unit; id += unit) {
					for(int j = id; j < id + unit; j++ ) {
						moyenne += this.histogramme[bHisto][j];
					}
					moyenne /= unit;
					
					
				}
				
				histoDiscrete[bHisto][elem] = moyenne;
			}
			
		}*/
		
		this.histogramme = histoDiscrete;
	}
	
	public Histogramme normalize() {
		double[][] out = new double[this.nbCannaux][this.histogramme[0].length];
		System.out.println();
		
		for (int bHisto=0; bHisto < this.nbCannaux; bHisto++) {
			for(int col = 0; col < this.histogramme[bHisto].length; col++) {
				out[bHisto][col] = this.histogramme[bHisto][col] / this.nbPixel;
			}

		}
		
		return new Histogramme(out);
	}
	
}
