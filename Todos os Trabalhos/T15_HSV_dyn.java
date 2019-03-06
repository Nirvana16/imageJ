import java.awt.AWTEvent;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.DialogListener;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T15_HSV_dyn  implements PlugIn, DialogListener {
	
	ImageProcessor img = IJ.getProcessor();
	ImagePlus imgRGB = IJ.createImage("HSV", "RGB white", img.getWidth(), img.getHeight(), 1);
	ImageProcessor RGB = imgRGB.getProcessor();
	
	private static GenericDialog gd;
	int valor_maximo_H;
	int valor_minimo_H;
	int valor_maximo_V;
	int valor_minimo_V;
	int valor_maximo_S;
	int valor_minimo_S;
		
	public void run(String arg) {
		mostrarInterface();
	}
	
	public float normaliza(float min, float max, float newMin, float newMax, float pixel) {
		return (pixel-min)*((newMax-newMin)/(max-min))+newMin;
	}
	
	void ajustar_HSV(int Valor_maximo_Hue, int Valor_minimo_Hue, int Valor_maximo_value, int Valor_minimo_Value, int Valor_maximo_Saturation, int Valor_minimo_Saturation) {		
		
		int[] rgb = new int[3];
		float r, g, b, h = 0, s, v;
		float max, min;
		float Hi, f, p, q, t;
		float h_desejado = 255, s_desejado = 255, v_desejado = 255;
		
		int apoio;
		
		
		// Bloco par converter de RGB  PARA HSV
		for(int linha = 0; linha < img.getWidth(); linha++) {
			for(int coluna = 0; coluna < img.getHeight(); coluna++) {
				r = img.getPixel(linha, coluna, rgb)[0];
				g = img.getPixel(linha, coluna, rgb)[1];
				b = img.getPixel(linha, coluna, rgb)[2];
				
				r = normaliza(0, 255, 0, 1, r);
				g = normaliza(0, 255, 0, 1, g);
				b = normaliza(0, 255, 0, 1, b);
				
				// regras para conversão e definição de max e min conforme wiki
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
						h = (float) (((g - b)/(max - min))*0.166666667);
					}
					else {
						if(max == g) {
							h = (float) ((2 + (b - r)/(max - min))*0.166666667);
						}
						else {
							h = (float) ((4 + (r - g)/(max - min))*0.166666667);
						}
					}
				}
				
				if (h < 0) {
					h++;
				}
				if (max == 0) {
					s = 0;
				}
				else {
					s = (max-min)/max;
				}			
				
				v = max;
				
				h = normaliza(0, 1, 0, 255, h);
				s = normaliza(0, 1, 0, 255, s);
				v = normaliza(0, 1, 0, 255, v);
				
				// bloco de código que recebe os valores do slider e soma ao HSV
				
				if(Valor_maximo_Hue < Valor_minimo_Hue) {
					apoio = Valor_minimo_Hue;
					Valor_minimo_Hue = Valor_maximo_Hue;
					Valor_maximo_Hue = apoio;
				}
				
				if(Valor_maximo_value < Valor_minimo_Value) {
					apoio = Valor_minimo_Value;
					Valor_minimo_Value = Valor_maximo_value;
					Valor_maximo_value = apoio;
				}
				
				if(Valor_maximo_Saturation < Valor_minimo_Saturation) {
					apoio = Valor_minimo_Saturation;
					Valor_minimo_Saturation = Valor_maximo_Saturation;
					Valor_maximo_Saturation = apoio;
				}
				
				if((Valor_maximo_Hue >= h && h >= Valor_minimo_Hue) 
						&& (Valor_maximo_value >= v && v >= Valor_minimo_Value) 
						&& (Valor_maximo_Saturation >= s && s >= Valor_minimo_Saturation)) {
//					h_desejado = h;
//					v_desejado = v;
//					s_desejado = s;

					//Converte HSV para RGB
					
					r = normaliza(0, 255, 0, 359, h);
					g = normaliza(0, 255, 0, 1, s);
					b = normaliza(0, 255, 0, 1, v);
					
					if(g==0) {
						r = b;
						g = b;
					}
					else if(g != 0) {
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
					
					rgb[0] = (int) r;
					rgb[1] = (int) g;
					rgb[2] = (int) b;
					
					RGB.putPixel(linha, coluna, rgb);
				}
				else {
					rgb[0] = 255;
					rgb[1] = 255;
					rgb[2] = 255;
					
					RGB.putPixel(linha, coluna, rgb);					
				}
			}
		}		
		imgRGB.updateAndDraw();
		imgRGB.show();
		
	}

    void mostrarInterface() {
        gd = new GenericDialog("Interface de Teste");
        gd.addMessage("Valores para H");
        gd.addSlider("Maximo:", 0, 255, 0);
        gd.addSlider("Minimo:", 0, 255, 0);
        
        gd.addMessage("Valores para V");
        gd.addSlider("Maximo:", 0, 255, 255);
        gd.addSlider("Minimo:", 0, 255, 0);
        
        gd.addMessage("Valores para S");
        gd.addSlider("Maximo:", 0, 255, 255);
        gd.addSlider("Minimo:", 0, 255, 0);
        
        gd.addDialogListener(this);
        gd.showDialog();
        
        if (gd.wasCanceled()) {
        	IJ.showMessage("PlugIn cancelado!");
        }
        else {
        	if (gd.wasOKed()) {
        		IJ.showMessage("Plugin encerrado com sucesso!");
        	}
        }
    }
	
	public boolean dialogItemChanged(GenericDialog gd, AWTEvent e) {
		valor_maximo_H = (int)gd.getNextNumber();
		valor_minimo_H = (int)gd.getNextNumber();
		valor_maximo_V = (int)gd.getNextNumber();
		valor_minimo_V = (int)gd.getNextNumber();
		valor_maximo_S = (int)gd.getNextNumber();
		valor_minimo_S = (int)gd.getNextNumber();
		ajustar_HSV(valor_maximo_H, valor_minimo_H, valor_maximo_V, valor_minimo_V, valor_maximo_S,valor_minimo_S);
	    return true;
	}
}
