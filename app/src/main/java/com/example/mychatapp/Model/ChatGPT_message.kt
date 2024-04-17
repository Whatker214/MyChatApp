package com.example.mychatapp.Model

class ChatGPT_message {

    companion object {
        const val SENT_BY_ME: String = "me"
        const val SENT_BY_GPT: String = "GPT"
    }

    var message: String? = null
    var sentBy: String? = null

    constructor(){}

    constructor(message: String?, sentBy: String?) {
        this.message = message
        this.sentBy = sentBy
    }
}