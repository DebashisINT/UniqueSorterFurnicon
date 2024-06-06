package com.furniconbreeze.features.viewAllOrder.model

import com.furniconbreeze.app.domain.NewOrderColorEntity
import com.furniconbreeze.app.domain.NewOrderGenderEntity
import com.furniconbreeze.app.domain.NewOrderProductEntity
import com.furniconbreeze.app.domain.NewOrderSizeEntity
import com.furniconbreeze.features.stockCompetetorStock.model.CompetetorStockGetDataDtls

class NewOrderDataModel {
    var status:String ? = null
    var message:String ? = null
    var Gender_list :ArrayList<NewOrderGenderEntity>? = null
    var Product_list :ArrayList<NewOrderProductEntity>? = null
    var Color_list :ArrayList<NewOrderColorEntity>? = null
    var size_list :ArrayList<NewOrderSizeEntity>? = null
}

