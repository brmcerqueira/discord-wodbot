package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.ReplyProcessor
import com.brmcerqueira.discord.wodbot.getDescription
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake

class InitiativeProcessor : ReplyProcessor<InitiativeDto>(InitiativeBotMessage()) {
    override fun getRegex(): Regex = "^!(?<amount>[1-9]?\\d)\\s*(%(?<withoutPenalty>\\*)?(?<actions>[2-9]))?(?<description>.*)\$".toRegex()

    override fun extractDto(matchResult: MatchResult, channel: MessageChannel, userId: Snowflake?): InitiativeDto
            = InitiativeDto(userId!!,
            matchResult.groups["amount"]!!.value.toInt(),
            matchResult.groups["withoutPenalty"] != null,
            if (matchResult.groups["actions"] != null)
                matchResult.groups["actions"]!!.value.toInt()
            else null,
            matchResult.getDescription())
}