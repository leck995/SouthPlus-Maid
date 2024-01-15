package net.southplus.southplusmaid.model;

public class BbsTitle {
    private String name;
    private String url;

    public BbsTitle(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BbsTitle{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
