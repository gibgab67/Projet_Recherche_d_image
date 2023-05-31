import fr.unistra.pelican.Image;

import fr.unistra.pelican.algorithms.io.ImageLoader;
import fr.unistra.pelican.algorithms.visualisation.Viewer2D;

public class Main {

	public static void main(String[] args) {
		Image img = ImageLoader.exec("img_Test/lenaB.png");
		
		//AnalyseImage.constructHistogramme(img);
		
		System.out.println(AnalyseImage.constructHistogramme(img));
		
	}

}
