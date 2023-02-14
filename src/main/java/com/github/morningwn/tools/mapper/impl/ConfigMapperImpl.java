package com.github.morningwn.tools.mapper.impl;

import com.github.morningwn.tools.entity.ConfigEntity;
import com.github.morningwn.tools.mapper.ConfigMapper;
import com.github.morningwn.tools.mapper.DataSourceHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * @author morningwn
 * @create in 2022/11/10 9:38
 */
public class ConfigMapperImpl implements ConfigMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigMapperImpl.class);

    @Override
    public ConfigEntity getByType(Integer type) {
        Connection connection = DataSourceHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from config where type = ?")) {
            preparedStatement.setObject(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return readData(resultSet);
            }
            resultSet.close();
        } catch (SQLException e) {
            LOGGER.error("ConfigMapperImpl#getByCode fail", e);
        }
        return null;
    }

    @Override
    public ConfigEntity insertOrUpdate(ConfigEntity configEntity) {
        Connection connection = DataSourceHolder.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("insert into config (content, type) values (?, ?)  ON DUPLICATE KEY UPDATE content=VALUES(content),type=VALUES(type);")) {
            preparedStatement.setObject(1, configEntity.getContent());
            preparedStatement.setObject(2, configEntity.getType());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("ConfigMapperImpl#getByCode fail", e);
        }

        return configEntity;
    }

    private ConfigEntity readData(ResultSet resultSet) throws SQLException {
        ConfigEntity configEntity = new ConfigEntity();
        int id = resultSet.getInt("id");
        configEntity.setId(id);
        int type = resultSet.getInt("type");
        configEntity.setType(type);
        String content = resultSet.getString("content");
        configEntity.setContent(content);
        LocalDateTime createTime = resultSet.getTimestamp("create_time").toLocalDateTime();
        configEntity.setCreateTime(createTime);
        LocalDateTime updateTime = resultSet.getTimestamp("update_time").toLocalDateTime();
        configEntity.setUpdateTime(updateTime);
        return configEntity;
    }
}
