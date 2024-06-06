package com.furniconbreeze.features.newcollectionreport

import com.furniconbreeze.features.photoReg.model.UserListResponseModel

interface PendingCollListner {
    fun getUserInfoOnLick(obj: PendingCollData)
}