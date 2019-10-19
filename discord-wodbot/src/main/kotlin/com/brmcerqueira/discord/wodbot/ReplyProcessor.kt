package com.brmcerqueira.discord.wodbot

import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Flux

abstract class ReplyProcessor<T>(private val botMessage: BotMessage<T>, private val customMatch: (() -> Boolean)? = null) : IProcessor {

    private val regex = getRegex()

    protected abstract fun getRegex(): Regex

    protected abstract fun extractDto(matchResult: MatchResult, channel: MessageChannel, user: Member?): T

    override fun match(event: MessageCreateEvent): Boolean {
        return (customMatch == null || customMatch.invoke()) && event.message.content.map { regex.matches(it) }.orElse(false)
    }

    override fun go(event: MessageCreateEvent): Flux<Unit> {
        val matchResult = regex.matchEntire(event.message.content.get())!!
        return Flux.just(event.message)
                .flatMap { it.channel }
                .flatMap {
                    val user = if (event.member.isPresent) event.member.get() else null
                    botMessage.send(it,
                            extractDto(matchResult, it, user),
                            user?.id,
                            matchResult.getDescription())
                }
    }
}