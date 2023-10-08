package com.desafiolatam.cryptolist.model

import com.desafiolatam.cryptolist.model.db.AssetEntity
import com.desafiolatam.cryptolist.model.db.CryptoApplication
import com.desafiolatam.cryptolist.model.remote.Asset
import com.desafiolatam.cryptolist.model.remote.AssetDetail
import com.desafiolatam.cryptolist.model.remote.RetrofitClient
import timber.log.Timber

class Repository {

    private val assetsDao = CryptoApplication.assetDatabase!!.assetDao()

    fun getAssets() = assetsDao.getAssets()
    suspend fun retrieveAssets() {
        val response = RetrofitClient.instance().getAssets()
        when (response.isSuccessful) {
            true -> {
                response.body()?.let { assets ->
                    Timber.d("${assets.data.size} - ${assets.data[0]}")
                    val entities = assets.data.map { asset -> storeAsset(asset,
                        assets.timestamp) }
                    assetsDao.insert(entities)
                }
            }
            false -> {
                Timber.d("${response.code()} - ${response.errorBody()}")
            }
        }
    }

    private fun storeAsset(asset: Asset, timestamp: Long): AssetEntity {
        val imgSrc = getImageFromSymbol(asset.symbol)
        val entity = model2DB(asset, timestamp, imgSrc)
        return entity
    }
    suspend fun retrieveAssetDetail(id: String) {
        val response = RetrofitClient.instance().getAssets(id)
        when (response.isSuccessful) {
            true -> {
                response.body()?.let { detail ->
                    Timber.d("$detail")
                    val storeAssetDetail = storeAssetDetail(detail)
                    assetsDao.insert(storeAssetDetail)
                }
            }
            false -> {
                Timber.d("Detail - ${response.code()} - ${response.errorBody()}")
            }
        }
    }
    private fun storeAssetDetail(detail: AssetDetail) : AssetEntity {
        val imgSrc = getImageFromSymbol(detail.data.symbol)
        val model2DB = model2DB(detail, imgSrc)
        return model2DB
    }
    fun getAsset(id: String) = assetsDao.getAsset(id)
}

/*
* La imagen asociada se construye con el símbolo en minúscula
* https://static.coincap.io/assets/icons/{symbol}@2x.png
*/
fun getImageFromSymbol(symbol: String) =
    "https://static.coincap.io/assets/icons/${symbol.lowercase()}@2x.png"
fun model2DB(asset: Asset, ts: Long, imgSrc: String): AssetEntity {
    return AssetEntity(
        id = asset.id,
        symbol = asset.symbol,
        name = asset.name,
        priceUsd = asset.priceUsd,
        changePercent24Hr = asset.changePercent24Hr,
        timestamp = ts,
        imgSrc = imgSrc
    )
}
fun model2DB(detail: AssetDetail, imgSrc: String): AssetEntity {
    val d = detail.data
    return AssetEntity(
        d.id,
        d.rank,
        d.symbol,
        d.name,
        d.priceUsd,
        d.changePercent24Hr,
        d.supply,
        d.maxSupply ?: "",
        d.marketCapUsd,
        d.volumeUsd24Hr,
        d.vwap24Hr ?: "",
        d.explorer,
        detail.timestamp,
        imgSrc
    )
}

