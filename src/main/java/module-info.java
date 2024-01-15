 module net.southplus.southplusmaid {
     requires javafx.controls;
     requires javafx.fxml;
     requires javafx.web;
     requires org.jsoup;
     requires de.saxsys.mvvmfx;
     requires com.fasterxml.jackson.databind;
     requires atlantafx.base;
     requires java.desktop;
     requires org.kordamp.ikonli.material2;
     requires org.kordamp.ikonli.javafx;
     requires org.kordamp.ikonli.core;
     requires jdk.crypto.cryptoki;

     opens net.southplus.southplusmaid to javafx.fxml;
     opens net.southplus.southplusmaid.ui to javafx.fxml,de.saxsys.mvvmfx;
     opens net.southplus.southplusmaid.model.dlsite to com.fasterxml.jackson.databind;
     exports net.southplus.southplusmaid.model to com.fasterxml.jackson.databind;
     exports net.southplus.southplusmaid;
     exports net.southplus.southplusmaid.model.dlsite;
}
