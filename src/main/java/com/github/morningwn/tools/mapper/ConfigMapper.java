package com.github.morningwn.tools.mapper;

import com.github.morningwn.tools.entity.ConfigEntity;

/**
 * @author morningwn
 * @create in 2022/11/10 9:38
 */
public interface ConfigMapper {

    ConfigEntity getByType(Integer type);

    ConfigEntity insertOrUpdate(ConfigEntity configEntity);
}
