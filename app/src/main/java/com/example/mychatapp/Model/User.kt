package com.example.mychatapp.Model

class User {
    var name:String? = null
    var gmail:String? =null
    var uid:String? = null

    constructor(){}
    constructor(name: String?, gmail: String?, uid: String?) {
        this.name = name
        this.gmail = gmail
        this.uid = uid
    }
}