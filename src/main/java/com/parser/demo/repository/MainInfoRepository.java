package com.parser.demo.repository;

import com.parser.demo.entity.MainInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainInfoRepository extends JpaRepository<MainInfo, Integer> {
}
