package com.mrickar.hearthstonedeckhelper.presentation.deck_details_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrickar.hearthstonedeckhelper.common.Constants
import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.CardInDeck
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.Deck
import com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.add_card_to_deck.AddCardToDeckUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.delete_card_from_deck.DeleteCardFromDeckUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.get_deck_details_from_firebase.GetDeckDetailsByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DeckDetailsViewModel @Inject constructor(
    private val getDeckDeckDetailsByIdUseCase: GetDeckDetailsByIdUseCase,
    private val deleteCardFromDeckUseCase: DeleteCardFromDeckUseCase,
    private val addCardToDeckUseCase: AddCardToDeckUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _state = mutableStateOf<DeckDetailsState>(DeckDetailsState())
    val state : State<DeckDetailsState> = _state

    init {
        savedStateHandle.get<String>(Constants.DECK_ID)?.let { id->
            getDeckDetailsById(id = id)
        }
    }
    private fun getDeckDetailsById(
        id:String
    )
    {
        getDeckDeckDetailsByIdUseCase(id).onEach {
            when(it)
            {
                is Resource.Success->
                {
                    _state.value = DeckDetailsState(deck = it.data)
                }
                is Resource.Error->
                {
                    _state.value = DeckDetailsState(error = it.message?:"getDeckDeckDetailsByIdFromFirebaseUseCaseVM Error")
                }
                is Resource.Loading->
                {
                    _state.value = DeckDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    fun deleteCardFromDeck(deletedNum:Int,cardIdName:String,deck: Deck){
        deleteCardFromDeckUseCase(deletedNum = deletedNum, cardIdName = cardIdName, deck = deck)
    }
    fun addCardToDeck(cardInDeck: CardInDeck,deck: Deck)
    {
        addCardToDeckUseCase(cardInDeck = cardInDeck, deck = deck)
    }
}