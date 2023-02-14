package com.github.morningwn.tools.controller;

import com.github.morningwn.tools.entity.NoteEntity;
import com.github.morningwn.tools.mapper.NoteMapper;
import com.github.morningwn.tools.utils.IOUtils;
import com.github.morningwn.tools.utils.LoadResourceUtils;
import com.github.morningwn.tools.utils.StrUtils;
import com.github.morningwn.tools.utils.bean.BeanUtils;
import com.github.morningwn.tools.view.NoteListCell;
import com.sandec.mdfx.MarkdownView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author morningwn
 * @create in 2022/9/29 11:08
 */
public class NoteController extends Controller {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);
    @FXML
    public Button addBt;
    public VBox root;
    @FXML
    private TextField textField;
    @FXML
    private ListView<NoteEntity> listView;
    private NoteMapper noteMapper;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noteMapper = BeanUtils.getBean(NoteMapper.class);
        listView.setCellFactory(param -> new NoteListCell(this::onDelete, this::onUpdate));
        listView.setEditable(true);

        this.refresh();
        String mdString = IOUtils.readString(LoadResourceUtils.load("test.md"));
        MarkdownView markdownView = new MarkdownView(mdString);
        root.getChildren().clear();
        root.getChildren().add(markdownView);
    }

    @FXML
    public void addNote(ActionEvent actionEvent) {
        String text = textField.getText();
        if (StrUtils.isNotBlank(text)) {
            NoteEntity noteEntity = new NoteEntity();
            noteEntity.setContent(text);
            noteMapper.insert(noteEntity);
        }
        textField.setText("");
        this.refresh();
    }

    private void refresh() {
        listView.getItems().clear();
        List<NoteEntity> all = noteMapper.all();
        listView.getItems().addAll(all);
        listView.refresh();
    }

    public void onDelete(NoteEntity noteEntity) {
        noteMapper.delete(noteEntity.getId());
        refresh();
    }

    public NoteEntity onUpdate(NoteEntity noteEntity) {
        noteMapper.update(noteEntity);
        return noteEntity;
    }
}
