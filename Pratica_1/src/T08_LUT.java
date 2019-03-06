import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.process.LUT;

public class T08_LUT  implements PlugIn {
	public void run(String arg) {		
		ImageProcessor img = IJ.getProcessor();
		
		ImagePlus imgRED = IJ.createImage("red", "8-bit white", img.getWidth(), img.getHeight(), 1);
		ImageProcessor redPRC = imgRED.getProcessor();
		ImagePlus imgGreen = IJ.createImage("green", "8-bit white", img.getWidth(), img.getHeight(), 1);
		ImageProcessor greenPRC = imgGreen.getProcessor();
		ImagePlus imgBlue = IJ.createImage("blue", "8-bit white", img.getWidth(), img.getHeight(), 1);
		ImageProcessor bluePRC = imgBlue.getProcessor();
		ImagePlus imgNova = IJ.createImage("Nova", "RGB white", img.getWidth(), img.getHeight(), 1);
		ImageProcessor Nova = imgNova.getProcessor();
		


		int[] rgb = new int[3];
		int r;
		int g;
		int b;		
		int[] rgbNova = new int[3];
		
		for(int x = 0; x < img.getWidth(); x++) {
			for(int y = 0; y < img.getHeight(); y++) {
				r = img.getPixel(x, y, rgb)[0];
				g = img.getPixel(x, y, rgb)[1];
				b = img.getPixel(x, y, rgb)[2];
				
				redPRC.putPixel(x, y, r);
				greenPRC.putPixel(x, y, g);
				bluePRC.putPixel(x, y, b);
			}
		}
		
		byte[] byteRed = new byte[256];
		byte[] byteBlue = new byte[256];
		byte[] byteGreen = new byte[256];
		
		for(int x=0;x<=255;x++) {
			byteRed[x] = (byte) x;
			byteGreen[x] = (byte) 0;
			byteBlue[x] = (byte) 0;
		}
		
		LUT vermelho = new LUT(byteRed, byteGreen, byteBlue);
		redPRC.setLut(vermelho);
		
		for(int x=0;x<=255;x++) {
			byteRed[x] = (byte) 0;
			byteGreen[x] = (byte) x;
			byteBlue[x] = (byte) 0;
		}
		
		LUT verde = new LUT(byteRed, byteGreen, byteBlue);
		greenPRC.setLut(verde);
		
		for(int x=0;x<=255;x++) {
			byteRed[x] = (byte) 0;
			byteGreen[x] = (byte) 0;
			byteBlue[x] = (byte) x;
		}
		
		LUT azul = new LUT(byteRed, byteGreen, byteBlue);
		bluePRC.setLut(azul);
		
		for(int x = 0; x < img.getWidth(); x++) {
			for(int y = 0; y < img.getHeight(); y++) {
				rgbNova[0] = redPRC.get(x, y);
				rgbNova[1] = greenPRC.get(x, y);
				rgbNova[2] = bluePRC.get(x, y);				
				Nova.putPixel(x, y, rgbNova);
			}
		}
		
		imgRED.show();
		imgGreen.show();
		imgBlue.show();
		imgNova.show();
	}
}