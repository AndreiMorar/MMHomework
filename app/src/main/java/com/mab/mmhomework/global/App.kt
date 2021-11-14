package com.mab.mmhomework.global

import android.app.Application
import com.mab.mmhomework.db.AppDatabase
import com.mab.mmhomework.repository.ChatRepository
import com.mab.mmhomework.websocket.MockWebSocket

/**
 * @author MAB
 */
class App : Application() {

    lateinit var repo: ChatRepository
    lateinit var webSocket: MockWebSocket

    override fun onCreate() {
        super.onCreate()

        AppDatabase.init(this)
        webSocket = MockWebSocket()
        repo = ChatRepository(AppDatabase.get().chatMsgDao(), webSocket).also { repo ->
            webSocket.repo = repo
        }

    }
}