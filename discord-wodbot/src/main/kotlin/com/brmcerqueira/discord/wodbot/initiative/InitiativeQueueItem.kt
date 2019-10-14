package com.brmcerqueira.discord.wodbot.initiative

import discord4j.core.`object`.util.Snowflake

data class InitiativeQueueItem(val userId: Snowflake, val amount: Int, val name: String? = null)