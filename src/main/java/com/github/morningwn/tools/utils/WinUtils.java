package com.github.morningwn.tools.utils;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author morningwn
 * @create in 2022/10/19 15:55
 */
public class WinUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(WinUtils.class);

    static {
//        System.load(getAbsolutePath("libwinTool"));
//        System.loadLibrary("libwinTool");
    }

    @Deprecated
    public static void setWallpaper(File file) {
//        setWallpaper(file.getAbsolutePath());
    }


    /**
     * 设置壁纸
     *
     * @param path
     */
    //public static native void setWallpaper(String path);
    public static double getHeight() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension scrnsize = toolkit.getScreenSize();
        return scrnsize.getHeight();
    }

    public static double getWidth() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension scrnsize = toolkit.getScreenSize();
        return scrnsize.getWidth();
    }

    private static WinDef.HWND desktopWorkerw;
    private static WinDef.HWND windowhWnd;

    /**
     * 将窗口置于windows系统桌面与图表之间
     *
     * @param title
     */
    public static void setWinIconAfter(String title) {
        //获取程序经理句柄
        WinDef.HWND hWnd2 = User32.INSTANCE.FindWindow("Progman", null);
        //发送消息给程序管理员
        WinDef.DWORDByReference result = new WinDef.DWORDByReference();
        WinDef.LRESULT r = User32.INSTANCE.SendMessageTimeout(hWnd2, 0x052C,
                new WinDef.WPARAM(),
                new WinDef.LPARAM(),
                User32.SMTO_NORMAL,
                1000,
                result
        );
        //获取到新创建的窗口的句柄
        WinDef.HWND[] workerw = {new WinDef.HWND(Pointer.NULL)};
        User32.INSTANCE.EnumWindows((hwnd, pointer) -> {
            WinDef.HWND h = User32.INSTANCE.FindWindowEx(hwnd, null, "SHELLDLL_DefView", null);
            if (h != null) {
                workerw[0] = User32.INSTANCE.FindWindowEx(null, hwnd, "WorkerW", null);
            }
            return true;
        }, Pointer.NULL);
        //在图标和墙纸之间绘制图形
        WinDef.HWND hWnd = User32.INSTANCE.FindWindow(null, title);    //使用标题寻找窗体
        WinDef.HDC dc = User32.INSTANCE.GetDC(hWnd);
        User32.INSTANCE.ReleaseDC(workerw[0], dc);
        //将Windows窗体放在桌面图标后面
        User32.INSTANCE.SetParent(hWnd, workerw[0]);
        windowhWnd = hWnd;
        desktopWorkerw = workerw[0];
    }

    /**
     * 将窗口移动到桌面图标上层
     */
    public static void setWinIconTop(String title) {
//        WinDef.HWND hWnd = User32.INSTANCE.FindWindow(null,title);    //使用标题寻找窗体
        WinDef.HWND hWnd = User32.INSTANCE.FindWindowEx(desktopWorkerw, null, null, title);
        User32.INSTANCE.SetParent(hWnd, User32.INSTANCE.GetDesktopWindow());
    }
}
