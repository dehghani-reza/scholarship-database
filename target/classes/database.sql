create table scholarship
(
    id           int auto_increment
        primary key,
    status       varchar(24) not null,
    name         varchar(24) not null,
    family       varchar(24) not null,
    nationalCode varchar(10) not null,
    lastUni      varchar(24) not null,
    lastDegree   varchar(24) not null,
    lastField    varchar(24) not null,
    lastScore    float       not null,
    applyUni     varchar(24) not null,
    applyDegree  varchar(24) not null,
    applyField   varchar(24) not null,
    applyDate    varchar(24) not null
);

create table user
(
    id       int auto_increment
        primary key,
    username varchar(24) not null,
    password varchar(24) not null,
    role     varchar(10) not null,
    constraint user_username_uindex
        unique (username)
);

