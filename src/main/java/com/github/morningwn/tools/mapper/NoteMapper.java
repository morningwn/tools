package com.github.morningwn.tools.mapper;

import com.github.morningwn.tools.entity.NoteEntity;

import java.util.List;

/**
 * @author morningwn
 * @create in 2022/10/9 9:56
 */
public interface NoteMapper {

    void insert(NoteEntity entity);

    void update(NoteEntity entity);

    void delete(Integer id);

    NoteEntity select(Integer id);

    List<NoteEntity> all();
}
