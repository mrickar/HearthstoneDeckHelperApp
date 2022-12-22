package com.mrickar.hearthstonedeckhelper.presentation.deck_list_screen


import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.Deck
import com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.create_new_deck.CreateNewDeckUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.delete_deck.DeleteDeckUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.edit_deck_name.EditDeckUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.get_current_deck_list.GetCurrentDeckListUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.get_deck_list_from_firebase.GetDeckListFromFirebaseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckListViewModel @Inject constructor(
    private val getDeckListFromFirebaseUseCase: GetDeckListFromFirebaseUseCase,
    private val createNewDeckUseCase: CreateNewDeckUseCase,
    private val editDeckUseCase: EditDeckUseCase,
    private val deleteDeckUseCase: DeleteDeckUseCase,
    private val getCurrentDeckListUseCase: GetCurrentDeckListUseCase,
): ViewModel(){
    private val _state = mutableStateOf<DeckListState>(DeckListState())
    val state : State<DeckListState> = _state


    init{
        getDeckList()
    }
    private fun getDeckList()
    {
        val deckList=getCurrentDeckListUseCase()
        println("***** DeckListViewModel *****")
        if(deckList.decks.isEmpty())
        {
            println("FROM FIREBASE")
            getDeckListFromFirebase()
        }
        else{
            println("FROM CURRENT")
            _state.value=DeckListState(deckList = deckList, isEmpty = false)
        }
        println(_state.value.deckList)
        println("----- DeckListViewModel -----")
    }
    private fun getDeckListFromFirebase()
    {
        viewModelScope.launch{
            getDeckListFromFirebaseUseCase().onEach { resource ->
                when(resource)
                {
                    is Resource.Success -> {
                        _state.value=DeckListState(deckList = resource.data, isEmpty = resource.data?.decks?.size!=0)
                    }
                    is Resource.Error ->{
                        Log.d(TAG, "getDeckListFromFirebase: ${resource.message}")
                        _state.value=DeckListState(error = resource.message?:"Error while getting decks from database")
                    }
                    is Resource.Loading -> {
                        _state.value=DeckListState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun createNewDeck(name:String,classType: String) {
        viewModelScope.launch {
            createNewDeckUseCase(name = name,classType=classType).onEach { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _state.value = DeckListState(
                            deckList = resource.data,
                            isEmpty = resource.data?.decks?.size != 0
                        )
                    }
                    is Resource.Error -> {
                        Log.d(TAG, "createNewDeck: ${resource.message}")
                        _state.value = DeckListState(
                            error = resource.message ?: "Error while getting decks from database"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = DeckListState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun editDeckName(deck: Deck, newName:String)
    {
        deck.name=newName
        editDeckUseCase(deck)
    }
    fun deleteDeck(deletedDeck:Deck)
    {
        viewModelScope.launch {
            deleteDeckUseCase(deletedDeck = deletedDeck).onEach {
                when(it)
                {
                    is Resource.Success->
                    {
                        _state.value = DeckListState(deckList = it.data)
                    }
                    is Resource.Error->
                    {
                        _state.value = DeckListState(error = it.message?:"VM Error")
                        Log.d(TAG, "DeckListViewModel: ${it.message}")
                    }
                    is Resource.Loading->
                    {
                        _state.value = DeckListState(isLoading = true)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}


