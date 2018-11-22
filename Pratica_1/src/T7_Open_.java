import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T7_Open_  implements PlugIn {
	public void run(String arg) {
		ImageProcessor imp = IJ.getProcessor();			
		ImagePlus segundaImagem = IJ.createImage("segundaImagem", "8-bit white", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor segundoProcessor = segundaImagem.getProcessor();
		ImagePlus terceiraImagem = IJ.createImage("TerceiraImagem", "8-bit white", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor terceiroProcessor = terceiraImagem.getProcessor();
		
		//Erosão
		for(int x = 1; x < imp.getWidth()-1; x++) {
			for(int y = 1; y < imp.getHeight()-1; y++) {
				if (imp.getPixel(x, y) == 0){
					if ((imp.getPixel(x-1, y) == 0) && (imp.getPixel(x+1, y) == 0) && (imp.getPixel(x, y-1) == 0) &&(imp.getPixel(x, y+1) == 0)){
						segundoProcessor.set(x, y, imp.getPixel(x, y));
					}
				}
			}
		}
		//Dilatação
		for(int x = 0; x < imp.getWidth(); x++) {
			for(int y = 0; y < imp.getHeight(); y++) {
				if (segundoProcessor.getPixel(x, y) == 0){
					for(int xFoco = x-1; xFoco<=x+1; xFoco++) {
						for(int yFoco = y-1; yFoco<=y+1; yFoco++) {
							terceiroProcessor.set(xFoco, yFoco, segundoProcessor.getPixel(x, y));
						}
					}
				}
			}
		}
		terceiraImagem.show();
		
	}
}