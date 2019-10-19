package com.brmcerqueira.discord.wodbot.initiative

import com.brmcerqueira.discord.wodbot.PenaltyMode
import com.brmcerqueira.discord.wodbot.ReplyProcessor
import com.brmcerqueira.discord.wodbot.getDescription
import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake

class InitiativeProcessor : ReplyProcessor<InitiativeDto>(InitiativeBotMessage()) {
    override fun getRegex(): Regex = "^!(?<amount>[1-9]?\\d)\\s*(\\*(?<penaltyModeNone>\\*)?(?<penaltyModeDefensive>!)?(?<actions>[1-9]))?\\s*(?<description>.*)\$".toRegex()

    override fun extractDto(matchResult: MatchResult, channel: MessageChannel, user: Member?): InitiativeDto
            = InitiativeDto(matchResult.groups["amount"]!!.value.toInt(),
            PenaltyMode.parse(matchResult),
            if (matchResult.groups["actions"] != null)
                matchResult.groups["actions"]!!.value.toInt()
            else null,
            matchResult.getDescription() ?: user?.displayName)
}