package com.github.morningwn.tools.listener;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author morningwn
 * @create in 2022/12/19 14:49
 */
public class MouseListener implements NativeMouseInputListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(MouseListener.class);

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeEvent) {
        //LOGGER.info("nativeMouseClicked: {}", nativeEvent);
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeEvent) {
        //LOGGER.info("nativeMousePressed: {}", nativeEvent);
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeEvent) {
        //LOGGER.info("nativeMouseReleased: {}", nativeEvent);
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeEvent) {
        //LOGGER.info("nativeMouseMoved: {}", nativeEvent);
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeEvent) {
        LOGGER.info("nativeMouseDragged: {}", nativeEvent.paramString());
    }
}
