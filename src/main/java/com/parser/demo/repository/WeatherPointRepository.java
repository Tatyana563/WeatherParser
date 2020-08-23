package com.parser.demo.repository;

import com.parser.demo.entity.WeatherPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherPointRepository extends JpaRepository<WeatherPoint, Integer> {

//

    //    @Query("update CityEntity c set c.population = :cityPopulation where c.id = :id")
//    void updateCityPopulation(@Param("id") int cityId, @Param("cityPopulation") int cityPopulation);
}

//String sql = "SELECT * FROM EMPLOYEE WHERE id = :employee_id";
//SQLQuery query = session.createSQLQuery(sql);
//query.addEntity(Employee.class);
//query.setParameter("employee_id", 10);
//List results = query.list();