package com.parser.demo.repository;

import com.parser.demo.entity.RainInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RainInfoRepository extends JpaRepository<RainInfo, Integer> {
 Optional<RainInfo> findByOneHourAndThreeHours(String one, String three);
}