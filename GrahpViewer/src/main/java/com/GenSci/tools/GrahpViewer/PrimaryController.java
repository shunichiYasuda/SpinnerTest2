package com.GenSci.tools.GrahpViewer;

import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
//import javafx.embed.swing.SwingFXUtils;

public class PrimaryController implements Initializable {
	// private static final double RADIUS = 10_000f;
	// private DoubleProperty scale = new SimpleDoubleProperty(1.0);
	@FXML
	Button openAveBtn;
	@FXML
	Button openTypeBtn;
	@FXML
	Button drawBtn;
	@FXML
	Button saveBtn;
	@FXML
	TextArea log;
	@FXML
	Button quitBtn;
	@FXML
	Canvas aveCanvas;
	@FXML
	Canvas typeCanvas;
	@FXML
	Spinner<Integer> expSpinner;
	@FXML
	Slider aveSlider;
	@FXML
	Slider typeSlider;

	//
	GraphicsContext gc;
	//
	double width, height;// canvas の横幅と高さ
	boolean aveDataFlag = false; //平均データを読み込んだら true にする。
	String dir = null; //データのディレクトリ
	//
	List<String> aveDataStr = new ArrayList<String>();// ファイルから読み込んだ平均値テーブル
	List<String> typeDataStr = new ArrayList<String>();// ファイルから読み込んだタイプ別個体比率テーブル
	//
	int EXP = 0; // 実験回数
	int GEN = 0; // 世代数
	double[][] aveDataTable; // データテーブル。
	//
	
	//
	public void quitAction() {
		System.exit(0);
	}

	//
	public void draw(Canvas c, int[] data) {

	}

	//
	public void execAction() {
		width = aveCanvas.getWidth();
		height = aveCanvas.getHeight();
		log.appendText("Hello World.W= " + width + "   H=" + height + "\n");

		gc = aveCanvas.getGraphicsContext2D();
		gc.setStroke(Color.DARKBLUE);
		gc.strokeLine(0, 0, 100, 100); // (1)
		//
		//データを表示させてみる
		log.appendText("世代数:"+GEN+"\t実験回数:"+EXP+"\n");
		if(aveDataFlag) {
			for(int i=0;i<GEN;i++) {
				for(int j=0;j<EXP;j++) {
					log.appendText(aveDataTable[i][j]+",");
				}
				log.appendText("\n");
			}
		}

	}

	//
	public void openAveFile() {
		openAction(aveDataStr);
		// 1行取り出す
		String str = aveDataStr.get(0);
		//log.appendText(str + "\n");
		String[] row = str.split("\t");
		//log.appendText(row.length + "\tgen=" + aveDataStr.size() + "\n");
		//
		EXP = row.length;
		GEN = aveDataStr.size();
		//データを double配列にしまい込む。
		aveDataTable = new double[GEN][EXP];
		for(int i=0;i<GEN;i++) {
			str = aveDataStr.get(i); //一行読み込んだ
			row = str.split("\t"); //tab 区切りで列を分けた
			for(int j=0;j<EXP;j++) {
				aveDataTable[i][j] = Double.parseDouble(row[j]);
			}
		}//end of for( double 配列にしまいこむ
		aveDataFlag = true;
		//double 配列にデータが入ったので、スピナーから実験番号を読んでその列を別の配列に移す。
		SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, EXP-1, 1);
        expSpinner.setValueFactory(valueFactory1);
		
	}

	public void openTypeFile() {
		openAction(typeDataStr);
	}

	//
	public void openAction(List<String> list) {
		FileChooser fc= new FileChooser();
		if(dir != null) {
			fc.setInitialDirectory(new File(dir));
		}	 
		fc.setTitle("open data file");
		File file = fc.showOpenDialog(null);
		dir = file.getAbsolutePath();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while ((line = br.readLine()) != null) {
				list.add(line);
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
		System.out.println("is file null ? " + file);
		if (file != null) {
			WritableImage writableImage = new WritableImage((int) width, (int) height);
			aveCanvas.snapshot(null, writableImage);
			RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
			try {
				ImageIO.write(renderedImage, "png", file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		expSpinner.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
}
