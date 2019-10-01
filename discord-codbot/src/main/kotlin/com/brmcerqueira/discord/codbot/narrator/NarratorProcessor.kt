package com.brmcerqueira.discord.codbot.narrator

import com.brmcerqueira.discord.codbot.ReplyProcessor
import com.brmcerqueira.discord.codbot.messageChannel
import com.brmcerqueira.discord.codbot.modifier
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake

class NarratorProcessor : ReplyProcessor<NarratorDto>(NarratorBotMessage()) {
    override fun getRegex(): Regex = "^!(?<modifier>-?[1-9]?\\d)?\$".toRegex()

    override fun extractDto(matchResult: MatchResult, channel: MessageChannel, userId: Snowflake?): NarratorDto {
        val modifierValue = matchResult.groups["modifier"]?.value?.toInt()

        if (modifierValue != null) {
            modifier = modifierValue
        } else if (userId != null) {
            messageChannel = channel
        }

        return NarratorDto(modifierValue, userId != null)
    }
}