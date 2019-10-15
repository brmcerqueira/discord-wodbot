package com.brmcerqueira.discord.wodbot

import discord4j.core.`object`.entity.MessageChannel
import kotlin.random.Random

object Wod {
    var messageChannel: MessageChannel? = null

    var difficulty: Int? = null

    fun randomDice() = Random.nextInt(1,11)
}