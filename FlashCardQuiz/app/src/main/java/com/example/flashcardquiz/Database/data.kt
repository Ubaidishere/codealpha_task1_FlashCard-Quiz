package com.example.flashcardquiz.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DecksData(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val deck : String,
)