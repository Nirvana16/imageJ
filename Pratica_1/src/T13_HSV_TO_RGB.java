import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.process.LUT;

public class T13_HSV_TO_RGB  implements PlugIn {
	public void run(String arg) {		
		ImageProcessor imagemPRC = IJ.getProcessor();
		
		ImagePlus img1 = IJ.createImage("ImagemH", "8-bit white", imagemPRC.getWidth(), imagemPRC.getHeight(), 1);
		ImageProcessor img1PRC = img1.getProcessor();		
		ImagePlus img2 = IJ.createImage("ImagemS", "8-bit white", imagemPRC.getWidth(), imagemPRC.getHeight(), 1);
		ImageProcessor img2PRC = img2.getProcessor();		
		ImagePlus img3 = IJ.createImage("ImagemV", "8-bit white", imagemPRC.getWidth(), imagemPRC.getHeight(), 1);
		ImageProcessor img3PRC = img3.getProcessor();	
		ImagePlus imgRGB = IJ.createImage("HSV to RGB", "RGB white", imagemPRC.getWidth(), imagemPRC.getHeight(), 1);
		ImageProcessor imgRgbPRC = imgRGB.getProcessor();	
		

		List<Float> pixels;	
		int[] rgb = new int[3];
		float[] hsv = new float[3];

		float Hi, f, p, q, t, r,g,b;

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
				img1PRC.setf(linha, coluna, normalizar(0.0f, 1.0f, 255, 0, hsv[0])); //Isso aqui eu ja set nas novas imagens os valores de H convertido para R
				img2PRC.setf(linha, coluna, normalizar(0.0f, 1.0f, 255, 0, hsv[1])); //Isso aqui eu ja set nas novas imagens os valores de S convertido para G
				img3PRC.setf(linha, coluna, normalizar(0.0f, 1.0f, 255, 0, hsv[2])); //Isso aqui eu ja set nas novas imagens os valores de V convertido para B
				
				// Fazendo o processo de retorno ===============================================
				
				r = normalizar(0.0f, 1.0f, 255, 0, hsv[0]);
				g = normalizar(0.0f, 1.0f, 255, 0, hsv[1]);
				b = normalizar(0.0f, 1.0f, 255, 0, hsv[2]);
				
				//System.out.println("antes " + pixels);
				
				pixels = new ArrayList<>(); 
				pixels.add(normalizar(0, 255, 360.0f, 0.0f, r)); // Dentro da lista eu ja estou inserindo o valor do Pixel R normalizado
				pixels.add(normalizar(0, 255, 1.0f, 0.0f, g)); // Dentro da lista eu ja estou inserindo o valor do Pixel G normalizado
				pixels.add(normalizar(0, 255, 1.0f, 0.0f, b)); // Dentro da lista eu ja estou inserindo o valor do Pixel B normalizado
				
				//System.out.println("depois " + pixels);
				
				// formulas de acordo com wikibr
				
				if (pixels.get(1) == 0) {
					pixels.set(0, pixels.get(2));
					pixels.set(1, pixels.get(2));
				}
				
				else {
					Hi = (int)((int)(pixels.get(0) / 60));
					f = ((pixels.get(0)/60) - Hi );
					p = (pixels.get(1)*(1-pixels.get(1)));
					q = (pixels.get(1)*(1-(f*pixels.get(1))));
					t = (pixels.get(1)*(1-(1-f)*pixels.get(1)));
					
										
					if (Hi == 0 ){
						pixels.set(0, pixels.get(2));
						pixels.set(1, t);
						pixels.set(2, p);						
					}
					
					else if(Hi==1) {
						pixels.set(0, q);
						pixels.set(1, pixels.get(2));
						pixels.set(2, p);
					}
					else if(Hi==2) {
						pixels.set(0, p);
						pixels.set(1, pixels.get(2));
						pixels.set(2, t);
					}
					else if(Hi==3) {
						pixels.set(0, p);
						pixels.set(1, q);
					}
					else if(Hi==4) {
						pixels.set(0, t);
						pixels.set(1, p);
					}
					else if(Hi==5) {
						pixels.set(0, pixels.get(2));
						pixels.set(1, p);
						pixels.set(2, q);
					}
					
				}
				
				//System.out.println("depois do depois " + pixels);
//				rgb[0] = (int) normalizar(0.0f, 1.0f, 255, 0, pixels.get(0));
//				rgb[1] = (int) normalizar(0.0f, 1.0f, 255, 0, pixels.get(1));
//				rgb[2] = (int) normalizar(0.0f, 1.0f, 255, 0, pixels.get(2));
//				
//				imgRgbPRC.putPixel(linha, coluna, rgb);
//				
				
				
				
				
				r = normalizar(0.0f, 1.0f, 255, 0, pixels.get(0));
				g = normalizar(0.0f, 1.0f, 255, 0, pixels.get(1));
				b = normalizar(0.0f, 1.0f, 255, 0, pixels.get(2));
				
				imgRgbPRC.putPixel(linha, coluna, (int)r);
				imgRgbPRC.putPixel(linha, coluna, (int)g);
				imgRgbPRC.putPixel(linha, coluna, (int)b);
				
				
			}
		}
		img1.show();
		img2.show();
		img3.show();
		imgRGB.show();
	}
	
	public float normalizar(float valorMinimoAtual, float valorMaximoAtual, float novoValorMaximo, float novoValorMinimo, float pixel) {
		return ((pixel-valorMinimoAtual)*((novoValorMaximo-novoValorMinimo)/(valorMaximoAtual-valorMinimoAtual))+novoValorMinimo);
	}
}

