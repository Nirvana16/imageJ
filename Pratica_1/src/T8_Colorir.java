import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T8_Colorir  implements PlugIn {
	public void run(String arg) {
		//Pegar processor
		ImageProcessor imp = IJ.getProcessor();	
		//ImagePlus img = IJ.getImage();		
		
		//criar canal red
		ImagePlus imgRED = IJ.createImage("imgRED", "RGB white", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor redPRC = imgRED.getProcessor();	
		
		//criar canal Green
		ImagePlus imgGREEN = IJ.createImage("imgGREEN", "RGB white", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor greenPRC = imgGREEN.getProcessor();
		
		//criar canal blue
		ImagePlus imgBLUE = IJ.createImage("imgBLUE", "RGB white", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor bluePRC = imgBLUE.getProcessor();
		
		//cria nova imagem
		ImagePlus imgRGB = IJ.createImage("imgRGB", "RGB white", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor rgbPRC = imgRGB.getProcessor();

		int[] rgb = new int[3];
		int[] r = new int[] {0,0,0};		
		int[] g = new int[] {0,0,0};	
		int[] b = new int[] {0,0,0};
		int[] rgb2 = new int[] {0,0,0};
				
		
		for(int x = 0; x < imp.getWidth(); x++) {
			for(int y = 0; y < imp.getHeight(); y++) {
				imp.getPixel(x,y,rgb);
				
				r[0] = rgb[0];
				redPRC.putPixel(x , y, r);
				
				g[1] = rgb[1];
				greenPRC.putPixel(x , y, g);
				
				b[2] = rgb[2];
				bluePRC.putPixel(x , y, b);	
				
				rgb2[0] = r[0];
				rgb2[1] = g[1];
				rgb2[2] = b[2];				
				rgbPRC.putPixel(x, y, rgb2);				

			}
		}
		imgRED.show();
		imgGREEN.show();
		imgBLUE.show();	
		imgRGB.show();
	}	
}