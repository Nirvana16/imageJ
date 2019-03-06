import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T09_Rect_Roi  implements PlugIn {
	public void run(String arg) {		
		ImageProcessor img = IJ.getProcessor();
		
		ImagePlus imgNova = IJ.createImage("Nova", "RGB white", img.getWidth(), img.getHeight(), 1);
		ImageProcessor Nova = imgNova.getProcessor();
		

		int[] rgb = new int[3];		
		Rectangle rect = img.getRoi();		
		List<Integer> pixelsR = new ArrayList<Integer>();
		List<Integer> pixelsG = new ArrayList<Integer>();	
		List<Integer> pixelsB = new ArrayList<Integer>();	
		
	
		int minimoR = img.getPixel(0, 0, rgb)[0], maximoR = img.getPixel(0, 0, rgb)[0];
		int minimoG = img.getPixel(0, 0, rgb)[1], maximoG = img.getPixel(0, 0, rgb)[1];
		int minimoB = img.getPixel(0, 0, rgb)[2], maximoB = img.getPixel(0, 0, rgb)[2];
		
		
		for(int linha = rect.x; linha < rect.getWidth()+rect.x; linha++) {
			for(int coluna = rect.y; coluna < rect.getHeight()+rect.y; coluna++) {
				pixelsR.add(img.getPixel(linha, coluna, rgb)[0]);
				pixelsG.add(img.getPixel(linha, coluna, rgb)[1]);
				pixelsB.add(img.getPixel(linha, coluna, rgb)[2]);				
			}
		}
		
		maximoR = Collections.max(pixelsR);
		minimoR = Collections.min(pixelsR);
		
		maximoG = Collections.max(pixelsG);
		minimoG = Collections.min(pixelsG);
		
		maximoB = Collections.max(pixelsB);
		minimoB = Collections.min(pixelsB);
		
		for(int linha = 0; linha < img.getWidth(); linha++) {
			for(int coluna = 0; coluna < img.getHeight(); coluna++) {
				if (((img.getPixel(linha, coluna, rgb)[0] <= maximoR) && (img.getPixel(linha, coluna, rgb)[0] >= minimoR)) 
						&& ((img.getPixel(linha, coluna, rgb)[1] <= maximoG) && (img.getPixel(linha, coluna, rgb)[1] >= minimoG)) 
						&& ((img.getPixel(linha, coluna, rgb)[2] <= maximoB) && (img.getPixel(linha, coluna, rgb)[2] >= minimoB))){	
					
					Nova.putPixel(linha, coluna, rgb);					
				}				
			}
		}
		imgNova.show();

	}
}