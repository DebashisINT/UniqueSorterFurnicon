package com.furniconbreeze.features.dailyPlan.api

import com.furniconbreeze.app.Pref
import com.furniconbreeze.base.BaseResponse
import com.furniconbreeze.features.dailyPlan.model.AllPlanListResponseModel
import com.furniconbreeze.features.dailyPlan.model.GetPlanDetailsResponseModel
import com.furniconbreeze.features.dailyPlan.model.GetPlanListResponseModel
import com.furniconbreeze.features.dailyPlan.model.UpdatePlanListInputParamsModel
import io.reactivex.Observable

/**
 * Created by Saikat on 24-12-2019.
 */
class PlanRepo(val apiService: PlanApi) {
    fun getPlanList(): Observable<GetPlanListResponseModel> {
        return apiService.getPlanList(Pref.session_token!!, Pref.user_id!!)
    }

    fun updatePlanList(updatePlan: UpdatePlanListInputParamsModel): Observable<BaseResponse> {
        return apiService.updatePlanList(updatePlan)
    }

    fun getPlanDetails(plan_id: String): Observable<GetPlanDetailsResponseModel> {
        return apiService.getPlanListDetails(Pref.session_token!!, Pref.user_id!!, plan_id)
    }

    fun getAllPlanList(): Observable<AllPlanListResponseModel> {
        return apiService.getAllPlanList(Pref.session_token!!, Pref.user_id!!)
    }
}