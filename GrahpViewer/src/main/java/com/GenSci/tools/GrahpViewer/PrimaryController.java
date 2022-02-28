package com.GenSci.tools.GrahpViewer;

import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
//import javafx.embed.swing.SwingFXUtils;

public class PrimaryController {
	// private static final double RADIUS = 10_000f;
	// private DoubleProperty scale = new SimpleDoubleProperty(1.0);
	@FXML
	Button openBtn;
	@FXML
	Button execBtn;
	@FXML
	Button saveBtn;
	@FXML
	TextArea log;
	@FXML
	Button quitBtn;
	@FXML
	Canvas canvas;
	//
	GraphicsContext gc;
	//
	double width,height;//canvas の横幅と高さ
	//
	List<String> dataStr = new ArrayList<String>();//ファイルから読み込んだテーブル
	//
	int EXP =0; //実験回数
	int GEN = 0; //世代数
	double [][] dataTable ; //データテーブル。
	public void quitAction() {
		System.exit(0);
	}

	//
	public void execAction() {
		width = canvas.getWidth();
		height = canvas.getHeight();
		log.appendText("Hello World.W= " + width + "   H=" + height + "\n");
		
		gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.DARKBLUE);
		gc.strokeLine(0, 0, 100, 100); // (1)
		//
		//列数を数えてみる
		//1行取り出す
		String str = dataStr.get(0);
		log.appendText(str+"\n");
		String[] row = str.split("\t");
		log.appendText(row.length+"\tgen="+dataStr.size()+"\n");
		//
		EXP = row.length;
		GEN = dataStr.size();
		
	}
	//
	public void openAction() {
		FileChooser fc = new FileChooser();
		fc.setTitle("open data file");
		File file  = fc.showOpenDialog(null);
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while((line =br.readLine())!=null) {
				dataStr.add(line);
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}// end of openAction();
	//
	public void saveAction() {
		 
		FileChooser savefile = new FileChooser();
         savefile.setTitle("Save File");

         File file = savefile.showSaveDialog(null);
         System.out.println("is file null ? "+ file);
         if (file != null) {
             WritableImage writableImage = new WritableImage((int)width, (int)height);
			 canvas.snapshot(null, writableImage);
			 RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
			 try {
				ImageIO.write(renderedImage, "png", file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         }
	}
}
