import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;
import ij.text.TextWindow;

public class T0_Pratica_2  implements PlugIn {
	public void run(String arg) {
		//ImagePlus imp = IJ.openImage("C:\\FACULDADE\\8 Periodo\\03 - Topicos Avançados II\\teste.tif");
		ImageProcessor imp = IJ.getProcessor();
		
		String[] list = new String[imp.getWidth()];
		String linha = "";
		StringBuffer sb = new StringBuffer();				
		for(int x = 0; x < imp.getWidth(); x++) {
			for(int y = 0; y < imp.getHeight(); y++) {
				int pixel = imp.getPixel(x, y);
				linha = linha + Integer.toString(pixel) + " ";
			}			
			list[x] = linha;
			linha = "";
			sb.append(list[x]);
			sb.append("\n");
		}
		imp.invert();
		imp.flipVertical();
		TextWindow tw = new TextWindow("Teste", "", sb.toString(), 300, 400); 		
	}
}