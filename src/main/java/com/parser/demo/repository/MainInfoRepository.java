package com.parser.demo.repository;

import com.parser.demo.entity.MainInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MainInfoRepository extends JpaRepository<MainInfo, Integer> {
  Optional<MainInfo> findByHumidityAndPressureAndTemp(int humidity, int pressure, double temp);
}
