package com.mab.mmhomework.repository

import com.mab.mmhomework.db.ChatMsgDAO
import com.mab.mmhomework.db.entities.ChatMsg
import com.mab.mmhomework.user.MockUserManager
import com.mab.mmhomework.websocket.MockWebSocket
import kotlinx.coroutines.flow.Flow

/**
 * @author MAB
 */
class ChatRepository(
    private val chatMsgDAO: ChatMsgDAO,
    private val websocket: MockWebSocket
) {

    val allMsgsFlow: Flow<List<ChatMsg>> = chatMsgDAO.getAll()

    suspend fun sendLocalMsg(msg: String) {
        ChatMsg.newInstance(msg, MockUserManager.CUR_USER_ID).apply {
            chatMsgDAO.insert(this)
            websocket.sendMsg(this)
        }
    }

    suspend fun addRemoteMsg(msg: ChatMsg) {
        chatMsgDAO.insert(msg)
    }

}