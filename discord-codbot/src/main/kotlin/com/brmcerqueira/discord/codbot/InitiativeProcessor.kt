package com.brmcerqueira.discord.codbot

import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.MessageChannel

class InitiativeProcessor : ReplyProcessor<Int>(InitiativeBotMessage()) {
    override fun getRegex(): Regex = "^\\$(?<initiative>[1-9]?\\d)\\s*(?<description>.*)\$".toRegex()

    override fun extractDto(matchResult: MatchResult, channel: MessageChannel, member: Member?): Int = matchResult.groups["initiative"]!!.value.toInt()
}