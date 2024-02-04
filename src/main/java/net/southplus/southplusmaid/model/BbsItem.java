package net.southplus.southplusmaid.model;

import net.southplus.southplusmaid.config.Config;
import net.southplus.southplusmaid.model.dlsite.DLSiteWork;

public class BbsItem {
    private String rjId;
    private String title;
    private NetDisk disk;
    private DLSiteWork work;
    private String url;

    private String type; //获取类型，简体，繁体，翻译，AI等等

    public String getType(){
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRjId() {
        return rjId;
    }

    public void setRjId(String rjId) {
        this.rjId = rjId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NetDisk getDisk() {
        return disk;
    }

    public void setDisk(NetDisk disk) {
        this.disk = disk;
    }

    public DLSiteWork getWork() {
        return work;
    }

    public void setWork(DLSiteWork work) {
        this.work = work;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
