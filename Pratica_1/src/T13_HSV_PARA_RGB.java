import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T13_HSV_PARA_RGB  implements PlugIn {
	
	public float normaliza(float min, float max, float newMin, float newMax, float pixel) {
		
		float pixelNormalizado;
		pixelNormalizado = (pixel-min)*((newMax-newMin)/(max-min))+newMin;
		
		return pixelNormalizado;
	}
	
	public void run(String arg) {		
		ImageProcessor img = IJ.getProcessor();
		
		ImagePlus imgHue = IJ.createImage("Hue", "8-bit white", img.getWidth(), img.getHeight(), 1);
		ImagePlus imgSat = IJ.createImage("Saturation", "8-bit white", img.getWidth(), img.getHeight(), 1);
		ImagePlus imgValue = IJ.createImage("Value", "8-bit white", img.getWidth(), img.getHeight(), 1);
		ImagePlus imgRGB = IJ.createImage("HSV to RGB", "RGB white", img.getWidth(), img.getHeight(), 1);
		
		ImageProcessor Hue = imgHue.getProcessor();
		ImageProcessor Sat = imgSat.getProcessor();
		ImageProcessor Value = imgValue.getProcessor();
		ImageProcessor RGB = imgRGB.getProcessor();

		int[] rgb = new int[3];
		float r;
		float g;
		float b;
		
		float max;
		float min;
		
		float Hi, f, p, q, t;
		
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
				
				Hue.setf(linha, coluna, r);
				Sat.setf(linha, coluna, g);
				Value.setf(linha, coluna, b);
				
				r = normaliza(0, 255, 0, 360, r);
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
	
		imgHue.show();
		imgSat.show();
		imgValue.show();
		imgRGB.show();
	}
}
