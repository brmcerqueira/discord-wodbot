package com.brmcerqueira.discord.codbot

import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Flux

abstract class ReplyProcessor<T>(private val botMessage: BotMessage<T>) : IProcessor {

    private val regex = getRegex()

    protected abstract fun getRegex(): Regex

    protected abstract fun extractDto(matchResult: MatchResult, channel: MessageChannel, member: Member?): T

    override fun match(event: MessageCreateEvent): Boolean {
        return event.message.content.map { regex.matches(it) }.orElse(false)
    }

    override fun go(event: MessageCreateEvent): Flux<Unit> {
        val content = event.message.content.get()
        println("Chegou '$content'")
        val matchResult = regex.matchEntire(content)!!
        return Flux.just(event.message)
                .flatMap { it.channel }
                .flatMap {
                    val member = event.member.orElse(null)
                    botMessage.send(it,
                            extractDto(matchResult, it, member),
                            member,
                            if (matchResult.groupValues.contains("description")) matchResult.groups["description"]!!.value else null)
                }
    }
}