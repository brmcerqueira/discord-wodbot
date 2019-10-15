package com.brmcerqueira.discord.wodbot.initiative

import discord4j.core.`object`.util.Snowflake

data class InitiativeQueueItem(
    val userId: Snowflake,
    val characterId: Int,
    val index: Int,
    val initiative: Int,
    val total: Int,
    val name: String? = null,
    val penalty: Int? = null
)