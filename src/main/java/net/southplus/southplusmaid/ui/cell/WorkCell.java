package net.southplus.southplusmaid.ui.cell;

import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import net.southplus.southplusmaid.model.BbsItem;

/**
 * @program: SouthPlusMaid
 * @description:
 * @author: Leck
 * @create: 2024-01-14 04:02
 */
public class WorkCell extends VBox {
    ImageView itemAlbum;
    BbsItem item;

    public ImageView getItemAlbum() {
        return itemAlbum;
    }

    public void setItemAlbum(ImageView itemAlbum) {
        this.itemAlbum = itemAlbum;
    }

    public BbsItem getItem() {
        return item;
    }

    public void setItem(BbsItem item) {
        this.item = item;
    }
}