import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.unistra.pelican.ByteImage;
import fr.unistra.pelican.Image;

public class AnalyseImage {
	
	public static Image FiltreMedian(Image input) {
		
	    int width = input.getXDim();
	    int height = input.getYDim();

	    ByteImage newImgMedian=new ByteImage(width,height,1,1,1);


	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            
	            int tmp=0;
	            List<Integer> values = new ArrayList<>();
	           

	            for (int dy = -1; dy <= 1; dy++) {
	                for (int dx = -1; dx <= 1; dx++) {
	                    if (x + dx >= 0 && x + dx < width && y + dy >= 0 && y + dy < height) {
	                        int valeurVoisin = input.getPixelXYBByte(x+dx, y+dy,0);
	                     
	                        values.add(valeurVoisin);
	                    }
	                }
	            }
	            
	            Collections.sort(values);

	            int valeurMedian = values.get((values.size() + 1) / 2 - 1);
	            
	            
	            System.out.println("Valeur avant : "+tmp+" NOuvelle  Valeur : "+valeurMedian);
	           
	            newImgMedian.setPixelXYBByte(x, y,0,valeurMedian);
	          
	        }
	    }
	    return newImgMedian;
	    //Viewer2D.exec(image);   
	}
	
	public static Histogramme constructHistogramme (Image input) {
		return new Histogramme(input);
	}
	
	public static double distanceEuclidienneHisto(Histogramme histo1, Histogramme histo2, int canal) {
		double somme = 0;
		for(int i = 0; i < histo1.histogramme.length; i++) {
			somme += Math.pow((histo1.getHistogramme()[canal][i] - histo2.getHistogramme()[canal][i]), 2);
		}
		
		double result = Math.sqrt(somme);
		
		return result;
	}
}
