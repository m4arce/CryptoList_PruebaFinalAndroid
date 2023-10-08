package com.desafiolatam.cryptolist.model.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AssetEntity (
    @PrimaryKey val id: String,
    val rank: String = "",
    val symbol: String,
    val name: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val supply: String = "",
    val maxSupply: String = "",
    val marketCapUsd: String = "",
    val volumeUsd24Hr: String = "",
    val vwap24Hr: String = "",
    val explorer: String = "",
    val timestamp: Long,
    val imgSrc: String
)
