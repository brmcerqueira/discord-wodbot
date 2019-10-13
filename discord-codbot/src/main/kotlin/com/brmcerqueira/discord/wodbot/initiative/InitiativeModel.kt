package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.IDescription

data class InitiativeModel(val amount: Int, override val description: String? = null) : IDescription