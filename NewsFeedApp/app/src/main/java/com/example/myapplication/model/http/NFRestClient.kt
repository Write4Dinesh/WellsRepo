package com.example.myapplication.model.http

import com.example.myapplication.utils.NFLogger
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory

object NFRestClient {
    private val endPointInterface: NFEndpointInterface

    init {
        val httpClient = OkHttpClient
            .Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(getInterceptor())
            .build()

        val retrofit = Retrofit
            .Builder()
            .baseUrl(NFHttpConstants.BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        endPointInterface = retrofit.create(NFEndpointInterface::class.java)
    }

    fun getEndpointImplementation(): NFEndpointInterface {
        return endPointInterface
    }

    private fun getInterceptor(): Interceptor {
        return Interceptor { chain ->
            //Manipulate request if required. add common request headers here
            val originalRequest = chain.request()
            val newUrl = originalRequest
                .url
                .newBuilder()
                .addQueryParameter("apiKey", "80e27ad0404d476587a3619722680148")
                .build()
            val response = chain.proceed(
                originalRequest
                    .newBuilder()
                    .url(newUrl)
                    .build()
            )
            //Manipulate response here
            when (response.code) {
                in 401..403 -> {
                    //Authentication error. fetch token or ask user to login again
                    NFLogger.log(javaClass.name, "Http code 401..403");
                }
                400 -> {
                    //Bad request...
                    NFLogger.log(javaClass.name, "Http code 400: Bad request");
                }
                else -> response
            }
            response

        }
    }
}