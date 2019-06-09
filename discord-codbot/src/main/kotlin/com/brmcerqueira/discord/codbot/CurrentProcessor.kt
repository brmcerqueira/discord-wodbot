package com.brmcerqueira.discord.codbot

import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.MessageChannel

class CurrentProcessor : ReplyProcessor<Boolean>(CurrentBotMessage()) {
    override fun getRegex(): Regex = "^!\$".toRegex()

    override fun extractDto(matchResult: MatchResult, channel: MessageChannel, member: Member?) : Boolean {
        if (member != null) {
            usersHashMap[member.id.asBigInteger()] = Pair(channel, member)
            return  true
        }
        return  false
    }
}