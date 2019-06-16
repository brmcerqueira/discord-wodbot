package com.brmcerqueira.discord.codbot

import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake

class CurrentProcessor : ReplyProcessor<Boolean>(CurrentBotMessage()) {
    override fun getRegex(): Regex = "^!\$".toRegex()

    override fun extractDto(matchResult: MatchResult, channel: MessageChannel, userId: Snowflake?) : Boolean {
        if (userId != null) {
            messageChannel = channel
            return  true
        }
        return  false
    }
}