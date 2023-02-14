package com.github.morningwn.tools.entity;

import java.time.LocalDateTime;

/**
 * @author morningwn
 * @create in 2022/10/9 9:55
 */
public class NoteEntity {
    private Integer id;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public NoteEntity() {
    }

    public NoteEntity(String content) {
        this.content = content;
    }

    public NoteEntity(Integer id, String content, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
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

    @Override
    public String toString() {
        return "NoteEntity{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
