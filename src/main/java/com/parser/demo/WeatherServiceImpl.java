package com.parser.demo;

import com.parser.ConnectionFactory;
import com.parser.demo.dto.*;
import com.parser.demo.entity.*;
import com.parser.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
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

    @Autowired
    private CloudInfoRepository cloudInfoRepository;

    @Autowired
    private MainInfoRepository mainInfoRepository;
    @Autowired
    private RainInfoRepository rainInfoRepository;
    @Autowired
    private WindInfoRepository windInfoRepository;


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(WeatherResponseDto weatherDto) {
        int cityId = weatherDto.getId();
        WeatherPoint byDate = weatherPointRepository.findByDateAndCity_Id(weatherDto.getDt(), cityId);

        if (byDate == null) {
            WeatherPoint point = new WeatherPoint();
            City city = cityRepository.findById(cityId).orElseGet(() -> {
                City newCity = new City();
                newCity.setId(cityId);
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
            CloudInfo cloudInfo = new CloudInfo();
            cloudInfo.setPercentageOfClouds(weatherDto.getClouds().getAll());

            MainInfo mainInfo = new MainInfo();
            mainInfo.setHumidity(weatherDto.getMain().getHumidity());
            mainInfo.setPressure(weatherDto.getMain().getPressure());
            mainInfo.setTemp(weatherDto.getMain().getTemp());

            RainInfo rainInfo = null;
            RainDto rain = weatherDto.getRain();
            if (rain != null) {
                rainInfo = new RainInfo();
                rainInfo.setOneHour(rain.getOneHour());
                rainInfo.setThreeHours(rain.getThreeHours());
            }

            //Проверить на нулл
            WindInfo windInfo = new WindInfo();
            windInfo.setGust(weatherDto.getWind().getGust());
            windInfo.setSpeed(weatherDto.getWind().getSpeed());


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
                    + new Date(String.valueOf(weatherDto.getDt())) + " has already been found");
        }
    }

    @Override
    public AvgTempResponse avgTempInCityBetweenTwoDates(String cityName, String startDate, String finalDate) {
        String q = " SELECT AVG(main_info.temp) as average, city.name " +
                " FROM main_info" +
                " INNER JOIN weather_point ON main_info.id = weather_point.main_info_id " +
                " INNER JOIN city ON weather_point.city_id = city.id " +
                "WHERE city.name= ? AND weather_point.date >= ? and weather_point.date <= ? ";
//        Query query = entityManager.createNativeQuery(" SELECT AVG(main_info.temp), city.name " +
//                " FROM main_info" +
//                " INNER JOIN weather_point ON main_info.id = weather_point.main_info_id " +
//                " INNER JOIN city ON weather_point.city_id = city.id " +
//                "WHERE city.name= ? AND weather_point.date >= ? and weather_point.date <= ? ");
//        query.setParameter(1, cityName);
//        query.setParameter(2, startDate);
//        query.setParameter(3, finalDate);

        Connection connection = ConnectionFactory.getConnection();
        AvgTempResponse avgTempResponse = null;
        try (PreparedStatement statement = connection.prepareStatement(q)) {
            statement.setString(1, cityName);
            statement.setString(2,startDate);
            statement.setString(3,finalDate);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Double avgTemp = resultSet.getDouble("average");
                String name = resultSet.getString("name");
                avgTempResponse = new AvgTempResponse(avgTemp, name);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return avgTempResponse;
    }


}
