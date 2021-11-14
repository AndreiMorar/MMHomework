package com.mab.mmhomework.global

import android.app.Application
import com.mab.mmhomework.db.AppDatabase

/**
 * @author MAB
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        AppDatabase.init(this)
    }
}