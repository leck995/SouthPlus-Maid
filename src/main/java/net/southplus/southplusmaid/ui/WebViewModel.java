package net.southplus.southplusmaid.ui;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;

public class WebViewModel implements ViewModel {
    private SimpleStringProperty host;
    public WebViewModel() {
        host=new SimpleStringProperty();



    }

    public void setHost(String host){
        this.host.set(null);
        this.host.set(host);
    }

    public String getHost() {
        return host.get();
    }

    public SimpleStringProperty hostProperty() {
        return host;
    }
}
