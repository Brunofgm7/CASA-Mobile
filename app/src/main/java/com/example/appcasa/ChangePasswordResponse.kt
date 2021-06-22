package com.example.appcasa

class ChangePasswordResponse {

    private lateinit var message:String
    private var status:Boolean = false

    fun getMessage():String {
        return message
    }

    fun getStatus():Boolean {
        return status
    }

}