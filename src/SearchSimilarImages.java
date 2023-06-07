import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.conversion.RGBToHSV;
import fr.unistra.pelican.algorithms.io.ImageLoader;

public class SearchSimilarImages {

    public static class SimilarImage {
        private String imageName;
        private double similarity;

        public SimilarImage(String imageName, double similarity) {
            this.imageName = imageName;
            this.similarity = similarity;
        }

        public String getImageName() {
            return imageName;
        }

        public double getSimilarity() {
            return similarity;
        }
    }

    public static void Search(Image analyseImage) {
        String baseImage = "src/images/broad";

        String imageR = "src/images/zepeck.jpg";

        Image queryImage = ImageLoader.exec(imageR);

        Image queryHistogram = computeHistogram(queryImage);

    
        List<SimilarImage> similarImages = new ArrayList<>();

     
        File baseFolder = new File(baseImage);
        File[] imageFiles = baseFolder.listFiles();

        if (imageFiles != null) {
            for (File imageFile : imageFiles) {
                if (!imageFile.getAbsolutePath().equals(imageR)) {
                  
                    Image baseImage = ImageLoader.exec(imageFile.getAbsolutePath());

               
                    Image denoisedImage = AnalyseImage.FiltreMedian(baseImage);

              
                    Image baseHistogram = computeHistogram(denoisedImage);

                    double similarity = calculateSimilarity(queryHistogram, baseHistogram);

                 
                    SimilarImage similarImage = new SimilarImage(imageFile.getName(), similarity);
                    similarImages.add(similarImage);
                }
            }
        }

        Collections.sort(similarImages, Comparator.comparingDouble(SimilarImage::getSimilarity).reversed());

      
        int numSimilarImages = Math.min(similarImages.size(), 10);
        for (int i = 0; i < numSimilarImages; i++) {
            SimilarImage similarImage = similarImages.get(i);
            String imageName = similarImage.getImageName();
            double similarity = similarImage.getSimilarity();
            System.out.println("Image: " + imageName + ", Similarity: " + similarity);
        }
    }

 
    private static double calculateSimilarity(Image hist1, Image hist2) {
        double[] histData1 = hist1.getImageData();
        double[] histData2 = hist2.getImageData();

        if (histData1.length != histData2.length) {
            throw new IllegalArgumentException("Les histogrammes doivent avoir la même taille !");
        }

        double sumOfSquaredDifferences = 0.0;
        for (int i = 0; i < histData1.length; i++) {
            sumOfSquaredDifferences += Math.pow(histData1[i] - histData2[i], 2);
        }

        return Math.sqrt(sumOfSquaredDifferences);
    }
}
