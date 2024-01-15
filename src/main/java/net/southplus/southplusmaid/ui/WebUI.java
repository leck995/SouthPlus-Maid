package net.southplus.southplusmaid.ui;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import net.southplus.southplusmaid.util.AnchorPaneUtil;

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
    private SimpleStringProperty host;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

}
