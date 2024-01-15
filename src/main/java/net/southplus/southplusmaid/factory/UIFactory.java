package net.southplus.southplusmaid.factory;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import net.southplus.southplusmaid.ui.*;

public class UIFactory {
    public static ViewTuple<VoiceListUI, VoiceListViewModel> VoiceListUI(){
        return FluentViewLoader.fxmlView(VoiceListUI.class).load();
    }

    public static ViewTuple<MainUI, MainViewModel> MainUI(){
        return FluentViewLoader.fxmlView(MainUI.class).load();
    }

    public static ViewTuple<WebUI, WebViewModel> WebUI(){
        return FluentViewLoader.fxmlView(WebUI.class).load();
    }
}
