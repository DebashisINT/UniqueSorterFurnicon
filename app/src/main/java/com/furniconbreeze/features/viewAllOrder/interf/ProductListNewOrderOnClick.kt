package com.furniconbreeze.features.viewAllOrder.interf

import com.furniconbreeze.app.domain.NewOrderGenderEntity
import com.furniconbreeze.app.domain.NewOrderProductEntity

interface ProductListNewOrderOnClick {
    fun productListOnClick(product: NewOrderProductEntity)
}