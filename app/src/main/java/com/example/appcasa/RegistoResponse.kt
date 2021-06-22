package com.example.appcasa

class RegistoResponse {

    private var status:Boolean = false
    private lateinit var message:String

    fun getMessage():String {
        return message
    }
    fun getStatus():Boolean {
        return status
    }

}