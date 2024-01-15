package net.southplus.southplusmaid.ui.cell;


import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import net.southplus.southplusmaid.config.Config;
import net.southplus.southplusmaid.model.BbsItem;
import net.southplus.southplusmaid.model.MessageInfo;
import net.southplus.southplusmaid.model.MessageType;
import net.southplus.southplusmaid.model.dlsite.DLSiteWork;
import net.southplus.southplusmaid.util.AnchorPaneUtil;
import net.southplus.southplusmaid.util.FXResourcesLoader;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class SmallWorkCell extends WorkCell{

    private static final Image BASE_IMAGE=new Image(FXResourcesLoader.load("/net/southplus/southplusmaid/image/cover-thumb.jpg"));
    public SmallWorkCell(BbsItem item) {
        super.item =item;
        DLSiteWork work = item.getWork();
        itemAlbum=new ImageView();
        Rectangle rectangle=new Rectangle(240,180);
        rectangle.setArcHeight(15);
        rectangle.setArcWidth(15);
        itemAlbum.setClip(rectangle);


        Label titleLabel=new Label();
        titleLabel.setFont(Font.font(16));
        titleLabel.setWrapText(true);
        titleLabel.getStyleClass().add("list-item-title");

        Label rjLabel = new Label();
        rjLabel.getStyleClass().add("list-item-rj");

        Label dateLabel = new Label();
        dateLabel.getStyleClass().add("list-item-date");


        AnchorPane headPane = new AnchorPane(itemAlbum, rjLabel, dateLabel);
        headPane.getStyleClass().add("list-item-head-pane");
        AnchorPaneUtil.setPosition(rjLabel, 10.0, null, null, 10.0);
        AnchorPaneUtil.setPosition(dateLabel, null, 5.0, 5.0, null);


        if (work == null){
            itemAlbum.setImage(BASE_IMAGE);
            titleLabel.setText(item.getTitle());
            rjLabel.setVisible(false);
            dateLabel.setVisible(false);
        }else {
            itemAlbum.setImage(new Image(work.getImage_thumb(),true));
            titleLabel.setText(work.getWork_name());
            rjLabel.setText(work.getWorkno());
            rjLabel.setOnMouseClicked(mouseEvent -> {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(work.getWorkno());
                clipboard.setContent(content);

                MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.SUCCESS,String.format("%s:已经复制到剪切板", work.getWorkno())));
                mouseEvent.consume();
            });
            dateLabel.setText(work.getRegist_date());

        }
        Tooltip titleTip=new Tooltip(item.getTitle());
        titleTip.setWrapText(true);
        titleTip.setPrefWidth(400);
        titleTip.setShowDelay(Duration.millis(800));
        titleLabel.setTooltip(titleTip);




        getChildren().addAll(headPane,titleLabel);
        setSpacing(5);
        setPrefWidth(240);
        getStyleClass().add("list-item-pane");

        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                if (Config.BROWSER_TYPE){
                    MvvmFX.getNotificationCenter().publish("host",Config.SOUTH_PLUS_HOST+ "/" +item.getUrl());
                }else {
                    try {
                        URI uri=new URI(Config.SOUTH_PLUS_HOST+ "/" +item.getUrl());
                        Desktop.getDesktop().browse(uri);
                    } catch (URISyntaxException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public SmallWorkCell(BbsItem item,Image image) {
        super.item =item;
        DLSiteWork work = item.getWork();
        itemAlbum=new ImageView(image);
        itemAlbum.setFitWidth(240);
        itemAlbum.setFitHeight(180);
        Rectangle rectangle=new Rectangle(240,180);
        rectangle.setArcHeight(15);
        rectangle.setArcWidth(15);
        itemAlbum.setClip(rectangle);


        Label titleLabel=new Label();
        titleLabel.setFont(Font.font(16));
        titleLabel.setWrapText(true);
        titleLabel.getStyleClass().add("list-item-title");

        Label rjLabel = new Label();
        rjLabel.getStyleClass().add("list-item-rj");

        Label dateLabel = new Label();
        dateLabel.getStyleClass().add("list-item-date");


        AnchorPane headPane = new AnchorPane(itemAlbum, rjLabel, dateLabel);
        headPane.getStyleClass().add("list-item-head-pane");
        AnchorPaneUtil.setPosition(rjLabel, 10.0, null, null, 10.0);
        AnchorPaneUtil.setPosition(dateLabel, null, 5.0, 5.0, null);


        if (work == null){
            titleLabel.setText(item.getTitle());
            rjLabel.setVisible(false);
            dateLabel.setVisible(false);
        }else {
            titleLabel.setText(work.getWork_name());
            rjLabel.setText(work.getWorkno());
            rjLabel.setOnMouseClicked(mouseEvent -> {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(work.getWorkno());
                clipboard.setContent(content);

                MvvmFX.getNotificationCenter().publish("message",new MessageInfo(MessageType.SUCCESS,String.format("%s:已经复制到剪切板", work.getWorkno())));
                mouseEvent.consume();
            });
            dateLabel.setText(work.getRegist_date());

        }

        Tooltip titleTip=new Tooltip(item.getTitle());
        titleTip.setWrapText(true);
        titleTip.setPrefWidth(400);
        titleTip.setShowDelay(Duration.millis(800));
        titleLabel.setTooltip(titleTip);

        getChildren().addAll(headPane,titleLabel);
        setSpacing(5);
        setPrefWidth(240);
        getStyleClass().add("list-item-pane");

        setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY){
                if (Config.BROWSER_TYPE){
                    MvvmFX.getNotificationCenter().publish("host",Config.SOUTH_PLUS_HOST+ "/" +item.getUrl());
                }else {
                    try {
                        URI uri=new URI(Config.SOUTH_PLUS_HOST+ "/" +item.getUrl());
                        Desktop.getDesktop().browse(uri);
                    } catch (URISyntaxException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}