import java.util.ArrayList;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.WindowManager;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.text.TextWindow;

public class T02_Rotacionar_  implements PlugIn {
	public void run(String arg) {
		ImageProcessor imp = IJ.getProcessor();	
		ImageProcessor novaImagem = criarInvertida(imp.getWidth(), imp.getHeight());


		for(int x = 0; x < imp.getWidth(); x++) {
			for(int y = 0; y < imp.getHeight(); y++) {
				int pixel = imp.getPixel(x, y); 
				novaImagem.set(imp.getHeight()-1-y, x, pixel);
			}
		}
	}	
	public ImageProcessor criarInvertida(int largura, int altura) {
		ImagePlus img = IJ.createImage("Rotacionada", "8-bit black", altura, largura, 1);
		img.show();
		ImageProcessor imp = IJ.getProcessor();
		return imp;
	}
}