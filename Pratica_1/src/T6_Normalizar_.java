import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T6_Normalizar_ implements PlugIn {
	public void run(String arg) {		
		ImageProcessor imagemPRC = IJ.getProcessor();
		ImagePlus segundaIMG = IJ.createImage("Imagem_Normalizada", "8-bit black", imagemPRC.getWidth(), imagemPRC.getHeight(), 1);
		ImageProcessor segundoPRC = segundaIMG.getProcessor();
		
		
		int limiteMin = 0, limiteMax = 255, minimo = imagemPRC.getPixel(0, 0), maximo = imagemPRC.getPixel(0, 0);
		
		for(int linha = 0; linha < imagemPRC.getWidth(); linha++) {
			for(int coluna = 0; coluna < imagemPRC.getHeight(); coluna++) {
				
				if (imagemPRC.getPixel(linha, coluna) < minimo) {
					minimo = imagemPRC.get(linha, coluna);
				}				
				if (imagemPRC.getPixel(linha, coluna) > maximo) {
					maximo = imagemPRC.get(linha, coluna);
				}
			}
		}
		
		for(int linha = 0; linha < imagemPRC.getWidth(); linha++) {
			for(int coluna = 0; coluna < imagemPRC.getHeight(); coluna++) {
				segundoPRC.setf(linha, coluna, ((imagemPRC.get(linha, coluna)-minimo)*((limiteMax-limiteMin)/(maximo-minimo))+limiteMin));
			}
		}
		segundaIMG.show();
	}
}