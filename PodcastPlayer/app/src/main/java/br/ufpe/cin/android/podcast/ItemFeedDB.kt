package br.ufpe.cin.android.podcast

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//creating the database, and not instantiating it multiple times
//based in the Room Class, where Estado was replaced by ItemFeed. The same about the interface class

@Database(entities= arrayOf(ItemFeed::class), version=1)
abstract class ItemFeedDB : RoomDatabase(){
    abstract fun itemFeedDAO(): ItemFeedDAO
    companion object{
        private var INSTANCE : ItemFeedDB? = null
        fun getDatabase(ctx : Context) : ItemFeedDB {
            if (INSTANCE == null) {
                synchronized(ItemFeedDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        ctx.applicationContext,
                        ItemFeedDB::class.java,
                        "itemFeed.db"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}