package com.example.flashcardquiz.Database

import android.app.Application
import androidx.room.Room

class MyApplication : Application() {

    companion object {
        lateinit var myDatabase : MyDataBase
    }


    override fun onCreate() {
        super.onCreate()

        myDatabase = Room.databaseBuilder(
            applicationContext,
            MyDataBase::class.java,
            "myDatabase"
        ).build()

    }

}