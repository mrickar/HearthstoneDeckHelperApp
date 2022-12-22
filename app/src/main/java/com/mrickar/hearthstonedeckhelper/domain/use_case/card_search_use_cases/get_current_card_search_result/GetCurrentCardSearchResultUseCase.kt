package com.mrickar.hearthstonedeckhelper.domain.use_case.card_search_use_cases.get_current_card_search_result

import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.data.remote.dto.toCardSearchResult
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardSearchResult
import com.mrickar.hearthstonedeckhelper.domain.repository.CardSearchRepository
import javax.inject.Inject

class GetCurrentCardSearchResultUseCase @Inject constructor(
    private val repository: CardSearchRepository
){
    operator fun invoke(
    ): Resource<CardSearchResult> {
        val resultDto= repository.getCardSearchResultDto()
            ?: return Resource.Error<CardSearchResult>("No previous search!")
        return Resource.Success<CardSearchResult>(resultDto.toCardSearchResult())
    }
}