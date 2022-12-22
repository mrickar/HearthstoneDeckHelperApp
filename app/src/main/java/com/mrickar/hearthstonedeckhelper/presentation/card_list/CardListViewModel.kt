package com.mrickar.hearthstonedeckhelper.presentation.card_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrickar.hearthstonedeckhelper.common.Constants
import com.mrickar.hearthstonedeckhelper.common.QueryParameters
import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardInfo
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.ClassType
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.toCardInDeck
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.Deck
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.DeckList
import com.mrickar.hearthstonedeckhelper.domain.use_case.card_search_use_cases.get_acces_token.GetAccessTokenUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.card_search_use_cases.get_card_search_result.GetCardSearchUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.card_search_use_cases.get_current_card_search_result.GetCurrentCardSearchResultUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.add_card_to_deck.AddCardToDeckUseCase
import com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.get_current_deck_list.GetCurrentDeckListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(
    val getCardSearchUseCase: GetCardSearchUseCase,
    val getAccessTokenUseCase: GetAccessTokenUseCase,
    val getCurrentCardSearchResultUseCase: GetCurrentCardSearchResultUseCase,
    private val addCardToDeckUseCase: AddCardToDeckUseCase,
    private val getCurrentDeckListUseCase: GetCurrentDeckListUseCase,
    savedStateHandle: SavedStateHandle
):ViewModel(){

    private val _state = mutableStateOf<CardListState>(CardListState())
    val state: State<CardListState> = _state

    private var isTokenCalled: Boolean =false

    var cardText:String?
    var manaCost:String?
    var rarities:String?
    var classes:String?
    var cardType:String


    init {
        cardText = savedStateHandle.get<String?>(QueryParameters.TEXT_FILTER)
        manaCost = savedStateHandle.get<String?>(QueryParameters.MANA_COST)
        rarities = savedStateHandle.get<String?>(QueryParameters.RARITY)
        classes = savedStateHandle.get<String?>(QueryParameters.CLASS)
        cardType = savedStateHandle.get<String>(QueryParameters.CARD_TYPE)!!

        if(Constants.ACCESS_TOKEN==null) getAccessTokenResult()
        else getCardSearchResult()
    }

    fun loadNewPage(
        newPageNum:Int
    )
    {
        getCardSearchResult(newPageNum)
    }
    private fun getCardSearchResult(
        page:Int=1
    )
    {
        getCardSearchUseCase(
            mutableMapOf(
                "textFilter" to cardText,
                "manaCost" to manaCost,
                "rarity" to rarities,
                "classes" to classes,
                "cardType" to cardType,
                "page" to page.toString()
            )

        ).onEach { response ->
            when(response)
            {
                is Resource.Success ->
                {
                    var isNextPageAvailable=true
                    var isPrevPageAvailable=true
                    if(response.data!!.curPage >= response.data.pageCount)
                    {
                        isNextPageAvailable=false
                    }
                    if (response.data.curPage==1)
                    {
                        isPrevPageAvailable=false
                    }
                    _state.value=CardListState(cardSearchResult = response.data,isNextPageAvailable=isNextPageAvailable,isPreviousPageAvailable=isPrevPageAvailable)
                }
                is Resource.Loading ->
                {
                    _state.value=CardListState(isLoading = true)
                }
                is Resource.Error ->
                {
                    if(response.message=="Unauthorized" && !isTokenCalled) {
                        getAccessTokenResult()
                    }
                    else _state.value=CardListState(error = response.message?:"ERROR WHEN GETTING CARDS IN CardListVM")
                }
            }
        }.launchIn(viewModelScope)
    }
    private fun getAccessTokenResult()
    {
        isTokenCalled=true
        getAccessTokenUseCase().onEach { response ->
            when(response)
            {
                is Resource.Success ->
                {
                    Constants.ACCESS_TOKEN=response.data
                    getCardSearchResult()
                }
                is Resource.Loading ->
                {
                    _state.value=CardListState(isLoading = true)
                }
                is Resource.Error ->
                {
                    _state.value=CardListState(error = response.message?:"ERROR WHEN GETTING ACCESS TOKEN")
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