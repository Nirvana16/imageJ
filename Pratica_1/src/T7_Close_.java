import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T7_Close_  implements PlugIn {
	public void run(String arg) {
		ImageProcessor imp = IJ.getProcessor();			
		ImagePlus segundaIMG = IJ.createImage("segundaIMG", "8-bit white", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor segundoPRC = segundaIMG.getProcessor();	
		ImagePlus terceiraIMG = IJ.createImage("terceiraIMG", "8-bit white", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor terceiroPRC = terceiraIMG.getProcessor();
		
		
		//Dilatação
		for(int x = 0; x < imp.getWidth(); x++) {
			for(int y = 0; y < imp.getHeight(); y++) {
				if (imp.getPixel(x, y) == 0){
					for(int xFoco = x-1; xFoco<=x+1; xFoco++) {
						for(int yFoco = y-1; yFoco<=y+1; yFoco++) {
							segundoPRC.set(xFoco, yFoco, imp.getPixel(x, y));
						}
					}
				}
			}
		}
		//Erosão
		for(int x = 1; x < imp.getWidth()-1; x++) {
			for(int y = 1; y < imp.getHeight()-1; y++) {
				if (segundoPRC.getPixel(x, y) == 0){
					if ((segundoPRC.getPixel(x-1, y) == 0) && (segundoPRC.getPixel(x+1, y) == 0) && (segundoPRC.getPixel(x, y-1) == 0) &&(segundoPRC.getPixel(x, y+1) == 0)){
						terceiroPRC.set(x, y, segundoPRC.getPixel(x, y));
					}
				}
			}
		}
		terceiraIMG.show();
	}
}