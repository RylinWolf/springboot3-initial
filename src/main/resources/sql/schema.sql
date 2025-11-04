create database if not exists `boot3_initial_db`;

use boot3_initial_db;

create table if not exists `user`
(
    id              bigint primary key comment '用户 ID',
    username        varchar(255) unique not null comment '用户名称',
    `account`       varchar(255) unique not null comment '帐号',
    avatar          varchar(512) comment '头像',
    gender          tinyint comment '性别',
    birth           date comment '生日',
    personal_status varchar(255) comment '个性标签',
    phone           varchar(128) comment '手机',
    email           varchar(255) unique not null comment '邮箱',
    login_date      datetime default current_timestamp comment '最后登录时间',
    register_date   datetime default current_timestamp comment '注册时间',
    is_deleted      tinyint  default 0 comment '逻辑删除'
) comment '用户表';


create table if not exists `admin`
(
    user_id          bigint primary key comment '用户 ID',
    admin_name       varchar(255) unique not null comment '管理员名称',
    `authentication` text                not null comment '管理权限，列表形式',
    is_deleted       tinyint default 0 comment '逻辑删除'
) comment '管理员表';

create table if not exists `authentication`
(
    id            bigint primary key comment '权限 ID',
    parent_id     bigint comment '父权限 ID',
    `code`        varchar(255) unique not null comment '权限标识',
    `description` text                not null comment '描述',
    index parent_id_idx (parent_id) comment '父权限索引'
) comment '权限表';