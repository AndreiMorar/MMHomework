package com.mab.mmhomework.websocket.events

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.mab.mmhomework.db.entities.ChatMsg
import com.mab.mmhomework.db.entities.TChatMsg

/**
 * @author MAB
 */
class WSRespChatMsg(
    val id: String,
    val senderId: Int,
    val timestamp: Long,
    val type: String,
    val message: String?
){
    companion object{
        fun mockNewInstance(msg: String, userId: Int): WSRespChatMsg {
            val timestamp: Long = System.currentTimeMillis()
            return WSRespChatMsg(
                ChatMsg.generateId(msg, timestamp, userId),
                userId,
                timestamp,
                TChatMsg.TEXT.name.lowercase(),
                msg
            )
        }
    }
}