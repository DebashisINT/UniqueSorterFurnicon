package com.furniconbreeze.features.location.api

import com.furniconbreeze.features.location.shopdurationapi.ShopDurationApi
import com.furniconbreeze.features.location.shopdurationapi.ShopDurationRepository


object LocationRepoProvider {
    fun provideLocationRepository(): LocationRepo {
        return LocationRepo(LocationApi.create())
    }
}