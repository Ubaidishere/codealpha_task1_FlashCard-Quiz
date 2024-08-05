package com.example.flashcardquiz.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flashcardquiz.Database.CardsDatabase.CardsDao
import com.example.flashcardquiz.Database.CardsDatabase.CardsData

@Database(entities = [DecksData::class,CardsData::class],version = 1)
abstract class MyDataBase : RoomDatabase() {

    abstract fun getDeck() : DeckDao

    abstract fun getCard() : CardsDao
}