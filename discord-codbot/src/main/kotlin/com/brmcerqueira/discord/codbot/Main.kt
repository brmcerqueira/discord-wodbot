package com.brmcerqueira.discord.codbot

import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.DiscordClientBuilder
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import reactor.core.publisher.Flux
import kotlin.random.Random

fun main(args: Array<String>) {
    val port = System.getenv("PORT")?.toInt() ?: 4100

    println("Porta: $port")

    val client = DiscordClientBuilder(args.first()).build()

    client.eventDispatcher.on(ReadyEvent::class.java)
            .subscribe { ready -> println("Entrou como ${ready.self.username}.") }

    client.eventDispatcher.on(MessageCreateEvent::class.java)
            .register(InitiativeProcessor(),
                    DicePoolProcessor())
            .subscribe()

    client.login().subscribe()

    val server = embeddedServer(Netty, port = port) {
        routing {
            get("/") {
                call.respondText("Hello, world!", ContentType.Text.Html)
            }
        }
    }

    server.start(wait = true)
}

private fun Flux<MessageCreateEvent>.register(vararg processors: IProcessor): Flux<Unit> {
    return this.flatMap { event ->
        val processor = processors.firstOrNull { it.match(event) }
        return@flatMap processor?.go(event) ?: Flux.just(Unit)
    }
}

fun randomDice() = Random.nextInt(1,11)