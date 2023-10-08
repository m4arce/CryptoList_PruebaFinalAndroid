package com.desafiolatam.cryptolist.model.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//En caso de dividir la entidad de los assets deben ser agregados en el parámetro entities
@Database(entities = [AssetEntity::class], version = 1, exportSchema = false)
abstract class AssetsDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
}


//Singleton de db
class CryptoApplication: Application() {
    companion object {
        var assetDatabase: AssetsDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        assetDatabase = Room.databaseBuilder(this, AssetsDatabase::class.java, "crypto_db").build()
    }
}