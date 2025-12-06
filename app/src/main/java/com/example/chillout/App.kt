package com.example.chillout

import android.app.Application
import android.content.Context

class App: Application () {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
    companion object{
        private lateinit var instance: App

        fun appContext (): Context = instance.applicationContext
    }
}