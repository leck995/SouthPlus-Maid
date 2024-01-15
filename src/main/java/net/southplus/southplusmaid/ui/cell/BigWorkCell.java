package net.southplus.southplusmaid.ui.cell;


import de.saxsys.mvvmfx.MvvmFX;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import net.southplus.southplusmaid.config.Config;
import net.southplus.southplusmaid.model.BbsItem;
import net.southplus.southplusmaid.model.MessageInfo;
import net.southplus.southplusmaid.model.MessageType;
import net.southplus.southplusmaid.model.dlsite.DLActor;
import net.southplus.southplusmaid.model.dlsite.DLGenre;
import net.southplus.southplusmaid.model.dlsite.DLSiteWork;
import net.southplus.southplusmaid.util.AnchorPaneUtil;
import net.southplus.southplusmaid.util.FXResourcesLoader;


import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @program: Asmr-Online
 * @description:
 * @author: Leck
 * @create: 2023-07-12 21:53
 */
public class BigWorkCell extends WorkCell {
    private static final Image BASE_IMAGE=new Image(FXResourcesLoader.load("/net/southplus/southplusmaid/image/cover-main.jpg"));
    public BigWorkCell(BbsItem item){
        super.item =item;
        DLSiteWork work = item.getWork();

        itemAlbum = new ImageView();
        itemAlbum.setFitWidth(Config.BBS_ITEM_BIG_WIDTH);
        itemAlbum.setFitHeight(Config.BBS_ITEM_BIG_HEIGHT);
        itemAlbum.setSmooth(true);
        itemAlbum.setPreserveRatio(true);
        itemAlbum.getStyleClass().add("list-item-image");
        Rectangle rectangle=new Rectangle(Config.BBS_ITEM_BIG_WIDTH,Config.BBS_ITEM_BIG_HEIGHT);
        rectangle.setArcHeight(15);
        rectangle.setArcWidth(15);
        itemAlbum.setClip(rectangle);

        Label itemTitle = new Label();
        itemTitle.getStyleClass().add("list-item-title");
        itemTitle.setWrapText(true);

        itemTitle.setFont(Font.font(20));




        Label circleLabel = new Label();
        circleLabel.setGraphic(new Region());
        circleLabel.getStyleClass().add("list-item-circle");

        Label rjLabel = new Label();
        rjLabel.getStyleClass().add("list-item-rj");

        Label dateLabel = new Label();
        dateLabel.getStyleClass().add("list-item-date");


        AnchorPane headPane = new AnchorPane(itemAlbum, rjLabel, dateLabel);
        headPane.getStyleClass().add("list-item-head-pane");
        AnchorPaneUtil.setPosition(rjLabel, 10.0, null, null, 10.0);
        AnchorPaneUtil.setPosition(dateLabel, null, 5.0, 5.0, null);


        HBox tagPane = new HBox();
        tagPane.setAlignment(Pos.CENTER_RIGHT);
        tagPane.setSpacing(10);


        getChildren().addAll(headPane, itemTitle, tagPane, circleLabel);
        setSpacing(10);
        setPrefWidth(Config.BBS_ITEM_BIG_WIDTH);
        getStyleClass().add("list-item-pane");

        if (work == null){
            itemAlbum.setImage(BASE_IMAGE);
            itemTitle.setText(item.getTitle());
            circleLabel.setVisible(false);
            rjLabel.setVisible(false);
            dateLabel.setVisible(false);
        }else {
            itemAlbum.setImage(new Image(work.getImage_main().getUrl(),Config.BBS_ITEM_BIG_WIDTH,Config.BBS_ITEM_BIG_HEIGHT,true,true,true));
            itemTitle.setText(work.getWork_name());
            circleLabel.setText(work.getMaker_name());
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


            Label nsfwLabel= new Label(work.getAge_category());
            nsfwLabel.setText(work.isNsfw() ? "NSFW":"全年龄");
            nsfwLabel.getStyleClass().add("list-item-tag-nsfw");
            tagPane.getChildren().add(nsfwLabel);


            FlowPane categoriesPane = new FlowPane();
            categoriesPane.setId("grid-item-categories");
            if (work.getGenres() != null) {
                categoriesPane.setVgap(5);
                categoriesPane.setHgap(5);
                Label label;
                for (DLGenre category : work.getGenres()) {
                    label = new Label(category.getName());
                    label.setAccessibleText("category");
                    label.getStyleClass().add("tag-label");
                    categoriesPane.getChildren().add(label);
                }
            }
            getChildren().add(categoriesPane);
            FlowPane vaPane = new FlowPane();
            vaPane.setId("grid-item-vas");
            vaPane.setMaxHeight(90);
            if (work.getCreaters() !=null && work.getCreaters().getVoice_by() != null) {
                vaPane.setVgap(5);
                vaPane.setHgap(5);
                Label label;
                for (DLActor va : work.getCreaters().getVoice_by()) {
                    label = new Label(va.getName());
                    label.setAccessibleText("va");
                    label.getStyleClass().add("actor-label");
                    vaPane.getChildren().add(label);
                }
            }
            getChildren().add(vaPane);
        }


        if (item.getDisk() != null) {
            Label diskLabel = new Label();
            diskLabel.getStyleClass().add("list-item-tag");
            diskLabel.setText(item.getDisk().getName());
            tagPane.getChildren().add(diskLabel);
        }


        Tooltip titleTip=new Tooltip(item.getTitle());
        titleTip.setPrefWidth(400);
        titleTip.setWrapText(true);
        titleTip.setShowDelay(Duration.millis(800));
        itemTitle.setTooltip(titleTip);

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


    public BigWorkCell(BbsItem item,Image image){
        super.item =item;
        DLSiteWork work = item.getWork();

        itemAlbum = new ImageView(image);
        itemAlbum.setFitWidth(Config.BBS_ITEM_BIG_WIDTH);
        itemAlbum.setFitHeight(Config.BBS_ITEM_BIG_HEIGHT);
        itemAlbum.setSmooth(true);
        itemAlbum.setPreserveRatio(true);
        itemAlbum.getStyleClass().add("list-item-image");
        Rectangle rectangle=new Rectangle(Config.BBS_ITEM_BIG_WIDTH,Config.BBS_ITEM_BIG_HEIGHT);

        rectangle.setArcHeight(15);
        rectangle.setArcWidth(15);
        itemAlbum.setClip(rectangle);

        Label itemTitle = new Label();
        itemTitle.getStyleClass().add("list-item-title");
        itemTitle.setWrapText(true);
        itemTitle.setFont(Font.font(20));




        Label circleLabel = new Label();
        circleLabel.setGraphic(new Region());
        circleLabel.getStyleClass().add("list-item-circle");

        Label rjLabel = new Label();
        rjLabel.getStyleClass().add("list-item-rj");

        Label dateLabel = new Label();
        dateLabel.getStyleClass().add("list-item-date");


        AnchorPane headPane = new AnchorPane(itemAlbum, rjLabel, dateLabel);
        headPane.getStyleClass().add("list-item-head-pane");
        AnchorPaneUtil.setPosition(rjLabel, 10.0, null, null, 10.0);
        AnchorPaneUtil.setPosition(dateLabel, null, 5.0, 5.0, null);


        HBox tagPane = new HBox();
        tagPane.setAlignment(Pos.CENTER_RIGHT);
        tagPane.setSpacing(10);


        getChildren().addAll(headPane, itemTitle, tagPane, circleLabel);
        setSpacing(10);
        setPrefWidth(Config.BBS_ITEM_BIG_WIDTH);
        getStyleClass().add("list-item-pane");

        if (work == null){
            itemTitle.setText(item.getTitle());
            circleLabel.setVisible(false);
            rjLabel.setVisible(false);
            dateLabel.setVisible(false);
        }else {
            itemTitle.setText(work.getWork_name());
            circleLabel.setText(work.getMaker_name());
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


            Label nsfwLabel= new Label(work.getAge_category());
            nsfwLabel.setText(work.isNsfw() ? "NSFW":"全年龄");
            nsfwLabel.getStyleClass().add("list-item-tag-nsfw");
            tagPane.getChildren().add(nsfwLabel);


            FlowPane categoriesPane = new FlowPane();
            categoriesPane.setId("grid-item-categories");
            if (work.getGenres() != null) {
                categoriesPane.setVgap(5);
                categoriesPane.setHgap(5);
                Label label;
                for (DLGenre category : work.getGenres()) {
                    label = new Label(category.getName());
                    label.setAccessibleText("category");
                    label.getStyleClass().add("tag-label");
                    categoriesPane.getChildren().add(label);
                }
            }
            getChildren().add(categoriesPane);
            FlowPane vaPane = new FlowPane();
            vaPane.setId("grid-item-vas");
            vaPane.setMaxHeight(90);
            if (work.getCreaters() !=null && work.getCreaters().getVoice_by() != null) {
                vaPane.setVgap(5);
                vaPane.setHgap(5);
                Label label;
                for (DLActor va : work.getCreaters().getVoice_by()) {
                    label = new Label(va.getName());
                    label.setAccessibleText("va");
                    label.getStyleClass().add("actor-label");
                    vaPane.getChildren().add(label);
                }
            }
            getChildren().add(vaPane);
        }

        if (item.getDisk() != null) {
            Label diskLabel = new Label();
            diskLabel.getStyleClass().add("list-item-tag");
            diskLabel.setText(item.getDisk().getName());
            tagPane.getChildren().add(diskLabel);
        }


        Tooltip titleTip=new Tooltip(item.getTitle());
        titleTip.setWrapText(true);
        titleTip.setPrefWidth(400);
        titleTip.setShowDelay(Duration.millis(800));
        itemTitle.setTooltip(titleTip);


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