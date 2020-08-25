package com.parser.demo.repository;

import com.parser.demo.entity.City;
import com.parser.demo.entity.WeatherPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherPointRepository extends JpaRepository<WeatherPoint, Integer> {

    @Query("from WeatherPoint as wp where wp.date=:data")
    WeatherPoint findByDate(@Param("data") Long date);

    WeatherPoint findByDateAndCity_Id(Long date, int cityId);


//    WeatherPoint findByDate(Long date);
//

}

//String sql = "SELECT * FROM EMPLOYEE WHERE id = :employee_id";
//SQLQuery query = session.createSQLQuery(sql);
//query.addEntity(Employee.class);
//query.setParameter("employee_id", 10);
//List results = query.list();