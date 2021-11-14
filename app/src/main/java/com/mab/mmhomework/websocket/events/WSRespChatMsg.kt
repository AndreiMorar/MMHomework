package com.mab.mmhomework.websocket.events

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

/**
 * @author MAB
 */
class WSRespChatMsg(
    val id: String,
    val senderId: Int,
    val timestamp: Long,
    val type: String,
    val message: String?
)