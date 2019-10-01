package com.brmcerqueira.discord.codbot.wod

import com.brmcerqueira.discord.codbot.ReplyProcessor
import com.brmcerqueira.discord.codbot.isCod
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake

class WodDicePoolProcessor : ReplyProcessor<WodDicePoolDto>(WodDicePoolBotMessage(), { !isCod }) {
    override fun getRegex(): Regex = "^%(?<isSpecialization>\\?)?(?<isCanceller>!)?(?<dices>[1-9]?\\d)\\s*(\\*(?<difficulty>[1-9]?\\d))?(?<description>.*)\$".toRegex()

    override fun extractDto(matchResult: MatchResult, channel: MessageChannel, userId: Snowflake?): WodDicePoolDto {
        return WodDicePoolDto(matchResult.groups["dices"]!!.value.toInt(),
                if (matchResult.groups["difficulty"] != null)
                    matchResult.groups["difficulty"]!!.value.toInt()
                else 6, matchResult.groups["isCanceller"] == null,
                matchResult.groups["isSpecialization"] != null)
    }
}