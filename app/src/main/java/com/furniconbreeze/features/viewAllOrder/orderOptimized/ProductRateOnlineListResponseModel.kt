package com.furniconbreeze.features.viewAllOrder.orderOptimized

import com.furniconbreeze.app.domain.ProductOnlineRateTempEntity
import com.furniconbreeze.base.BaseResponse
import com.furniconbreeze.features.login.model.productlistmodel.ProductRateDataModel
import java.io.Serializable

class ProductRateOnlineListResponseModel: BaseResponse(), Serializable {
    var product_rate_list: ArrayList<ProductOnlineRateTempEntity>? = null
}