package com.example.flashcardquiz.Database.CardsDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CardsDao {

    @Insert
    fun addCard(card:CardsData)

    @Query("SELECT * FROM CardsData WHERE deckId = :deckId")
    fun getAllCards(deckId: Int) : LiveData<List<CardsData>>

    @Query("DELETE FROM CardsData WHERE ID = :id")
    fun removeCard(id:Int)

    @Query("SELECT COUNT(*) FROM CardsData WHERE deckId = :deckId")
    fun getCardCountForDeck(deckId: Int): LiveData<Int>

    @Query("SELECT * FROM cardsdata WHERE deckId = :deckId and question LIKE '%' || :query || '%'")
    fun searchResult(query: String,deckId: Int) : LiveData<List<CardsData>>
}