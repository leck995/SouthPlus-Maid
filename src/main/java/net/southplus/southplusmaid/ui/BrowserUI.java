package net.southplus.southplusmaid.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;

import java.net.URL;
import java.util.ResourceBundle;

public class BrowserUI implements Initializable {
    @FXML
    private WebView webView;
    @FXML
    private Button loadBtn;
    @FXML
    private ToggleButton analysisBtn;
    private WebEngine engine;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        engine=webView.getEngine();
        engine.load("https://www.south-plus.net/thread.php?fid-128-type-2-page-3.html");
        loadBtn.setOnAction(event -> webView.getEngine().load("https://www.south-plus.net/thread.php?fid-128-type-2-page-3.html"));
        analysisBtn.setOnAction(event -> {
            Document document = engine.getDocument();
            System.out.println(document.toString());
        });
    }
}
