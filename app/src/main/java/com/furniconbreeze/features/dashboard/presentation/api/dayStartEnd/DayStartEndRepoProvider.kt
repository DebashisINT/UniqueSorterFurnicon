package com.furniconbreeze.features.dashboard.presentation.api.dayStartEnd

import com.furniconbreeze.features.stockCompetetorStock.api.AddCompStockApi
import com.furniconbreeze.features.stockCompetetorStock.api.AddCompStockRepository

object DayStartEndRepoProvider {
    fun dayStartRepositiry(): DayStartEndRepository {
        return DayStartEndRepository(DayStartEndApi.create())
    }

}