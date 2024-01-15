package net.southplus.southplusmaid;

import atlantafx.base.theme.PrimerDark;
import cn.tealc995.teaFX.stage.RoundStage;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import net.southplus.southplusmaid.config.Config;
import net.southplus.southplusmaid.factory.UIFactory;
import net.southplus.southplusmaid.model.MessageInfo;
import net.southplus.southplusmaid.model.MessageType;
import net.southplus.southplusmaid.ui.MainUI;
import net.southplus.southplusmaid.ui.MainViewModel;
import net.southplus.southplusmaid.util.FXResourcesLoader;

import java.io.IOException;

public class Main extends Application {
    public static RoundStage stage;
    /*
    * --add-exports javafx.web/com.sun.webkit.network=net.southplus.southplusmaid
    * --add-opens java.base/java.net=ALL-UNNAMED
    * */

    @Override
    public void start(Stage primaryStage) throws IOException {
        if (Config.PROXY){
            System.setProperty("https.proxyHost","127.0.0.1");
            System.setProperty("https.proxyPort","7890");
            System.setProperty("https.proxyHost","127.0.0.1");
            System.setProperty("https.proxyPort","7890");
        }
        stage=new RoundStage();
        ViewTuple<MainUI, MainViewModel> viewTuple = UIFactory.MainUI();
        stage.setContent((Region) viewTuple.getView());


        stage.setWidth(Config.WIDTH);
        stage.setHeight(Config.HEIGHT);
        stage.setTitle("South-Plus Maid");
        stage.getIcons().add(new Image(FXResourcesLoader.load("/net/southplus/southplusmaid/image/icon.png")));
        stage.show();


        Application.setUserAgentStylesheet(new PrimerDark().getUserAgentStylesheet());
        checkSetting();


    }



    public static void exit(){
        Config.saveSetting();
        Platform.exit();
    }



    public void checkSetting(){
       if (Config.COOKIE==null || Config.COOKIE.isEmpty()){
           MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.WARNING,"请先在配置文件中设置论坛Cookie"));
       }
        if (Config.SOUTH_PLUS_HOST==null || Config.SOUTH_PLUS_HOST.isEmpty()){
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.WARNING,"请先在配置文件中设置论坛网址"));
        }
        if (Config.DLSITE_HOST==null || Config.DLSITE_HOST.isEmpty()){
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.WARNING,"请先在配置文件中设置DL站网址"));
        }
    }

    public static void main(String[] args) {
        launch();
    }
}