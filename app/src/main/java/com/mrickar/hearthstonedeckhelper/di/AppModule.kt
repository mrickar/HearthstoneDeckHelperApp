package com.mrickar.hearthstonedeckhelper.di

import com.mrickar.hearthstonedeckhelper.common.Constants
import com.mrickar.hearthstonedeckhelper.common.IsMock
import com.mrickar.hearthstonedeckhelper.data.remote.HsApi
import com.mrickar.hearthstonedeckhelper.data.remote.OAuthApi
import com.mrickar.hearthstonedeckhelper.data.remote.interceptor.AuthInterceptor
import com.mrickar.hearthstonedeckhelper.data.remote.interceptor.MockInterceptor
import com.mrickar.hearthstonedeckhelper.data.remote.interceptor.QueryInterceptor
import com.mrickar.hearthstonedeckhelper.data.repository.AuthRepositoryImpl
import com.mrickar.hearthstonedeckhelper.data.repository.CardSearchRepositoryImpl
import com.mrickar.hearthstonedeckhelper.data.repository.DeckRepositoryImpl
import com.mrickar.hearthstonedeckhelper.domain.repository.AuthRepository
import com.mrickar.hearthstonedeckhelper.domain.repository.CardSearchRepository
import com.mrickar.hearthstonedeckhelper.domain.repository.DeckRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHsApiForCards(): HsApi
    {
        val client=OkHttpClient.Builder()

        val queryInterceptor= QueryInterceptor()
        client.addInterceptor(queryInterceptor)

        val logging=HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        client.addInterceptor(logging)

        if(IsMock.isMock)
        {
            val mockInterceptor=MockInterceptor()
            client.addInterceptor(mockInterceptor)
        }

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOAuthApi(): OAuthApi
    {

        val client=OkHttpClient.Builder()

        val authInterceptor= AuthInterceptor()
        client.addInterceptor(authInterceptor)
        val logging=HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        client.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_AUTH)
            .client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OAuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCardSearchRepository(api: HsApi): CardSearchRepository
    {
        return CardSearchRepositoryImpl(api)
    }

    @Singleton
    @Provides
    fun provideDeckRepository(firebaseDb: DatabaseReference,auth: FirebaseAuth): DeckRepository
    {
        return DeckRepositoryImpl(firebaseDb,auth)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth
    {
        return Firebase.auth
    }
    @Singleton
    @Provides
    fun provideAuthRepository(auth:FirebaseAuth): AuthRepository
    {
        return AuthRepositoryImpl(auth)
    }
    @Provides
    fun provideFirebaseDatabase(
    ): DatabaseReference
    {
        return Firebase.database.reference
    }
}