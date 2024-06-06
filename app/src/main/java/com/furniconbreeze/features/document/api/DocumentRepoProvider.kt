package com.furniconbreeze.features.document.api

import com.furniconbreeze.features.dymanicSection.api.DynamicApi
import com.furniconbreeze.features.dymanicSection.api.DynamicRepo

object DocumentRepoProvider {
    fun documentRepoProvider(): DocumentRepo {
        return DocumentRepo(DocumentApi.create())
    }

    fun documentRepoProviderMultipart(): DocumentRepo {
        return DocumentRepo(DocumentApi.createImage())
    }
}