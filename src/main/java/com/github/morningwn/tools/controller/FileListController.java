package com.github.morningwn.tools.controller;

import com.github.morningwn.tools.component.FileListComponent;
import com.github.morningwn.tools.utils.bean.BeanUtils;
import com.github.morningwn.tools.view.FileListCell;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author morningwn
 * @create in 2022/11/4 12:03
 */
public class FileListController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileListController.class);
    public ImageView lockImage;
    @FXML
    private Button changeLockBtn;
    @FXML
    private VBox layout;
    @FXML
    private Button hideBtn;
    @FXML
    private ListView<File> fileList;
    @FXML
    private HBox lab;
    private FileListComponent fileListComponent;
    private double oldStageX;
    private double oldStageY;
    private double oldScreenX;
    private double oldScreenY;
    private Boolean lockStatus = Boolean.TRUE;
    private Boolean hiden = Boolean.FALSE;

    @Override
    public void initialize(URL u, ResourceBundle resources) {
        fileListComponent = BeanUtils.getBean(FileListComponent.class);
        fileList.setCellFactory(param -> new FileListCell(this::onOpen, this::onDropOut));
        refresh();
        fileList.setOnDragDropped(event -> {
            LOGGER.info("OnDragDropped {}", event);
            Dragboard dragboard = event.getDragboard();
            List<File> files = dragboard.getFiles();
            fileListComponent.moveTo(files);
            refresh();
        });

        fileList.setOnDragOver(event -> {
            Dragboard dragboard = event.getDragboard();
            if (dragboard.hasFiles()) {
                event.acceptTransferModes(TransferMode.ANY);
            } else {
                event.consume();
            }
        });
    }

    private void refresh() {
        ObservableList<File> items = fileList.getItems();
        items.clear();
        items.addAll(fileListComponent.list());
        fileList.refresh();
    }

    public void onOpen(File file) {
        fileListComponent.open(file);
    }

    public void onDropOut(File file) {

    }

    public void onRefresh(ActionEvent actionEvent) {
        refresh();
    }

    public void onChangeLock(ActionEvent actionEvent) {
        LOGGER.info("checkLock");
//        if (lockStatus) {
//            lab.setOnMouseDragged(event -> {
//                Point location = MouseInfo.getPointerInfo().getLocation();
//                //Stage stage = ViewHolder.fileList();
//
//                double x = location.getX() - oldScreenX + oldStageX;
//                double y = location.getY() - oldScreenY + oldStageY;
//                stage.setX(x);
//                stage.setY(y);
//
////            LOGGER.info("setOnMouseDragged x: {} sceneX: {}, ex: {}, lx: {}, y: {}, sceneY: {}, ey: {}, ly:{}", x, event.getSceneX(), event.getX(), location.getX(), y, event.getSceneY(), event.getY(), location.getY());
//            });
//
//            lab.setOnMousePressed(event -> {
////            LOGGER.info("x: {}, y: {}", event.getX(), event.getY());
//                Stage stage = ViewHolder.fileList();
//                oldStageX = stage.getX();
//                oldStageY = stage.getY();
//                oldScreenX = event.getScreenX();
//                oldScreenY = event.getScreenY();
//            });
//            changeLockBtn.setText("锁定");
//            lockStatus = Boolean.FALSE;
//        } else {
//            lab.setOnMouseDragged(null);
//            lab.setOnMousePressed(null);
//            changeLockBtn.setText("解锁");
//            lockStatus = Boolean.TRUE;
//        }
    }

    public void onDelete(ActionEvent actionEvent) {

        refresh();
    }

    public void onHide(ActionEvent actionEvent) {
        if (hiden) {
            layout.getChildren().add(fileList);
            hideBtn.setText("隐藏");
            hiden = Boolean.FALSE;
        } else {
            layout.getChildren().remove(fileList);
            hideBtn.setText("显示");
            hiden = Boolean.TRUE;
        }
    }
}
