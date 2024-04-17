package com.example.mychatapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mychatapp.Adapter.ChatGPT_Adapter
import com.example.mychatapp.Model.ChatGPT_message
import com.example.mychatapp.databinding.ActivityChatGptactivityBinding
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class ChatGPTActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatGptactivityBinding
    private lateinit var messageList: ArrayList<ChatGPT_message>
    private lateinit var messageAdapter: ChatGPT_Adapter
    private val JSON: MediaType = "application/json".toMediaType()
    private var client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatGptactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "ChatGPT"
        supportActionBar?.setIcon(R.drawable.chatgpt)

        messageList = ArrayList()

        messageAdapter = ChatGPT_Adapter(this, messageList)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true

        binding.chatgptRecyclerView.layoutManager = layoutManager
        binding.chatgptRecyclerView.adapter = messageAdapter



        binding.sentQuestionIcon.setOnClickListener {
            val question: String = binding.questionBox.text.toString().trim()
            addToChat(question, ChatGPT_message.SENT_BY_ME)
            binding.questionBox.setText("")
            callAPI(question)
        }

    }
    private fun addToChat(message: String, sentBy: String){
        runOnUiThread {
            messageList.add(ChatGPT_message(message, sentBy))
            messageAdapter.notifyDataSetChanged()
            binding.chatgptRecyclerView.smoothScrollToPosition(messageAdapter.itemCount)
        }
    }

    private fun addResponse(response: String){
        addToChat(response, ChatGPT_message.SENT_BY_GPT)
    }
    private fun callAPI(question: String){
        val jsonBody = JSONObject()
        try {
            jsonBody.put("model", "gpt-3.5-turbo-0125")
            jsonBody.put("prompt", question)
            jsonBody.put("max_tokens", 4000)
            jsonBody.put("temperature", 0)
        }catch (e:JSONException){
            e.printStackTrace()
        }
        val bodyRequest = RequestBody.create(JSON, jsonBody.toString())
        val request = Request.Builder()
            .url("https://api.openai.com/v1/completions")
            .header("Authorization", "Bearer sk-proj-ggKAgSZ64StrPB7CShVwT3BlbkFJNafFehCr94DIZF9hxGdI")
            .post(bodyRequest)
            .build()

        client.newCall(request).enqueue(object: Callback{
            override fun onFailure(call: Call, e: IOException) {
                addResponse("Failed to load response due to ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    var jsonObject: JSONObject? = null
                    try {
                        jsonObject = JSONObject(response.body?.string())
                        val jsonArray = jsonObject.getJSONArray("choices")
                        val result: String = jsonArray.getJSONObject(0).getString("text")
                        addResponse(result.trim())
                    }catch (e: JSONException){
                        e.printStackTrace()
                    }
                }else{
                    addResponse("Failed to load response due to ${response.body}")
                }
            }
        })
    }
}