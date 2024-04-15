package com.example.mychatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mychatapp.databinding.ActivitySignInBinding
import com.example.mychatapp.databinding.ActivitySignUpBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        binding.btnSignUp.setOnClickListener {
            val name = binding.name.text.toString()
            val gmail = binding.gmail.text.toString()
            val password = binding.password.text.toString()
            signUp(name, gmail, password)
        }
    }
    private fun signUp(name:String, gmail:String, password:String){
        auth.createUserWithEmailAndPassword(gmail, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, gmail, auth.currentUser?.uid!!)
                    startActivity(Intent(this, MainActivity::class.java))
                        finish()
                } else {
                    Toast.makeText(this, "account create failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUserToDatabase(name:String, gmail:String, uid:String){

    }
}