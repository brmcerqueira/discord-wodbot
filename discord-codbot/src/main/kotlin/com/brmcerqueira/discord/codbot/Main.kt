package com.brmcerqueira.discord.codbot

import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.DiscordClientBuilder
import reactor.core.publisher.Flux

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val client = DiscordClientBuilder(args.first()).build()

            client.eventDispatcher.on(ReadyEvent::class.java)
                    .subscribe { ready -> println("Logged in as " + ready.self.username) }

            client.eventDispatcher.on(MessageCreateEvent::class.java)
                    .register(InitiativeProcessor(),
                            DicePoolProcessor())
                    .subscribe()

            client.login().block()
        }

        private fun Flux<MessageCreateEvent>.register(vararg processors: IProcessor): Flux<Unit> {
            return this.flatMap { event ->
                val processor = processors.firstOrNull { it.match(event) }
                return@flatMap processor?.go(event) ?: Flux.just(Unit)
            }
        }
    }
}