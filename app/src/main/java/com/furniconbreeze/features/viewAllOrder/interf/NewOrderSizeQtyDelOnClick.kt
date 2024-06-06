package com.furniconbreeze.features.viewAllOrder.interf

import com.furniconbreeze.app.domain.NewOrderGenderEntity
import com.furniconbreeze.features.viewAllOrder.model.ProductOrder
import java.text.FieldPosition

interface NewOrderSizeQtyDelOnClick {
    fun sizeQtySelListOnClick(product_size_qty: ArrayList<ProductOrder>)
    fun sizeQtyListOnClick(product_size_qty: ProductOrder,position: Int)
}