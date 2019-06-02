package com.brmcerqueira.discord.codbot

import discord4j.core.`object`.entity.Message
import discord4j.core.event.domain.message.MessageCreateEvent
import reactor.core.publisher.Flux

class DicePoolProcessor : IProcessor {
    override fun match(event: MessageCreateEvent): Boolean {
        return event.message.content.map{ "!ping" == it }.orElse(false)
    }

    override fun go(event: MessageCreateEvent): Flux<Unit> {
        return Flux.just(event.message)
            .flatMap(Message::getChannel)
            .flatMap {
                it.createMessage(DicePool(10).dices.toString())
            }
            .map { Unit }
    }
}