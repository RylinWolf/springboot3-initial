### 权限表 ###
-- 插入根权限
INSERT INTO `authentication` (id, parent_id, `code`, `description`)
VALUES (1, NULL, 'service', '服务根权限'),

-- 插入管理员模块权限
       (10000, 1, 'service:admin', '管理员模块所有权限'),
       (10001, 10000, 'service:admin:add', '管理员添加权限'),
       (10002, 10000, 'service:admin:update', '管理员更新权限'),
       (10003, 10000, 'service:admin:delete', '管理员删除权限'),
       (10004, 10000, 'service:admin:query', '管理员查询权限'),

-- 插入用户模块权限
       (20000, 1, 'service:user', '用户模块所有权限'),
       (20001, 20000, 'service:user:add', '用户添加权限'),
       (20002, 20000, 'service:user:update', '用户更新权限'),
       (20003, 20000, 'service:user:delete', '用户删除权限'),

-- 插入权限模块权限
       (30000, 1, 'service:auth', '权限模块所有权限'),
       (30001, 30000, 'service:auth:add', '权限添加权限'),
       (30002, 30000, 'service:auth:update', '权限更新权限'),
       (30003, 30000, 'service:auth:delete', '权限删除权限'),
       (30004, 30000, 'service:auth:query', '权限查询权限')
;

# 用户名：admin
# 密码：123123123

insert into `user_auth`(id, passcode)
values (1, '$2a$10$bmxkGxsZYB/W6IyF6OQ2YOmgqYzkE99gyyISiqQy.z0gn1nwWy4GG');

insert into `user`(id, username, account, email)
values (1, 'admin', 'admin', 'admin@localhost');

insert into `admin`(user_id, admin_name, `authentication`)
values (1, 'admin', '[1]');
