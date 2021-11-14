package com.mab.mmhomework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mab.mmhomework.db.entities.ChatMsg

/**
 * @author MAB
 */
@Database(entities = [ChatMsg::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun chatMsgDao(): ChatMsgDAO

    companion object {

        private lateinit var db: AppDatabase

        fun init(applicationCtx: Context) {
            db = Room.databaseBuilder(applicationCtx, AppDatabase::class.java, "AppDatabase")
                .build()
        }

        fun get(): AppDatabase {
            return db
        }

    }

}