package com.mrickar.hearthstonedeckhelper.domain.use_case.deck_use_cases.create_new_deck

import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.DeckList
import com.mrickar.hearthstonedeckhelper.domain.repository.DeckRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CreateNewDeckUseCase @Inject constructor(
    private val repository: DeckRepository
){
    operator fun invoke(
        name:String,
        classType: String
    ): Flow<Resource<DeckList>> {
        return flow {
            try {
                emit(Resource.Loading<DeckList>())
                val newDeck=repository.createNewDeck(name = name, classType = classType)
                val deckList=repository.addNewDeckToFirebase(newDeck)
                emit(Resource.Success<DeckList>(data=deckList))
            }catch (e: HttpException) {
                emit(Resource.Error<DeckList>("An unexpected error has occured."))
            }catch (e: IOException){
                emit(Resource.Error<DeckList>("Couldn't reach the server."))
            }
        }
    }
}



