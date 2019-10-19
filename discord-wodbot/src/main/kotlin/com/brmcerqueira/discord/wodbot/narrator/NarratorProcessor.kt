package com.brmcerqueira.discord.wodbot.narrator

import com.brmcerqueira.discord.wodbot.ReplyProcessor
import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake

class NarratorProcessor : ReplyProcessor<Pair<MatchResult, MessageChannel>>(NarratorBotMessage()) {
    override fun getRegex(): Regex = "^\\$(?<command>\\w*)(?<arguments>.*)\$".toRegex()

    override fun extractDto(matchResult: MatchResult, channel: MessageChannel, user: Member?): Pair<MatchResult, MessageChannel>
            = Pair(matchResult, channel)
}

