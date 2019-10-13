package com.brmcerqueira.discord.wodbot.dicepool

import com.brmcerqueira.discord.wodbot.IDescription

data class DicePoolModel(val amount: Int, val difficulty: Int, val isCanceller: Boolean, val isSpecialization: Boolean, override val description: String? = null) : IDescription