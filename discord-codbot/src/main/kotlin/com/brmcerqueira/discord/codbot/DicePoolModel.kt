package com.brmcerqueira.discord.codbot

data class DicePoolModel(val amount: Int, val explosion: Int, val isCanceller: Boolean, val description: String? = null)