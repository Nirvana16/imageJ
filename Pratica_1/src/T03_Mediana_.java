import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T03_Mediana_  implements PlugIn {
	public void run(String arg) {
		ImageProcessor imp = IJ.getProcessor();	
		
		ImagePlus novaImagem = IJ.createImage("NovaImagem", "8-bit black", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor imagemMediana = novaImagem.getProcessor();	
		
		List<Integer> mediana = new ArrayList<Integer>();			
		
		for(int x = 1; x < imp.getWidth()-1; x++) {
			for(int y = 1; y < imp.getHeight()-1; y++) {
				for(int xFoco = x-1; xFoco<=x+1; xFoco++) {
					for(int yFoco = y-1; yFoco<=y+1 ; yFoco++) {
						mediana.add(imp.get(xFoco, yFoco));	
					}
				}
				Collections.sort(mediana);
				imagemMediana.set(x, y, mediana.get(4));
				mediana.clear();
			}
		}
		IJ.saveAs(novaImagem, "Jpeg", "C:\\Users\\zedan\\Downloads\\NovaImagem.jpg");
	}
}