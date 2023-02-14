package com.github.morningwn.tools.mapper;

import com.github.morningwn.tools.entity.NoteEntity;
import com.github.morningwn.tools.mapper.impl.NoteMapperImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author morningwn
 * @create in 2022/10/11 15:30
 */
class NoteMapperTest {
    NoteMapperImpl noteMapper = new NoteMapperImpl();

    @Test
    void test() {
        System.out.println("===========insert============");
        insert();
        System.out.println("===========insert============");

        System.out.println();

        System.out.println("==========all=============");
        all();
        System.out.println("===========all============");

        System.out.println();

        System.out.println("============select===========");
        select();
        System.out.println("===========select============");

        System.out.println();

        System.out.println("============update===========");
        update();
        System.out.println("===========update============");

        System.out.println();

        System.out.println("============select===========");
        select();
        System.out.println("===========select============");
        System.out.println("============select===========");
        select();
        System.out.println("===========select============");
        System.out.println();

        System.out.println("============all===========");
        all();
        System.out.println("============all===========");
    }

    @Test
    void insert() {
        NoteEntity noteEntity = new NoteEntity("hello");
        noteMapper.insert(noteEntity);
    }

    @Test
    void update() {
        NoteEntity noteEntity = new NoteEntity();
        noteEntity.setId(1);
        noteEntity.setContent("test");
        noteMapper.update(noteEntity);
    }

    @Test
    void select() {
        NoteEntity select = noteMapper.select(2);
        System.out.println(select);
    }

    @Test
    void all() {
        List<NoteEntity> all = noteMapper.all();
        System.out.println(all);
    }
}