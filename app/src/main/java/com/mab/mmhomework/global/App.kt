package com.mab.mmhomework.global

import android.app.Application
import com.mab.mmhomework.db.AppDatabase
import com.mab.mmhomework.repository.ChatRepository

/**
 * @author MAB
 */
class App : Application() {

    lateinit var repo : ChatRepository

    override fun onCreate() {
        super.onCreate()

        AppDatabase.init(this)
        repo = ChatRepository(AppDatabase.get().chatMsgDao())
    }
}