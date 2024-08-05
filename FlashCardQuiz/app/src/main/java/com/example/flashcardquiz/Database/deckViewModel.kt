package com.example.flashcardquiz.Database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class deckViewModel : ViewModel() {

    private val _getDeck = MyApplication.myDatabase.getDeck()
    val getDeck = _getDeck.getAllDeck()

    init {
        _getDeck.getAllDeck()
    }

    fun addDeck(addDeck : DecksData){
        viewModelScope.launch(Dispatchers.IO) {
            _getDeck.addDeck(addDeck)
        }
    }

    fun removeDeck(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            _getDeck.removeDeck(id)
        }
    }

    fun searchResult(query: String): LiveData<List<DecksData>> {
        return _getDeck.searchResult(query)
    }
}