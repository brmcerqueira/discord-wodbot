package com.brmcerqueira.discord.codbot

import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.MessageChannel
import reactor.core.publisher.Mono

abstract class BotMessage<T> {

    protected abstract fun buildMessage(dto: T, stringBuffer: StringBuffer)

    fun send(channel: MessageChannel, dto: T, member: Member?, description: String?): Mono<Unit> {
        val stringBuffer = StringBuffer()

        stringBuffer.append(if (member != null) member.mention else "**Você**")

        if (description != null && description.isNotEmpty()) {
            stringBuffer.appendln(" __*$description*__")
        }
        else {
            stringBuffer.appendln()
        }

        buildMessage(dto, stringBuffer)

        return channel.createMessage(stringBuffer.toString()).map { Unit }
    }
}