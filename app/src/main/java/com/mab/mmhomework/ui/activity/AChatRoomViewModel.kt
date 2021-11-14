package com.mab.mmhomework.ui.activity

import androidx.lifecycle.*
import com.mab.mmhomework.db.entities.ChatMsg
import com.mab.mmhomework.repository.ChatRepository
import kotlinx.coroutines.launch

/**
 * @author MAB
 */
class AChatRoomViewModel(private val repo: ChatRepository) : ViewModel() {

    val chatMsgsLiveData: LiveData<List<ChatMsg>> = repo.allMsgsFlow.asLiveData()

    fun sendMsg(msg: String) = viewModelScope.launch {
        repo.addMsg(msg)
    }

    class ChatRoomViewModelFactory(private val repo: ChatRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AChatRoomViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AChatRoomViewModel(repo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel")
        }

    }

}