package com.mab.mmhomework.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mab.mmhomework.utils.Utils
import com.mab.mmhomework.websocket.events.WSRespChatMsg

@Entity(tableName = "chat_msg")
data class ChatMsg(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "sender_id") val senderId: Int,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "message") val message: String?
) {
    companion object {
        fun newInstance(msg: String, userId: Int): ChatMsg {
            val timestamp: Long = System.currentTimeMillis()
            return ChatMsg(
                generateId(msg, timestamp, userId),
                userId,
                timestamp,
                TChatMsg.TEXT.name.lowercase(),
                msg
            )
        }

        fun generateId(msg: String, timestamp: Long, userId: Int): String =
            Utils.hash("$msg$timestamp$userId")
    }
}

enum class TChatMsg {
    TEXT, PHOTO, VIDEO
}

fun WSRespChatMsg.toInternal() = ChatMsg(
    id = id,
    senderId = senderId,
    timestamp = timestamp,
    type = type,
    message = message
)
