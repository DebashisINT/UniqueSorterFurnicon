package com.furniconbreeze.features.leaderboard.api

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import com.fasterxml.jackson.databind.ObjectMapper
import com.furniconbreeze.app.FileUtils
import com.furniconbreeze.app.Pref
import com.furniconbreeze.base.BaseResponse
import com.furniconbreeze.features.addshop.model.AddLogReqData
import com.furniconbreeze.features.addshop.model.AddShopRequestData
import com.furniconbreeze.features.addshop.model.AddShopResponse
import com.furniconbreeze.features.addshop.model.LogFileResponse
import com.furniconbreeze.features.addshop.model.UpdateAddrReq
import com.furniconbreeze.features.contacts.CallHisDtls
import com.furniconbreeze.features.contacts.CompanyReqData
import com.furniconbreeze.features.contacts.ContactMasterRes
import com.furniconbreeze.features.contacts.SourceMasterRes
import com.furniconbreeze.features.contacts.StageMasterRes
import com.furniconbreeze.features.contacts.StatusMasterRes
import com.furniconbreeze.features.contacts.TypeMasterRes
import com.furniconbreeze.features.dashboard.presentation.DashboardActivity
import com.furniconbreeze.features.login.model.WhatsappApiData
import com.furniconbreeze.features.login.model.WhatsappApiFetchData
import com.google.gson.Gson
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by Puja on 10-10-2024.
 */
class LeaderboardRepo(val apiService: LeaderboardApi) {

    fun branchlist(session_token: String): Observable<LeaderboardBranchData> {
        return apiService.branchList(session_token)
    }
    fun ownDatalist(user_id: String,activitybased: String,branchwise: String,flag: String): Observable<LeaderboardOwnData> {
        return apiService.ownDatalist(user_id,activitybased,branchwise,flag)
    }
    fun overAllAPI(user_id: String,activitybased: String,branchwise: String,flag: String): Observable<LeaderboardOverAllData> {
        return apiService.overAllDatalist(user_id,activitybased,branchwise,flag)
    }
}