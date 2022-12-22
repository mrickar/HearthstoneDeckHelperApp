package com.mrickar.hearthstonedeckhelper.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth:FirebaseAuth
):AuthRepository {
    override fun getUser(): FirebaseUser {
        return auth.currentUser!!
    }
    override fun isUserAuthenticatedInFirebase(): Boolean {
        return auth.currentUser!=null
    }
    override suspend fun signInFirebase(email:String,password:String): Flow<Resource<Boolean>> {
        return flow{
            try {
                emit(Resource.Loading<Boolean>())
                auth.signInWithEmailAndPassword(email, password).await()
                emit(Resource.Success<Boolean>(data = true))
            }catch (e: HttpException) {
                emit(Resource.Error<Boolean>("An unexpected error has occured."))
            }catch (e: IOException){
                emit(Resource.Error<Boolean>("Couldn't reach the server."))
            }catch (e:Exception){
                emit(Resource.Error<Boolean>(e.message?:"Error while creating account"))
            }
        }
    }

    override suspend fun signUpFirebase(email:String,password:String): Flow<Resource<Boolean>> {
        return callbackFlow{
            try {
                trySend(Resource.Loading<Boolean>())
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task->
                    if(task.isSuccessful)
                    {
                        trySend(Resource.Success<Boolean>(data = true))
                    }
                    else{
                        trySend(Resource.Error<Boolean>(task.exception?.message?:"Error while creating account"))
                    }
                }
            }catch (e: HttpException) {
                trySend(Resource.Error<Boolean>("An unexpected error has occured."))
            }catch (e: IOException){
                trySend(Resource.Error<Boolean>("Couldn't reach the server."))
            }catch (e:Exception){
                trySend(Resource.Error<Boolean>(e.message?:"Error while creating account"))
            }
            awaitClose {
//                channel.close() TODO
            }
        }
    }

    override suspend fun signOutFirebase(): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading<Boolean>())
            auth.signOut()
            emit(Resource.Success<Boolean>(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Error while signing out"))
        }
    }

}