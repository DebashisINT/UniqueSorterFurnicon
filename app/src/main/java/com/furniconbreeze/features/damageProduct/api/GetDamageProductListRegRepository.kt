package com.furniconbreeze.features.damageProduct.api

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.Log
import com.furniconbreeze.app.FileUtils
import com.furniconbreeze.base.BaseResponse
import com.furniconbreeze.features.NewQuotation.model.*
import com.furniconbreeze.features.addshop.model.AddShopRequestData
import com.furniconbreeze.features.addshop.model.AddShopResponse
import com.furniconbreeze.features.damageProduct.model.DamageProductResponseModel
import com.furniconbreeze.features.damageProduct.model.delBreakageReq
import com.furniconbreeze.features.damageProduct.model.viewAllBreakageReq
import com.furniconbreeze.features.login.model.userconfig.UserConfigResponseModel
import com.furniconbreeze.features.myjobs.model.WIPImageSubmit
import com.furniconbreeze.features.photoReg.model.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class GetDamageProductListRegRepository(val apiService : GetDamageProductListApi) {

    fun viewBreakage(req: viewAllBreakageReq): Observable<DamageProductResponseModel> {
        return apiService.viewBreakage(req)
    }

    fun delBreakage(req: delBreakageReq): Observable<BaseResponse>{
        return apiService.BreakageDel(req.user_id!!,req.breakage_number!!,req.session_token!!)
    }

}