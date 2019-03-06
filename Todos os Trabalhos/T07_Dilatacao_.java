import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T07_Dilatacao_  implements PlugIn {
	public void run(String arg) {
		ImageProcessor imp = IJ.getProcessor();			
		ImagePlus novaImagem = IJ.createImage("NovaImagem", "8-bit white", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor imagemDois = novaImagem.getProcessor();	
		
		

		for(int x = 0; x < imp.getWidth(); x++) {
			for(int y = 0; y < imp.getHeight(); y++) {
				if (imp.getPixel(x, y) == 0){
					if((x != 0) && (y !=0) && (x != imp.getWidth()-1) && (y !=imp.getHeight()-1)){
						for(int xFoco = x-1; xFoco<=x+1; xFoco++) {
							for(int yFoco = y-1; yFoco<=y+1; yFoco++) {
								imagemDois.set(xFoco, yFoco, imp.getPixel(x, y));
							}
						}
					}
				}
			}
		}
		novaImagem.show();
	}
}