package com.github.morningwn.tools.mapper.impl;

import com.github.morningwn.tools.entity.NoteEntity;
import com.github.morningwn.tools.mapper.DataSourceHolder;
import com.github.morningwn.tools.mapper.NoteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author morningwn
 * @create in 2022/10/9 14:55
 */
public class NoteMapperImpl implements NoteMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(NoteMapperImpl.class);

    @Override
    public void insert(NoteEntity entity) {
        Connection connection = DataSourceHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into note (content) values (?)")) {
            preparedStatement.setObject(1, entity.getContent());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("NoteMapperImpl#insert fail", e);
        }
    }

    @Override
    public void update(NoteEntity entity) {
        Connection connection = DataSourceHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("update note set content = ?, update_time = ? where id = ?")) {
            preparedStatement.setObject(1, entity.getContent());
            preparedStatement.setObject(2, LocalDateTime.now());
            preparedStatement.setObject(3, entity.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("NoteMapperImpl#insert fail", e);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection connection = DataSourceHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("delete from note where id = ?")) {
            preparedStatement.setObject(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error("NoteMapperImpl#delete fail", e);
        }
    }

    @Override
    public NoteEntity select(Integer id) {
        Connection connection = DataSourceHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from note where id = ?")) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return readData(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error("NoteMapperImpl#select fail", e);
        }
        return null;
    }

    private NoteEntity readData(ResultSet resultSet) throws SQLException {
        NoteEntity noteEntity = new NoteEntity();
        int id = resultSet.getInt("id");
        noteEntity.setId(id);
        String content = resultSet.getString("content");
        noteEntity.setContent(content);
        LocalDateTime createTime = resultSet.getTimestamp("create_time").toLocalDateTime();
        noteEntity.setCreateTime(createTime);
        LocalDateTime updateTime = resultSet.getTimestamp("update_time").toLocalDateTime();
        noteEntity.setUpdateTime(updateTime);
        return noteEntity;
    }

    @Override
    public List<NoteEntity> all() {
        Connection connection = DataSourceHolder.getConnection();
        List<NoteEntity> noteEntities = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from note");
            while (resultSet.next()) {
                noteEntities.add(readData(resultSet));
            }
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error("NoteMapperImpl#all fail", e);
        }
        return noteEntities;
    }
}
