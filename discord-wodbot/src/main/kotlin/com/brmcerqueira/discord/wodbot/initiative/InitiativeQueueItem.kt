package com.brmcerqueira.discord.wodbot.initiative

import discord4j.core.`object`.util.Snowflake

data class InitiativeQueueItem(
        val userId: Snowflake,
        val initiative: Int,
        val total: Int,
        val name: String? = null,
        val index: Int = 1,
        val penalty: Int? = null)