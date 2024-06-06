package com.furniconbreeze.features.viewAllOrder.interf

import com.furniconbreeze.app.domain.NewOrderColorEntity
import com.furniconbreeze.app.domain.NewOrderProductEntity

interface ColorListNewOrderOnClick {
    fun productListOnClick(color: NewOrderColorEntity)
}