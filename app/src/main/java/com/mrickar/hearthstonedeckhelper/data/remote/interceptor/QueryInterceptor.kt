package com.mrickar.hearthstonedeckhelper.data.remote.interceptor

import com.mrickar.hearthstonedeckhelper.common.Constants
import com.mrickar.hearthstonedeckhelper.common.QueryParameters
import okhttp3.Interceptor
import okhttp3.Response

class QueryInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest=chain.request()
        val originalUrl=originalRequest.url
        val newUrl=originalUrl.newBuilder()
            .addQueryParameter("access_token",Constants.ACCESS_TOKEN)
            .addQueryParameter("pageSize",QueryParameters.PAGE_SIZE)
            .addQueryParameter("locale",QueryParameters.LOCALE)
            .addQueryParameter("gameMode",QueryParameters.GAME_MODE)
            .addQueryParameter("sort",QueryParameters.MANA_ASC)
//            .addQueryParameter("type","${QueryParameters.MINION},${QueryParameters.SPELL},${QueryParameters.WEAPON}")
            .build()
        val newRequest= originalRequest.newBuilder().url(newUrl).build()
        return chain.proceed(newRequest)
    }
}
