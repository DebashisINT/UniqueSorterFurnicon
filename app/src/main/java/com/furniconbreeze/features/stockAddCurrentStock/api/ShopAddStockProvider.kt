package com.furniconbreeze.features.stockAddCurrentStock.api

import com.furniconbreeze.features.location.shopRevisitStatus.ShopRevisitStatusApi
import com.furniconbreeze.features.location.shopRevisitStatus.ShopRevisitStatusRepository

object ShopAddStockProvider {
    fun provideShopAddStockRepository(): ShopAddStockRepository {
        return ShopAddStockRepository(ShopAddStockApi.create())
    }
}