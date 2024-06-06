package com.furniconbreeze.features.weather.api

import com.furniconbreeze.base.BaseResponse
import com.furniconbreeze.features.task.api.TaskApi
import com.furniconbreeze.features.task.model.AddTaskInputModel
import com.furniconbreeze.features.weather.model.ForeCastAPIResponse
import com.furniconbreeze.features.weather.model.WeatherAPIResponse
import io.reactivex.Observable

class WeatherRepo(val apiService: WeatherApi) {
    fun getCurrentWeather(zipCode: String): Observable<WeatherAPIResponse> {
        return apiService.getTodayWeather(zipCode)
    }

    fun getWeatherForecast(zipCode: String): Observable<ForeCastAPIResponse> {
        return apiService.getForecast(zipCode)
    }
}