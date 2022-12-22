package com.mrickar.hearthstonedeckhelper.presentation.card_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mrickar.hearthstonedeckhelper.R

@Composable
fun PageNavigation(
    modifier: Modifier,
    curPage:Int,
    pageCount:Int,
    isNextButtonClickable:Boolean,
    isPreviousButtonClickable:Boolean,
    onButtonOnClick:((Int)->Unit)?,
){
    Row(modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
        )
    {
        IconButton(
            onClick={
                if (onButtonOnClick != null) {
                    onButtonOnClick(curPage-1)
                }
            },
            enabled = isPreviousButtonClickable
        ){
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription=null
            )
        }
        Text(
            text = "$curPage .. $pageCount"
        )
        IconButton(
            onClick={
                if (onButtonOnClick != null) {
                    onButtonOnClick(curPage+1)
                }
            },
            enabled = isNextButtonClickable
        ){
            Icon(
                painter = painterResource(R.drawable.ic_arrow_forward),
                contentDescription=null
            )
        }
    }
}

@Preview(showBackground=true)
@Composable
fun PageNavigationPreview() {
    PageNavigation(
        modifier = Modifier.fillMaxWidth(),
        pageCount = 50,
        curPage = 1,
        isNextButtonClickable = true,
        isPreviousButtonClickable = false,
        onButtonOnClick = null
    )
}
