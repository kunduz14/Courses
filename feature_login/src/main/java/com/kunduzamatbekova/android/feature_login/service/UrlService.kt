package com.kunduzamatbekova.android.feature_login.service

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.kunduzamatbekova.android.feature_login.api.UrlApi

class UrlService(
    private val urlApi: UrlApi,
    private val context: Context
) {
    
    suspend fun openVk() {
        urlApi.openUrl("https://vk.com/")
        openInBrowser("https://vk.com/")
    }
    
    suspend fun openOk() {
        urlApi.openUrl("https://ok.ru/")
        openInBrowser("https://ok.ru/")
    }
    
    private fun openInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
} 