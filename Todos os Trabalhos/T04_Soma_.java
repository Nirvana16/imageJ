import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T04_Soma_  implements PlugIn {
	public void run(String arg) {
		ImagePlus imagem1 = IJ.openImage("C:\\FACULDADE\\8 Periodo\\03 - Topicos Avançados II\\img1.tif");
		ImageProcessor imp1 = imagem1.getProcessor();
		imagem1.show();
		ImagePlus imagem2 = IJ.openImage("C:\\FACULDADE\\8 Periodo\\03 - Topicos Avançados II\\img2.tif");		
		ImageProcessor imp2 = imagem2.getProcessor();
		imagem2.show();
		ImagePlus novaImagem = IJ.createImage("soma", "8-bit black", imagem1.getWidth(), imagem1.getHeight(), 1);
		ImageProcessor imp3 = novaImagem.getProcessor();

		for(int x = 0; x < imagem1.getWidth(); x++) {
			for(int y = 0; y < imagem1.getHeight(); y++) {
				int pixel = imp1.getPixel(x, y)+imp2.getPixel(x, y);
				if (pixel > 255) {
					pixel = 255;
				}
				imp3.putPixel(x, y, pixel);
			}
		}
		novaImagem.show();	
	}
}