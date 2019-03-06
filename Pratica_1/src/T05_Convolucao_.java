import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ij.IJ;
import ij.ImagePlus;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

public class T05_Convolucao_  implements PlugIn {
	public void run(String arg) {
		ImageProcessor imp = IJ.getProcessor();			
		ImagePlus novaImagem = IJ.createImage("NovaImagem", "8-bit black", imp.getWidth(), imp.getHeight(), 1);
		ImageProcessor imagemConv = novaImagem.getProcessor();	
		
		
		// RECT ===========================================================================
		List<Float> convolucao1 = new ArrayList<Float>(Arrays.asList(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 
																	0.0f, 0.11f, 0.11f, 0.11f, 0.0f, 
																	0.0f, 0.11f, 0.11f, 0.11f, 0.0f,
																	0.0f, 0.11f, 0.11f, 0.11f, 0.0f,
																	0.0f, 0.0f, 0.0f, 0.0f, 0.0f));		
		
		// BIG RECT ===========================================================================
		List<Float> convolucao2 = new ArrayList<Float>(Arrays.asList(0.04f, 0.04f, 0.04f, 0.04f, 0.04f, 
																	 0.04f, 0.04f, 0.04f, 0.04f, 0.04f, 
																	 0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
																	 0.04f, 0.04f, 0.04f, 0.04f, 0.04f,
																	 0.04f, 0.04f, 0.04f, 0.04f, 0.04f));
		// GAUSSIAN ===========================================================================
		List<Float> convolucao3 = new ArrayList<Float>(Arrays.asList(0.01f, 0.02f, 0.03f, 0.2f, 0.1f, 
																	0.02f, 0.06f, 0.08f, 0.06f, 0.02f, 
																	0.03f, 0.08f, 0.11f, 0.08f, 0.03f,
																	0.02f, 0.06f, 0.08f, 0.06f, 0.02f,
																	0.01f, 0.02f, 0.03f, 0.02f, 0.01f));
		// SHARPEN ===========================================================================
		List<Float> convolucao4 = new ArrayList<Float>(Arrays.asList(0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
																	 0.0f, 0.00f, -0.125f, 0.00f, 0.0f, 
																	 0.0f, -0.125f, 1.5f, -0.125f, 0.0f,
																	 0.0f, 0.00f, -0.125f, 0.00f, 0.0f,
																	 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));
		// EDGES ===========================================================================
		List<Float> convolucao5 = new ArrayList<Float>(Arrays.asList(0.00f, 0.00f, 0.00f, 0.00f, 0.00f,
																	 0.00f, 0.00f, -0.125f, 0.00f, 0.00f, 
																	 0.00f, -0.125f, 0.50f, -0.125f, 0.00f,
																	 0.00f, 0.00f, -0.125f, 0.00f, 0.00f,
																	 0.00f, 0.00f, 0.00f, 0.00f, 0.00f));	
		// SHIFT ===========================================================================
		List<Float> convolucao6 = new ArrayList<Float>(Arrays.asList(0.0f, 0.0f, 0.0f, 0.0f, 0.0f,
																	 0.0f, 0.00f, 0.00f, 0.00f, 0.0f, 
																	 1.0f, 0.00f, 0.00f, 0.00f, 0.0f,
																	 0.0f, 0.00f, 0.00f, 0.00f, 0.0f,
																	 0.0f, 0.0f, 0.0f, 0.0f, 0.0f));	
		// HANDSHAKE ===========================================================================
		List<Float> convolucao = new ArrayList<Float>(Arrays.asList(0.20f, 0.0f, 0.0f, 0.0f, 0.0f,
																	 0.0f, 0.20f, 0.00f, 0.00f, 0.0f, 
																	 0.0f, 0.00f, 0.20f, 0.00f, 0.0f,
																	 0.0f, 0.00f, 0.00f, 0.20f, 0.0f,
																	 0.0f, 0.0f, 0.0f, 0.0f, 0.20f));	
		
		System.out.println(convolucao);
		
		
		float pixel = 0;
		for(int x = 2; x < imp.getWidth()-2; x++) {
			for(int y = 2; y < imp.getHeight()-2; y++) {
				int index = 0;
				for(int xFoco = x-2; xFoco<=x+2; xFoco++) {
					for(int yFoco = y-2; yFoco<=y+2 ; yFoco++) {
						pixel = (pixel+ (convolucao.get(index)*imp.getPixel(xFoco, yFoco)));
						index += 1;
					}
				}
				imagemConv.setf(x, y, pixel);
				pixel = 0;
			}
		}
		novaImagem.show();
	}
}