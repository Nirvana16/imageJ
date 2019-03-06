import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T07_Erosao_  implements PlugIn {
	public void run(String arg) {
		ImageProcessor imp = IJ.getProcessor();			
		ImagePlus novaImagem = IJ.createImage("NovaImagem", "8-bit white", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor imagemDois = novaImagem.getProcessor();	
		
		for(int x = 1; x < imp.getWidth()-1; x++) {
			for(int y = 1; y < imp.getHeight()-1; y++) {
				if (imp.getPixel(x, y) == 0){
					if ((imp.getPixel(x-1, y) == 0) && (imp.getPixel(x+1, y) == 0) && (imp.getPixel(x, y-1) == 0) &&(imp.getPixel(x, y+1) == 0)){
						imagemDois.set(x, y, imp.getPixel(x, y));
					}
				}
			}
		}
		novaImagem.show();
	}
}