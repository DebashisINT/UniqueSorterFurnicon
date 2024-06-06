package com.furniconbreeze.features.stockAddCurrentStock.api

import com.furniconbreeze.base.BaseResponse
import com.furniconbreeze.features.location.model.ShopRevisitStatusRequest
import com.furniconbreeze.features.location.shopRevisitStatus.ShopRevisitStatusApi
import com.furniconbreeze.features.stockAddCurrentStock.ShopAddCurrentStockRequest
import com.furniconbreeze.features.stockAddCurrentStock.model.CurrentStockGetData
import com.furniconbreeze.features.stockCompetetorStock.model.CompetetorStockGetData
import io.reactivex.Observable

class ShopAddStockRepository (val apiService : ShopAddStockApi){
    fun shopAddStock(shopAddCurrentStockRequest: ShopAddCurrentStockRequest?): Observable<BaseResponse> {
        return apiService.submShopAddStock(shopAddCurrentStockRequest)
    }

    fun getCurrStockList(sessiontoken: String, user_id: String, date: String): Observable<CurrentStockGetData> {
        return apiService.getCurrStockListApi(sessiontoken, user_id, date)
    }

}