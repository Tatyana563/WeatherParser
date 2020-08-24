package com.parser.demo.repository;

import com.parser.demo.entity.DailyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyInfoRepository extends JpaRepository<DailyInfo, Integer> {
}
