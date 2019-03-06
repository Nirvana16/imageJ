import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T14_BRILHO  implements PlugIn, DialogListener {
	private static GenericDialog gd;
	
	ImageProcessor img = IJ.getProcessor();
	ImagePlus imgOriginal = IJ.getImage();
	
	ImagePlus imgHue = IJ.createImage("Hue", "8-bit white", img.getWidth(), img.getHeight(), 1);
	ImageProcessor Hue = imgHue.getProcessor();
	
	ImagePlus imgSat = IJ.createImage("Saturation", "8-bit white", img.getWidth(), img.getHeight(), 1);
	ImageProcessor Sat = imgSat.getProcessor();
	ImagePlus imgValue = IJ.createImage("Value", "8-bit white", img.getWidth(), img.getHeight(), 1);
	ImageProcessor Value = imgValue.getProcessor();
	
	ImagePlus imgRGB = IJ.createImage("HSV to RGB", "RGB white", img.getWidth(), img.getHeight(), 1);		
	ImageProcessor RGB = imgRGB.getProcessor();
	
	public void ajustaHSV(int valor_do_pixel) {



		int[] rgb = new int[3];
		float r;
		float g;
		float b;
		
		float max;
		float min;
		
		float Hi, f, p, q, t;
		//float pixelDinamico = 0;
		
		for(int linha = 0; linha < img.getWidth(); linha++) {
			for(int coluna = 0; coluna < img.getHeight(); coluna++) {
				r = img.getPixel(linha, coluna, rgb)[0];
				g = img.getPixel(linha, coluna, rgb)[1];
				b = img.getPixel(linha, coluna, rgb)[2];
				
				r = normaliza(0, 255, 0, 1, r);
				g = normaliza(0, 255, 0, 1, g);
				b = normaliza(0, 255, 0, 1, b);
				
				if (r > g) {
					if (r > b) {
						max = r;
					}
					else {
						max = b;
					}
				}
				else {
					if (g > b) {
						max = g;
					}
					else {
						max = b;
					}
				}
				
				if (r < g) {
					if (r < b) {
						min = r;
					}
					else {
						min = b;
					}
				}
				else {
					if (g < b) {
						min = g;
					}
					else {
						min = b;
					}
				}
				
				if (max == min) {
					r = 0;
				}
				else {
					if (max == r) {
						r = (float) (((g - b)/(max - min))*0.166666667);
					}
					else {
						if(max == g) {
							r = (float) ((2 + (b - r)/(max - min))*0.166666667);
						}
						else {
							r = (float) ((4 + (r - g)/(max - min))*0.166666667);
						}
					}
				}
				
				if (r < 0) {
					r++;
				}
				
				g = (max-min)/max;
				
				b = max;
				
				
				r = normaliza(0, 1, 0, 255, r);
				g = normaliza(0, 1, 0, 255, g);
				b = normaliza(0, 1, 0, 255, b);
				
				r = r + (valor_do_pixel);
				g = g + (valor_do_pixel);
				b = b + (valor_do_pixel);
				
				
				if (r > 255) {
					r = 255;
				}
				if (g > 255) {
					g = 255;
				}
				if (b > 255) {
					b = 255;
				}
				if (b > 255) {
					b = 255;
				}
				if (r < -255) {
					r = 0;
				}
				if (g < -255) {
					g = 0;
				}
				if (b < -255) {
					b = 0;
				}
				
				
				Hue.setf(linha, coluna, r);
				Sat.setf(linha, coluna, g);
				Value.setf(linha, coluna, b);
				
				
				r = normaliza(0, 255, 0, 359, r);
				g = normaliza(0, 255, 0, 1, g);
				b = normaliza(0, 255, 0, 1, b);
				
				if(g==0) {
					r = b;
					g = b;
				}
				
				if(g != 0) {
					Hi = (int)((int)r/60);
					f = (r/60)-Hi;
					p = b*(1-g);
					q = b*(1-f*g);
					t = b*(1-(1-f)*g);

					if(Hi == 0) {
						r = b;
						g = t;
						b = p;
					}
					else if(Hi==1) {
						r = q;
						g = b;
						b = p;
					}
					else if(Hi==2) {
						r = p;
						g = b;
						b = t;
					}
					else if(Hi==3) {
						r = p;
						g = q;
					}
					else if(Hi==4) {
						r = t;
						g = p;
					}
					else if(Hi==5) {
						r = b;
						g = p;
						b = q;
					}
				}
				
				r = normaliza(0, 1, 0, 255, r);
				g = normaliza(0, 1, 0, 255, g);
				b = normaliza(0, 1, 0, 255, b);
				
				rgb[0] = (int)r;
				rgb[1] = (int)g;
				rgb[2] = (int)b;
				
				RGB.putPixel(linha, coluna, rgb);
			}
		}
		//imgOriginal.close();
		
		imgRGB.show();
		imgRGB.updateAndDraw();
		
		
	}
	
	public void run(String arg) {
		mostrarInterface();
		
	}
	
	void mostrarInterface() {
		int pixelDinamico;
		gd = new GenericDialog("Interface de Teste");
		gd.addSlider("Campo Deslizante:", -255, 255, 0);
		//gd.addNumericField("Campo Numérico:", 0, 5);
		gd.addDialogListener(this);
		gd.showDialog();
		
		if (gd.wasCanceled()) {
			IJ.showMessage("PlugIn cancelado!");
		}
		else {
			if (gd.wasOKed()) {
//				pixelDinamico = gd.getNextNumber();
//				ajustaHSV(pixelDinamico);
				//IJ.showMessage("Valor campo deslizante: " + gd.getNextNumber() + "\nValor do campo numérico:" + gd.getNextNumber());
				IJ.showMessage("Plugin encerrado com sucesso!");
			}
		}
	}

    public boolean dialogItemChanged(GenericDialog gd, AWTEvent e) {
    	int pixelDinamico;
    	pixelDinamico = (int) gd.getNextNumber();
		ajustaHSV(pixelDinamico);
        //IJ.log("\nValor do campo deslizante: " + gd.getNextNumber());
        //IJ.log("Valor do campo numérico: " + gd.getNextNumber());
        return true;
    }
    
	public float normaliza(float min, float max, float newMin, float newMax, float pixel) {
		
		float pixelNormalizado;
		pixelNormalizado = (pixel-min)*((newMax-newMin)/(max-min))+newMin;
		return pixelNormalizado;
	}
	
}
