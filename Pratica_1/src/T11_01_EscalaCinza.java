import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.process.LUT;

public class T11_01_EscalaCinza  implements PlugIn {
	public void run(String arg) {		
		ImageProcessor img = IJ.getProcessor();
		
		ImagePlus img1 = IJ.createImage("Imagem 1", "8-bit white", img.getWidth(), img.getHeight(), 1);
		ImageProcessor img1PRC = img1.getProcessor();
		
		ImagePlus img2 = IJ.createImage("Imagem 2", "8-bit white", img.getWidth(), img.getHeight(), 1);
		ImageProcessor img2PRC = img2.getProcessor();
		
		ImagePlus img3 = IJ.createImage("Imagem 3", "8-bit white", img.getWidth(), img.getHeight(), 1);
		ImageProcessor img3PRC = img3.getProcessor();
		
//		ImagePlus imgNova = IJ.createImage("Nova", "RGB white", img.getWidth(), img.getHeight(), 1);
//		ImageProcessor Nova = imgNova.getProcessor();
		


		int[] rgb = new int[3];
		int r;
		int g;
		int b;		
		
		int[] rgbNova = new int[3];
		
		double pesoR = 0.299;
		double pesoG = 0.587;
		double pesoB = 0.2114;
		
		double pesoR2 = 0.2125;
		double pesoG2 = 0.7154;
		double pesoB2 = 0.072;
		
		for(int x = 0; x < img.getWidth(); x++) {
			for(int y = 0; y < img.getHeight(); y++) {
				r = img.getPixel(x, y, rgb)[0];
				g = img.getPixel(x, y, rgb)[1];
				b = img.getPixel(x, y, rgb)[2];
				
				img1PRC.putPixel(x, y, ((r+g+b)/3));
				img2PRC.putPixelValue(x, y, ((pesoR*r)+(pesoG*g)+(pesoB*b)));
				img3PRC.putPixelValue(x, y, ((pesoR2*r)+(pesoG2*g)+(pesoB2*b)));
			}
		}
				
		img1.show();
		img2.show();
		img3.show();
	}
}