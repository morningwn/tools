package com.github.morningwn.tools.view.stage;

import javafx.stage.Stage;

/**
 * @author morningwn
 * @create in 2023/1/18 19:42
 */
public class BaseStageHolder extends AbstractStageHolder{
    public BaseStageHolder(ViewEnum name, Stage mainStage) {
        super(name, mainStage);
    }

    public BaseStageHolder(Stage stage, ViewEnum name, Stage mainStage) {
        super(stage, name, mainStage);
    }
}
