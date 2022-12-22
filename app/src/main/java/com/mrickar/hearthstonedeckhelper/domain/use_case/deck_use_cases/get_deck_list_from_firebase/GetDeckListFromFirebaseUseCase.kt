package com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.get_deck_list_from_firebase

import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.DeckList
import com.mrickar.hearthstonedeckhelper.domain.repository.DeckRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDeckListFromFirebaseUseCase @Inject constructor(
    private val repository: DeckRepository
){
    suspend operator fun invoke(
    ): Flow<Resource<DeckList>> {
        return flow {
            try {
                emit(Resource.Loading<DeckList>())
                val deckListFromFirebase = repository.getDeckListFromFirebase()
                emit(Resource.Success<DeckList>(data = deckListFromFirebase))
            }catch (e: HttpException) {
                emit(Resource.Error<DeckList>(e.message()?:"An unexpected error has occured."))
            }catch (e: IOException){
                emit(Resource.Error<DeckList>("Couldn't reach the server."))
            } catch (e:Exception){
                emit(Resource.Error<DeckList>(e.message?:"Some Error"))
            }
        }
    }
}
