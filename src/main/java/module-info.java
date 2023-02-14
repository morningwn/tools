open module com.github.morningwn.tools {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;
    requires java.sql;
    requires com.h2database;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires com.google.gson;
    requires java.net.http;
    requires javafx.graphics;
    requires javafx.swing;
    requires org.controlsfx.controls;
    requires com.github.kwhat.jnativehook;
    requires com.sun.jna;
    requires com.sun.jna.platform;
    requires org.bytedeco.javacv;
    requires com.sandec.mdfx;
    requires hutool.all;
//    requires core.renderer.R8;
    requires flying.saucer.core;
    requires net.sf.cssbox;
    requires net.sf.cssbox.jstyleparser;

    exports com.github.morningwn.tools;
    exports com.github.morningwn.tools.view;
    exports com.github.morningwn.tools.controller;
//    opens com.github.morningwn.tools to javafx.fxml;
//    opens com.github.morningwn.tools.view to javafx.fxml;
//    opens com.github.morningwn.tools.controller to javafx.fxml;
//    opens com.github.morningwn.tools.entity to com.google.gson;
    exports com.github.morningwn.tools.entity to com.google.gson;
    exports com.github.morningwn.tools.listener;
//    opens com.github.morningwn.tools.listener to javafx.fxml;
    exports com.github.morningwn.tools.view.stage;
//    opens com.github.morningwn.tools.view.stage to javafx.fxml;
}