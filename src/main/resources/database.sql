drop table if exists demo.users;

create table users
(
    uuid        varchar(255) not null comment '用户ID'
        primary key
        unique,
    name        varchar(255) not null comment '用户名'
        unique,
    password    varchar(255) not null comment '密码',
    role        varchar(23)  not null comment '用户角色，决定其权限',
    create_time date         not null comment '创建时间'
)
    comment '用户表'
    engine = InnoDB
    default charset = utf8mb4;

insert into users (uuid, name, password, role, create_time)
values ('c61b474d-9294-415b-9bb5-0360e4e3f533', 'rika', '$2a$10$5m6uBAD4iyaTuPCVEnstLONgYEcucUPIbd0e/HryVbHLazPJuwTrm',
        'ADMIN', '2023-02-02');

insert into users (uuid, name, password, role, create_time)
values ('f33aef03-32fd-4d3c-a807-63dbf2b6f525', 'ikun', '$2a$10$ADq5dnMF6Vz5/pQodN7fqOgxrse/v6gXa9zZJR6LjAVoqPfJtgKEK',
        'USER', '2023-02-02');
