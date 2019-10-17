package com.brmcerqueira.discord.wodbot.initiative

import discord4j.core.`object`.util.Snowflake

data class InitiativeItem(
    val userId: Snowflake,
    val characterId: Int,
    val index: Int,
    val initiative: Int,
    val total: Int,
    val name: String? = null,
    var penalty: Int? = null
)