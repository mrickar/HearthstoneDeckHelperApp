package com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.get_deck_details_from_firebase

import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.Deck
import com.mrickar.hearthstonedeckhelper.domain.repository.DeckRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDeckDetailsByIdUseCase @Inject constructor(
    private val repository: DeckRepository
){
    operator fun invoke(
        id:String
    ): Flow<Resource<Deck>> {
        return flow {
            try {
                emit(Resource.Loading<Deck>())
                val deck = repository.getDeckByID(id = id) ?: throw Exception()
                emit(Resource.Success<Deck>(data = deck))
            } catch (e: HttpException) {
                emit(Resource.Error<Deck>("An unexpected error has occured."))
            } catch (e: IOException) {
                emit(Resource.Error<Deck>("Couldn't reach the server."))
            } catch (e: Exception) {
                emit(Resource.Error<Deck>(message=e.message?:"ErrorWhileGettingDeck"))
            }
        }
    }
}