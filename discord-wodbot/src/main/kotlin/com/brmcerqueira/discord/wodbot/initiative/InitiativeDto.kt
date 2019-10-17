package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.PenaltyMode

data class InitiativeDto(val amount: Int, val penaltyMode: PenaltyMode, val actions: Int? = null, val name: String? = null)

