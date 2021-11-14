package com.mab.mmhomework.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.mab.mmhomework.databinding.AChatRoomBinding
import com.mab.mmhomework.extensions.afterTextChanged
import com.mab.mmhomework.global.App

/**
 * @author MAB
 */
class AChatRoom : AppCompatActivity() {

    private lateinit var _binding: AChatRoomBinding

    private val _viewModel: AChatRoomViewModel by viewModels {
        AChatRoomViewModel.ChatRoomViewModelFactory((application as App).repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = AChatRoomBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        _binding.apply {
            etInput.afterTextChanged {
                setInputActiveState(it.trim().isNotEmpty())
            }
            btnSend.setOnClickListener { sendInputMsg() }
        }

        _viewModel.chatMsgsLiveData.observe(this, Observer { msgs ->
            println("== DATA ===============")
            msgs.forEach { msg ->
                println("== DATA $msg")
            }
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