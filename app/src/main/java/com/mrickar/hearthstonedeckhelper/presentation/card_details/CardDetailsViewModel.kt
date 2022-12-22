package com.mrickar.hearthstonedeckhelper.presentation.card_details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrickar.hearthstonedeckhelper.common.Constants
import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardInfo
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.ClassType
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.toCardInDeck
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.Deck
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.DeckList
import com.mrickar.hearthstonedeckhelper.domain.use_case.card_search_use_cases.get_acces_token.GetAccessTokenUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.card_search_use_cases.get_card_by_id_name_from_api.GetCardByIdNameFromApiUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.card_search_use_cases.get_card_by_id_name_from_search_result.GetCardByIdNameFromSearchResultUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.add_card_to_deck.AddCardToDeckUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.get_current_deck_list.GetCurrentDeckListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CardDetailsViewModel @Inject constructor(
    private val getCardByIdNameFromApiUseCase:GetCardByIdNameFromApiUseCase,
    private val getCardByIdNameFromSearchResultUseCase:GetCardByIdNameFromSearchResultUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val addCardToDeckUseCase: AddCardToDeckUseCase,
    private val getCurrentDeckListUseCase: GetCurrentDeckListUseCase,
    savedStateHandle: SavedStateHandle
):ViewModel() {
    private val _state= mutableStateOf<CardDetailsState>(CardDetailsState())
    val state: State<CardDetailsState> = _state
    private var isTokenCalled: Boolean =false

    init{
        savedStateHandle.get<String>(Constants.ID_NAME)?.let { idName->
            getCardByIdNameFromSearchResult(idName = idName)
        }
        if(_state.value.card==Constants.EMPTY_CARD) {
            savedStateHandle.get<String>(Constants.ID_NAME)?.let { idName ->
                if(Constants.ACCESS_TOKEN==null) getAccessTokenResult(idName = idName)
                else getCardByIdNameFromApi(idName = idName)
            }
        }
    }
    private fun getCardByIdNameFromSearchResult(idName:String)
    {
        getCardByIdNameFromSearchResultUseCase(idName).onEach { response ->
            when(response)
            {
                is Resource.Success->
                {
                    _state.value=CardDetailsState(card = response.data)
                }
                is Resource.Error->
                {
                    _state.value=CardDetailsState(error = response.message?:"ERROR IN CardDetailsVM")
                    println(state.value.error)
                }
                is Resource.Loading->
                {
                    _state.value=CardDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun getCardByIdNameFromApi(idName:String)
    {
        getCardByIdNameFromApiUseCase(idName).onEach { response ->
            when(response)
            {
                is Resource.Success->
                {
                    _state.value=CardDetailsState(card = response.data)
                }
                is Resource.Error->
                {
                    println("***** CardDetailsViewModel *****")
                    println(response.message)
                    println("----- CardDetailsViewModel -----")
                    if(response.message=="Unauthorized" && !isTokenCalled) {

                        getAccessTokenResult(idName = idName)
                    }
                    _state.value=CardDetailsState(error = response.message?:"ERROR IN CardDetailsVM")

                }
                is Resource.Loading->
                {
                    _state.value=CardDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun getAccessTokenResult(idName: String)
    {
        isTokenCalled=true
        getAccessTokenUseCase().onEach { response ->
            when(response)
            {
                is Resource.Success ->
                {
                    Constants.ACCESS_TOKEN=response.data
                    getCardByIdNameFromApi(idName = idName)
                }
                is Resource.Loading ->
                {
                    _state.value= CardDetailsState(isLoading = true)
                }
                is Resource.Error ->
                {
                    _state.value= CardDetailsState(error = response.message?:"ERROR WHEN GETTING ACCESS TOKEN")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun addCardToDeck(
        card:CardInfo,
        deck:Deck,
        cardCount:Int
    ): String {
        if(card.classType!=ClassType.NEUTRAL && card.classType!=deck.classType) return "Class of card and deck does not match"
        val resource = addCardToDeckUseCase(cardInDeck = card.toCardInDeck(cardNum = cardCount),deck=deck)
        when(resource)
        {
            is Resource.Success ->{
                return Constants.CARD_ADDED_SUCCESS
            }
            is Resource.Error ->{
                return resource.message!!
            }
            else ->{ return "Unexpected result"}
        }
    }

    fun getDeckList(): DeckList {
        return getCurrentDeckListUseCase()
    }
}