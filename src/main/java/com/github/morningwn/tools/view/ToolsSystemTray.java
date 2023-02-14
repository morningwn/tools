package com.github.morningwn.tools.view;

import com.github.morningwn.tools.MainApplication;
import com.github.morningwn.tools.component.WallpaperComponent;
import com.github.morningwn.tools.utils.bean.BeanUtils;
import com.github.morningwn.tools.view.stage.ViewEnum;
import javafx.application.Platform;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;


/**
 * @author morningwn
 * @create in 2023/1/17 21:06
 */
public class ToolsSystemTray {

    public void setSystemTray() throws AWTException {
        Platform.setImplicitExit(false);
        SystemTray systemTray = SystemTray.getSystemTray();
        URL resource = MainApplication.class.getClassLoader().getResource("application.png");
        Image image = Toolkit.getDefaultToolkit().getImage(resource);
        PopupMenu popupMenu = new PopupMenu();
        popupMenu.add(wallPaperItem());
        popupMenu.add(noteItem());
        popupMenu.add(settingItem());

        MenuItem exit = new MenuItem("退出");
        exit.addActionListener(e -> System.exit(0));
        popupMenu.add(exit);

        TrayIcon trayIcon = new TrayIcon(image, "Tools", popupMenu);
        trayIcon.setImageAutoSize(true);
        systemTray.add(trayIcon);
        trayIcon.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private MenuItem settingItem() {
        MenuItem setting = new MenuItem("设置");
        setting.addActionListener(e -> Platform.runLater(()-> BeanUtils.getOrCreateStage(ViewEnum.SETTING).show()));
        return setting;
    }

    private MenuItem wallPaperItem() {
        Menu wallPaperItem = new Menu("壁纸");
        MenuItem wallPaperDown = new MenuItem("上一张");
        wallPaperDown.addActionListener(e -> Platform.runLater(() -> {
            WallpaperComponent wallpaperEngine = BeanUtils.getBean(WallpaperComponent.class);
            wallpaperEngine.upWallpaper();
        }));
        MenuItem wallPaperUp = new MenuItem("下一张");
        wallPaperUp.addActionListener(e -> Platform.runLater(() -> {
            WallpaperComponent wallpaperEngine = BeanUtils.getBean(WallpaperComponent.class);
            wallpaperEngine.downWallpaper();
        }));
        MenuItem wallPaperRefresh = new MenuItem("刷新");
        wallPaperRefresh.addActionListener(e -> Platform.runLater(() -> {
            WallpaperComponent wallpaperEngine = BeanUtils.getBean(WallpaperComponent.class);
            wallpaperEngine.refreshWallpaper();
        }));
        wallPaperItem.add(wallPaperDown);
        wallPaperItem.add(wallPaperUp);
        wallPaperItem.add(wallPaperRefresh);

        return wallPaperItem;
    }

    private MenuItem noteItem() {
        Menu noteItem = new Menu("备忘录");
        MenuItem noteShow = new MenuItem("打开");
        noteShow.addActionListener(e -> Platform.runLater(()-> BeanUtils.getOrCreateStage(ViewEnum.NOTE).show()));
        MenuItem noteClose = new MenuItem("关闭");
        noteClose.addActionListener(e -> Platform.runLater(()-> BeanUtils.getOrCreateStage(ViewEnum.NOTE).close()));
        noteItem.add(noteShow);
        noteItem.add(noteClose);
        return noteItem;
    }
}
