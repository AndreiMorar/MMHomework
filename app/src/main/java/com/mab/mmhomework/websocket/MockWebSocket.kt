package com.mab.mmhomework.websocket

import com.mab.mmhomework.db.entities.ChatMsg
import com.mab.mmhomework.db.entities.toInternal
import com.mab.mmhomework.repository.ChatRepository
import com.mab.mmhomework.user.MockUserManager
import com.mab.mmhomework.websocket.events.WSRespChatMsg
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

        //trigger a mock reply from the remote user
        for (i in 1..randomizer.nextInt(-4, 4)) {
            if (i < 0) continue
            onMockEventListener(
                WSRespChatMsg.mockNewInstance("Echoed #$i ${msg.message}", MockUserManager.REMOTE_USER_ID)
            )
        }
    }

    suspend fun onMockEventListener(msg: WSRespChatMsg) {
        repo.addRemoteMsg(msg.toInternal())
    }

}