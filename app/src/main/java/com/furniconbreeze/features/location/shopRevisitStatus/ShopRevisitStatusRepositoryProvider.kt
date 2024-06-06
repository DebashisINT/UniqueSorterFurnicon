package com.furniconbreeze.features.location.shopRevisitStatus

import com.furniconbreeze.features.location.shopdurationapi.ShopDurationApi
import com.furniconbreeze.features.location.shopdurationapi.ShopDurationRepository

object ShopRevisitStatusRepositoryProvider {
    fun provideShopRevisitStatusRepository(): ShopRevisitStatusRepository {
        return ShopRevisitStatusRepository(ShopRevisitStatusApi.create())
    }
}