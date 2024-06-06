package com.furniconbreeze.features.location.shopRevisitStatus

import com.furniconbreeze.base.BaseResponse
import com.furniconbreeze.features.location.model.ShopDurationRequest
import com.furniconbreeze.features.location.model.ShopRevisitStatusRequest
import io.reactivex.Observable

class ShopRevisitStatusRepository(val apiService : ShopRevisitStatusApi) {
    fun shopRevisitStatus(shopRevisitStatus: ShopRevisitStatusRequest?): Observable<BaseResponse> {
        return apiService.submShopRevisitStatus(shopRevisitStatus)
    }
}