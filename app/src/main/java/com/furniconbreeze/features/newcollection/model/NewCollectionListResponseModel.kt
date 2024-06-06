package com.furniconbreeze.features.newcollection.model

import com.furniconbreeze.app.domain.CollectionDetailsEntity
import com.furniconbreeze.base.BaseResponse
import com.furniconbreeze.features.shopdetail.presentation.model.collectionlist.CollectionListDataModel

/**
 * Created by Saikat on 15-02-2019.
 */
class NewCollectionListResponseModel : BaseResponse() {
    //var collection_list: ArrayList<CollectionListDataModel>? = null
    var collection_list: ArrayList<CollectionDetailsEntity>? = null
}