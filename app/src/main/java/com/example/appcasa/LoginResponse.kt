package com.example.appcasa

class LoginResponse {

    private lateinit var token:String
    private var status:Boolean = false
    private lateinit var message:String

    fun getToken():String {
        return token
    }
    fun getMessage():String {
        return message
    }
    fun getStatus():Boolean {
        return status
    }

}