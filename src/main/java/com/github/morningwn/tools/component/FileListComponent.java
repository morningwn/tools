package com.github.morningwn.tools.component;

import com.github.morningwn.tools.entity.ConfigEntity;
import com.github.morningwn.tools.entity.ConfigTypeEnum;
import com.github.morningwn.tools.entity.FileListSetting;
import com.github.morningwn.tools.mapper.ConfigMapper;
import com.github.morningwn.tools.utils.CollUtils;
import com.github.morningwn.tools.utils.bean.BeanUtils;
import com.github.morningwn.tools.utils.bean.LifeBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author morningwn
 * @create in 2022/11/8 18:37
 */
public class FileListComponent implements LifeBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileListComponent.class);
    private FileListSetting fileListSetting;
    private ConfigMapper configMapper;
    private File defaultDir;

    @Override
    public void init() {
        configMapper = BeanUtils.getBean(ConfigMapper.class);
        ConfigEntity configEntity = configMapper.getByType(ConfigTypeEnum.FILE.getCode());
        if (Objects.nonNull(configEntity)) {
            fileListSetting = (FileListSetting) configEntity.getConfig();
        } else {
            fileListSetting = new FileListSetting();
        }
        defaultDir = new File(fileListSetting.getFileDir());
    }

    public List<File> list() {
        File[] files = defaultDir.listFiles();
        return CollUtils.toList(files);
    }

    public void moveTo(File file) {
        File tmp = new File(defaultDir.getPath() + File.separator + file.getName());
        LOGGER.debug("FileListComponent#moveTo tmp {}, file: {}", tmp.getAbsoluteFile(), file.getAbsoluteFile());
        file.renameTo(tmp);
    }

    public void moveTo(List<File> files) {
        for (File file : files) {
            moveTo(file);
        }
    }

    public void open(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            LOGGER.error("FileListComponent#open file: {}", file.getAbsoluteFile(), e);
        }
    }

    @Override
    public void destroy() {
    }
}
