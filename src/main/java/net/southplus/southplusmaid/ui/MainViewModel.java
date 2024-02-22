package net.southplus.southplusmaid.ui;

import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewModel;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;
import net.southplus.southplusmaid.Main;
import net.southplus.southplusmaid.model.MessageInfo;
import net.southplus.southplusmaid.model.MessageType;
import net.southplus.southplusmaid.service.BbsTaskService;

public class MainViewModel implements ViewModel {

    public MainViewModel() {
        BbsTaskService taskService = new BbsTaskService();
        taskService.setDelay(Duration.seconds(5));
        taskService.setMaximumFailureCount(1);
        taskService.start();
    }

    public void exit(){
        Main.exit();
    }
}
