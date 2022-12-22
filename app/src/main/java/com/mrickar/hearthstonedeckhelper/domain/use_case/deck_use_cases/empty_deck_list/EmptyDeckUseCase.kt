package com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.empty_deck_list

import com.mrickar.hearthstonedeckhelper.domain.repository.DeckRepository
import javax.inject.Inject

class EmptyDeckUseCase @Inject constructor(
    private val repository: DeckRepository
){
    operator fun invoke(
    ): Boolean {
        return repository.emptyDeckList()
    }
}