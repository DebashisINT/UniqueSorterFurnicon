package com.furniconbreeze.features.nearbyuserlist.api

import com.furniconbreeze.app.Pref
import com.furniconbreeze.features.nearbyuserlist.model.NearbyUserResponseModel
import com.furniconbreeze.features.newcollection.model.NewCollectionListResponseModel
import com.furniconbreeze.features.newcollection.newcollectionlistapi.NewCollectionListApi
import io.reactivex.Observable

class NearbyUserRepo(val apiService: NearbyUserApi) {
    fun nearbyUserList(): Observable<NearbyUserResponseModel> {
        return apiService.getNearbyUserList(Pref.session_token!!, Pref.user_id!!)
    }
}