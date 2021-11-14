package com.mab.mmhomework.ui.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mab.mmhomework.adapters.ChatRoomAdapter
import com.mab.mmhomework.databinding.AChatRoomBinding
import com.mab.mmhomework.extensions.afterTextChanged
import com.mab.mmhomework.global.App
import com.mab.mmhomework.user.MockUserManager
import com.mab.mmhomework.websocket.events.WSRespChatMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author MAB
 */
class AChatRoom : AppCompatActivity() {

    private lateinit var _binding: AChatRoomBinding

    private val adapter = ChatRoomAdapter()

    private val _viewModel: AChatRoomViewModel by viewModels {
        AChatRoomViewModel.ChatRoomViewModelFactory((application as App).repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = AChatRoomBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            etInput.afterTextChanged {
                setInputActiveState(it.trim().isNotEmpty())
            }
            ivAvatar.setOnClickListener {
                lifecycleScope.launch() {
                    (application as App).webSocket.onMockEventListener(
                        WSRespChatMsg.mockNewInstance(
                            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. ",
                            MockUserManager.REMOTE_USER_ID
                        )
                    )
                }
            }
            btnSend.setOnClickListener { sendInputMsg() }

            rvList.apply {
                adapter = this@AChatRoom.adapter
                layoutManager = LinearLayoutManager(this@AChatRoom).also {
                    it.stackFromEnd = true
                }
                itemAnimator = null
            }

        }

        _viewModel.chatMsgsLiveData.observe(this, Observer { msgs ->
            adapter.addData(msgs)
            _binding.rvList.scrollToPosition(adapter.itemCount - 1)
        })
    }

    private fun setInputActiveState(active: Boolean) {
        _binding.apply {
            btnSend.apply {
                isActivated = active
                isClickable = active
            }
            etInput.isActivated = active
        }
    }

    private fun sendInputMsg() {
        _binding.etInput.apply {
            _viewModel.sendMsg(text.toString())
            text.clear()
        }

    }

}