package com.brmcerqueira.discord.codbot

import com.fasterxml.jackson.databind.SerializationFeature
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.DiscordClientBuilder
import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.MessageChannel
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import reactor.core.publisher.Flux
import kotlin.random.Random
import io.ktor.jackson.*
import io.ktor.request.authorization
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.post
import io.ktor.util.KtorExperimentalAPI
import java.math.BigInteger
import kotlin.collections.HashMap

@KtorExperimentalAPI
fun main(args: Array<String>) {
    val port = System.getenv("PORT")?.toInt() ?: 4100

    println("Porta: $port")

    val client = DiscordClientBuilder(args.first()).build()

    client.eventDispatcher.on(ReadyEvent::class.java)
            .subscribe { ready -> println("Entrou como ${ready.self.username}.") }

    client.eventDispatcher.on(MessageCreateEvent::class.java)
            .register(InitiativeProcessor(),
                    DicePoolProcessor(),
                    CurrentProcessor())
            .subscribe()

    client.login().subscribe()

    val server = embeddedServer(Netty, port = port) {
        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }
        routing {
            get("/") {
                call.respondText("Codbot!", ContentType.Text.Html)
            }
            post("/roll/dices") {
                val dto = call.receive<DicePoolModel>()

                val authorization = call.request.authorization()?.toBigIntegerOrNull()
                if (authorization != null && usersHashMap.containsKey(authorization)) {
                    val pair = usersHashMap[authorization]!!
                    val botMessage = DicePoolBotMessage()

                    botMessage.send(pair.first, DicePoolDto(dto.amount, dto.explosion, dto.isCanceller), pair.second, dto.description).subscribe()
                    call.respond(HttpStatusCode.OK)
                }

                call.respond(HttpStatusCode.Unauthorized)
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

val usersHashMap = HashMap<BigInteger, Pair<MessageChannel, Member?>>()