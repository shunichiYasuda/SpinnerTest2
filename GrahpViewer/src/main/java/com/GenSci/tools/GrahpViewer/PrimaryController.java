package com.GenSci.tools.GrahpViewer;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class PrimaryController {

    @FXML
    Button execBtn;
    @FXML
    TextArea log;
    @FXML
    Button quitBtn;
    @FXML
    Canvas canvas;
    
    //
    public void quitAction() {
    	System.exit(0);
    }
    //
    public void execAction() {
    	log.appendText("Hello World\n");
    }
}
