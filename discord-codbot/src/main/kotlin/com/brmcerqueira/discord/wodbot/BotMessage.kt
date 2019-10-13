package com.brmcerqueira.discord.wodbot

import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake
import reactor.core.publisher.Mono

abstract class BotMessage<T> {

    protected abstract fun buildMessage(dto: T, stringBuffer: StringBuffer)

    fun send(channel: MessageChannel, dto: T, userId: Snowflake?, description: String?): Mono<Unit> {
        val stringBuffer = StringBuffer()

        stringBuffer.append(if (userId != null) "<@${userId.asString()}>" else "**Você**")

        if (description != null && description.isNotEmpty()) {
            stringBuffer.appendln(" ${description.trim()}")
        }
        else {
            stringBuffer.appendln()
        }

        buildMessage(dto, stringBuffer)

        return channel.createMessage(stringBuffer.toString()).map { Unit }
    }
}