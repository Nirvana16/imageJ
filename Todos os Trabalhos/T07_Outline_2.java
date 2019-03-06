
import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T07_Outline_2  implements PlugIn {
	public void run(String arg) {
		ImageProcessor imp = IJ.getProcessor();			
		ImagePlus segundaIMG = IJ.createImage("segundaIMG", "8-bit white", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor segundoPRC = segundaIMG.getProcessor();	
		ImagePlus terceiraIMG = IJ.createImage("terceiraIMG", "8-bit white", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor terceiroPRC = terceiraIMG.getProcessor();
		
		
		for(int x = 1; x < imp.getWidth()-1; x++) {
			for(int y = 1; y < imp.getHeight()-1; y++) {
				if (imp.getPixel(x, y) == 0){
					if ((imp.getPixel(x-1, y) == 0) && (imp.getPixel(x+1, y) == 0) && (imp.getPixel(x, y-1) == 0) &&(imp.getPixel(x, y+1) == 0)){
						segundoPRC.set(x, y, imp.getPixel(x, y));
					}
				}
				if (segundoPRC.getPixel(x, y) !=imp.getPixel(x, y) ) {
					terceiroPRC.set(x, y,(imp.getPixel(x, y)- segundoPRC.getPixel(x, y)));				
				}
			}
		}
		terceiraIMG.show();
	}
}