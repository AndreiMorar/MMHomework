package com.mab.mmhomework.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mab.mmhomework.R
import com.mab.mmhomework.db.entities.ChatMsg
import com.mab.mmhomework.db.entities.TChatMsg
import com.mab.mmhomework.user.MockUserManager
import kotlinx.android.synthetic.main.item_chat_msg_text.view.*

/**
 * @author MAB
 */
class ChatRoomAdapter : RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {

    companion object {
        private val TYPE_TEXT = 1
        private val TYPE_PHOTO = 2
        private val TYPE_VIDEO = 3
    }

    private val items: ArrayList<ChatMsg> = arrayListOf()

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = when (items[position].type) {
        TChatMsg.TEXT.name -> TYPE_TEXT
        TChatMsg.PHOTO.name -> TYPE_PHOTO
        TChatMsg.VIDEO.name -> TYPE_VIDEO
        else -> TYPE_TEXT

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //        when(viewType) {
        //            TYPE_TEXT ->
        //            TYPE_PHOTO ->
        //            TYPE_VIDEO->
        //            else ->
        //        }
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_msg_text, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun addData(msgs: List<ChatMsg>) {
        val diffCount = items.size - msgs.size;
        items.clear()
        items.addAll(msgs)
        val curCount = items.size
        notifyItemRangeInserted(curCount - diffCount, curCount)
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bind(msg: ChatMsg) = with(itemView) {
            val isLocalMsg = msg.senderId == MockUserManager.CUR_USER_ID

            tvMsg.text = msg.message

            val gravity: Int = (if (isLocalMsg) Gravity.END else Gravity.START)
            (itemView as LinearLayout).gravity = gravity
            (vBubble.layoutParams as FrameLayout.LayoutParams).gravity = gravity

            tvMsg.setBackgroundResource(
                if (isLocalMsg) R.drawable.shape_chat_bubble_local else R.drawable.shape_chat_bubble_remote
            )

            tvMsg.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (isLocalMsg) R.color.app_white else R.color.app_black
                )
            )



        }

    }

}