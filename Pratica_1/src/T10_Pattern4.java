import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

/*
 * Pegar a regi�o de interesse (ROI), e em seguir varrer a imagem buscando quais pixels s�o iguais o do ROI
 * aonde os pixels do ROI forem iguais o da imagem esses pixels ser�o copiados para uma nova imagem
 */

public class T10_Pattern4  implements PlugIn {
	public void run(String arg) {		
		//Pego o processor da imagem aberta
		ImageProcessor img = IJ.getProcessor();
		// Cria uma nov aimagem pelo ImageJ (IJ) e pego o processor da nova imagem
		ImagePlus imgNova = IJ.createImage("Nova", "RGB white", img.getWidth(), img.getHeight(), 1);
		ImageProcessor Nova = imgNova.getProcessor();
		
		// Como estou trabalhando com imagem colorida a variavel RGB que servir� para receber o pixel precisar ser um vetor
		// pois o getPixel ir� retornar um array de 3 posi��es, cada um correspondendo a uma cor.
		int[] rgb = new int[3];
		
		// Rect serve para pegar a regi�o de interesse selecionada no imageJ, o metodo getROI retorna um retangulo com as informa��es dessa regiao
		Rectangle rect = img.getRoi();
		
		//Vou trabalhar com listas, preciso de duas listas identicas, uma ira receber todos os pixels do ROI e a outra os pixels da imagem.
		// Estou criando uam lista de listas, ou seja, cada posi��o do meu ArrayList vai conter uma lista de inteiros.
		List<List<Integer>>  pixels_do_ROI = new ArrayList<List<Integer>>();
		List<List<Integer>>  pixels_da_imagem_toda = new ArrayList<List<Integer>>();
	    
		//Vou utilizar essa varaivel para inseri-la na lista anterior, ou seja, cada posi��o do "pixels_do_ROI" por exemplo, vai conter uma lista "pixels"
		List<Integer> pixels; 
		
		//Outras listas, iguais ao pixels, para armazenar as coordenadas...
	    List<Integer> coordenadasX;
	    List<Integer> coordenadasY;   
	   
	    
	    // for para varrer a area de interesse, linha e coluna come�am na posi��o rect.x e rect.y, respectivametne, que corresponde a coordenada de 
	    // inicio da minha area de interesse, sendo que o limite � rect.getWidth() o comprimento da minha area de interesse e a altura da mesma. 
	        
	    for(int linha = rect.x ; linha < rect.width+rect.x ; linha++) {
			for(int coluna = rect.y; coluna < rect.height+rect.y ; coluna++) {
				//instancio o Arraylist a cada loop, para que limpe o pixel, dessa forma eu garanto que em cada posi��o da o meu pixels_do_roi contenha
				//um objeto diferente, nao poderia utilizar o pixels.clear() pois o java trata as listas de lista como se fossem todos um mesmo objeto.
				pixels = new ArrayList<>();  
				// esse for � para pegar cada posi��o , r , g , b do pixel 
				for (int i =0; i < 3; i++) {
					pixels.add(img.getPixel(linha, coluna, rgb)[i]);
				}
				//adiciono a lista de pixels na minha lista de lista "pixels do roi"
				pixels_do_ROI.add(pixels);
			}
		}		


		// contador eh para debug, posso remover depois
		int contador = 0;
		
		//coordenadas x e y instnaciadas antes do proximo for, poderia fazer la e mcima...
		coordenadasX = new ArrayList<>();  
		coordenadasY = new ArrayList<>();  
		
		// for para varrer a imagem inteira, a ideia � adicionar na nova lista de lista os pixels da imagem que n�o estao no ROI, o conceito eh  omesmo
		// do for anterior
		for(int linha = 0; linha < (img.getWidth()/2)-rect.width ; linha++) {			
			for(int coluna = 0; coluna < (img.getHeight()/2)-rect.height; coluna++) {	
				
				for(int rectLinha = linha; rectLinha < rect.width + linha ; rectLinha++) {			
					for(int rectColuna = coluna; rectColuna < rect.height + coluna; rectColuna++) {
						System.out.println("buscando pixels");
						pixels = new ArrayList<>();	
						for (int i =0; i < 3; i++) {
							pixels.add(img.getPixel(rectLinha, rectColuna, rgb)[i]);
						}			
						pixels_da_imagem_toda.add(pixels);		
						coordenadasX.add(rectLinha);
						coordenadasY.add(rectColuna);	
					}
				}
				if (pixels_da_imagem_toda.equals(pixels_do_ROI)){					
					contador = contador +1;
					for (int index = 0; index < pixels_da_imagem_toda.size(); index++) {
						for(int posicao= 0; posicao < 3; posicao++) {
							rgb[posicao] = pixels_da_imagem_toda.get(index).get(posicao);
						}
						Nova.putPixel(coordenadasX.get(index), coordenadasY.get(index), rgb);					    	
					}
					
				}
				System.out.println("Imagens grandes, maior que 500px costuma demorar. ");
				pixels_da_imagem_toda.clear();
				pixels_da_imagem_toda = new ArrayList<List<Integer>>();
				coordenadasX = new ArrayList<>();  
				coordenadasY = new ArrayList<>(); 
									
			}
		}
		System.out.println(contador);
		imgNova.show();	
	}
}


		