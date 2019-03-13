-- 客户端信息
drop table if exists oauth2_client;
create table oauth2_client(
  id bigint unsigned primary key auto_increment comment 'ID主键',
  bzid varchar(32) unique not null comment '业务ID',
  client_id varchar(32) unique not null comment '客户端ID',
  client_secret varchar(128) not null comment '客户端密钥',
  kwm_name varchar(128) not null default '' comment '客户端的名称',
  logo varchar(128) not null default '' comment '客户端的LOGO',
  kwm_state integer not null default 0 comment '客户端状态[0-正常,1-锁定,2-禁用]',
  grant_type varchar(128) not null default 'authorization_code' comment '授权类型[默认authorization_code-授权码模式],多个使用,分割',
  kwm_scope varchar(128) not null default '' comment '授权范围,多个使用,分割',
  redirect_url varchar(250) not null default '' comment '重定向地址,多个使用,分割',
  created_time bigint not null default 0 comment '信息创建时间戳(毫秒)',
  primary key (id),
  unique key uk_bzid(bzid),
  unique key uk_client_id(client_id)
) comment '客户端信息';

-- 用户信息
drop table if exists oauth2_user;
create table oauth2_user(
  id bigint unsigned auto_increment comment 'ID主键',
  bzid varchar(32) unique not null comment '业务ID',
  kwm_username varchar(64) unique not null comment '用户名',
  kwm_pwd varchar(128) not null comment '用户密码',
  show_name varchar(128) not null default '' comment '用户显示名称',
  head_picture varchar(128) not null default '' comment '用户头像',
  kwm_state integer not null default 0 comment '用户状态[0-正常,1-锁定,2-禁用]',
  created_time bigint not null default 0 comment '信息创建时间戳(毫秒)',
  login_ip varchar(48) not null default '' comment '登录IP',
  login_time bigint not null default 0 comment '登录时间戳(毫秒)',
  primary key (id),
  unique key uk_bzid(bzid),
  unique key uk_username(kwm_username)
) comment '用户信息';