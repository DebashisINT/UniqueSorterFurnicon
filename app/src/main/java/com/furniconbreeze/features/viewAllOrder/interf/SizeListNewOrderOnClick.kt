package com.furniconbreeze.features.viewAllOrder.interf

import com.furniconbreeze.app.domain.NewOrderProductEntity
import com.furniconbreeze.app.domain.NewOrderSizeEntity

interface SizeListNewOrderOnClick {
    fun sizeListOnClick(size: NewOrderSizeEntity)
}