package com.brmcerqueira.discord.wodbot.initiative

data class InitiativeDto(val amount: Int, val withoutPenalty: Boolean, val actions: Int? = null)