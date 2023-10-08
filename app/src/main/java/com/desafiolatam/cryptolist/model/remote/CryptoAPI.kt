package com.desafiolatam.cryptolist.model.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoAPI {

    @GET("assets")
    suspend fun getAssets(): Response<AssetsWrapper> //AssetsWrapper

    @GET("assets/{id}")
    suspend fun getAssets(@Path("id") id:String): Response<AssetDetail>
}