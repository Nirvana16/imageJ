import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.process.LUT;

public class T12_HSV  implements PlugIn {
	public void run(String arg) {		
		ImageProcessor imagemPRC = IJ.getProcessor();
		
		ImagePlus img1 = IJ.createImage("ImagemH", "8-bit white", imagemPRC.getWidth(), imagemPRC.getHeight(), 1);
		ImageProcessor img1PRC = img1.getProcessor();		
		ImagePlus img2 = IJ.createImage("ImagemS", "8-bit white", imagemPRC.getWidth(), imagemPRC.getHeight(), 1);
		ImageProcessor img2PRC = img2.getProcessor();		
		ImagePlus img3 = IJ.createImage("ImagemV", "8-bit white", imagemPRC.getWidth(), imagemPRC.getHeight(), 1);
		ImageProcessor img3PRC = img3.getProcessor();	

		List<Float> pixels;	
		int[] rgb = new int[3];
		float[] hsv = new float[3];

		for(int linha = 0; linha < imagemPRC.getWidth(); linha++) {
			for(int coluna = 0; coluna < imagemPRC.getHeight(); coluna++) {
				//Normalizar ==================================================================================		
				pixels = new ArrayList<>(); 
				pixels.add(normalizar(0, 255, 1.0f, 0.0f, imagemPRC.getPixel(linha, coluna, rgb)[0]));
				pixels.add(normalizar(0, 255, 1.0f, 0.0f, imagemPRC.getPixel(linha, coluna, rgb)[1]));
				pixels.add(normalizar(0, 255, 1.0f, 0.0f, imagemPRC.getPixel(linha, coluna, rgb)[2]));							

				
				// transformando para HSV, Calculo do H
				if ((Collections.max(pixels) == pixels.get(0)) && (pixels.get(1) >= pixels.get(2))) {
					hsv[0] = (0.166666667f * ((pixels.get(1)-pixels.get(2)) / (Collections.max(pixels)-Collections.min(pixels))) + 0);
				}
				else {
					if ((Collections.max(pixels) == pixels.get(0)) && (pixels.get(1) < pixels.get(2))) {
						hsv[0] = (0.166666667f * ((pixels.get(1)-pixels.get(2)) / (Collections.max(pixels)-Collections.min(pixels))) +360);
					}
					else {
						if (Collections.max(pixels) == pixels.get(1)){
							hsv[0] = (0.166666667f * ((pixels.get(2)-pixels.get(0)) / (Collections.max(pixels)-Collections.min(pixels))) +120);
						}
						else {
							if (Collections.max(pixels) == pixels.get(2)){
								hsv[0] = (0.166666667f * ((pixels.get(0)-pixels.get(1)) / (Collections.max(pixels)-Collections.min(pixels))) +240);
							}
						}
					}
				}
			
		
				// calculo do S
				hsv[1] = (((Collections.max(pixels))-(Collections.min(pixels)))/(Collections.max(pixels)));
				
				// calculo do v
				hsv[2] = (Collections.max(pixels)); 
				
				// Normalizando o HSV e e Inserir pixels nas novas imagens
				img1PRC.setf(linha, coluna, normalizar(0.0f, 1.0f, 255, 0, hsv[0]));
				img2PRC.setf(linha, coluna, normalizar(0.0f, 1.0f, 255, 0, hsv[1]));
				img3PRC.setf(linha, coluna, normalizar(0.0f, 1.0f, 255, 0, hsv[2]));	
			}
		}
		img1.show();
		img2.show();
		img3.show();
	}
	
	public float normalizar(float valorMinimoAtual, float valorMaximoAtual, float novoValorMaximo, float novoValorMinimo, float pixel) {
		return ((pixel-valorMinimoAtual)*((novoValorMaximo-novoValorMinimo)/(valorMaximoAtual-valorMinimoAtual))+novoValorMinimo);
	}
}

