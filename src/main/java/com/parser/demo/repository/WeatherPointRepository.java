package com.parser.demo.repository;

import com.parser.demo.entity.WeatherPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherPointRepository extends JpaRepository<WeatherPoint, Integer> {

@Query(" select from  WeatherPoint as wp where wp.date=:data")
    WeatherPoint findByDate(@Param("data") long date);

    //    @Query("update CityEntity c set c.population = :cityPopulation where c.id = :id")
//    void updateCityPopulation(@Param("id") int cityId, @Param("cityPopulation") int cityPopulation);
}

//String sql = "SELECT * FROM EMPLOYEE WHERE id = :employee_id";
//SQLQuery query = session.createSQLQuery(sql);
//query.addEntity(Employee.class);
//query.setParameter("employee_id", 10);
//List results = query.list();