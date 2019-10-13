package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.ReplyProcessor
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake

class InitiativeProcessor : ReplyProcessor<Int>(InitiativeBotMessage()) {
    override fun getRegex(): Regex = "^\\$(?<initiative>[1-9]?\\d)\\s*(?<description>.*)\$".toRegex()

    override fun extractDto(matchResult: MatchResult, channel: MessageChannel, userId: Snowflake?): Int = matchResult.groups["initiative"]!!.value.toInt()
}