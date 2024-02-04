package net.southplus.southplusmaid;

import atlantafx.base.theme.PrimerDark;
import cn.tealc995.teaFX.stage.RoundStage;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.southplus.southplusmaid.config.Config;
import net.southplus.southplusmaid.factory.UIFactory;
import net.southplus.southplusmaid.model.MessageInfo;
import net.southplus.southplusmaid.model.MessageType;
import net.southplus.southplusmaid.service.BbsTaskService;
import net.southplus.southplusmaid.ui.MainUI;
import net.southplus.southplusmaid.ui.MainViewModel;
import net.southplus.southplusmaid.util.FXResourcesLoader;

import java.io.IOException;
import java.util.Timer;

public class Main extends Application {
    public static RoundStage stage;
    /*
    * --add-exports javafx.web/com.sun.webkit.network=net.southplus.southplusmaid
    * --add-opens java.base/java.net=ALL-UNNAMED
    * */

    @Override
    public void start(Stage primaryStage) throws IOException {
        if (Config.PROXY){
            System.setProperty("https.proxyHost",Config.PROXY_HOST);
            System.setProperty("https.proxyPort", String.valueOf(Config.PROXY_PORT));
            System.setProperty("https.proxyHost",Config.PROXY_HOST);
            System.setProperty("https.proxyPort",String.valueOf(Config.PROXY_PORT));
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

        startTask();



    }



    public static void exit(){
        Config.saveSetting();
        Platform.exit();
    }

    /**
     * @description: 领取任务，默认5s以后开始执行
     * @name: startTask
     * @author: Leck
     * @param:
     * @return  void
     * @date:   2024/2/4
     */
    public void startTask(){
        Timeline timeline=new Timeline(new KeyFrame(Duration.seconds(5),new KeyValue(new SimpleIntegerProperty(),5)));
        timeline.setOnFinished(actionEvent -> {
            BbsTaskService service=new BbsTaskService();
            service.run();
        });
        timeline.play();
    }


    public void checkSetting(){
       if (Config.COOKIE==null || Config.COOKIE.isEmpty()){
           MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.WARNING,"请先在配置文件中设置论坛Cookie"));
       }
        if (Config.USER_AGENT==null || Config.USER_AGENT.isEmpty()){
            MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.WARNING,"请先在配置文件中设置浏览器User-Agent"));
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