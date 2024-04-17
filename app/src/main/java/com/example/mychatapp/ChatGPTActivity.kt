package com.example.mychatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mychatapp.databinding.ActivityChatGptactivityBinding

class ChatGPTActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatGptactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatGptactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.sentQuestionIcon.setOnClickListener {
            val question: String = binding.questionBox.text.toString()
            Toast.makeText(this, question, Toast.LENGTH_SHORT).show()
        }
    }
}