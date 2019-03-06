import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;


public class T10_Pattern_Threads  implements PlugIn {
	
	static List<List<Integer>>  pixels_do_ROI = new ArrayList<List<Integer>>();
	static ImageProcessor img = IJ.getProcessor();
	static ImagePlus imgNova = IJ.createImage("Nova3", "RGB white", img.getWidth(), img.getHeight(), 1);
	static ImageProcessor Nova = imgNova.getProcessor();
	static int liberar = 0;
	static Rectangle rect = img.getRoi();
	
	
	public void run(String arg) {
		new Thread(t1).start();
		new Thread(t2).start();
	}
	private static Runnable t1 = new Runnable() {
		public void run() {
	        
			final long start = System.currentTimeMillis();
        	int[] rgb = new int[3];        	        	
        	List<List<Integer>>  pixels_da_imagem_toda = new ArrayList<List<Integer>>();
        	List<Integer> pixels; 		
            List<Integer> coordenadasX;
            List<Integer> coordenadasY;  		
		    
		    for(int linha = rect.x ; linha < rect.width+rect.x ; linha++) {
				for(int coluna = rect.y; coluna < rect.height+rect.y ; coluna++) {
					pixels = new ArrayList<>();  
					for (int i =0; i < 3; i++) {
						pixels.add(img.getPixel(linha, coluna, rgb)[i]);
					}
					pixels_do_ROI.add(pixels);
				}
			}
		    liberar = 1;
		    				
			coordenadasX = new ArrayList<>();  
			coordenadasY = new ArrayList<>();  
			
			for(int linha = 0; linha <= (img.getWidth()/2)-rect.width ; linha++) {			
				for(int coluna = 0; coluna < (img.getHeight())-rect.height; coluna++) {					
					for(int rectLinha = linha; rectLinha < rect.width + linha ; rectLinha++) {			
						for(int rectColuna = coluna; rectColuna < rect.height + coluna; rectColuna++) {
							//System.out.println("buscando pixels");
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
						for (int index = 0; index < pixels_da_imagem_toda.size(); index++) {
							for(int posicao= 0; posicao < 3; posicao++) {
								rgb[posicao] = pixels_da_imagem_toda.get(index).get(posicao);
							}
							Nova.putPixel(coordenadasX.get(index), coordenadasY.get(index), rgb);					    	
						}
						
					}
					final long end = System.currentTimeMillis();
					System.out.println("1 - Tempo gasto  " + (end-start) + "ms.");
					
					pixels_da_imagem_toda.clear();
					pixels_da_imagem_toda = new ArrayList<List<Integer>>();
					coordenadasX = new ArrayList<>();  
					coordenadasY = new ArrayList<>(); 
										
				}
			}
			liberar = 1;
			//imgNova.show();	
		}
	};
	
	private static Runnable t2 = new Runnable() {
		public void run() {        	
  	
			final long start = System.currentTimeMillis();
        	int[] rgb = new int[3];
        	List<List<Integer>>  pixels_da_imagem_toda = new ArrayList<List<Integer>>();
        	List<Integer> pixels; 		
            List<Integer> coordenadasX;
            List<Integer> coordenadasY;
			coordenadasX = new ArrayList<>();  
			coordenadasY = new ArrayList<>();
			
			while (liberar == 0 ) {
				System.out.println("aguardando thread 1");
			}
			//System.out.println(pixels_do_ROI);
			System.out.println("sai do while");
			liberar = 0;
			for(int linha = (img.getWidth()/2)-1; linha < img.getWidth()-rect.width ; linha++) {			
				for(int coluna = 0; coluna < img.getHeight()-rect.height; coluna++) {
					
					for(int rectLinha = linha; rectLinha < rect.width + linha ; rectLinha++) {			
						for(int rectColuna = coluna; rectColuna < rect.height + coluna; rectColuna++) {
							//System.out.println("buscando pixels");
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
						for (int index = 0; index < pixels_da_imagem_toda.size(); index++) {
							for(int posicao= 0; posicao < 3; posicao++) {
								rgb[posicao] = pixels_da_imagem_toda.get(index).get(posicao);
							}
							Nova.putPixel(coordenadasX.get(index), coordenadasY.get(index), rgb);					    	
						}
						
					}
					final long end = System.currentTimeMillis();
					System.out.println("2 - Tempo gasto " + (end-start) + "ms.");
					pixels_da_imagem_toda.clear();
					pixels_da_imagem_toda = new ArrayList<List<Integer>>();
					coordenadasX = new ArrayList<>();  
					coordenadasY = new ArrayList<>();											
				}
			}
			while (liberar == 0 ) {
				final long end = System.currentTimeMillis();
				System.out.println("2 - Tempo Gasto: " + (end-start) + "ms.");
				//System.out.println("aguardando thread 1, de novo");
			}
			imgNova.show();	
			liberar = 0;
        }
   };
}


		