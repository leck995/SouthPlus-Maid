package net.southplus.southplusmaid.ui;

import atlantafx.base.controls.Card;
import atlantafx.base.theme.PrimerLight;
import atlantafx.base.theme.Styles;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import net.southplus.southplusmaid.config.Config;
import net.southplus.southplusmaid.ui.control.LocalWebView;
import net.southplus.southplusmaid.util.AnchorPaneUtil;
import net.southplus.southplusmaid.util.FXResourcesLoader;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class WebUI implements FxmlView<WebViewModel>, Initializable {
    @InjectViewModel
    private WebViewModel viewModel;
    @FXML
    private AnchorPane root;

    private WebView webView;
    private WebEngine engine;

    private SimpleStringProperty host;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalWebView localWebView=new LocalWebView();
        webView=localWebView.getWebView();
        engine=webView.getEngine();
        root.getChildren().add(webView);
        AnchorPaneUtil.setPosition(webView,0.0,20.0,0.0,0.0);

        host=new SimpleStringProperty();
        host.bind(viewModel.hostProperty());
        host.addListener((observableValue, s, t1) -> {
            if (t1 != null){
                engine.load(t1);
            }
        });

        Rectangle rectangle=new Rectangle();
        rectangle.widthProperty().bind(root.widthProperty().add(-20));
        rectangle.heightProperty().bind(root.heightProperty().add(-20));
        rectangle.setArcHeight(15);
        rectangle.setArcWidth(15);
        root.setClip(rectangle);



        engine.locationProperty().addListener((observableValue, s, t1) -> {
            if (t1!= null && t1.contains("baidu.com")){
                try {
                    URI uri=new URI(t1);
                    Desktop.getDesktop().browse(uri);
                } catch (URISyntaxException | IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });

    }

}
