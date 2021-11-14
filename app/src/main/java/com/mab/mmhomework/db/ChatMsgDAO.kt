package com.mab.mmhomework.db

import androidx.room.Dao
import androidx.room.Query
import com.mab.mmhomework.db.entities.ChatMsg
import kotlinx.coroutines.flow.Flow

/**
 * @author MAB
 */
@Dao
interface ChatMsgDAO : BaseDao<ChatMsg> {

    @Query("SELECT * FROM chat_msg ORDER BY timestamp ASC")
    fun getAll(): Flow<List<ChatMsg>>

}