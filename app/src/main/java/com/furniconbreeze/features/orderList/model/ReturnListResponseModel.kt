package com.furniconbreeze.features.orderList.model

import com.furniconbreeze.base.BaseResponse


class ReturnListResponseModel: BaseResponse() {
    var return_list: ArrayList<ReturnDataModel>? = null
}