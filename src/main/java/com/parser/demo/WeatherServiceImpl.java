package com.parser.demo;

import com.parser.demo.dto.CoordinatesDto;
import com.parser.demo.dto.WeatherDto;
import com.parser.demo.dto.WeatherResponseDto;
import com.parser.demo.entity.City;
import com.parser.demo.entity.Coordinates;
import com.parser.demo.entity.WeatherCondition;
import com.parser.demo.entity.WeatherPoint;
import com.parser.demo.repository.CityRepository;
import com.parser.demo.repository.WeatherConditionRepository;
import com.parser.demo.repository.WeatherPointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WeatherServiceImpl implements WeatherService{
    @Autowired
    private WeatherPointRepository weatherPointRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private WeatherConditionRepository weatherConditionRepository;

    @Override
    @Transactional
    public void save(WeatherResponseDto weatherDto) {
        WeatherPoint point = new WeatherPoint();
        int cityId = weatherDto.getId();

        City city = cityRepository.findById(cityId).orElseGet(() -> {
            City newCity = new City();
            newCity.setId(cityId);
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

        point.setCity(city);
        point.setConditions();
        //Меняется раз в сутки. Следует проверить наличие существующей записи
        point.setDailyInfo();
        //Каждый раз своя дата. Изначально необходимо выполнить поиск в бд по этой дате
        //Если запись найдена - погодные условия не менялись.
        point.setDate();
        //Каждый раз новые данные, сохраняем как новые
        point.setMainInfo();

        weatherPointRepository.save(point);
    }}
