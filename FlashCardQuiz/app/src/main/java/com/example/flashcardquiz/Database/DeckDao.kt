package com.example.flashcardquiz.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DeckDao {

    @Insert
    fun addDeck(addDeck : DecksData)

    @Query("SELECT * FROM decksdata ORDER BY id DESC")
    fun getAllDeck() : LiveData<List<DecksData>>

    @Query("DELETE FROM DecksData WHERE ID = :id ")
    fun removeDeck(id:Int)

    @Query("SELECT * FROM decksdata WHERE deck LIKE '%' || :query || '%'")
    fun searchResult(query: String) : LiveData<List<DecksData>>
}