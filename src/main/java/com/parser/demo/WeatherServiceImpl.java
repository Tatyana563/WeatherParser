package com.parser.demo;

import com.parser.ConnectionFactory;
import com.parser.demo.dto.*;
import com.parser.demo.entity.*;
import com.parser.demo.repository.CityRepository;
import com.parser.demo.repository.DailyInfoRepository;
import com.parser.demo.repository.WeatherConditionRepository;
import com.parser.demo.repository.WeatherPointRepository;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class WeatherServiceImpl implements WeatherService {
    @Autowired
    private WeatherPointRepository weatherPointRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private WeatherConditionRepository weatherConditionRepository;
    @Autowired
    private DailyInfoRepository dailyInfoRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(WeatherResponseDto weatherDto) {
        int cityId = weatherDto.getId();
        WeatherPoint byDate = weatherPointRepository.findByDateAndCity_remoteId(weatherDto.getDt(), cityId);

        if (byDate == null) {
            WeatherPoint point = new WeatherPoint();
            City city = cityRepository.findByRemoteId(cityId).orElseGet(() -> {
                City newCity = new City();
                newCity.setRemoteId(cityId);
                newCity.setName(weatherDto.getName());
                CoordinatesDto coord = weatherDto.getCoord();
                newCity.setCoordinates(new Coordinates(coord.getLon(), coord.getLat()));
                newCity.setCountry(weatherDto.getSys().getCountry());

                return cityRepository.save(newCity);
            });

            Set<WeatherCondition> weatherConditionSet = new HashSet<>();
            for (WeatherDto condition : weatherDto.getWeather()) {
                WeatherCondition weatherCondition = weatherConditionRepository
                        .findById(condition.getId())
                        .orElseGet(() -> {
                            WeatherCondition newCondition = new WeatherCondition();
                            newCondition.setId(condition.getId());
                            newCondition.setDescription(condition.getDescription());
                            newCondition.setMain(condition.getMain());
                            newCondition.setIcon(condition.getIcon());

                            return weatherConditionRepository.save(newCondition);

                        });
                weatherConditionSet.add(weatherCondition);
            }

            Instant sunrise = weatherDto.getSys().getSunrise();
            Instant sunset = weatherDto.getSys().getSunset();
            int timezone = weatherDto.getTimezone();

            Optional<DailyInfo> bySunriseAndSunsetAndTimezone = dailyInfoRepository.findBySunriseAndSunsetAndTimezone(sunrise, sunset, timezone);

            DailyInfo dailyInfo;
            if (bySunriseAndSunsetAndTimezone.isEmpty()) {
                DailyInfo newDailyInfo = new DailyInfo();
                newDailyInfo.setSunrise(weatherDto.getSys().getSunrise());
                newDailyInfo.setSunset(weatherDto.getSys().getSunset());
                newDailyInfo.setTimezone(weatherDto.getTimezone());
                dailyInfo = dailyInfoRepository.save(newDailyInfo);
            } else {
                dailyInfo = bySunriseAndSunsetAndTimezone.get();
            }

            //Проверить на нулл
            CloudInfo cloudInfo = null;
            if (!Objects.isNull(weatherDto.getClouds())) {
                cloudInfo = new CloudInfo();
                cloudInfo.setPercentageOfClouds(weatherDto.getClouds().getAll());
            }


            MainInfo mainInfo = null;
            if (weatherDto.getMain() != null) {
                mainInfo = new MainInfo();
                mainInfo.setHumidity(weatherDto.getMain().getHumidity());
                mainInfo.setPressure(weatherDto.getMain().getPressure());
                mainInfo.setTemp(weatherDto.getMain().getTemp());
            }

            RainInfo rainInfo = null;
            if (weatherDto.getRain() != null) {
                rainInfo = new RainInfo();
                rainInfo.setOneHour(weatherDto.getRain().getOneHour());
                rainInfo.setThreeHours(weatherDto.getRain().getThreeHours());
            }

            WindInfo windInfo = null;
            if (weatherDto.getWind() != null) {
                windInfo = new WindInfo();
                windInfo.setGust(weatherDto.getWind().getGust());
                windInfo.setSpeed(weatherDto.getWind().getSpeed());
            }

            point.setCity(city);
            point.setConditions(weatherConditionSet);
            point.setDailyInfo(dailyInfo);
            point.setCloudInfo(cloudInfo);
            point.setMainInfo(mainInfo);
            point.setRainInfo(rainInfo);
            point.setWindInfo(windInfo);
            point.setDate(weatherDto.getDt());
            weatherPointRepository.save(point);
//        //Меняется раз в сутки. Следует проверить наличие существующей записи
//        point.setDailyInfo();
//        //Каждый раз своя дата. Изначально необходимо выполнить поиск в бд по этой дате
//        //Если запись найдена - погодные условия не менялись.
//        point.setDate();
//        //Каждый раз новые данные, сохраняем как новые
//        point.setMainInfo();
        } else {
            System.out.println("Current weather for "
                    + weatherDto.getName() + " at "
                    + (weatherDto.getDt()) +
                    " has already been saved in DB");
        }
    }

    //VARIANT1: NATIVEQUERY
    @Override
//    //TODO: заменить String на Instant
    public AvgTempResponse avgTempInCityBetweenTwoDates(String cityName, String startDate, String finalDate) {
        String q = " SELECT AVG(main_info.temp) as average, city.name " +
                " FROM main_info" +
                " INNER JOIN weather_point ON main_info.id = weather_point.main_info_id " +
                " INNER JOIN city ON weather_point.city_id = city.id " +
                "WHERE city.name= ? AND weather_point.date >= ? and weather_point.date <= ? ";
        Query query = entityManager.createNativeQuery(q);
        query.setParameter(1, cityName);
        query.setParameter(2, startDate);
        query.setParameter(3, finalDate);
        Object[] result = (Object[]) query.getSingleResult();
        AvgTempResponse avgTempResponse = new AvgTempResponse((double) result[0], (String) result[1]);
        return avgTempResponse;
    }

    public PrecipitationResponse getSumOfPrecipitations(String city, String startDate) {
      PrecipitationResponse response = null;

        LocalDate localDate = LocalDate.parse(startDate);
        Instant instant = localDate.minusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant();


        response =  (PrecipitationResponse) entityManager.createQuery("select new " +
                " com.parser.demo.dto.PrecipitationResponse(wp.city.name, SUM(wp.rainInfo.oneHour)) from WeatherPoint wp" +
                " where wp.city.name=:city AND wp.date>:time ")
                .setParameter("city", city)
                .setParameter("time", instant)
                .getSingleResult();

        if (cityRepository.findByname(city) != null) {
            if (response.getPrecipitationSum() == null) {
                response.setName(city);
                response.setPrecipitationSum(0.);
            }
        }
        return response;
    }


}






    //VARIANT@: Hibernate version
//    public AvgTempResponse avgTempInCityBetweenTwoDates(String cityName, String startDate, String finalDate) {
//        String q = " SELECT AVG(main_info.temp) as average, city.name " +
//                " FROM main_info" +
//                " INNER JOIN weather_point ON main_info.id = weather_point.main_info_id " +
//                " INNER JOIN city ON weather_point.city_id = city.id " +
//                "WHERE city.name= ? AND weather_point.date >= ? and weather_point.date <= ? ";
//        Connection connection = ConnectionFactory.getConnection();
//        AvgTempResponse avgTempResponse = null;
//        try (PreparedStatement statement = connection.prepareStatement(q)) {
//            statement.setString(1, cityName);
//            statement.setString(2, startDate);
//            statement.setString(3, finalDate);
//            ResultSet resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                Double avgTemp = resultSet.getDouble("average");
//                String name = resultSet.getString("name");
//                avgTempResponse = new AvgTempResponse(avgTemp, name);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//
//        }
//        return avgTempResponse;
//    }
//}

    //VARIANT 3: HQL query
//    public AvgTempResponse avgTempInCityBetweenTwoDates(String q, String start, String finish) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        AvgTempResponse avgTempResponse = new AvgTempResponse();
//        try {
//            avgTempResponse = (AvgTempResponse) entityManager.createQuery("select new com.parser.demo.dto.AvgTempResponse( avg(wp.mainInfo.temp), wp.city.name) " +
//                    "from WeatherPoint wp " +
//                    "where wp.city.name = :city " +
//                    "and wp.date >= :start " +
//                    "and wp.date <= :finish ")
//                    .setParameter("city", q)
//                    .setParameter("start", simpleDateFormat.parse(start).toInstant())
//                    .setParameter("finish", simpleDateFormat.parse(finish).toInstant())
//                    .getSingleResult();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return avgTempResponse;
//    }
//}
//hql changed
//    public AvgTempResponse avgTempInCityBetweenTwoDates(String q, String start, String finish) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        AvgTempResponse avgTempResponse = new AvgTempResponse();
//        try {
//       avgTempResponse = (AvgTempResponse) entityManager.createQuery("select new com.parser.demo.dto.AvgTempResponse (avg(wp.mainInfo.temp), city.name)" +
//                    "from City city " +
//                    "join WeatherPoint wp on wp.city.id = city.id " +
//                    "where city.name = :cityName " +
//                    "and wp.date >= :startDate " +
//                    "and wp.date <= :endDate ")
//                    .setParameter("cityName", q)
//                    .setParameter("startDate", simpleDateFormat.parse(start).toInstant())
//                    .setParameter("endDate", simpleDateFormat.parse(finish).toInstant())
//                    .getSingleResult();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return avgTempResponse;
//    }
//}
    //hql slightly changed
//    public AvgTempResponse avgTempInCityBetweenTwoDates(String q, String start, String finish) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        AvgTempResponse avgTempResponse = new AvgTempResponse();
//        try {
//            avgTempResponse = (AvgTempResponse) entityManager.createQuery("select new com.parser.demo.dto.AvgTempResponse(avg(wp.mainInfo.temp), city.name)  " +
//                    "from City city " +
//                    "join WeatherPoint wp on wp.city.id = city.id " +
//                    "where city.name = :cityName " +
//                    "and wp.date >= :startDate " +
//                    "and wp.date <= :endDate ")
//                    .setParameter("cityName", q)
//                    .setParameter("startDate", simpleDateFormat.parse(start).toInstant())
//                    .setParameter("endDate", simpleDateFormat.parse(finish).toInstant())
//                    .getSingleResult();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return avgTempResponse;
//    }
//}
//TODO:chech, return null;
//    public AvgTempResponse avgTempInCityBetweenTwoDates(String q, String start, String finish) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        AvgTempResponse avgTempResponse = new AvgTempResponse();
//        try {
//            entityManager.createQuery("select new " + AvgTempResponse.class.getName() + "(avg(wp.mainInfo.temp), city.name)  " +
//                    "from City city " +
//                    "join WeatherPoint wp on wp.city.id = city.id " +
//                    "where city.name = :cityName " +
//                    "and wp.date >= :startDate " +
//                    "and wp.date <= :endDate ")
//                    .setParameter("cityName", q)
//                    .setParameter("startDate", simpleDateFormat.parse(start).toInstant())
//                    .setParameter("endDate", simpleDateFormat.parse(finish).toInstant())
//
//                    .getSingleResult();
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return avgTempResponse;
//    }
//}




