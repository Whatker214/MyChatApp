package com.example.mychatapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.example.mychatapp.Model.ChatGPT_message
import com.example.mychatapp.Model.ChatGPT_message.Companion.SENT_BY_ME
import com.example.mychatapp.R

class ChatGPT_Adapter(val context: Context, val messageList: ArrayList<ChatGPT_message>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val leftChatview = itemView.findViewById<LinearLayout>(R.id.left_chat_view)
        val leftTextview = itemView.findViewById<TextView>(R.id.left_chat_text_view)
        val rightChatview = itemView.findViewById<LinearLayout>(R.id.right_chat_view)
        val rightTextview = itemView.findViewById<TextView>(R.id.right_chat_text_view)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val chatView: View = LayoutInflater.from(context).inflate(R.layout.chat_item, parent, false)
        return  MyViewHolder(chatView)
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messageList = messageList[position]
        val viewHolder = holder as MyViewHolder

        if (messageList.sentBy!! == SENT_BY_ME){
            holder.leftChatview.visibility = View.GONE
            holder.rightChatview.visibility = View.VISIBLE
            holder.rightTextview.text = messageList.message
        }else{
            holder.rightChatview.visibility = View.GONE
            holder.leftChatview.visibility = View.VISIBLE
            holder.leftTextview.text = messageList.message
        }
    }

}