package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.IDescription

data class InitiativeModel(val amount: Int, val withoutPenalty: Boolean = false, val actions: Int? = null, override val description: String? = null) : IDescription

