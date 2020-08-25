package com.parser.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherDto, Integer> {

    @Query(value = "select from WeatherDto as wd inner join main as m where wd.name:=city ")
    WeatherDto select(@Param("city") String city);

}

