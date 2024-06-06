package com.furniconbreeze.features.stockCompetetorStock.api

import com.furniconbreeze.base.BaseResponse
import com.furniconbreeze.features.orderList.model.NewOrderListResponseModel
import com.furniconbreeze.features.stockCompetetorStock.ShopAddCompetetorStockRequest
import com.furniconbreeze.features.stockCompetetorStock.model.CompetetorStockGetData
import io.reactivex.Observable

class AddCompStockRepository(val apiService:AddCompStockApi){

    fun addCompStock(shopAddCompetetorStockRequest: ShopAddCompetetorStockRequest): Observable<BaseResponse> {
        return apiService.submShopCompStock(shopAddCompetetorStockRequest)
    }

    fun getCompStockList(sessiontoken: String, user_id: String, date: String): Observable<CompetetorStockGetData> {
        return apiService.getCompStockList(sessiontoken, user_id, date)
    }
}