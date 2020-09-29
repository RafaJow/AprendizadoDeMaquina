package application;

import java.io.File;
import java.util.Arrays;

import classifica_caracteristicas.ClassificaCaracteristicas;
import extrator_caracteristicas.ExtractCaracteristicas;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class SampleController {
	
	@FXML
	private Label lblMarromCabeloFlanders;
	@FXML
	private Label lblRosaCamisaFlanders;
	@FXML
	private Label lblVerdeSueterFlanders;
	@FXML
	private Label lblAzulLacoMaggie;
	@FXML
	private Label lblVermelhoBicoMaggie;
	@FXML
	private Label lblAzulRoupaMaggie;
	@FXML
	private Label lblProbFlanders;
	@FXML
	private Label lblProbMaggie;
	
	@FXML
	private ImageView imageView;
	
	File f;
	
	@FXML
	public void classificarImg() {
		if(f != null) {
			
			double[] caracteristicas = ExtractCaracteristicas.extraiCaracteristicas(f);
			double[] caracteristicasBase = ClassificaCaracteristicas.naiveBayes(caracteristicas);

			double flanders = caracteristicasBase[0];
			double maggie = caracteristicasBase[1];
			
			System.out.println("Características "+Arrays.toString(caracteristicas));
			
			lblMarromCabeloFlanders.setText("Marrom Cabelo: "+caracteristicas[0]);
			lblRosaCamisaFlanders.setText("Rosa Camisa: "+caracteristicas[1]);
			lblVerdeSueterFlanders.setText("Verde Suéter: "+caracteristicas[2]);
	        //------------------------------------------------------
			lblAzulLacoMaggie.setText("Azul Laço: "+caracteristicas[3]);
			lblVermelhoBicoMaggie.setText("Vermelho Bico: "+caracteristicas[4]);
			lblAzulRoupaMaggie.setText("Azul Roupa: "+caracteristicas[5]);
	        
			flanders = flanders*100;
			maggie = maggie*100;
			
			lblProbFlanders.setText("Flanders: "+flanders);
			lblProbMaggie.setText("Maggie: "+maggie);
		}
	}
	
	@FXML
	public void extrairCaracteristicas() {
		ExtractCaracteristicas.extrair();
	}
	
	@FXML
	public void selecionaImagem() {
		f = buscaImg();
		if(f != null) {
			Image img = new Image(f.toURI().toString());
			imageView.setImage(img);
			imageView.setFitWidth(img.getWidth());
			imageView.setFitHeight(img.getHeight());
		}
	}
	
	private File buscaImg() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new 
				   FileChooser.ExtensionFilter(
						   "Imagens", "*.jpg", "*.JPG", 
						   "*.png", "*.PNG", "*.gif", "*.GIF", 
						   "*.bmp", "*.BMP")); 	
		 fileChooser.setInitialDirectory(new File("src/imagens"));
		 File imgSelec = fileChooser.showOpenDialog(null);
		 try {
			 if (imgSelec != null) {
			    return imgSelec;
			 }
		 } catch (Exception e) {
			e.printStackTrace();
		 }
		 return null;
	}

}
