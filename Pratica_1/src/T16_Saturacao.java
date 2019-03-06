
import java.io.File;

import ij.IJ;
import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T16_Saturacao  implements PlugIn {
	private static GenericDialog gd;
	private String diretorio, dirsaida;
	

	
	void mostrarInterface() {
        gd = new GenericDialog("Interface de Teste"); 
        diretorio = IJ.getDirectory("SELECIONAR PASTA DE ORIGEM");
		gd.addStringField("Input: ", diretorio );
		dirsaida = IJ.getDirectory("SELECIONAR PASTA DE DESTINO");
		gd.addStringField("Output: ", dirsaida );
        gd.showDialog();
      
        if (gd.wasCanceled()) {
        	IJ.showMessage("PlugIn cancelado!");
        }
        else {
        	if (gd.wasOKed()) {       		
        		processaImagensEmLote(diretorio, dirsaida);
    	        IJ.showMessage("Plugin encerrado com sucesso!");
        	}
        }
    }

	public void processaImagensEmLote (String diretorio_de_entrada, String diretorio_de_saida) {
		File pastaDeorigem = new File(diretorio_de_entrada);

		for (File fileEntry : pastaDeorigem.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	//Nao leia isso, siga para proxima linha poderia negar esse if, mas nao quero.
	        } 
	        else {
	        	String nomeDoArquivo = fileEntry.getName();	        	
	        	
	        	System.out.println("endereço completo: " + diretorio_de_entrada + nomeDoArquivo);	        	
	        	
	        	desaturizar(nomeDoArquivo, diretorio_de_entrada, diretorio_de_saida);
	        }
		}
	}
		
	
	public void desaturizar(String nome_do_arquivo, String diretorio_de_entrada, String diretorio_de_destino) {	
		
		int[] rgb = new int[3];
		float r;
		float g;
		float b;
		
		ImagePlus imagem = IJ.openImage(diretorio_de_entrada + nome_do_arquivo);
		ImageProcessor img = imagem.getProcessor();
		
		ImagePlus imgRGB = IJ.createImage("Desaturation", "RGB white", img.getWidth(), img.getHeight(), 1);
		ImageProcessor RGB = imgRGB.getProcessor();

		float novoPixel;
		
		for(int linha = 0; linha < img.getWidth(); linha++) {
			for(int coluna = 0; coluna < img.getHeight(); coluna++) {
				r = img.getPixel(linha, coluna, rgb)[0];
				g = img.getPixel(linha, coluna, rgb)[1];
				b = img.getPixel(linha, coluna, rgb)[2];
				
				novoPixel = (float) ((r*0.2125)+(g*0.7154)+(b*0.072));
				
				rgb[0] = (int) ((int)(novoPixel) + 0.2 * (r - novoPixel));
				rgb[1] = (int) ((int)(novoPixel) + 0.2 * (g - novoPixel));
				rgb[2] = (int) ((int)(novoPixel) + 0.2 * (b - novoPixel));
									
				RGB.putPixel(linha, coluna, rgb);
		}
	}
		IJ.save(imgRGB, diretorio_de_destino+nome_do_arquivo);		
	}



	@Override
	public void run(String arg) {
		mostrarInterface();		
	}
}