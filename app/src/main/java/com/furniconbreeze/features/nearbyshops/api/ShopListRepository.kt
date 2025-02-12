package com.furniconbreeze.features.nearbyshops.api

import com.furniconbreeze.app.Pref
import com.furniconbreeze.app.utils.AppUtils
import com.furniconbreeze.base.BaseResponse
import com.furniconbreeze.features.login.model.GetQtsAnsSubmitDtlsResponseModel
import com.furniconbreeze.features.login.model.GetSecImageUploadResponseModel
import com.furniconbreeze.features.login.model.productlistmodel.ModelListResponse
import com.furniconbreeze.features.nearbyshops.model.*
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by Pratishruti on 28-11-2017.
 */
// revision
//1.0 saheli v 4.0.8 getShopTypeList parameter pass 11-04-2023
class ShopListRepository(val apiService: ShopListApi) {
    fun getShopList(sessiontoken: String, user_id: String): Observable<ShopListResponse> {
        return apiService.getShopList(sessiontoken, user_id)
    }

    fun getExtraTeamShopList(sessiontoken: String, user_id: String): Observable<ShopListResponse> {
        return apiService.getExtraTeamShopList(sessiontoken, user_id)
    }

    fun getShopTypeList(sessiontoken:String=Pref.session_token!!,user_id: String=Pref.user_id!!): Observable<ShopTypeResponseModel> {
//        return apiService.getShopTypeList(Pref.session_token!!, Pref.user_id!!)
        return apiService.getShopTypeList(sessiontoken,user_id)//1.0 saheli v 4.0.8 getShopTypeList parameter pass
    }

    fun getShopTypeStockVisibilityList(): Observable<ShopTypeStockViewResponseModel> {
        return apiService.getShopTypeListStockView(Pref.session_token!!, Pref.user_id!!)
    }

    fun getModelList(): Observable<ModelListResponseModel> {
        return apiService.getModelList(Pref.session_token!!, Pref.user_id!!)
    }

    fun getModelListNew(): Observable<ModelListResponse> {
        return apiService.getModelListNew(Pref.session_token!!, Pref.user_id!!)
    }

    fun getPrimaryAppList(): Observable<PrimaryAppListResponseModel> {
        return apiService.getPrimaryAppList(Pref.session_token!!, Pref.user_id!!)
    }

    fun getSecondaryAppList(): Observable<SecondaryAppListResponseModel> {
        return apiService.getSecondaryAppList(Pref.session_token!!, Pref.user_id!!)
    }

    fun getLeadTypeList(): Observable<LeadListResponseModel> {
        return apiService.getLeadList(Pref.session_token!!, Pref.user_id!!)
    }

    fun getStagList(): Observable<StageListResponseModel> {
        return apiService.getStageList(Pref.session_token!!, Pref.user_id!!)
    }

    fun getFunnelStageList(): Observable<FunnelStageListResponseModel> {
        return apiService.getFunnelStageList(Pref.session_token!!, Pref.user_id!!)
    }

    fun getProsList(): Observable<ProsListResponseModel> {
        return apiService.getProsList(Pref.session_token!!, Pref.user_id!!)
    }


    fun getQuestionAnsSubmitDetails(): Observable<GetQtsAnsSubmitDtlsResponseModel> {
        return apiService.getQuestionAnsSubmitDetails(Pref.session_token!!, Pref.user_id!!)
    }

    fun getSecUploadImages(): Observable<GetSecImageUploadResponseModel> {
        return apiService.getSecImageUpload(Pref.session_token!!, Pref.user_id!!)
    }

    fun getQuestionList(): Observable<QuesListResponseModel> {
        return apiService.getQuesList(Pref.session_token!!, Pref.user_id!!)
    }

    fun deleteImei(): Observable<BaseResponse> {
        Timber.d("deleteImei Repo" + AppUtils.getCurrentDateTime())
        return apiService.deleteImeiAPI(Pref.session_token!!, Pref.user_id!!)
    }


}