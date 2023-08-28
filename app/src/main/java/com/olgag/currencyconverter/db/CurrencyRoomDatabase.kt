package com.olgag.currencyconverter.db

import android.content.Context
import androidx.room.*
import com.olgag.currencyconverter.model.CurrencyRow

@Database(entities = [(CurrencyRow::class)], version = 1)
abstract class CurrencyRoomDatabase: RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    companion object {

        private var INSTANCE: CurrencyRoomDatabase? = null

        fun getInstance(context: Context): CurrencyRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CurrencyRoomDatabase::class.java,
                        "currency_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}