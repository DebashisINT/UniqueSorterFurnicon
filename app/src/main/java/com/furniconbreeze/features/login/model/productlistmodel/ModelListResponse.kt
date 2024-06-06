package com.furniconbreeze.features.login.model.productlistmodel

import com.furniconbreeze.app.domain.ModelEntity
import com.furniconbreeze.app.domain.ProductListEntity
import com.furniconbreeze.base.BaseResponse

class ModelListResponse: BaseResponse() {
    var model_list: ArrayList<ModelEntity>? = null
}