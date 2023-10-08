package com.desafiolatam.cryptolist.model.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.coincap.io/v2/"

class RetrofitClient {

    companion object {
        fun instance(): CryptoAPI {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
                GsonConverterFactory.create()).build()
            return retrofit.create(CryptoAPI::class.java)
        }
    }
}