import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T17_Limiarization  implements PlugIn, DialogListener {
	private static GenericDialog gd;
	int preto = 0, branco = 0;
	
	ImageProcessor img = IJ.getProcessor();
	
	ImagePlus img1 = IJ.createImage("Imagem 1", "8-bit white", img.getWidth(), img.getHeight(), 1);
	ImageProcessor img1PRC = img1.getProcessor();
	

	
	public void ajustar(int valor_do_pixel) {

		preto = 0;
		branco = 0;
		
		int[] rgb = new int[3];
		float r;
		float g;
		float b; 
		int pixel;
		
		for(int x = 0; x < img.getWidth(); x++) {
			for(int y = 0; y < img.getHeight(); y++) {				
				r = img.getPixel(x, y, rgb)[0];
				g = img.getPixel(x, y, rgb)[1];
				b = img.getPixel(x, y, rgb)[2];
				
				pixel = (int) ((r+g+b)/3);
				
				if (valor_do_pixel < pixel){
					img1PRC.set(x, y, 255);	
					branco++;
				}
				else {
					img1PRC.set(x, y, 0);
					preto++;					
				}
				
			}
		}
		img1.show();
		img1.updateAndDraw();
		
	
		
	}
	
	public void run(String arg) {
		mostrarInterface();
		
	}
	
	void mostrarInterface() {
		gd = new GenericDialog("Limiarizacao");
		gd.addSlider("Campo Deslizante:", 0, 255, 0);
		gd.addDialogListener(this);
		gd.showDialog();
		
		if (gd.wasCanceled()) {
			IJ.showMessage("PlugIn cancelado!");
		}
		else {
			if (gd.wasOKed()) {
				calculaProporção();
				IJ.showMessage("Plugin encerrado com sucesso!");
			}
		}
	}

    public boolean dialogItemChanged(GenericDialog gd, AWTEvent e) {
    	int pixelDinamico;
    	pixelDinamico = (int) gd.getNextNumber();
		ajustar(pixelDinamico);
        return true;
    }
    
    public void calculaProporção() {
    	float total_de_pixels = img.getWidth()*img.getHeight();
    	float proporcao_de_branco = (branco*100)/total_de_pixels;
    	float proporcao_de_preto = (preto*100)/total_de_pixels;
    	IJ.showMessage("Percentual preto: " + String.format("%.2f", proporcao_de_preto) + "%");
    }
 
	
}
