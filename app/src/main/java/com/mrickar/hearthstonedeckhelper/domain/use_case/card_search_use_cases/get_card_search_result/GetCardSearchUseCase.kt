package com.mrickar.hearthstonedeckhelper.domain.use_case.card_search_use_cases.get_card_search_result

import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.data.remote.dto.toCardSearchResult
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardSearchResult
import com.mrickar.hearthstonedeckhelper.domain.repository.CardSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCardSearchUseCase @Inject constructor(
    private val repository: CardSearchRepository,
) {
//    set:String?=null,
//    manaCost:String?=null,
//    attack:String?=null,
//    health:String?=null,
//    rarity:String?=null,
//    cardType:String?=null,
//    keyword:String?=null,
//    textFilter:String?=null,
//    classes:String?=null
    operator fun invoke(
        queries:MutableMap<String,String?>
    ): Flow<Resource<CardSearchResult>> {
        return flow {
            try {
                emit(Resource.Loading<CardSearchResult>())
                val cardSearchResult=repository.makeCardSearch(queries["set"],queries["manaCost"],queries["attack"],queries["health"],queries["rarity"],queries["cardType"],queries["keyword"],queries["textFilter"],queries["classes"],queries["page"]).toCardSearchResult()
                emit(Resource.Success<CardSearchResult>(cardSearchResult))
            }catch (e: HttpException) {
                emit(Resource.Error<CardSearchResult>(e.message()?:"An unexpected error has occured."))
            }catch (e: IOException){
                emit(Resource.Error<CardSearchResult>("Couldn't reach the server."))
            }
        }
    }
}