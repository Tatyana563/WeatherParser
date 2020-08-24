package com.parser.demo.repository;

import com.parser.demo.entity.WeatherCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface WeatherConditionRepository extends JpaRepository<WeatherCondition, Integer> {

}

