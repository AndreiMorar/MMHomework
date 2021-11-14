package com.mab.mmhomework.websocket

import com.mab.mmhomework.db.entities.ChatMsg
import com.mab.mmhomework.repository.ChatRepository
import com.mab.mmhomework.user.MockUserManager
import kotlin.random.Random

/**
 * @author MAB
 */
class MockWebSocket {

    private val randomizer = Random(555)

    lateinit var repo: ChatRepository

    suspend fun sendMsg(msg: ChatMsg) {

        //
        //sendEvent(...)
        //

        //trigger a mock reply from the u
        for (i in 1..randomizer.nextInt(1, 4)) {
            onMockEventListener(
                ChatMsg.newInstance("$i - ${msg.message}", MockUserManager.REMOTE_USER_ID)
            )
        } // ser
    }

    suspend fun onMockEventListener(msg: ChatMsg) {
        repo.addRemoteMsg(msg)
    }

}