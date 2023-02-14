package com.github.morningwn.tools.mapper;

import com.github.morningwn.tools.utils.ConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author morningwn
 * @create in 2022/10/11 15:31
 */
public class DataSourceHolder {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceHolder.class);
    private static volatile Connection connection;
    private static final String sqlFilePath = "init.sql";

    private static void init() {
//        try {
//            Class.forName("org.h2.Driver");
//        } catch (ClassNotFoundException e) {
//            LOGGER.error("DataSourceHolder#init", e);
//        }
        try {
            // h2
            connection = DriverManager.getConnection("jdbc:h2:" + ConfigUtils.getDataDir() + "/tools;MODE=MYSQL");
//            connection = DriverManager.getConnection("jdbc:sqlite:tools.db");
        } catch (SQLException e) {
            LOGGER.error("DataSourceHolder#init", e);
        }
    }

    private static void initDataSource() {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        String sql = null;
        try (InputStream ism = contextClassLoader.getResourceAsStream(sqlFilePath);) {
            byte[] bytes = ism.readAllBytes();
            sql = new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("DataSourceHolder#initDataSource", e);
        }
        LOGGER.info(sql);
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            LOGGER.error("DataSourceHolder#initDataSource", e);
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            synchronized (DataSourceHolder.class) {
                if (connection == null) {
                    init();
                    initDataSource();
                }
            }
        }
        return connection;
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            LOGGER.error("DataSourceHolder#close", e);
        }
    }
}
