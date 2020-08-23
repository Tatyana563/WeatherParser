package com.parser.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyInfoRepository extends JpaRepository<DailyInfoRepository, Integer> {
}
