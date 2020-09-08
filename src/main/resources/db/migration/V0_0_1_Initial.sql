create table cloud_info
(
    id                   int auto_increment
        primary key,
    percentage_of_clouds double not null
);

-- $ mvn clean flyway:migrate -Dflyway.configFile=application.properties
