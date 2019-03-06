import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.process.LUT;

public class T11_02_TrocaTroca  implements PlugIn {
	public void run(String arg) {		
		ImageProcessor img = IJ.getProcessor();
		
//		ImagePlus img1 = IJ.createImage("Imagem 1", "8-bit white", img.getWidth(), img.getHeight(), 1);
//		ImageProcessor img1PRC = img1.getProcessor();
//		
//		ImagePlus img2 = IJ.createImage("Imagem 2", "8-bit white", img.getWidth(), img.getHeight(), 1);
//		ImageProcessor img2PRC = img2.getProcessor();
//		
//		ImagePlus img3 = IJ.createImage("Imagem 3", "8-bit white", img.getWidth(), img.getHeight(), 1);
//		ImageProcessor img3PRC = img3.getProcessor();
//		
		ImagePlus imgNova = IJ.createImage("GBR", "RGB white", img.getWidth(), img.getHeight(), 1);
		ImageProcessor nova = imgNova.getProcessor();
		
		ImagePlus imgNova2 = IJ.createImage("BGR", "RGB white", img.getWidth(), img.getHeight(), 1);
		ImageProcessor nova2 = imgNova2.getProcessor();
		
		


		int[] rgb = new int[3];
		
		int[] gbr = new int[3];
		int[] bgr = new int[3];
		

		for(int x = 0; x < img.getWidth(); x++) {
			for(int y = 0; y < img.getHeight(); y++) {
				gbr[1] = img.getPixel(x, y, rgb)[0];
				gbr[2] = img.getPixel(x, y, rgb)[1];
				gbr[0] = img.getPixel(x, y, rgb)[2];	
				
				nova.putPixel(x, y, gbr);
				
				bgr[2] = img.getPixel(x, y, rgb)[0];
				bgr[0] = img.getPixel(x, y, rgb)[1];
				bgr[1] = img.getPixel(x, y, rgb)[2];
				
				
				nova2.putPixel(x, y, bgr);
			}
		}
				
		imgNova.show();
		imgNova2.show();
	}
}