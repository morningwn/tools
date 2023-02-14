package com.github.morningwn.tools.view;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;

public class FileListCellNode extends HBox {
    private File file;
    private Label text;
    private ImageView imageView;
    private Consumer<File> onOpen;
    private Consumer<File> onDropOut;

    public FileListCellNode(File file, Consumer<File> onOpen, Consumer<File> onDropOut) {
        super();
        this.file = file;
        this.onOpen = onOpen;
        this.onDropOut = onDropOut;
        text = new Label(file.getName());
        text.setWrapText(true);
        Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file);
        BufferedImage image = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        g2.drawImage(((ImageIcon) icon).getImage(), 0, 0, null);

        imageView = new ImageView();
        imageView.setImage(SwingFXUtils.toFXImage(image, null));

        getChildren().add(imageView);
        getChildren().add(text);

        text.setOnMouseClicked(event -> {
            if (MouseButton.PRIMARY == event.getButton() && event.getClickCount() == 2) {
                onOpen.accept(file);
            }
        });
//        text.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
//            @Override
//            public void handle(Event event) {
//                System.out.println(event.getEventType());
//            }
//        });

//        text.setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//
//                System.out.println(event.getEventType());
//            }
//        });

//        text.setOnMouseReleased(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                Clipboard clipboard = Clipboard.getSystemClipboard();
//                ClipboardContent content = new ClipboardContent();
//                ArrayList<File> files = new ArrayList<>();
//                files.add(file);
//                content.putFiles(files);
//                clipboard.setContent(content);
//                System.out.println(event);
//            }
//        });

        text.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //System.out.println(event);
                text.startDragAndDrop(TransferMode.COPY);
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                ArrayList<File> files = new ArrayList<>();
                files.add(file);
                content.putFiles(files);
                clipboard.setContent(content);
            }
        });
//        text.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
//            @Override
//            public void handle(MouseDragEvent event) {
//                System.out.println(event.getPickResult());
//            }
//        });
    }
}
