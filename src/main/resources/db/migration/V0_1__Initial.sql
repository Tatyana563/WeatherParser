create table city
(
    id        int auto_increment
        primary key,
    lat       float        not null,
    lon       float        not null,
    country   varchar(255) null,
    name      varchar(255) null,
    remote_id int          not null
);

create table daily_info
(
    id       int auto_increment
        primary key,
    sunrise  datetime(6) null,
    sunset   datetime(6) null,
    timezone int         not null
);

create table weather_condition
(
    id          int          not null
        primary key,
    description varchar(255) null,
    icon        varchar(255) null,
    main        varchar(255) null
);

create table main_info
(
    id       int auto_increment
        primary key,
    humidity int    null,
    pressure int    null,
    temp     double null
);

create table wind_info
(
    id    int auto_increment
        primary key,
    gust  double not null,
    speed double not null
);

create table weather_point
(
    id            int auto_increment
        primary key,
    date          datetime(6) null,
    city_id       int         null,
    daily_info_id int         null,
    main_info_id  int         null,
    wind_info_id  int         null,
    constraint FK4udu9uofd3v1uplf4qsoyxfhs
        foreign key (main_info_id) references main_info (id),
    constraint FKi69qofeijw94keerenhciitt2
        foreign key (wind_info_id) references wind_info (id),
    constraint FKktbfmav4pw80fcvpi7c5mocm0
        foreign key (daily_info_id) references daily_info (id),
    constraint FKt8uh84m6lx241oivi4rgsighr
        foreign key (city_id) references city (id)
);

create table weather_point_conditions
(
    weather_point_id int not null,
    conditions_id    int not null,
    primary key (weather_point_id, conditions_id),
    constraint FKd45a3xykrxhr7v4owpraysfm6
        foreign key (conditions_id) references weather_condition (id),
    constraint FKqxbpe6okkc3577kb5uxnvg2p9
        foreign key (weather_point_id) references weather_point (id)
);
