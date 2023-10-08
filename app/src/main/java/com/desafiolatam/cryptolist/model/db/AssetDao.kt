package com.desafiolatam.cryptolist.model.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AssetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assets: AssetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assets: List<AssetEntity>)
    // TODO pueden deben ser pojos distintos y agregar conversores

    @Query("SELECT * FROM assetentity order by name")
    fun getAssets(): LiveData<List<AssetEntity>>

    @Query("SELECT * FROM assetentity WHERE id=:aid")
    fun getAsset(aid: String): LiveData<AssetEntity>
}