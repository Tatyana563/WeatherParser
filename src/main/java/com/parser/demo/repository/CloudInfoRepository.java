package com.parser.demo.repository;

import com.parser.demo.entity.CloudInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// 2020.2.123123
// 2020.2
// 2020 - major version
// .2 - minor version

//V4__new_Gen.sql
@Repository
public interface CloudInfoRepository extends JpaRepository<CloudInfo, Integer> {
   Optional<CloudInfo> findBypercentageOfClouds(double percOfClouds);
}
