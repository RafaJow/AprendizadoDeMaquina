package extrator_caracteristicas;

import java.io.File;
import java.io.FileOutputStream;

import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

public class ExtractCaracteristicas {
	
	public static double[] extraiCaracteristicas(File f) {
		
		double[] caracteristicas = new double[7];
		
		double marromCabeloFlanders = 0;
		double rosaCamisaFlanders = 0;
		double verdeSueterFlanders = 0;
		
		double azulLacoMaggie = 0;
		double vermelhoBicoMaggie = 0;
		double azulRoupaMaggie = 0; 
		
		
		Image img = new Image(f.toURI().toString());
		PixelReader pr = img.getPixelReader();
		
		Mat imagemOriginal = Imgcodecs.imread(f.getPath());
        Mat imagemProcessada = imagemOriginal.clone();
		
		int w = (int)img.getWidth();
		int h = (int)img.getHeight();
		
		
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				
				Color cor = pr.getColor(j,i);
				
				double r = cor.getRed()*255; 
				double g = cor.getGreen()*255;
				double b = cor.getBlue()*255;
				
				if(isMarromCabeloFlanders(r, g, b)) {
					marromCabeloFlanders++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if(isRosaCamisaFlanders(r, g, b)) {
					rosaCamisaFlanders++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				if (isVerdeSueterFlanders(r, g, b)) {
					verdeSueterFlanders++;
					imagemProcessada.put(i, j, new double[]{0, 255, 128});
				}
				
				//--------------------------------------------------------------
				
				if(isAzulLacoMaggie(r, g, b)) {
					azulLacoMaggie++;
					imagemProcessada.put(i, j, new double[]{0, 255, 255});
				}
				if(isVermelhoBicoMaggie(r, g, b)) {
					vermelhoBicoMaggie++;
					imagemProcessada.put(i, j, new double[]{0, 255, 255});
				}
				if (isAzulRoupaMaggie(r, g, b)) {
					azulRoupaMaggie++;
					imagemProcessada.put(i, j, new double[]{0, 255, 255});
				}
				
			}
		}
		
		// Normaliza as características pelo número de pixels totais da imagem para %
		marromCabeloFlanders = (marromCabeloFlanders / (w * h)) * 100;
		rosaCamisaFlanders = (rosaCamisaFlanders / (w * h)) * 100;
		verdeSueterFlanders = (verdeSueterFlanders / (w * h)) * 100;
        
        //-----------------------------------------------------------------
		azulLacoMaggie = (azulLacoMaggie / (w * h)) * 100;
		vermelhoBicoMaggie = (vermelhoBicoMaggie / (w * h)) * 100;
		azulRoupaMaggie = (azulRoupaMaggie / (w * h)) * 100;
        
        
        
        caracteristicas[0] = marromCabeloFlanders;
        caracteristicas[1] = rosaCamisaFlanders;
        caracteristicas[2] = verdeSueterFlanders;
        
        //------------------------------------------------------
        caracteristicas[3] = azulLacoMaggie;
        caracteristicas[4] = vermelhoBicoMaggie;
        caracteristicas[5] = azulRoupaMaggie;
        //APRENDIZADO SUPERVISIONADO - JÁ SABE QUAL A CLASSE NAS IMAGENS DE TREINAMENTO
        caracteristicas[6] = f.getName().charAt(0)=='f'?0:1;
		
		HighGui.imshow("Imagem original", imagemOriginal);
        HighGui.imshow("Imagem processada", imagemProcessada);
        
        HighGui.waitKey(1); // 0
		
		return caracteristicas;
	}
	
	public static boolean isMarromCabeloFlanders(double r, double g, double b) {
		 if (b >= 10 && b <= 60 &&  g >= 50 && g <= 109 &&  r >= 75 && r <= 137) {                       
         	return true;
         }
		 return false;
	}
	public static boolean isRosaCamisaFlanders(double r, double g, double b) {
		if (b >= 127 && b <= 188 &&  g >= 105 && g <= 190 &&  r >= 160 && r <= 225) {                       
			return true;
		}
		return false;
	}
	public static boolean isVerdeSueterFlanders(double r, double g, double b) {
		if (b >= 14 && b <= 60 &&  g >= 60 && g <= 140 &&  r >= 20 && r <= 70) {                       
			return true;
		}
		return false;
	}

	//--------------------------------------------------------------

	public static boolean isAzulLacoMaggie(double r, double g, double b) {
		if (b >= 150 && b <= 205 &&  g >= 105 && g <= 180 &&  r >= 50 && r <= 80) {                       
			return true;
		}
		return false;
	}
	public static boolean isVermelhoBicoMaggie(double r, double g, double b) {
		if (b >= 0 && b <= 10 &&  g >= 20 && g <= 47 &&  r >= 125 && r <= 190) {                       
			return true;
		}
		return false;
	}
	public static boolean isAzulRoupaMaggie(double r, double g, double b) {
		if (b >=170 && b <= 220 &&  g >= 100 && g <= 180 &&  r >= 45 && r <= 80) {                       
			return true;
		}
		return false;
	}

	public static void extrair() {
				
	    // Cabeçalho do arquivo Weka
		String exportacao = "@relation caracteristicas\n\n";
		exportacao += "@attribute marrom_cabelo_flanders real\n";
		exportacao += "@attribute rosa_camisa_flanders real\n";
		exportacao += "@attribute verde_sueter_flanders real\n";
		
		exportacao += "@attribute azul_laco_maggie real\n";
		exportacao += "@attribute vermelho_bico_maggie real\n";
		exportacao += "@attribute azul_roupa_maggie real\n";
		exportacao += "@attribute classe {Flanders, Maggie}\n\n";
		exportacao += "@data\n";
	        
	    // Diretório onde estão armazenadas as imagens
	    File diretorio = new File("src\\imagens");
	    File[] arquivos = diretorio.listFiles();
	    
        // Definição do vetor de características
        double[][] caracteristicas = new double[1581][7];
        
        // Percorre todas as imagens do diretório
        int cont = -1;
        for (File imagem : arquivos) {
        	cont++;
        	caracteristicas[cont] = extraiCaracteristicas(imagem);
        	
        	String classe = caracteristicas[cont][6] == 0 ?"Flanders":"Maggie";
        	
        	System.out.println("Marrom Cabelo Flanders: " + caracteristicas[cont][0] 
            		+ " - Rosa Camisa Flanders: " + caracteristicas[cont][1] 
            		+ " - Verde Sueter Flanders: " + caracteristicas[cont][2] 
            				
            		+ " - Azul Laco Maggie: " + caracteristicas[cont][3] 
            		+ " - Vermelho Bico Maggie: " + caracteristicas[cont][4] 
            		+ " - Azul Roupa Maggie: " + caracteristicas[cont][5] 
            		+ " - Classe: " + classe);
        	
        	exportacao += caracteristicas[cont][0] + "," 
                    + caracteristicas[cont][1] + "," 
        		    + caracteristicas[cont][2] + "," 
                    + caracteristicas[cont][3] + "," 
        		    + caracteristicas[cont][4] + "," 
                    + caracteristicas[cont][5] + "," 
                    + classe + "\n";
        }
        
     // Grava o arquivo ARFF no disco
        try {
        	File arquivo = new File("caracteristicas.arff");
        	FileOutputStream f = new FileOutputStream(arquivo);
        	f.write(exportacao.getBytes());
        	f.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

}
