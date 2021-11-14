package com.mab.mmhomework.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * @author MAB
 */
@Dao
interface BaseDao<in T> {
    @Insert
    suspend fun insert(t: T)

    @Update
    suspend fun update(t: T)

    @Delete
    suspend fun delete(t: T)
}