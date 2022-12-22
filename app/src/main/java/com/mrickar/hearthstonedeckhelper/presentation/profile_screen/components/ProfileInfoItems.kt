package com.mrickar.hearthstonedeckhelper.presentation.profile_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ProfileInfoItems(
    tagList:List<String>,
    infoList:List<String>,
    modifier: Modifier
){
    Column{
        for(i in tagList.indices)
        {
            val tag=tagList[i]
            val info=infoList[i]
            Row{
                Text(modifier = modifier, text = "$tag : ", style = MaterialTheme.typography.body1, color = Color.Gray, fontStyle = FontStyle.Italic)
                Text(modifier = modifier, text = info, style = MaterialTheme.typography.body1)
            }

        }
    }

}

@Preview(showBackground=true)
@Composable
fun ProfileInfoItemsPreview() {
    ProfileInfoItems(tagList=listOf("Email","Name"), infoList = listOf("meric@gmail.com","meric"), modifier = Modifier)
}
