package com.mrickar.hearthstonedeckhelper.data.remote.interceptor

import com.mrickar.hearthstonedeckhelper.common.IsMock
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.lang.NullPointerException

class MockInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        lateinit var response: Response
        if(IsMock.isMock)
        {
            val uri=chain.request().url.toUri()

            var path=uri.path + uri.query +"/MOCK"
            path = if (path.startsWith('/')) path.substring(1) else path
            println("path: $path")
            val responseString: String = readFromJson(path).orEmpty()
            response = Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(responseString.toByteArray().toResponseBody("application/json".toMediaType()))
                .addHeader("content-type", "application/json")
                .build()

        }
        else{
            response=chain.proceed(chain.request())
        }
        return response
    }
    private fun readFromJson(fileName:String):String?{

        return try {
//            println("fileName " + fileName)
            val jsonString = this::class.java.classLoader.getResource("$fileName.json").readText()
            jsonString
        } catch (e: IOException) {
            println("COULDN'T FIND THE JSON FILE FOR MOCKING!")
            null
        }
        catch (e:NullPointerException)
        {
            println(e.message)
            null
        }

    }
}