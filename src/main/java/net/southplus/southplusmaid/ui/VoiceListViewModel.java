package net.southplus.southplusmaid.ui;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.southplus.southplusmaid.config.Config;
import net.southplus.southplusmaid.model.BbsItem;
import net.southplus.southplusmaid.model.BbsTitle;
import net.southplus.southplusmaid.service.BbsRowService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

public class VoiceListViewModel implements ViewModel {
    private static final String HOST="https://www.south-plus.net/";
    private static final String cookie= "_ga=GA1.2.736480112.1700998828; eb9e6_ck_info=%2F%09; eb9e6_cknum=AVUBAlRbBAVUA206UQkGUFIOAVtQBwRVVFNXB1MLBwgGUAUFAgQDCFcAVAY%3D; eb9e6_winduser=AVUGAVFSBThWBABWBAAHUVcAAVoBVldXD1UEAF0AVQJSBQUPAFEJBW8%3D; eb9e6_threadlog=%2C73%2C9%2C201%2C128%2C; _ga_3G0BZEH5V0=GS1.2.1704181033.15.1.1704181168.0.0.0; peacemaker=1; eb9e6_readlog=%2C2058769%2C2055538%2C2054811%2C2054269%2C2053873%2C2052430%2C2046826%2C2035308%2C2012659%2C; eb9e6_lastpos=other; eb9e6_ol_offset=136479; eb9e6_lastvisit=508%091704901838%09%2Fsearch.php%3F";
    private ObservableList<BbsItem> items;
    private ObservableList<BbsTitle> titles;
    private SimpleIntegerProperty pageIndex;
    private SimpleIntegerProperty pageSize;
    private static final Number PAGE_NUMBER=0.1;


    private SimpleIntegerProperty titleIndex;
    private SimpleIntegerProperty itemIndex;

    private BbsRowService service;
    private SimpleDoubleProperty loadingProgress;
    private SimpleBooleanProperty dialogShow;

    public VoiceListViewModel() {
        items= FXCollections.observableArrayList();
        titles= FXCollections.observableArrayList();
        pageIndex=new SimpleIntegerProperty();
        pageSize=new SimpleIntegerProperty();
        titleIndex=new SimpleIntegerProperty(0);
        itemIndex=new SimpleIntegerProperty(0);
        loadingProgress=new SimpleDoubleProperty();
        dialogShow=new SimpleBooleanProperty(false);
        service=new BbsRowService();
        service.valueProperty().addListener((observableValue, bbsInfo, t1) -> {
            if (t1 != null) {
                items.setAll(t1.getItems());
                pageIndex.set(t1.getPageIndex());
                if (t1.getPageSize() != null){
                    pageSize.set(t1.getPageSize());
                    if (titles.isEmpty()){
                        titles.setAll(t1.getTitles()); //因为只服务于一个板块，所以这里不需要随时更新
                    }
                }
            }
        });

        loadingProgress.bind(service.progressProperty());
        dialogShow.bind(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return Boolean.valueOf(service.getTitle());
            }
        },service.titleProperty()));



        service.init(BbsRowService.TITLE);
        titleIndex.addListener((observableValue, number, t1) -> {
            if (t1.intValue() >= 0)
                changeTitle();
        });

        pageIndex.addListener((observableValue, number, t1) -> {
           setPage(true);
        });
    }


    /**
     * @description: 切换子栏目
     * @name: changeTitle
     * @author: Leck
     * @param:
     * @return  void
     * @date:   2024/1/14
     */
    private void changeTitle(){
        if (getPageIndex()==0){
            setPage(false);
        }else {
            pageIndex.set(0);
        }
    }



    /**
     * @description: 刷新，当初始化网络错误导致的刷新，则使用 service.init(),否则当作翻页行为刷新
     * @name: refresh
     * @author: Leck
     * @param:
     * @return  void
     * @date:   2024/1/14
     */
    public void refresh(){
        if (titles.isEmpty()){
            service.init(BbsRowService.TITLE);
        }else {
            String url = titles.get(getTitleIndex()).getUrl();
            service.changePage(url,getPageIndex(),true);
        }

    }



    /**
     * @description:
     * @param isPageEvent 判断是否是翻页行为，否为切换栏目行为
     * @return: void
     * @author: leck
     * @time: 2024/1/11 22:45
     */
    public void setPage(boolean isPageEvent){
        String url = titles.get(getTitleIndex()).getUrl();
        service.changePage(url,getPageIndex(),isPageEvent);
    }








    public ObservableList<BbsItem> getItems() {
        return items;
    }

    public ObservableList<BbsTitle> getTitles() {
        return titles;
    }

    public int getPageIndex() {
        return pageIndex.get();
    }

    public SimpleIntegerProperty pageIndexProperty() {
        return pageIndex;
    }

    public int getPageSize() {
        return pageSize.get();
    }

    public SimpleIntegerProperty pageSizeProperty() {
        return pageSize;
    }

    public int getTitleIndex() {
        return titleIndex.get();
    }

    public SimpleIntegerProperty titleIndexProperty() {
        return titleIndex;
    }

    public int getItemIndex() {
        return itemIndex.get();
    }

    public SimpleIntegerProperty itemIndexProperty() {
        return itemIndex;
    }

    public void setTitleIndex(int titleIndex) {
        this.titleIndex.set(titleIndex);
    }

    public void setItemIndex(int itemIndex) {
        this.itemIndex.set(itemIndex);
    }

    public double getLoadingProgress() {
        return loadingProgress.get();
    }

    public SimpleDoubleProperty loadingProgressProperty() {
        return loadingProgress;
    }

    public boolean isDialogShow() {
        return dialogShow.get();
    }

    public SimpleBooleanProperty dialogShowProperty() {
        return dialogShow;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex.set(pageIndex);
    }


}
