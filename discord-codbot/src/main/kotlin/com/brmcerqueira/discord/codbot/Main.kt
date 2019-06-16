package com.brmcerqueira.discord.codbot

import com.fasterxml.jackson.databind.SerializationFeature
import discord4j.core.event.domain.message.MessageCreateEvent
import discord4j.core.event.domain.lifecycle.ReadyEvent
import discord4j.core.DiscordClientBuilder
import discord4j.core.`object`.entity.Channel
import discord4j.core.`object`.entity.Member
import discord4j.core.`object`.entity.MessageChannel
import discord4j.core.`object`.util.Snowflake
import discord4j.core.event.domain.guild.MemberJoinEvent
import io.ktor.application.ApplicationCall
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
import io.ktor.util.pipeline.PipelineContext
import java.math.BigInteger
import kotlin.collections.HashMap

@KtorExperimentalAPI
fun main(args: Array<String>) {
    val client = DiscordClientBuilder(args.first()).build()

    client.eventDispatcher.on(MessageCreateEvent::class.java)
            .register(InitiativeProcessor(),
                    DicePoolProcessor(),
                    CurrentProcessor())
            .subscribe()

    client.login().subscribe()

    messageChannel = client.eventDispatcher.on(ReadyEvent::class.java)
            .flatMap { client.getGuildById(it.guilds.first().id) }
            .flatMap { it.channels }
            .filter { it.type  == Channel.Type.GUILD_TEXT }
            .cast(MessageChannel::class.java).blockFirst()

    val server = embeddedServer(Netty, port = System.getenv("PORT")?.toInt() ?: 4100) {
        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }
        routing {
            get("/") {
                call.respondText("Codbot!", ContentType.Text.Html)
            }
            post("/roll/dices", treatRequest<DicePoolModel> { dto, messageChannel, userId ->
                DicePoolBotMessage().send(messageChannel, DicePoolDto(dto.amount, dto.explosion, dto.isCanceller), userId, dto.description).subscribe()
            })
            post("/roll/initiative", treatRequest<InitiativeModel> { dto, messageChannel, userId ->
                InitiativeBotMessage().send(messageChannel, dto.amount, userId, dto.description).subscribe()
            })
        }
    }

    server.start(wait = true)
}

@KtorExperimentalAPI
private inline fun <reified T : Any> treatRequest(crossinline action: (T, MessageChannel, Snowflake) -> Unit):
        suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit {
    return {
        val authorization = if (call.request.authorization() != null)
            Snowflake.of(call.request.authorization()!!.toBigInteger()) else null
        if (authorization != null && messageChannel != null) {
            action(call.receive(), messageChannel!!, authorization)
            call.respond(HttpStatusCode.OK)
        }

        call.respond(HttpStatusCode.Unauthorized)
    }
}

private fun Flux<MessageCreateEvent>.register(vararg processors: IProcessor): Flux<Unit> {
    return this.flatMap { event ->
        val processor = processors.firstOrNull { it.match(event) }
        return@flatMap processor?.go(event) ?: Flux.just(Unit)
    }
}

fun randomDice() = Random.nextInt(1,11)

var messageChannel: MessageChannel? = null