package net.southplus.southplusmaid.ui;

import atlantafx.base.controls.Message;
import atlantafx.base.controls.ModalPane;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import cn.tealc995.teaFX.controls.TitleBar;
import cn.tealc995.teaFX.enums.TitleBarStyle;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import net.southplus.southplusmaid.Main;
import net.southplus.southplusmaid.factory.UIFactory;
import net.southplus.southplusmaid.model.MessageInfo;
import net.southplus.southplusmaid.model.MessageType;
import net.southplus.southplusmaid.util.AnchorPaneUtil;
import net.southplus.southplusmaid.util.FXResourcesLoader;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.material2.Material2OutlinedAL;
import org.kordamp.ikonli.material2.Material2OutlinedMZ;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainUI implements FxmlView<MainViewModel>, Initializable {
    @InjectViewModel
    private MainViewModel viewModel;
    @FXML
    private AnchorPane root;
    @FXML
    private VBox messagePane;



    private TitleBar titleBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TitleBar titleBar=new TitleBar(Main.stage, TitleBarStyle.ALL);
        titleBar.setCloseEvent(mouseEvent -> {
            viewModel.exit();
        });

      /*  Button settingBtn = new Button();
        settingBtn.setGraphic(new Region());
        settingBtn.getStyleClass().add("setting-btn");
        settingBtn.setOnAction(action -> {
         *//*   Pane item = null;
            try {
                item = FXMLLoader.load(FXResourcesLoader.loadURL(FxmlPaths.SETTING));
                AlertFactory.showDefault(0, item, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*//*
        });
        titleBar.getTitleBarRightPane().getChildren().add(0, settingBtn);*/

        titleBar.setTitle("South+ Maid");
        titleBar.setTitleIcon(new ImageView(new Image(FXResourcesLoader.load("/net/southplus/southplusmaid/image/icon.png"),37,37,true,true,true)));
        ViewTuple<VoiceListUI, VoiceListViewModel> viewTuple = UIFactory.VoiceListUI();
        titleBar.setContent(viewTuple.getView());
        root.getChildren().add(0,titleBar);
        AnchorPaneUtil.setPosition(titleBar,0.0,0.0,0.0,0.0);



       // messagePane.setMouseTransparent(true);
        MvvmFX.getNotificationCenter().subscribe("message",((s, objects) -> {
            System.out.println("获得message");
            MessageInfo info= (MessageInfo) objects[0];
            Message message = createMessage(info);
            message.setOnClose(e -> {
                var out = Animations.slideOutRight(message, Duration.millis(250));
                out.setOnFinished(f -> messagePane.getChildren().remove(message));
                out.playFromStart();
            });
            Platform.runLater(() -> messagePane.getChildren().add(message));

            Animations.slideInRight(message, Duration.millis(250)).playFromStart();

            Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    var out = Animations.slideOutRight(message, Duration.millis(250));
                    out.setOnFinished(f -> messagePane.getChildren().remove(message));
                    out.playFromStart();
                }
            }));
            fiveSecondsWonder.play();
        }));



    }




    private Message createMessage(MessageInfo messageInfo){
        Message message = null;
        switch (messageInfo.getType()){
            case SUCCESS -> {
                message = new Message(
                        "Success",
                        messageInfo.getMessage(),
                        new FontIcon(Material2OutlinedAL.CHECK_CIRCLE_OUTLINE)
                );
                message.getStyleClass().add(Styles.SUCCESS);
            }
            case WARNING -> {
                message = new Message(
                        "Warning",
                        messageInfo.getMessage(),
                        new FontIcon(Material2OutlinedMZ.OUTLINED_FLAG)
                );
                message.getStyleClass().add(Styles.WARNING);
            }
            case INFO -> {
                message = new Message(
                        "Info",
                        messageInfo.getMessage(),
                        new FontIcon(Material2OutlinedAL.HELP_OUTLINE)
                );
                message.getStyleClass().add(Styles.ACCENT);
            }
            case ERROR -> {
                message = new Message(
                        "ERROR",
                        messageInfo.getMessage(),
                        new FontIcon(Material2OutlinedAL.ERROR_OUTLINE)
                );
                message.getStyleClass().add(Styles.DANGER);
            }
        }

        message.setPrefSize(350,85);
        message.setMaxSize(350,85);
        return message;
    }


}
