package com.mrickar.hearthstonedeckhelper.domain.model.card_model


data class CardSearchResult(
    val cardCount: Int=0,
    val pageCount:Int=0,
    val curPage:Int=1,
    val cards: List<CardInfo> = listOf(),
)
{
    fun findByName(cardName:String): CardInfo? {
        var first=0
        var last=cards.size-1
        var mid: Int

        while(first<last)
        {
            mid=(first+last)/2
            if(cards[mid].name == cardName)
            {
                return cards[mid]
            }
            else if(cards[mid].name < cardName)
            {
                first=mid+1
            }
            else
            {
                last=mid-1
            }
        }
        return null
    }
    fun findByIdName(idName:String): CardInfo? {
        for (card in cards) {
            if (card.idName == idName) {
                return card
            }
        }
        return null
    }
}
