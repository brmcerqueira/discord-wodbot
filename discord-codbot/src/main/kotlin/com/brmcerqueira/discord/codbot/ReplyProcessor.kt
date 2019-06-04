package com.brmcerqueira.discord.codbot

import discord4j.core.`object`.entity.Message
import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Flux

abstract class ReplyProcessor : IProcessor {

    private val regex = getRegex()

    protected abstract fun getRegex(): Regex

    protected abstract fun buildMessage(matchResult: MatchResult, stringBuffer: StringBuffer)

    override fun match(event: MessageCreateEvent): Boolean {
        return event.message.content.map { regex.matches(it) }.orElse(false)
    }

    override fun go(event: MessageCreateEvent): Flux<Unit> {
        val matchResult = regex.matchEntire(event.message.content.get())!!
        return Flux.just(event.message)
            .flatMap(Message::getChannel)
            .flatMap { channel ->
                val description = matchResult.groups["description"]!!.value

                val stringBuffer = StringBuffer()

                stringBuffer.append(event.member.get().mention)

                if (description.isNotEmpty()) {
                    stringBuffer.appendln(" __***$description***__")
                }
                else {
                    stringBuffer.appendln()
                }

                buildMessage(matchResult, stringBuffer)

                channel.createMessage(stringBuffer.toString())
            }.map { Unit }
    }
}