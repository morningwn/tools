//package com.github.morningwn.tools.view;
//
//import com.github.morningwn.tools.utils.Pair;
//import com.github.morningwn.tools.utils.WinUtils;
//import com.github.morningwn.tools.view.stage.AbstractStageHolder;
//import com.github.morningwn.tools.view.stage.ViewEnum;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import javafx.stage.StageStyle;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author morningwn
// * @create in 2022/9/7 17:22
// */
//public class ViewHolder {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ViewHolder.class);
//    private static final Map<String, Pair<Scene, Object>> sceneMap = new ConcurrentHashMap<>();
//    private static final Map<String, AbstractStageHolder> stageHolder = new ConcurrentHashMap<>();
//    private static String curViewName;
//    private static Stage mainStage;
//    private static Stage noteStage;
//    private static Stage timeStage;
//    private static Stage fileListStage;
//    private static Stage settingStage;
//    private static Stage wallpaperStage;
//
//    public static void setMainStage(Stage stage) {
//        stage.initStyle(StageStyle.UTILITY);
//        //stage采用UTILITY风格
//        stage.initStyle(StageStyle.UTILITY);
//        //将stage的透明度设置为0
//        stage.setOpacity(0);
//        //stage展示出来，此步骤不可少，缺少此步骤stage还是会存在任务栏
//        stage.show();
//        mainStage = stage;
//    }
//
//    public static Scene loadScene(String viewName) {
//        Pair<Scene, Object> sceneObjectPair = sceneMap.get(viewName);
//        if (sceneObjectPair != null) {
//            Object r = sceneObjectPair.getR();
//            if (r instanceof Initializable) {
//                ((Initializable) r).initialize(null, null);
//            }
//            return sceneObjectPair.getL();
//        }
//        FXMLLoader fxmlLoader = new FXMLLoader(ViewHolder.class.getResource(viewName));
//        try {
//            Scene scene = new Scene(fxmlLoader.load());
//            Object controller = fxmlLoader.getController();
//            sceneObjectPair = new Pair<>(scene, controller);
//        } catch (IOException e) {
//            LOGGER.error("loadScene fail", e);
//            return null;
//        }
//        sceneMap.put(viewName, sceneObjectPair);
//        return sceneObjectPair.getL();
//    }
//
//    public static void toNote() {
//        if (noteStage == null) {
//            noteStage = new Stage();
//            noteStage.initOwner(mainStage);
//            if (ViewEnum.NOTE.getName().equals(curViewName)) {
//                return;
//            }
//            curViewName = ViewEnum.NOTE.getName();
//            Scene scene = loadScene(ViewEnum.NOTE.getName());
//            noteStage.setResizable(false);
//            if (scene != null) {
//                noteStage.setScene(scene);
//                noteStage.show();
//            }
//        } else {
//            if (!noteStage.isShowing()) {
//                noteStage.show();
//            }
//        }
//
//    }
//
//    public static void toTime() {
//        //新建一个stage
//        if (timeStage != null) {
//            if (!timeStage.isShowing()) {
//                timeStage.show();
//            } else {
//                return;
//            }
//        }
//        timeStage = new Stage();
//        //设置拥有者
//        timeStage.initOwner(mainStage);
//        if (ViewEnum.TIME.equals(curViewName)) {
//            return;
//        }
//        curViewName = ViewEnum.TIME;
//        Scene scene = loadScene(ViewEnum.TIME);
//        if (scene != null) {
//            // 透明底框
//            scene.setFill(null);
//            timeStage.setX(WinUtils.getWidth() - 350);
//            timeStage.setY(50);
//            timeStage.initStyle(StageStyle.transparent);
//            timeStage.setResizable(false);
//            timeStage.setScene(scene);
//            timeStage.show();
//        }
//    }
//
//    public static void timeClose() {
//        if (timeStage != null && timeStage.isShowing()) {
//            timeStage.hide();
//            timeStage.close();
//        }
//    }
//
//    public static void noteClose() {
//        if (noteStage != null && noteStage.isShowing()) {
//            noteStage.hide();
//            noteStage.close();
//        }
//    }
//
//    public static void toFileList() {
//        //新建一个stage
//        if (fileListStage != null) {
//            if (!fileListStage.isShowing()) {
//                fileListStage.show();
//            } else {
//                return;
//            }
//        }
//        fileListStage = new Stage();
//        //设置拥有者
//        fileListStage.initOwner(mainStage);
//        if (ViewEnum.FILE_LIST.equals(curViewName)) {
//            return;
//        }
//        curViewName = ViewEnum.FILE_LIST;
//        Scene scene = loadScene(ViewEnum.FILE_LIST);
//        if (scene != null) {
//            // 透明底框
//            scene.setFill(null);
//            fileListStage.setX(WinUtils.getWidth() - 350);
//            fileListStage.setY(50);
//            fileListStage.initStyle(StageStyle.TRANSPARENT);
//            fileListStage.setScene(scene);
//            fileListStage.show();
//        }
//    }
//
//    public static Stage fileList() {
//        return fileListStage;
//    }
//
//    public static void toSetting() {
//        //新建一个stage
//        if (settingStage != null) {
//            if (!settingStage.isShowing()) {
//                settingStage.show();
//            } else {
//                return;
//            }
//        }
//        if (ViewEnum.SETTING.equals(curViewName)) {
//            return;
//        }
//        settingStage = new Stage();
//        curViewName = ViewEnum.SETTING;
//        //设置拥有者
//        settingStage.initOwner(mainStage);
//        Scene scene = loadScene(ViewEnum.SETTING);
//        if (scene != null) {
//            settingStage.setScene(scene);
//            settingStage.show();
//        }
//    }
//
//    public static void settingClose() {
//        if (settingStage != null && settingStage.isShowing()) {
//            settingStage.hide();
//            settingStage.close();
//        }
//    }
//
//    public static Stage setting() {
//        return settingStage;
//    }
//
//    public static void toWallpaper() {
//        //新建一个stage
//        if (wallpaperStage != null) {
//            if (!wallpaperStage.isShowing()) {
//                wallpaperStage.show();
//            } else {
//                return;
//            }
//        }
//        wallpaperStage = new Stage();
//        wallpaperStage.setTitle("Tool/Wallpaper");
//        //设置拥有者
//        wallpaperStage.initOwner(mainStage);
//        if (ViewEnum.FILE_LIST.equals(curViewName)) {
//            return;
//        }
//        curViewName = ViewEnum.WALLPAPER;
//        Scene scene = loadScene(ViewEnum.WALLPAPER);
//        if (scene != null) {
//            // 透明底框
//            wallpaperStage.initStyle(StageStyle.TRANSPARENT);
//            wallpaperStage.setScene(scene);
//            wallpaperStage.show();
//            WinUtils.setWinIconAfter("Tool/Wallpaper");
//        }
//    }
//
//    public static AbstractStageHolder getStage(String name) {
//        return stageHolder.get(name);
//    }
//
//    public static void putStage(String name, AbstractStageHolder stage) {
//        stageHolder.put(name, stage);
//    }
//}
