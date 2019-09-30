package com.brmcerqueira.discord.codbot

data class DicePoolModel(val amount: Int, val explosion: Int, val isCanceller: Boolean, override val description: String? = null) : IDescription