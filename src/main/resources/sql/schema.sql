create database if not exists `boot3_initial_db`;

use boot3_initial_db;

create table if not exists `user`
(
    id              bigint primary key comment '用户 ID',
    username        varchar(255) unique not null comment '用户名称',
    `account`       varchar(255) unique not null comment '帐号',
    avatar          varchar(512)        null comment '头像',
    gender          tinyint             null comment '性别',
    birth           date                null comment '生日',
    personal_status varchar(255) comment '个性标签',
    phone           varchar(128) comment '手机',
    email           varchar(255) unique not null comment '邮箱',
    login_date      datetime default current_timestamp comment '最后登录时间',
    register_date   datetime default current_timestamp comment '注册时间',
    is_deleted      tinyint  default 0 comment '逻辑删除'
) comment '用户表';

create table if not exists `user_auth`
(
    id          bigint primary key comment '用户认证 ID',
    passcode    varchar(512) not null comment '密码密文',
    is_banned   tinyint      not null default 0 comment '是否禁用',
    banned_time datetime     null comment '禁用时间',
    banned_from bigint       null comment '禁用操作者(用户 ID)',
    banned_msg  text         null comment '禁用备注'
) comment '用户认证信息表';


create table if not exists `admin`
(
    user_id          bigint primary key comment '用户 ID',
    admin_name       varchar(255) unique not null comment '管理员名称',
    `authentication` text                null comment '管理权限，权限 ID 的列表形式 [2, 3, ...]',
    is_deleted       tinyint             not null default 0 comment '逻辑删除'
) comment '管理员表';

create table if not exists `authentication`
(
    id            bigint primary key comment '权限 ID',
    parent_id     bigint comment '父权限 ID',
    `code`        varchar(255) unique not null comment '权限标识',
    `description` text                not null comment '描述',
    index parent_id_idx (parent_id) comment '父权限索引',
    fulltext index (`description`) comment '描述-全文索引'
) comment '权限表';


create table if not exists `oss_upload_log`
(
    id            bigint primary key comment '日志 ID',
    `filename`    varchar(255) not null comment '文件名',
    `file_size`   bigint       not null comment '文件大小',
    `file_path`   varchar(512) not null comment '文件路径',
    `upload_user` bigint       not null comment '上传用户',
    `upload_time` datetime default current_timestamp comment '上传时间',
    index user_filename (upload_user, `filename`) comment '上传用户_文件名-联合索引'
)