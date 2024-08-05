package com.example.flashcardquiz.Database.CardsDatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcardquiz.Database.MyApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardsViewModel : ViewModel() {

    private val _getCard = MyApplication.myDatabase.getCard()

    fun getCardsByDeck(deckId: Int): LiveData<List<CardsData>> {
        return _getCard.getAllCards(deckId)
    }

    fun getCardCountForDeck(deckId: Int): LiveData<Int>{
        return _getCard.getCardCountForDeck(deckId)
    }

    fun addCard(addCard : CardsData){
        viewModelScope.launch(Dispatchers.IO) {
            _getCard.addCard(addCard)
        }
    }

    fun removeCard(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            _getCard.removeCard(id)
        }
    }

    fun searchResult(query: String,deckId: Int) : LiveData<List<CardsData>>{
        return _getCard.searchResult(query,deckId)
    }

}




