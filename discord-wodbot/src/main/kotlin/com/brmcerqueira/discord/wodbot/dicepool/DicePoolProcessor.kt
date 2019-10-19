package com.brmcerqueira.discord.wodbot.dicepool

import com.brmcerqueira.discord.wodbot.ReplyProcessor
import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake

class DicePoolProcessor : ReplyProcessor<DicePoolDto>(DicePoolBotMessage()) {
    override fun getRegex(): Regex = "^%(?<isSpecialization>\\?)?(?<isCanceller>!)?(?<dices>[1-9]?\\d)\\s*(\\*(?<difficulty>[1-9]?\\d))?\\s*(?<description>.*)\$".toRegex()

    override fun extractDto(matchResult: MatchResult, channel: MessageChannel, user: Member?): DicePoolDto {
        return DicePoolDto(matchResult.groups["dices"]!!.value.toInt(),
                if (matchResult.groups["difficulty"] != null)
                    matchResult.groups["difficulty"]!!.value.toInt()
                else 6, matchResult.groups["isCanceller"] == null,
                matchResult.groups["isSpecialization"] != null)
    }
}