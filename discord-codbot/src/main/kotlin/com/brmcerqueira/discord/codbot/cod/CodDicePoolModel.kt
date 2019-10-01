package com.brmcerqueira.discord.codbot.cod

import com.brmcerqueira.discord.codbot.IDescription

data class CodDicePoolModel(val amount: Int, val explosion: Int, val isCanceller: Boolean, override val description: String? = null) : IDescription

