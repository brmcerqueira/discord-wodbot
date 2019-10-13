package com.brmcerqueira.discord.wodbot

import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Flux

interface IProcessor {
    fun match(event: MessageCreateEvent): Boolean

    fun go(event: MessageCreateEvent): Flux<Unit>
}