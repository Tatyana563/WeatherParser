package com.parser.demo.repository;

import com.parser.demo.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
 Optional<City> findByRemoteId(int remoteId);
 City findByname(String cityName);
}
