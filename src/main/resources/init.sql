-- drop table if exists note;

create table if not exists note
(
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT,
    `content`     varchar(1000) NOT NULL DEFAULT '' COMMENT '',
    `create_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
)DEFAULT CHARSET=utf8mb4;

create table if not exists config (
    `id`          int(10) unsigned NOT NULL AUTO_INCREMENT,
    `type`          int(10) unsigned NOT NULL UNIQUE,
    `content`     varchar(1000) NOT NULL DEFAULT '' COMMENT '',
    `create_time` datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
)DEFAULT CHARSET=utf8mb4;
