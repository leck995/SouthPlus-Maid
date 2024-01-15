package net.southplus.southplusmaid.model;

import java.util.List;

public class BbsInfo {
    private List<BbsTitle> titles;
    private List<BbsItem> items;
    private Integer pageIndex;
    private Integer pageSize;


    public List<BbsTitle> getTitles() {
        return titles;
    }

    public void setTitles(List<BbsTitle> titles) {
        this.titles = titles;
    }

    public List<BbsItem> getItems() {
        return items;
    }

    public void setItems(List<BbsItem> items) {
        this.items = items;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
