package com.example.mychatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.mychatapp.Adapter.MessageAdapter
import com.example.mychatapp.Model.Message
import com.example.mychatapp.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import org.json.JSONException
import org.json.JSONObject

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var dbref:DatabaseReference
    private lateinit var jsonBody:JSONObject

    var receiverRoom: String? = null
    var senderRoom: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        dbref = FirebaseDatabase.getInstance().getReference()

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title = name

        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true

        binding.chatRecyclerView.layoutManager = layoutManager
        binding.chatRecyclerView.adapter = messageAdapter

        dbref.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        binding.chatRecyclerView.scrollToPosition(messageAdapter.getItemCount()-1)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })


        //將訊息加入database
        binding.sentMessageIcon.setOnClickListener {
            val message = binding.messagebox.text.toString()
            val messageObject = Message(message, senderUid)

            dbref.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnCompleteListener {
                    dbref.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            binding.messagebox.setText("")
        }

    }

    private fun callAPI(question: String){
        try {
            jsonBody.put("model", "gpt-3.5-turbo-instruct")
            jsonBody.put("prompt", "Say this is a test")
            jsonBody.put("max_tokens", 7)
            jsonBody.put("temperature", 0)
        }catch (e:Exception){
           e.printStackTrace()
        }

    }
}