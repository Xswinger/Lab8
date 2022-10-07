create table if not exists routes(
    id            bigint       not null
        constraint routes_pk
            primary key,
    name          varchar(255) not null,
    coordinate_x  bigint       not null,
    coordinate_y  integer      not null,
    from_x        real,
    from_y        double precision,
    from_z        integer,
    to_x          integer      not null,
    to_y          bigint       not null,
    to_z          real         not null,
    to_name       varchar(255) default ''::character varying,
    distance      integer,
    creation_date date         not null
);
create unique index if not exists routes_id_uindex
    on routes (id);
create table if not exists users
(
    id                bigint  not null
        constraint users_pk
            primary key,
    name              varchar not null,
    password          varchar(512),
    registration_date date    not null
);
create unique index if not exists users_user_password_uindex
    on users (password);
create table if not exists users_routes_map
(
    user_id  bigint  not null
        constraint users_routes_map_users_user_id_fk
            references users,
    route_id bigint  not null
        constraint users_routes_map_routes_id_fk
            references routes,
    id       integer not null
        constraint users_routes_map_pk
            primary key
);
create sequence if not exists routes_id_seq;
create sequence if not exists users_id_seq;