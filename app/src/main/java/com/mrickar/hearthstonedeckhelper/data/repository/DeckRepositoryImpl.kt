package com.mrickar.hearthstonedeckhelper.data.repository

import androidx.compose.runtime.toMutableStateList
import com.mrickar.hearthstonedeckhelper.common.Constants
import com.mrickar.hearthstonedeckhelper.common.MetaData
import com.mrickar.hearthstonedeckhelper.common.Resource
import com.mrickar.hearthstonedeckhelper.domain.model.card_model.Rarity
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.CardInDeck
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.Deck
import com.mrickar.hearthstonedeckhelper.domain.model.deck_model.DeckList
import com.mrickar.hearthstonedeckhelper.domain.repository.DeckRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class DeckRepositoryImpl @Inject constructor(
    private val firebaseDb: DatabaseReference,
    private val auth: FirebaseAuth
) :DeckRepository {
    override var deckList:DeckList= DeckList()
    override suspend fun getDeckListFromFirebase(): DeckList {
        val result=firebaseDb.child(auth.currentUser!!.uid).child(Constants.DECK_LIST).get().await()
        val tmpList= mutableListOf<Deck>()
        if(result.value==null)
        {
            deckList=DeckList()
            return deckList
        }
        (result.value as HashMap<String, *>).values.toList().forEach {it as HashMap<String,*>
            tmpList.add((Deck(it)))
        }
        deckList=DeckList(decks = tmpList.toMutableStateList())
        return deckList
    }

    override fun getDeckByID(id:String): Deck? {
        if (deckList.decks.isEmpty()) return null

        for( deck in deckList.decks)
        {
            if(deck.id==id) return deck
        }
        return null
    }

    override fun addNewDeckToFirebase(newDeck:Deck): DeckList {
        firebaseDb.child(auth.currentUser!!.uid).child(Constants.DECK_LIST).child(newDeck.id).setValue(newDeck)
        deckList.decks.add(newDeck)
        return deckList
    }

    override fun deleteDeckFromFirebase(deletedDeck: Deck): DeckList {
        firebaseDb.child(auth.currentUser!!.uid).child(Constants.DECK_LIST).child(deletedDeck.id).removeValue()
        deckList.decks.remove(deletedDeck)
        return deckList
    }

    override fun createNewDeck(name:String,classType: String): Deck {
        return Deck(
            id=firebaseDb.child(auth.currentUser!!.uid).push().key!!,
            name = name,
            classType = MetaData.ClassByName(classType)
        )
    }
    override fun editDeckFirebase(deck: Deck) {
        firebaseDb.child(auth.currentUser!!.uid).child(Constants.DECK_LIST).child(deck.id).setValue(deck)
    }

    override fun addCardToDeck(newCard: CardInDeck, deck: Deck) :Resource<Boolean>{
        if(deck.cardCount + newCard.cardNum > 30) return Resource.Error<Boolean>(message = "There is not enough space in deck.")
        val cards=deck.cards
        var isCardAlreadyInDeck=false
        val newAddedCardNum=newCard.cardNum
        for(cardInDeck in cards)
        {
            if(cardInDeck.idName==newCard.idName)
            {
                if(newCard.rarity==Rarity.LEGENDARY) {
                    return Resource.Error<Boolean>(message = "Card is already added.")
                }
                if((cardInDeck.cardNum + newCard.cardNum) > 2) return Resource.Error<Boolean>(message = "Card is already added.")
                newCard.cardNum +=cardInDeck.cardNum
                cardInDeck.cardNum=newCard.cardNum
                isCardAlreadyInDeck=true
                break
            }
        }
        if(!isCardAlreadyInDeck)
        {
            deck.cards.add(newCard)
        }
        deck.cardCount += newAddedCardNum
        deck.checkIsDeckCompleted()
        val databaseReference = firebaseDb.child(auth.currentUser!!.uid).child("${Constants.DECK_LIST}/${deck.id}")
        databaseReference.child("cards/${ newCard.idName }").setValue(newCard)
        databaseReference.child("cardCount").setValue(deck.cardCount)
        databaseReference.child("completed").setValue(deck.isCompleted)
        return Resource.Success<Boolean>(data = true)
    }
    
    override fun getCurrentDeckList(): DeckList {
        return deckList
    }

    override fun deleteCardFromDeck(deletedNum:Int, cardIdName: String, deck: Deck) {

        var deletedNumTmp=deletedNum
        var isCardStillExist=true
        val databaseReference = firebaseDb.child(auth.currentUser!!.uid).child("${Constants.DECK_LIST}/${deck.id}")
        for (cardIndex in deck.cards.indices)
        {
            if(deck.cards[cardIndex].idName == cardIdName)
            {
                if(deletedNum >= deck.cards[cardIndex].cardNum)
                {
                    deletedNumTmp= deck.cards[cardIndex].cardNum
                    isCardStillExist=false
                }
                if(!isCardStillExist)
                {
                    deck.cards.removeAt(cardIndex)
                    databaseReference.child("cards/${cardIdName}").removeValue()
                }
                else{
                    deck.cards[cardIndex].cardNum -= deletedNumTmp
                    databaseReference.child("cards/${cardIdName}/cardNum").setValue(deck.cards[cardIndex].cardNum)
                }
                deck.cardCount -= deletedNumTmp
                deck.checkIsDeckCompleted()
                break
            }
        }
        databaseReference.child("cardCount").setValue(deck.cardCount)
        databaseReference.child("completed").setValue(deck.isCompleted)
    }

    override fun emptyDeckList(): Boolean {
        deckList=DeckList()
        return deckList.decks.isEmpty()
    }
}


//    override suspend fun getDeckListFromFirebase(): Flow<Resource<DeckList>> {
//        println("***** DeckRepositoryImpl *****")
//        println("getDeckListFromFirebase is called")
//        return callbackFlow {
//            val deckListListener = object : ChildEventListener {
//                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                    println("onChildAdded is called")
//                    println("deckList size : ${deckList.decks.size}")
//                    deckList.decks.add(Deck(snapshot.value as HashMap<String,*>))
//                    if(!trySend(Resource.Success<DeckList>(data = deckList)).isSuccess){
//                        throw Exception()
//                    }
//                }
//                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onChildRemoved(snapshot: DataSnapshot) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    TODO("Not yet implemented")
//                }
//            }
//            try {
//                trySend(Resource.Loading<DeckList>())
//                firebaseDb.child(auth.currentUser!!.uid).child(Constants.DECK_LIST).addChildEventListener(deckListListener)
//                println("----- DeckRepositoryImpl -----")
//
//            }catch (e: HttpException) {
//                trySend(Resource.Error<DeckList>(e.message()?:"An unexpected error has occured."))
//            }catch (e: IOException){
//                trySend(Resource.Error<DeckList>("Couldn't reach the server."))
//            } catch (e:Exception){
//                trySend(Resource.Error<DeckList>(e.message?:"Some Error"))
//            }
//            awaitClose {
//                firebaseDb.child(auth.currentUser!!.uid).child(Constants.DECK_LIST).removeEventListener(deckListListener)
//            }
//        }
//    }