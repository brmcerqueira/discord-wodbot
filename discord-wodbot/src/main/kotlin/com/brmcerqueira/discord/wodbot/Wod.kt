package com.brmcerqueira.discord.wodbot

import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake
import kotlin.random.Random

object Wod {
    val narratorId = Snowflake.of(System.getenv("NARRATOR_ID"))

    var messageChannel: MessageChannel? = null

    var difficulty: Int? = null

    fun randomDice() = Random.nextInt(1,11)
}