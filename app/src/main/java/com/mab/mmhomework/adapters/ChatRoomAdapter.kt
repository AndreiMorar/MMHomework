package com.mab.mmhomework.adapters

import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mab.mmhomework.R
import com.mab.mmhomework.db.entities.ChatMsg
import com.mab.mmhomework.db.entities.TChatMsg
import com.mab.mmhomework.user.MockUserManager
import kotlinx.android.synthetic.main.item_chat_msg_text.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author MAB
 */
class ChatRoomAdapter : RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {

    companion object {
        private const val TYPE_TEXT = 1
        private const val TYPE_PHOTO = 2
        private const val TYPE_VIDEO = 3

        private const val TAIL_DIFF_MESSAGE_MS = 20000 //20sec
        private const val SECTION_DIFF_MESSAGE_MS = 2 * 60 * 1000 //2 min
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
        //Normally we would need to check for viewType but we only have TEXT currently
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_msg_text, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            items.getOrNull(position - 1),
            items[position],
            items.getOrNull(position + 1)
        )
    }

    fun addData(msgs: List<ChatMsg>) {
        val listIds = items.map { it.id }.toSet()
        val newItems = msgs.filter { it.id !in listIds }

        items.addAll(newItems)

        val curCount = items.size
        val newCount = newItems.size
        val diff = curCount - newCount
        notifyItemRangeInserted(diff, curCount)
        notifyItemChanged(diff - 1)
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bind(prevMsg: ChatMsg?, msg: ChatMsg, nextMsg: ChatMsg?) =
            with(itemView) {
                val isLocalMsg = msg.senderId == MockUserManager.CUR_USER_ID

                //Set left or right alignment
                val gravity: Int = (if (isLocalMsg) Gravity.END else Gravity.START)
                (vMessageContainer as LinearLayout).gravity = gravity
                (vInnerContainer as LinearLayout).gravity = gravity

                handleBubble(isLocalMsg, msg, nextMsg)
                handleText(isLocalMsg, msg)
                handleSeen(isLocalMsg)
                handleSection(prevMsg, msg)
            }

        private fun handleBubble(
            isLocalMsg: Boolean,
            msg: ChatMsg,
            nextMsg: ChatMsg?
        ) = with(itemView) {
            vBubble.setBackgroundResource(
                if (isLocalMsg) R.drawable.shape_chat_bubble_local
                else R.drawable.shape_chat_bubble_remote
            )
            handleTail(isLocalMsg, msg, nextMsg)
        }

        private fun handleTail(isLocalMsg: Boolean, msg: ChatMsg, nextMsg: ChatMsg?) =
            with(itemView) {
                ivTailLocal.visibility = View.INVISIBLE
                ivTailRemote.visibility = View.INVISIBLE
                if (haveTail(msg, nextMsg)) {
                    if (isLocalMsg) {
                        ivTailLocal.visibility = View.VISIBLE
                    } else {
                        ivTailRemote.visibility = View.VISIBLE
                    }
                }
            }

        private fun haveTail(
            msg: ChatMsg,
            nextMsg: ChatMsg?
        ): Boolean {
            //If we're the most recent message
            if (nextMsg == null) {
                return true
            }
            //If below message is sent more than X seconds ago
            if ((nextMsg.timestamp) - msg.timestamp >= TAIL_DIFF_MESSAGE_MS) {
                return true
            }
            //If we're the last message in a thread
            //(i.e. message below us is the other users')
            if (nextMsg.senderId != msg.senderId) {
                return true
            }
            return false
        }

        private fun handleText(isLocalMsg: Boolean, msg: ChatMsg) = with(itemView) {
            tvMsg.text = msg.message
            tvMsg.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    if (isLocalMsg) R.color.app_white else R.color.app_black
                )
            )
        }

        private fun handleSeen(isLocalMsg: Boolean) = with(itemView) {
            ivSeen.visibility = if (isLocalMsg) View.VISIBLE else View.INVISIBLE
        }

        private fun handleSection(prevMsg: ChatMsg?, msg: ChatMsg) = with(itemView) {
            if (haveSection(prevMsg, msg)) {
                setSectionValue(msg)
                tvSection.visibility = View.VISIBLE
            } else {
                tvSection.visibility = View.GONE
            }
        }

        private fun haveSection(prevMsg: ChatMsg?, msg: ChatMsg): Boolean {
            //If no previous messages
            if (prevMsg == null) return true
            //If previous message was sent more than X millis ago
            return msg.timestamp - prevMsg.timestamp >= SECTION_DIFF_MESSAGE_MS
        }

        private fun setSectionValue(msg: ChatMsg) = with(itemView) {
            var day = ""
            var time = ""
            var sdf = SimpleDateFormat("EEEE", Locale.getDefault())
            try {
                day = sdf.format(msg.timestamp)
            } catch (e: ParseException) {
            }
            sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
            try {
                time = sdf.format(msg.timestamp)
            } catch (e: ParseException) {
            }
            @SuppressWarnings("deprecation")
            tvSection.text = Html.fromHtml(context.getString(R.string.chat_room_section, day, time))
        }

    }

}