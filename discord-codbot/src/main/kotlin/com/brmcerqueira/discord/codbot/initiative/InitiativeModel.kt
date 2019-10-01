package com.brmcerqueira.discord.codbot.initiative

import com.brmcerqueira.discord.codbot.IDescription

data class InitiativeModel(val amount: Int, override val description: String? = null) : IDescription