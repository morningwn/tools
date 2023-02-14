package com.github.morningwn.tools.component;

import com.github.morningwn.tools.utils.bean.BeanUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

class FileListComponentTest {
    private FileListComponent fileListComponent;

    @BeforeEach
    public void bf() {
        BeanUtils.createBean();
        fileListComponent = BeanUtils.getBean(FileListComponent.class);
    }


    @Test
    void list() {
        List<File> list = fileListComponent.list();
        System.out.println(list);
    }

    @Test
    void moveTo() {
        File file = new File("D:/新建 文本文档.txt");
        fileListComponent.moveTo(file);
    }

    @Test
    void open() {
        File file = new File("D:/新建 文本文档.txt");
        fileListComponent.open(file);
    }
}