package com.github.morningwn.tools.view.stage;

import com.github.morningwn.tools.controller.Controller;
import com.github.morningwn.tools.utils.LoadResourceUtils;
import com.github.morningwn.tools.utils.Pair;
import com.github.morningwn.tools.utils.StrUtils;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author morningwn
 * @create in 2023/1/18 16:31
 */
public abstract class AbstractStageHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractStageHolder.class);
    private static final Image ICON = new Image("application.png");

    private final Stage stage;
    private final ViewEnum viewEnum;
    private final Controller controller;
    private final Scene scene;

    public AbstractStageHolder(ViewEnum name, Stage mainStage) {
        this(new Stage(), name, mainStage);
    }

    public AbstractStageHolder(Stage stage, ViewEnum viewEnum, Stage mainStage) {
        Pair<Scene, Controller> pair = loadScene(viewEnum.getName());
        this.stage = stage;
        this.viewEnum = viewEnum;
        this.scene = pair.getL();
        this.scene.setFill(viewEnum.getSceneFull());
        this.controller = pair.getR();

        if (StrUtils.isNotBlank(viewEnum.getTitle())) {
            this.stage.setTitle(viewEnum.getTitle());
        }
        this.stage.initStyle(viewEnum.getStyle());
        if (StageStyle.TRANSPARENT.equals(viewEnum.getStyle())) {
            this.stage.initOwner(mainStage);
        }
        if (viewEnum.getDefaultPosition() != null) {
            this.stage.setX(viewEnum.getDefaultPosition().getX());
            this.stage.setY(viewEnum.getDefaultPosition().getY());
        }
        if (viewEnum.getDefaultSize() != null) {
            this.stage.setMinWidth(viewEnum.getDefaultSize().getX());
            this.stage.setWidth(viewEnum.getDefaultSize().getX());
            this.stage.setMinHeight(viewEnum.getDefaultSize().getY());
            this.stage.setHeight(viewEnum.getDefaultSize().getY());
        }
        this.stage.setScene(scene);
        this.stage.getIcons().add(ICON);
    }

    protected Pair<Scene, Controller> loadScene(String viewName) {
        FXMLLoader fxmlLoader = new FXMLLoader(LoadResourceUtils.loadView(viewName));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Object c = fxmlLoader.getController();
            Controller controller = null;
            if (c instanceof Controller) {
                controller = (Controller) c;
            }
            return Pair.of(scene, controller);
        } catch (IOException e) {
            LOGGER.error("loadScene fail", e);
        }
        return Pair.of(null, null);
    }

    public void show() {
        if (stage == null || stage.isShowing()) {
            return;
        }
        stage.show();
        controller.show();
    }

    public void hidden() {
        if (stage == null || !stage.isShowing()) {
            return;
        }
        Platform.runLater(stage::hide);
        controller.hidden();
    }

    public void close() {
        this.hidden();
        controller.close();
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }

    public ViewEnum getViewEnum() {
        return viewEnum;
    }

    public Controller getController() {
        return controller;
    }
}
