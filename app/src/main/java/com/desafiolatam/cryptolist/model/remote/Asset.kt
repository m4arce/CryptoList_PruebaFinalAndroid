package com.desafiolatam.cryptolist.model.remote

// Corresponde a la información del asset utilizada al traer todos los assets
data class Asset (
    val id: String,
    val symbol: String,
    val name: String,
    val priceUsd: String,
    val changePercent24Hr: String
)

data class AssetsWrapper(val data: List<Asset>, val timestamp: Long)

// Utilizada para mostrar la información de detalle de un asset
data class AssetDetail(var data: Data, var timestamp: Long)

data class Data(
    var id: String,
    var rank: String,
    var symbol: String,
    var name: String,
    var supply: String,
    var maxSupply: String?,
    var marketCapUsd: String,
    var volumeUsd24Hr: String,
    var priceUsd: String,
    var changePercent24Hr: String,
    var vwap24Hr: String?,
    var explorer: String
)
