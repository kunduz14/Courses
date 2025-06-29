package com.kunduzamatbekova.android.feature_login.api

import retrofit2.http.GET
import retrofit2.http.Url

interface UrlApi {
    @GET
    suspend fun openUrl(@Url url: String): String
} 