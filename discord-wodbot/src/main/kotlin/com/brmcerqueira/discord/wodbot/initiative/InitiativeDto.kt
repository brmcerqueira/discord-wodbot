package com.brmcerqueira.discord.wodbot.initiative

import discord4j.core.`object`.util.Snowflake

data class InitiativeDto(val userId: Snowflake, val amount: Int, val withoutPenalty: Boolean, val actions: Int? = null, val name: String? = null)