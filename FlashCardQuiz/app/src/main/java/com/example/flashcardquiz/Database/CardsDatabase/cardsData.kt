package com.example.flashcardquiz.Database.CardsDatabase

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.flashcardquiz.Database.DecksData

@Entity(
    foreignKeys = [ForeignKey(
        entity = DecksData::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("deckId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class CardsData(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val question :String,
    val answer :String,
    val deckId: Int
)
