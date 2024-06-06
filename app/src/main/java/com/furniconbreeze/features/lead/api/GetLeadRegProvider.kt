package com.furniconbreeze.features.lead.api

import com.furniconbreeze.features.NewQuotation.api.GetQuotListRegRepository
import com.furniconbreeze.features.NewQuotation.api.GetQutoListApi


object GetLeadRegProvider {
    fun provideList(): GetLeadListRegRepository {
        return GetLeadListRegRepository(GetLeadListApi.create())
    }
}