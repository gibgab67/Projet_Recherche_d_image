import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.unistra.pelican.ByteImage;
import fr.unistra.pelican.Image;

public class AnalyseImage {
	
	public static Image FiltreMedian(Image input) {
		
		int width = input.getXDim();
	    int height = input.getYDim();

	    ByteImage newImgMedian = new ByteImage(width, height, 1, 1, 3); 

	    for (int y = 0; y < height; y++) {
	        for (int x = 0; x < width; x++) {
	            for (int b = 0; b < 3; b++) { 
	                List<Integer> values = new ArrayList<>();

	                for (int dy = -1; dy <= 1; dy++) {
	                    for (int dx = -1; dx <= 1; dx++) {
	                        if (x + dx >= 0 && x + dx < width && y + dy >= 0 && y + dy < height) {
	                            int valeurVoisin = input.getPixelXYBByte(x + dx, y + dy, b);
	                            values.add(valeurVoisin);
	                        }
	                    }
	                }

	                Collections.sort(values);
	                int valeurMedian = values.get((values.size() + 1) / 2 - 1);
	                newImgMedian.setPixelXYBByte(x, y, b, valeurMedian);
	            }
	        }
	    }

	    return newImgMedian;
	    //Viewer2D.exec(image);   
	}
}