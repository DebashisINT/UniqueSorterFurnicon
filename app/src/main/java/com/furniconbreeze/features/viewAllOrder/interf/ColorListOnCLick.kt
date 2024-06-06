package com.furniconbreeze.features.viewAllOrder.interf

import com.furniconbreeze.app.domain.NewOrderGenderEntity
import com.furniconbreeze.features.viewAllOrder.model.ProductOrder

interface ColorListOnCLick {
    fun colorListOnCLick(size_qty_list: ArrayList<ProductOrder>, adpPosition:Int)
}