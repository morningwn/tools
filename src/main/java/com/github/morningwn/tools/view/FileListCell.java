package com.github.morningwn.tools.view;

import javafx.scene.control.ListCell;

import java.io.File;
import java.util.function.Consumer;

public class FileListCell extends ListCell<File> {
    private final Consumer<File> onOpen;
    private final Consumer<File> onDropOut;
    private FileListCellNode fileListCellNode;

    public FileListCell(Consumer<File> onOpen, Consumer<File> onDropOut) {
        super();
        this.onOpen = onOpen;
        this.onDropOut = onDropOut;
    }

    @Override
    protected void updateItem(File item, boolean empty) {
        super.updateItem(item, empty);

        if (this.isEmpty() || item == null) {
            this.setText(null);
            this.setGraphic(null);
        } else {
            fileListCellNode = new FileListCellNode(this.getItem(), onOpen, onDropOut);
            this.setGraphic(fileListCellNode);
        }
    }
}
