package com.mrickar.hearthstonedeckhelper.domain.use_case.card_search_use_cases.get_card_by_id_name_from_api

import com.mrickar.hearthstonedeckhelper.common.Constants
import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.data.remote.dto.toCardInfo
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.CardInfo
import com.mrickar.hearthstonedeckhelper.domain.repository.CardSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCardByIdNameFromApiUseCase@Inject constructor(
    private val repository: CardSearchRepository
){
    operator fun invoke(idName:String): Flow<Resource<CardInfo>> {
        return flow {
            try {
                emit(Resource.Loading<CardInfo>())
                val card= repository.getCardInfoByIdName(idName).toCardInfo()
                emit(Resource.Success<CardInfo>(data=card?: Constants.EMPTY_CARD))
            }catch (e: HttpException) {
                emit(Resource.Error<CardInfo>(e.message()?:"An unexpected error has occured."))
            }catch (e: IOException){
                emit(Resource.Error<CardInfo>("Couldn't reach the server."))
            }
            catch (e:Exception)
            {
                emit(Resource.Error<CardInfo>("Couldn't find the card."))
            }
        }
    }
}