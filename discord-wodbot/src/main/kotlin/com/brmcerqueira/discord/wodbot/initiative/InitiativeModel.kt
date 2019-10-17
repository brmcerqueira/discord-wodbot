package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.IDescription
import com.brmcerqueira.discord.wodbot.PenaltyMode

data class InitiativeModel(val amount: Int, val penaltyMode: PenaltyMode = PenaltyMode.None, val actions: Int? = null, override val description: String? = null) : IDescription

