package com.brmcerqueira.discord.codbot.wod

import com.brmcerqueira.discord.codbot.IDescription

data class WodDicePoolModel(val amount: Int, val difficulty: Int, val isCanceller: Boolean, val isSpecialization: Boolean, override val description: String? = null) : IDescription