package com.parser.demo.repository;

import com.parser.demo.entity.WindInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WindInfoRepository extends JpaRepository<WindInfo, Integer> {
  Optional<WindInfo> findByGustAndSpeed(double speed, double gust);
}
