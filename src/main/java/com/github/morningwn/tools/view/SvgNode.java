package com.github.morningwn.tools.view;

import javafx.scene.Node;
import javafx.scene.shape.SVGPath;

/**
 * @author morningwn
 * @create in 2022/11/20 10:38
 */
public class SvgNode extends Node {
    private final String path;
    private final SVGPath svgPath;


    public SvgNode(String path) {

        this.path = path;
        this.svgPath = new SVGPath();
//        svgPath.
    }
}
