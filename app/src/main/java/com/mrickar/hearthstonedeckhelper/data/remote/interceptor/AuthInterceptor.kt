package com.mrickar.hearthstonedeckhelper.data.remote.interceptor

import com.mrickar.hearthstonedeckhelper.common.Secrets
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest=chain.request()
        val credentials=Credentials.basic(Secrets.CLIENT_ID,Secrets.CLIENT_SECRET)
        val newRequest= originalRequest.newBuilder()
            .addHeader("Authorization",credentials)
            .build()
        return chain.proceed(newRequest)
    }

}
