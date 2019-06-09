package com.brmcerqueira.discord.codbot

import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.MessageChannel

class DicePoolProcessor : ReplyProcessor<DicePoolDto>(DicePoolBotMessage()) {
    override fun getRegex(): Regex = "^%(?<isCanceller>!)?(?<dices>[1-9]?\\d)\\s*(\\*(?<explosion>0|8|9))?(?<description>.*)\$".toRegex()

    override fun extractDto(matchResult: MatchResult, channel: MessageChannel, member: Member?): DicePoolDto {
        return DicePoolDto(matchResult.groups["dices"]!!.value.toInt(),
            if (matchResult.groups["explosion"] != null)
                matchResult.groups["explosion"]!!.value.toInt()
            else 10, matchResult.groups["isCanceller"] != null)
    }
}

