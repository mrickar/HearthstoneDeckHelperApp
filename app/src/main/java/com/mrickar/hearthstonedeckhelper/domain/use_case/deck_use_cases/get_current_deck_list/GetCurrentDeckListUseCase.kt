package com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.get_current_deck_list

import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.DeckList
import com.mrickar.hearthstonedeckhelper.domain.repository.DeckRepository
import javax.inject.Inject

class GetCurrentDeckListUseCase @Inject constructor(
    private val repository: DeckRepository
){
    operator fun invoke(
    ): DeckList {
        return repository.getCurrentDeckList()
    }
}