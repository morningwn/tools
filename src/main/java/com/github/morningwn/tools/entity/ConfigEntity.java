package com.github.morningwn.tools.entity;

import com.github.morningwn.tools.utils.JSONUtils;

import java.time.LocalDateTime;

/**
 * @author morningwn
 * @create in 2022/11/7 19:22
 */
public class ConfigEntity {
    private Integer id;
    private String content;
    private Integer type;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public ConfigEntity() {
    }

    public ConfigEntity(String content, Integer type) {
        this.content = content;
        this.type = type;
    }

    public ConfigEntity(Integer id, String content, Integer type) {
        this.id = id;
        this.content = content;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Object getConfig() {
        return JSONUtils.toBean(content, ConfigTypeEnum.getClassByCode(type));
    }
}
