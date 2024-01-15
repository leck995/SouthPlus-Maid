package net.southplus.southplusmaid.model;

import net.southplus.southplusmaid.model.dlsite.DLSiteWork;

public class BbsItem {
    private String rjId;
    private String title;
    private NetDisk disk;
    private DLSiteWork work;
    private String url;


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
