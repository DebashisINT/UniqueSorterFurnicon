package com.furniconbreeze.features.weather.api

import com.furniconbreeze.features.task.api.TaskApi
import com.furniconbreeze.features.task.api.TaskRepo

object WeatherRepoProvider {
    fun weatherRepoProvider(): WeatherRepo {
        return WeatherRepo(WeatherApi.create())
    }
}