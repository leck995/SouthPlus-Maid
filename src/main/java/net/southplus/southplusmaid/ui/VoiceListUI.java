package net.southplus.southplusmaid.ui;

import atlantafx.base.controls.ModalPane;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.MvvmFX;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import net.southplus.southplusmaid.config.Config;
import net.southplus.southplusmaid.factory.UIFactory;
import net.southplus.southplusmaid.model.BbsItem;
import net.southplus.southplusmaid.model.BbsTitle;
import net.southplus.southplusmaid.service.DLSiteThread;
import net.southplus.southplusmaid.ui.cell.BigWorkCell;
import net.southplus.southplusmaid.ui.cell.SmallWorkCell;
import net.southplus.southplusmaid.ui.cell.WorkCell;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class VoiceListUI implements FxmlView<VoiceListViewModel>, Initializable {
    @InjectViewModel
    private VoiceListViewModel viewModel;
    @FXML
    private ListView<BbsTitle> titleListVIew;
    @FXML
    private Pagination pagination;
    @FXML
    private ScrollPane ItemScrollPane;
    @FXML
    private FlowPane itemFlowPane;

    @FXML
    private Label dialogLabel;

    @FXML
    private StackPane dialogPane;

    @FXML
    private ProgressBar dialogProgress;

    @FXML
    private ModalPane modalPane;

    @FXML
    private ToggleButton changItemViewBtn;
    @FXML
    private Button refreshBtn;
    @FXML
    private TextField pageTextField;

    private ViewTuple<WebUI, WebViewModel> webViewTuple;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        itemFlowPane.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                double deltaY= event.getDeltaY()*4;
                double height = ItemScrollPane.getContent().getBoundsInLocal().getHeight();
                double vvalue = ItemScrollPane.getVvalue();
                ItemScrollPane.setVvalue(vvalue + -deltaY/height);
                event.consume();
            }
        });

        titleListVIew.setItems(viewModel.getTitles());
        titleListVIew.setCellFactory(new Callback<>() {
            @Override
            public ListCell<BbsTitle> call(ListView<BbsTitle> bbsTitleListView) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(BbsTitle bbsTitle, boolean empty) {
                        super.updateItem(bbsTitle, empty);
                        if (!empty) {
                            setText(bbsTitle.getName());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
        titleListVIew.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> viewModel.setTitleIndex(t1.intValue()));


        changItemViewBtn.setSelected(Config.BBS_ITEM_SHOW_MODEL==0);
        changItemViewBtn.setOnAction(actionEvent -> {
            if (changItemViewBtn.isSelected()){
                Config.BBS_ITEM_SHOW_MODEL=0;
                changItemViewBtn.setText("详情");
            }else {
                Config.BBS_ITEM_SHOW_MODEL=1;
                changItemViewBtn.setText("简略");
            }
            changeItemView(changItemViewBtn.isSelected());
        });


        refreshBtn.setOnAction(actionEvent -> viewModel.refresh());

        pageTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d*")) {
                pageTextField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        pageTextField.setOnAction(actionEvent -> {
            int i = Integer.parseInt(pageTextField.getText()) - 1;
            if (i >= 0){
                viewModel.setPageIndex(i);
                pageTextField.setText(null);
                itemFlowPane.requestFocus();
            }
        });




        viewModel.getItems().addListener((ListChangeListener<? super BbsItem>) change -> {
            while (change.next()) {

                    if (change.wasAdded()) {
                        itemFlowPane.getChildren().clear();
                        ExecutorService executor = Executors.newFixedThreadPool(5, runnable -> {
                            Thread t = new Thread(runnable);
                            t.setDaemon(true);
                            return t ;
                        });
                        change.getAddedSubList().forEach(work -> {
                            if (work.getRjId() != null){
                                DLSiteThread dlSiteThread=new DLSiteThread(work.getRjId());

                                dlSiteThread.setOnSucceeded(workerStateEvent -> {
                                    work.setWork(dlSiteThread.getValue());
                                    if (Config.BBS_ITEM_SHOW_MODEL==0){
                                        itemFlowPane.getChildren().add(new BigWorkCell(work));
                                    }else {
                                        itemFlowPane.getChildren().add(new SmallWorkCell(work));
                                    }
                                });
                                executor.execute(dlSiteThread);
                            }else {
                                if (Config.BBS_ITEM_SHOW_MODEL==0){
                                    itemFlowPane.getChildren().add(new BigWorkCell(work));
                                }else {
                                    itemFlowPane.getChildren().add(new SmallWorkCell(work));
                                }
                            }

                     /*       if (Config.BBS_ITEM_SHOW_MODEL==0){
                                itemFlowPane.getChildren().add(new BigWorkCell(work));
                            }else {
                                itemFlowPane.getChildren().add(new SmallWorkCell(work));
                            }*/
                        });
                        executor.shutdown();
                    } else if (change.wasRemoved()) {
                        if (change.getRemovedSize() == 1) {
                            itemFlowPane.getChildren().remove(change.getFrom());
                        } else {
                            itemFlowPane.getChildren().clear();
                        }
                    }


            }
        });

        dialogPane.visibleProperty().bind(viewModel.dialogShowProperty());
        dialogProgress.progressProperty().bind(viewModel.loadingProgressProperty());
        pagination.pageCountProperty().bindBidirectional(viewModel.pageSizeProperty());
        pagination.currentPageIndexProperty().bindBidirectional(viewModel.pageIndexProperty());


        MvvmFX.getNotificationCenter().subscribe("host",(s, objects) -> {
            if (webViewTuple == null){
                webViewTuple= UIFactory.WebUI();
                AnchorPane view = (AnchorPane) webViewTuple.getView();
                view.maxWidthProperty().bind(modalPane.widthProperty().add(-200));
                view.maxHeightProperty().bind(modalPane.heightProperty().add(-100));
            }
            modalPane.show(webViewTuple.getView());
            webViewTuple.getViewModel().setHost((String) objects[0]);
        });

    }





    private void updateItemPane(int index, BbsItem replaceWork) {

        itemFlowPane.getChildren().set(index,new SmallWorkCell(replaceWork));

    }


    /**
     * @description: 切换Item视图
     * @name: changeItemView
     * @author: Leck
     * @param:	big
     * @return  void
     * @date:   2024/1/14
     */
    private void changeItemView(boolean big){
        for (int i = 0; i < itemFlowPane.getChildren().size(); i++) {
            WorkCell node = (WorkCell) itemFlowPane.getChildren().get(i);
            if (big){
                itemFlowPane.getChildren().set(i,new BigWorkCell(node.getItem(),node.getItemAlbum().getImage()));
            }else {
                itemFlowPane.getChildren().set(i,new SmallWorkCell(node.getItem(),node.getItemAlbum().getImage()));
            }
        }
    }

}



