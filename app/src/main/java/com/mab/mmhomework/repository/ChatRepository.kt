package com.mab.mmhomework.repository

import androidx.annotation.WorkerThread
import com.mab.mmhomework.db.ChatMsgDAO
import com.mab.mmhomework.db.entities.ChatMsg
import com.mab.mmhomework.user.MockupUserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.forEach

/**
 * @author MAB
 */
class ChatRepository(private val chatMsgDAO: ChatMsgDAO) {

    val allMsgsFlow: Flow<List<ChatMsg>> = chatMsgDAO.getAll()

    @WorkerThread
    suspend fun addMsg(msg: String) {
        ChatMsg.newInstance(msg, MockupUserManager.CUR_USER_ID).apply {
            chatMsgDAO.insert(this)
        }
    }

}