import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T09_Rect_Roi_Deselegante  implements PlugIn {
	public void run(String arg) {		
		ImageProcessor img = IJ.getProcessor();
		ImagePlus imgNova = IJ.createImage("Nova", "RGB white", img.getWidth(), img.getHeight(), 1);
		ImageProcessor Nova = imgNova.getProcessor();
		

		int[] rgb = new int[3];
		int r;
		int g;
		int b;		
		int[] rgbNova = new int[3];		
		Rectangle rect = img.getRoi();
	
		List<Integer> pixelsR = new ArrayList<Integer>();
		List<Integer> pixelsG = new ArrayList<Integer>();	
		List<Integer> pixelsB = new ArrayList<Integer>();	
		
		int minimoR = 255, maximoR = 0;
		int minimoG = 255, maximoG = 0;
		int minimoB = 255, maximoB = 0;
		
		
		for(int linha = rect.x; linha < rect.getWidth()+rect.x; linha++) {
			for(int coluna = rect.y; coluna < rect.getHeight()+rect.y; coluna++) {
				r = img.getPixel(linha, coluna, rgb)[0];
				pixelsR.add(r);
				g = img.getPixel(linha, coluna, rgb)[1];
				pixelsG.add(g);
				b = img.getPixel(linha, coluna, rgb)[2];
				pixelsB.add(b);
				
				maximoR = Collections.max(pixelsR);
				minimoR = Collections.min(pixelsR);
				maximoG = Collections.max(pixelsG);
				minimoG = Collections.min(pixelsG);
				maximoB = Collections.max(pixelsB);
				minimoB = Collections.min(pixelsB);				
			}
		}
		for(int linha = 0; linha < img.getWidth(); linha++) {
			for(int coluna = 0; coluna < img.getHeight(); coluna++) {
				r = img.getPixel(linha, coluna, rgb)[0];
				g = img.getPixel(linha, coluna, rgb)[1];				
				b = img.getPixel(linha, coluna, rgb)[2];
				if (((r <= maximoR) && (r >= minimoR)) && ((g <= maximoG) && (g >= minimoG)) && ((b <= maximoB) && (b >= minimoB))){
					rgbNova[0] = r;
					rgbNova[1] = g;
					rgbNova[2] = b;				
					Nova.putPixel(linha, coluna, rgbNova);					
				}				
			}
		}
		imgNova.show();

	}
}