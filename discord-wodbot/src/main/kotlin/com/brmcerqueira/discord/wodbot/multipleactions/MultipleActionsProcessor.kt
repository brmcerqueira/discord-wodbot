package com.brmcerqueira.discord.wodbot.multipleactions

import com.brmcerqueira.discord.wodbot.ReplyProcessor
import com.brmcerqueira.discord.wodbot.PenaltyMode
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake

class MultipleActionsProcessor : ReplyProcessor<MultipleActionsDto>(MultipleActionsBotMessage()) {
    override fun getRegex(): Regex = "^\\*(?<penaltyModeNone>\\*)?(?<penaltyModeDefensive>!)?(?<actions>[0-9])\\s*(&(?<characterId>[1-9]?\\d))?\\s*(?<description>.*)\$".toRegex()

    override fun extractDto(matchResult: MatchResult, channel: MessageChannel, userId: Snowflake?): MultipleActionsDto
        = MultipleActionsDto(matchResult.groups["actions"]!!.value.toInt(),
            PenaltyMode.parse(matchResult),
            if (matchResult.groups["characterId"] != null)
                matchResult.groups["characterId"]!!.value.toInt()
            else null)
}