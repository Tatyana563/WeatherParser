package com.parser.demo.repository;

import com.parser.demo.entity.DailyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface DailyInfoRepository extends JpaRepository<DailyInfo, Integer> {
    Optional<DailyInfo> findBySunriseAndSunsetAndTimezone(Instant sunrise, Instant sunset, int timezone);

}
