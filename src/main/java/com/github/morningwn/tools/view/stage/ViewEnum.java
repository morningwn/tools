package com.github.morningwn.tools.view.stage;

import com.github.morningwn.tools.utils.window.WindowUtils;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.StageStyle;

/**
 * @author morningwn
 * @create in 2022/10/10 17:41
 */
public enum ViewEnum {
    /**
     *
     */
    NOTE("note-view.fxml", "备忘录", StageStyle.DECORATED, null, Color.WHITE),
    TIME("time-view.fxml", "Tools/TIME", StageStyle.TRANSPARENT, new Point2D(WindowUtils.getWidth() - 350, 50.0), null),
    FILE_LIST("file-list-view.fxml", "Tools/FILE_LIST", StageStyle.DECORATED, null, Color.WHITE),
    SETTING("setting-view.fxml", "设置", StageStyle.DECORATED, null, Color.WHITE),
    WALLPAPER("wallpaper-view.fxml", "Tools/WALLPAPER", StageStyle.TRANSPARENT, new Point2D(0, 0), new Point2D(WindowUtils.getWidth(), WindowUtils.getHeight()), null),
    WALLPAPER_FILE("wallpaper-file-view.fxml", "壁纸列表", StageStyle.DECORATED, null, Color.WHITE);

    /**
     * 文件名称
     */
    private final String name;
    /**
     * 标题
     */
    private final String title;

    /**
     * ======================界面配置 start============================
     */
    private StageStyle style;
    private Point2D defaultPosition;
    private Point2D defaultSize;

    private Paint SceneFull;

    ViewEnum(String name, String title, StageStyle style, Point2D defaultPosition, Paint sceneFull) {
        this.name = name;
        this.title = title;
        this.style = style;
        this.defaultPosition = defaultPosition;
        SceneFull = sceneFull;
    }

    ViewEnum(String name, String title, StageStyle style, Point2D defaultPosition, Point2D defaultSize, Paint sceneFull) {
        this.name = name;
        this.title = title;
        this.style = style;
        this.defaultPosition = defaultPosition;
        this.defaultSize = defaultSize;
        SceneFull = sceneFull;
    }

    /**
     * ======================界面配置 end ============================
     */

//    ViewEnum(String name, StageStyle style) {
//        this.name = name;
//        this.style = style;
//    }
    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public StageStyle getStyle() {
        return style;
    }

    public Point2D getDefaultPosition() {
        return defaultPosition;
    }

    public Point2D getDefaultSize() {
        return defaultSize;
    }

    public Paint getSceneFull() {
        return SceneFull;
    }
}
