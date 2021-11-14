package com.mab.mmhomework.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mab.mmhomework.R
import com.mab.mmhomework.db.entities.ChatMsg
import com.mab.mmhomework.db.entities.TChatMsg
import kotlinx.android.synthetic.main.item_chat_text.view.*

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
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_text, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun addData(msgs : List<ChatMsg>){
        items.clear()
        items.addAll(msgs)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        fun bind(msg: ChatMsg) = with(itemView) {
            tvMsg.text = msg.message
        }
    }

}