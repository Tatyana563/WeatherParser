create table cloud_info
(
    id                   int auto_increment
        primary key,
    percentage_of_clouds double not null
);

create table rain_info
(
    id          int auto_increment
        primary key,
    one_hour    double null,
    three_hours double null
);

alter table weather_point add column cloud_info_id int null;
alter table weather_point add constraint FKmy9iqetrvgnwknr7by5inxbg0
        foreign key (cloud_info_id) references cloud_info (id);

alter table weather_point add column rain_info_id int null;
alter table weather_point add constraint FK2td499gx87e431m5dblg6bakb
        foreign key (rain_info_id) references rain_info (id);
