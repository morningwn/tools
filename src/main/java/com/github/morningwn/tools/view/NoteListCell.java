package com.github.morningwn.tools.view;

import com.github.morningwn.tools.controller.NoteController;
import com.github.morningwn.tools.entity.NoteEntity;
import javafx.scene.control.ListCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author morningwn
 * @create in 2022/10/14 15:53
 */
public class NoteListCell extends ListCell<NoteEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);
    private NoteListCellNode noteListCellNode;
    private Consumer<NoteEntity> onDelete;
    private Function<NoteEntity, NoteEntity> onUpdate;

    public NoteListCell(Consumer<NoteEntity> onDelete, Function<NoteEntity, NoteEntity> onUpdate) {
        this.onDelete = onDelete;
        this.onUpdate = onUpdate;
    }

    @Override
    protected void updateItem(NoteEntity item, boolean empty) {
        LOGGER.info("start updateItem this.isEmpty() {}, this.isEditing(): {}", this.isEmpty(), this.isEditing());
        super.updateItem(item, empty);

        if (this.isEmpty() || item == null) {
            this.setText(null);
            this.setGraphic(null);
        } else {
            noteListCellNode = new NoteListCellNode(this.getItem(), onDelete, onUpdate);
            if (this.isEditing()) {
                noteListCellNode.showTextField();
            } else {
                noteListCellNode.showText();
            }
            noteListCellNode.update();
            this.setGraphic(noteListCellNode);
        }
    }

    @Override
    public void startEdit() {
        LOGGER.info("start edit");
        super.startEdit();
        if (isEditing()) {
            noteListCellNode.showTextField();
        }
    }

    @Override
    public void commitEdit(NoteEntity newValue) {
        LOGGER.info("commitEdit edit");
        super.commitEdit(newValue);
        //this.setGraphic(null);
    }

    @Override
    public void cancelEdit() {
        LOGGER.info("cancelEdit edit");
        super.cancelEdit();
        noteListCellNode.showText();
    }
}
