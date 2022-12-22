package com.mrickar.hearthstonedeckhelper.data.remote.dto

data class TokenDto(
    val access_token: String,
    val expires_in: Int,
    val sub: String,
    val token_type: String
)