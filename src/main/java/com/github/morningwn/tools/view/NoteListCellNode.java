package com.github.morningwn.tools.view;

import com.github.morningwn.tools.entity.NoteEntity;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author morningwn
 * @create in 2022/10/14 16:46
 */
public class NoteListCellNode extends HBox {
    private Label text;
    private TextArea textArea;
    private Button button;
    private NoteEntity noteEntity;
    private Consumer<NoteEntity> onDelete;
    private Function<NoteEntity, NoteEntity> onChange;

    public NoteListCellNode(NoteEntity noteEntity, Consumer<NoteEntity> onDelete, Function<NoteEntity, NoteEntity> onChange) {
        super();
        this.noteEntity = noteEntity;
        this.onDelete = onDelete;
        this.onChange = onChange;

        text = new Label();
        text.setWrapText(true);
        textArea = new TextArea(noteEntity.getContent());
        button = new Button("删除");
        button.setOnAction(event -> onDelete.accept(noteEntity));

        getChildren().add(text);
//        getChildren().add(textArea);
        HBox hBox = new HBox();
        getChildren().add(hBox);
        setHgrow(hBox, Priority.ALWAYS);
        getChildren().add(button);
    }

    public void update() {
    }

    public void showTextField() {
        getChildren().set(0, textArea);
        textArea.setText(noteEntity.getContent());
        textArea.requestFocus();
        textArea.setEditable(true);
    }

    public void showText() {
        noteEntity.setContent(textArea.getText());
        this.noteEntity = onChange.apply(this.noteEntity);
        textArea.setEditable(false);
        text.setText(noteEntity.getContent());
        getChildren().set(0, text);
    }
}
