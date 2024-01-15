package net.southplus.southplusmaid.model.dlsite;

public class DLImage {
    public static final String HEAD="https:";
    private String file_name;
    private String extension;
    private String url;

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getUrl() {
        return HEAD+url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
