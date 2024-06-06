package com.furniconbreeze.features.photoReg.present

import com.furniconbreeze.app.domain.ProspectEntity
import com.furniconbreeze.features.photoReg.model.UserListResponseModel

interface DsStatusListner {
    fun getDSInfoOnLick(obj: ProspectEntity)
}