import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.text.TextWindow;

public class T01_Inverter_  implements PlugIn {
	public void run(String arg) {
		ImageProcessor imp = IJ.getProcessor();	
		
		for(int x = 0; x < imp.getWidth(); x++) {
			for(int y = 0; y < imp.getHeight(); y++) {
				int pixel = imp.getPixel(x, y);
				inveter(imp, pixel, x, y);
			}			
		}		 	
	}
	
	public void inveter(ImageProcessor imp, int pixel, int x, int y) {		
		pixel = (255 - pixel);
		imp.putPixel(x, y, pixel);		
		ImagePlus img = IJ.getImage();
		img.updateAndDraw();
		//img.setProcessor(imp);
	}
}