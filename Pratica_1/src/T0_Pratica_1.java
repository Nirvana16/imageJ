import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.text.TextWindow;

public class T0_Pratica_1  implements PlugIn {
	public void run(String arg) {		
		ImagePlus imp = IJ.openImage("C:\\FACULDADE\\8 Periodo\\03 - Topicos Avançados II\\teste.tif");
		IJ.run(imp, "Inverter ", "");
		IJ.run(imp, "8-bit", "");		
		String[] list = new String[imp.getWidth()];
		String linha = "";
		StringBuffer sb = new StringBuffer();		
		imp.show();		
		for(int x = 0; x < imp.getWidth(); x++) {
			for(int y = 0; y < imp.getHeight(); y++) {
				int pixel = imp.getPixel(x, y)[0];
				linha = linha + Integer.toString(pixel) + " ";
			}			
			list[x] = linha;
			linha = "";
			sb.append(list[x]);
			sb.append("\n");
		}
//		IJ.run(imp, "Invert", "");
//		IJ.run(imp, "Rotate 90 Degrees Right", "");
		TextWindow tw = new TextWindow("Teste", "", sb.toString(), 300, 400); 		
	}
}