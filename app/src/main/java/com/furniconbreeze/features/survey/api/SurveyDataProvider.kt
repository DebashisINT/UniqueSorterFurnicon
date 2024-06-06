package com.furniconbreeze.features.survey.api

import com.furniconbreeze.features.photoReg.api.GetUserListPhotoRegApi
import com.furniconbreeze.features.photoReg.api.GetUserListPhotoRegRepository

object SurveyDataProvider{

    fun provideSurveyQ(): SurveyDataRepository {
        return SurveyDataRepository(SurveyDataApi.create())
    }

    fun provideSurveyQMultiP(): SurveyDataRepository {
        return SurveyDataRepository(SurveyDataApi.createImage())
    }
}