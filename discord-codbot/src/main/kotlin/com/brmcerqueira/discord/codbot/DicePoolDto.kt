package com.brmcerqueira.discord.codbot

data class DicePoolDto(val amount: Int, val explosion: Int, val isCanceller: Boolean, val modifier: Int? = null)