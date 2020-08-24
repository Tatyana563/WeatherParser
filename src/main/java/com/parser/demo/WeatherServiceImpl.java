package com.parser.demo;

import com.parser.demo.dto.CoordinatesDto;
import com.parser.demo.dto.WeatherDto;
import com.parser.demo.dto.WeatherResponseDto;
import com.parser.demo.entity.*;
import com.parser.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public void save(WeatherResponseDto weatherDto) {
        WeatherPoint point = new WeatherPoint();
        int id = weatherDto.getId();

        City city = cityRepository.findById(id).orElseGet(() -> {
            City newCity = new City();
            newCity.setId(id);
            newCity.setName(weatherDto.getName());
            CoordinatesDto coord = weatherDto.getCoord();
            newCity.setCoordinates(new Coordinates(coord.getLon(), coord.getLat()));
            newCity.setCountry(weatherDto.getSys().getCountry());

            return cityRepository.save(newCity);
        });

        for (WeatherDto condition : weatherDto.getWeather()) {
            WeatherCondition weatherCondition = weatherConditionRepository
                    .findById(condition.getId())
                    .orElseGet(() -> {
                        WeatherCondition newCondition = new WeatherCondition();

                        return weatherConditionRepository.save(newCondition);
                    });
        }
//        DailyInfo dailyInfo = dailyInfoRepository.findById(weatherDto.getId()).get();
//
//        if (dailyInfo == null) {
//            DailyInfo newDailyInfo = new DailyInfo();
//            newDailyInfo.setId(id);
//            newDailyInfo.setSunrise(weatherDto.getSys().getSunrise());
//            newDailyInfo.setSunset(weatherDto.getSys().getSunset());
//            newDailyInfo.setTimezone(weatherDto.getTimezone());
//            dailyInfo = newDailyInfo;
//        }
        DailyInfo dailyInfo = dailyInfoRepository.findById(id).orElseGet(() -> {
            DailyInfo newDailyInfo = new DailyInfo();
            newDailyInfo.setId(id);
            newDailyInfo.setSunrise(weatherDto.getSys().getSunrise());
            newDailyInfo.setSunset(weatherDto.getSys().getSunset());
            newDailyInfo.setTimezone(weatherDto.getTimezone());
            return dailyInfoRepository.save(newDailyInfo);
        });

        CloudInfo cloudInfo = cloudInfoRepository.findById(id).orElseGet(() -> {
            CloudInfo newCloudInfo = new CloudInfo();
            newCloudInfo.setId(id);
            newCloudInfo.setPercentageOfClouds(weatherDto.getClouds().getAll());
            return cloudInfoRepository.save(newCloudInfo);
        });

        MainInfo mainInfo = mainInfoRepository.findById(id).orElseGet(() -> {
            MainInfo newMainInfo = new MainInfo();
            newMainInfo.setId(id);
            newMainInfo.setHumidity(weatherDto.getMain().getHumidity());
            newMainInfo.setPressure(weatherDto.getMain().getPressure());
            newMainInfo.setTemp(weatherDto.getMain().getTemp());
            return mainInfoRepository.save(newMainInfo);
        });

//        RainInfo rainInfo = rainInfoRepository.findById(id).orElseGet(() -> {
//            RainInfo newRainInfo = new RainInfo();
//            newRainInfo.setId(id);
//            String oneHour = weatherDto.getRain().getOneHour();
//            String threeHours = weatherDto.getRain().getThreeHours();
//            newRainInfo.setOneHour((oneHour==null ? "null": oneHour));
//            newRainInfo.setThreeHours(threeHours==null ? "null":threeHours);
//            return rainInfoRepository.save(newRainInfo);
//        });

        WindInfo windInfo = windInfoRepository.findById(id).orElseGet(() -> {
            WindInfo newWindInfo = new WindInfo();
            newWindInfo.setGust(weatherDto.getWind().getGust());
            newWindInfo.setSpeed(weatherDto.getWind().getSpeed());
            newWindInfo.setId(id);
            return windInfoRepository.save(newWindInfo);
        });

        point.setCity(city);
        // point.setConditions();
        point.setDailyInfo(dailyInfo);
        point.setCloudInfo(cloudInfo);
        point.setMainInfo(mainInfo);
        //  point.setRainInfo(rainInfo);
        point.setWindInfo(windInfo);
        weatherPointRepository.save(point);
//        //Меняется раз в сутки. Следует проверить наличие существующей записи
//        point.setDailyInfo();
//        //Каждый раз своя дата. Изначально необходимо выполнить поиск в бд по этой дате
//        //Если запись найдена - погодные условия не менялись.
//        point.setDate();
//        //Каждый раз новые данные, сохраняем как новые
//        point.setMainInfo();


    }
}
