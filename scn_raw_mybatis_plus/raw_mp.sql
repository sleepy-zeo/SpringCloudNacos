drop database raw_mp;
create database raw_mp;
use raw_mp;


create table user(
    uid int,
    name varchar(64),
    is_del int,
    create_time datetime default null,
    update_time datetime default null,
    primary key(uid)
)engine=Innodb charset=utf8;


